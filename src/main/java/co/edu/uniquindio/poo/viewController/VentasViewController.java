package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.VentaController;
import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * ViewController para las consultas de ventas.
 */
public class VentasViewController {

    private App app;
    private VentaController ventaController;

    @FXML
    private TextArea txtResultados;

    @FXML
    void initialize() {
        ventaController = new VentaController(App.restaurante);
        actualizarResultados();
    }

    @FXML
    void onActualizar() {
        actualizarResultados();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    private void actualizarResultados() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("VENTAS CONFIRMADAS O ENTREGADAS\n");
        resultado.append("================================\n");

        for (Pedido pedido : ventaController.consultarVentas()) {
            resultado.append(pedido.getCodigo())
                    .append(" | ")
                    .append(pedido.getCliente().getNombreCompleto())
                    .append(" | ")
                    .append(pedido.getEstado())
                    .append(" | Productos: $")
                    .append(String.format("%.0f", pedido.calcularSubtotalProductos()))
                    .append(" | Domicilio: $")
                    .append(String.format("%.0f", pedido.calcularCostoDomicilio()))
                    .append(" | Total: $")
                    .append(String.format("%.0f", pedido.calcularTotal()))
                    .append('\n');
        }

        resultado.append("\nIngresos propios del restaurante: $")
                .append(String.format("%.0f", ventaController.calcularIngresosRestaurante()))
                .append('\n');

        resultado.append("Valores de domicilios: $")
                .append(String.format("%.0f", ventaController.calcularIngresosDomicilios()))
                .append('\n');

        Producto masVendido = ventaController.productoMasVendidoPorUnidades();

        resultado.append("Producto con más unidades vendidas: ")
                .append(masVendido == null ? "Sin ventas" : masVendido.getNombre())
                .append('\n');

        String categoria = ventaController.categoriaMayorIngreso();

        resultado.append("Categoría con mayores ingresos: ")
                .append(categoria.isBlank() ? "Sin ventas" : categoria)
                .append('\n');

        txtResultados.setText(resultado.toString());
    }

    public void setApp(App app) {
        this.app = app;
    }
}