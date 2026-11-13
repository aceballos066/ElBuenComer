package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.Producto;
import co.edu.uniquindio.poo.model.Restaurante;

import java.util.List;

/**
 * Controlador para las consultas de ventas del restaurante.
 */
public class VentaController {

    private final Restaurante restaurante;

    public VentaController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public List<Pedido> consultarVentas() {
        return restaurante.consultarVentas();
    }

    public double calcularIngresosRestaurante() {
        return restaurante.calcularIngresosRestaurante();
    }

    public double calcularIngresosDomicilios() {
        return restaurante.calcularIngresosDomicilios();
    }

    public Producto productoMasVendidoPorUnidades() {
        return restaurante.productoMasVendidoPorUnidades();
    }

    public String categoriaMayorIngreso() {
        return restaurante.categoriaMayorIngreso();
    }
}
