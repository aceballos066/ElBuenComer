package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.PedidoController;
import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

/**
 * ViewController para registrar, confirmar, consultar y actualizar pedidos.
 */
public class PedidoViewController {

    private App app;
    private PedidoController pedidoController;
    private Pedido pedidoTemporal;

    @FXML
    private TextField txtCodigoPedido;
    @FXML
    private TextField txtDocumentoCliente;
    @FXML
    private ComboBox<ModalidadEntrega> cmbModalidad;
    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> cmbServicio;
    @FXML
    private TextField txtDistancia;
    @FXML
    private ComboBox<EstadoPedido> cmbEstado;
    @FXML
    private ListView<String> listDetalles;
    @FXML
    private ListView<String> listPedidos;
    @FXML
    private TextArea txtConsultaPedido;

    @FXML
    void initialize() {
        pedidoController = new PedidoController(App.restaurante);
        cmbModalidad.getItems().addAll(ModalidadEntrega.values());
        cmbModalidad.getSelectionModel().select(ModalidadEntrega.RESTAURANTE);
        cmbServicio.getItems().addAll("RAPIDO_EXPRESS", "ENTREGA_SEGURA");
        cmbServicio.getSelectionModel().selectFirst();
        cmbEstado.getItems().addAll(
                EstadoPedido.CONFIRMADO,
                EstadoPedido.ENTREGADO,
                EstadoPedido.CANCELADO
        );
        cmbEstado.getSelectionModel().selectFirst();
        actualizarPedidos();
    }

    @FXML
    void onCrearPedidoTemporal() {
        Cliente cliente = pedidoController.buscarCliente(
                txtDocumentoCliente.getText().trim()
        );

        if (cliente == null) {
            mostrarMensaje(
                    "El cliente no está registrado.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        String codigo = txtCodigoPedido.getText().trim();

        if (codigo.isBlank()) {
            mostrarMensaje(
                    "Ingrese el código del pedido.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        pedidoTemporal = new Pedido(
                codigo,
                LocalDate.now(),
                cliente,
                cmbModalidad.getValue()
        );

        listDetalles.getItems().clear();

        mostrarMensaje(
                "Pedido temporal creado. Ahora agregue los productos.",
                Alert.AlertType.INFORMATION
        );
    }

    @FXML
    void onAgregarProducto() {
        if (pedidoTemporal == null) {
            mostrarMensaje(
                    "Primero cree el pedido temporal.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Producto producto = pedidoController.buscarProducto(
                txtCodigoProducto.getText().trim()
        );

        if (producto == null) {
            mostrarMensaje(
                    "El producto no pertenece al menú.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            int cantidad = Integer.parseInt(
                    txtCantidad.getText().trim()
            );

            if (pedidoTemporal.agregarDetalle(producto, cantidad)) {
                actualizarDetalles();
                txtCodigoProducto.clear();
                txtCantidad.clear();
            } else {
                mostrarMensaje(
                        "No fue posible agregar el producto.",
                        Alert.AlertType.WARNING
                );
            }

        } catch (NumberFormatException e) {
            mostrarMensaje(
                    "La cantidad debe ser un número entero.",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    void onRegistrarPedido() {
        if (pedidoTemporal == null) {
            mostrarMensaje(
                    "Primero cree el pedido temporal.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (pedidoTemporal.getModalidadEntrega()
                == ModalidadEntrega.DOMICILIO) {

            try {
                double distancia = Double.parseDouble(
                        txtDistancia.getText()
                                .trim()
                                .replace(',', '.')
                );

                ServicioDomicilio servicio =
                        pedidoController.crearServicioDomicilio(
                                cmbServicio.getValue()
                        );

                if (!pedidoTemporal.asignarServicioDomicilio(
                        servicio,
                        distancia)) {

                    mostrarMensaje(
                            "La empresa seleccionada no acepta ese recorrido.",
                            Alert.AlertType.WARNING
                    );
                    return;
                }

            } catch (NumberFormatException e) {
                mostrarMensaje(
                        "Ingrese una distancia válida.",
                        Alert.AlertType.ERROR
                );
                return;
            }
        }

        if (pedidoController.registrarPedido(pedidoTemporal)) {

            mostrarMensaje(
                    "Pedido registrado en estado PENDIENTE.",
                    Alert.AlertType.INFORMATION
            );

            pedidoTemporal = null;
            listDetalles.getItems().clear();
            actualizarPedidos();

        } else {
            mostrarMensaje(
                    "El pedido no cumple las condiciones para registrarse.",
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    void onConfirmarPedido() {
        String codigo = txtCodigoPedido.getText().trim();

        if (pedidoController.confirmarPedido(codigo)) {

            mostrarMensaje(
                    "Pedido confirmado.",
                    Alert.AlertType.INFORMATION
            );

            actualizarPedidos();
            onConsultarPedido();

        } else {
            mostrarMensaje(
                    "No fue posible confirmar el pedido.",
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    void onActualizarEstado() {
        String codigo = txtCodigoPedido.getText().trim();
        EstadoPedido estado = cmbEstado.getValue();

        if (pedidoController.actualizarEstadoPedido(
                codigo,
                estado)) {

            mostrarMensaje(
                    "Estado actualizado.",
                    Alert.AlertType.INFORMATION
            );

            actualizarPedidos();
            onConsultarPedido();

        } else {
            mostrarMensaje(
                    "Transición de estado no permitida.",
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    void onConsultarPedido() {
        Pedido pedido = pedidoController.buscarPedido(
                txtCodigoPedido.getText().trim()
        );

        if (pedido == null) {
            txtConsultaPedido.setText(
                    "Pedido no encontrado."
            );
            return;
        }

        StringBuilder resultado = new StringBuilder();

        resultado.append("Código: ")
                .append(pedido.getCodigo())
                .append('\n');

        resultado.append("Fecha: ")
                .append(pedido.getFecha())
                .append('\n');

        resultado.append("Cliente: ")
                .append(
                        pedido.getCliente()
                                .getNombreCompleto()
                )
                .append('\n');

        resultado.append("Modalidad: ")
                .append(pedido.getModalidadEntrega())
                .append('\n');

        resultado.append("Estado: ")
                .append(pedido.getEstado())
                .append('\n');

        resultado.append("Subtotal productos: $")
                .append(
                        String.format(
                                "%.0f",
                                pedido.calcularSubtotalProductos()
                        )
                )
                .append('\n');

        resultado.append("Domicilio: $")
                .append(
                        String.format(
                                "%.0f",
                                pedido.calcularCostoDomicilio()
                        )
                )
                .append('\n');

        resultado.append("TOTAL: $")
                .append(
                        String.format(
                                "%.0f",
                                pedido.calcularTotal()
                        )
                )
                .append('\n');

        txtConsultaPedido.setText(
                resultado.toString()
        );
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private void actualizarDetalles() {
        listDetalles.getItems().clear();

        for (DetallePedido detalle
                : pedidoTemporal.getListPedidoDetalles()) {

            listDetalles.getItems().add(
                    detalle.producto().getCodigo()
                            + " | "
                            + detalle.producto().getNombre()
                            + " | Cantidad: "
                            + detalle.cantidad()
                            + " | Subtotal: $"
                            + String.format(
                            "%.0f",
                            detalle.calcularSubtotal()
                    )
            );
        }
    }

    private void actualizarPedidos() {
        listPedidos.getItems().clear();

        for (Pedido pedido
                : pedidoController.obtenerPedidos()) {

            listPedidos.getItems().add(
                    pedido.getCodigo()
                            + " | "
                            + pedido.getCliente().getNombreCompleto()
                            + " | "
                            + pedido.getEstado()
                            + " | $"
                            + String.format(
                            "%.0f",
                            pedido.calcularTotal()
                    )
            );
        }
    }

    private void mostrarMensaje(
            String mensaje,
            Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle("El Buen Comer");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}