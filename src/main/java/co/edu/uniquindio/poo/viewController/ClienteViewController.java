package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.ClienteController;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Pedido;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * ViewController para el registro de clientes y consulta de historial.
 */
public class ClienteViewController {

    private App app;
    private ClienteController clienteController;

    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtNombreCompleto;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private ListView<String> listClientes;
    @FXML
    private TextArea txtHistorial;

    @FXML
    void initialize() {
        clienteController = new ClienteController(App.restaurante);
        actualizarClientes();
    }

    @FXML
    void onRegistrarCliente() {
        Cliente cliente = new Cliente(
                txtDocumento.getText().trim(),
                txtNombreCompleto.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtDireccion.getText().trim()
        );

        if (clienteController.registrarCliente(cliente)) {
            mostrarMensaje("Cliente registrado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            actualizarClientes();
        } else {
            mostrarMensaje("No se pudo registrar. Verifique el documento.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onConsultarHistorial() {
        String documento = txtDocumento.getText().trim();
        Cliente cliente = clienteController.buscarCliente(documento);

        if (cliente == null) {
            mostrarMensaje("Cliente no encontrado.", Alert.AlertType.WARNING);
            return;
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Cliente: ").append(cliente.getNombreCompleto()).append('\n');
        resultado.append("Documento: ").append(cliente.getDocumento()).append("\n\n");

        for (Pedido pedido : clienteController.obtenerHistorial(documento)) {
            resultado.append("Pedido: ").append(pedido.getCodigo())
                    .append(" | Fecha: ").append(pedido.getFecha())
                    .append(" | Estado: ").append(pedido.getEstado())
                    .append(" | Total: $").append(String.format("%.0f", pedido.calcularTotal()))
                    .append('\n');
        }

        if (clienteController.obtenerHistorial(documento).isEmpty()) {
            resultado.append("Sin pedidos confirmados en el historial.");
        }

        txtHistorial.setText(resultado.toString());
    }

    @FXML
    void onLimpiar() {
        limpiarCampos();
        txtHistorial.clear();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private void actualizarClientes() {
        listClientes.getItems().clear();

        for (Cliente cliente : clienteController.obtenerClientes()) {
            listClientes.getItems().add(
                    cliente.getDocumento() + " | " + cliente.getNombreCompleto()
            );
        }
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombreCompleto.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
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
