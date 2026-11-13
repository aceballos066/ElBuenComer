package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.ProductoController;
import co.edu.uniquindio.poo.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

/**
 * ViewController para registrar y consultar productos del menú.
 */
public class ProductoViewController {

    private App app;
    private ProductoController productoController;

    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecioBase;
    @FXML
    private TextField txtTiempoPreparacion;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtVolumen;
    @FXML
    private TextField txtTemperatura;
    @FXML
    private ComboBox<TipoPreparacion> cmbTipoPreparacion;
    @FXML
    private CheckBox chkCoccionAprobada;
    @FXML
    private CheckBox chkEsFria;
    @FXML
    private CheckBox chkPreparacionEspecial;
    @FXML
    private CheckBox chkRefrigeracion;
    @FXML
    private ListView<String> listProductos;

    @FXML
    void initialize() {
        productoController = new ProductoController(App.restaurante);
        cmbCategoria.getItems().addAll("PLATO_FUERTE", "BEBIDA", "POSTRE");
        cmbTipoPreparacion.getItems().addAll(TipoPreparacion.values());
        cmbCategoria.getSelectionModel().selectFirst();
        cmbTipoPreparacion.getSelectionModel().selectFirst();
        actualizarLista(productoController.obtenerProductos());
    }

    @FXML
    void onRegistrarProducto() {
        try {
            Producto producto = construirProducto();

            if (productoController.registrarProducto(producto)) {
                mostrarMensaje(
                        "Producto registrado correctamente.",
                        Alert.AlertType.INFORMATION
                );

                limpiarCampos();
                actualizarLista(productoController.obtenerProductos());
            } else {
                mostrarMensaje(
                        "No se pudo registrar. Verifique el código del producto.",
                        Alert.AlertType.WARNING
                );
            }

        } catch (NumberFormatException e) {
            mostrarMensaje(
                    "Revise los campos numéricos.",
                    Alert.AlertType.ERROR
            );

        } catch (IllegalArgumentException e) {
            mostrarMensaje(
                    e.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    void onMostrarMenu() {
        actualizarLista(productoController.obtenerProductos());
    }

    @FXML
    void onMostrarSinCalidad() {
        actualizarLista(productoController.obtenerProductosSinCalidad());
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private Producto construirProducto() {
        String categoria = cmbCategoria.getValue();
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        double precioBase = Double.parseDouble(
                txtPrecioBase.getText().trim().replace(',', '.')
        );

        int tiempo = Integer.parseInt(
                txtTiempoPreparacion.getText().trim()
        );

        if (codigo.isBlank() || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "Código y nombre son obligatorios."
            );
        }

        if ("PLATO_FUERTE".equals(categoria)) {
            double peso = Double.parseDouble(
                    txtPeso.getText().trim().replace(',', '.')
            );

            return new PlatoFuerte(
                    codigo,
                    nombre,
                    descripcion,
                    precioBase,
                    tiempo,
                    peso,
                    cmbTipoPreparacion.getValue(),
                    chkCoccionAprobada.isSelected()
            );
        }

        if ("BEBIDA".equals(categoria)) {
            int volumen = Integer.parseInt(
                    txtVolumen.getText().trim()
            );

            double temperatura = Double.parseDouble(
                    txtTemperatura.getText().trim().replace(',', '.')
            );

            return new Bebida(
                    codigo,
                    nombre,
                    descripcion,
                    precioBase,
                    tiempo,
                    volumen,
                    temperatura,
                    chkEsFria.isSelected(),
                    chkPreparacionEspecial.isSelected()
            );
        }

        double peso = Double.parseDouble(
                txtPeso.getText().trim().replace(',', '.')
        );

        double temperatura = Double.parseDouble(
                txtTemperatura.getText().trim().replace(',', '.')
        );

        return new Postre(
                codigo,
                nombre,
                descripcion,
                precioBase,
                tiempo,
                peso,
                temperatura,
                chkRefrigeracion.isSelected()
        );
    }

    private void actualizarLista(List<Producto> productos) {
        listProductos.getItems().clear();

        for (Producto producto : productos) {
            listProductos.getItems().add(
                    producto.getCodigo()
                            + " | " + producto.getNombre()
                            + " | $" + String.format(
                            "%.0f",
                            producto.calcularPrecioVenta()
                    )
                            + " | Calidad: "
                            + (
                            producto.cumpleCondicionesCalidad()
                                    ? "OK"
                                    : "NO CUMPLE"
                    )
            );
        }
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecioBase.clear();
        txtTiempoPreparacion.clear();
        txtPeso.clear();
        txtVolumen.clear();
        txtTemperatura.clear();

        chkCoccionAprobada.setSelected(false);
        chkEsFria.setSelected(false);
        chkPreparacionEspecial.setSelected(false);
        chkRefrigeracion.setSelected(false);
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
