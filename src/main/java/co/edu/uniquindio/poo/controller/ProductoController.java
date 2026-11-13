package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Producto;
import co.edu.uniquindio.poo.model.Restaurante;

import java.util.List;

public class ProductoController {

    private final Restaurante restaurante;

    public ProductoController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public boolean registrarProducto(Producto producto) {
        return restaurante.registrarProducto(producto);
    }

    public Producto buscarProducto(String codigo) {
        return restaurante.buscarProducto(codigo);
    }

    public List<Producto> obtenerProductos() {
        return restaurante.consultarProductos();
    }

    public List<Producto> obtenerProductosSinCalidad() {
        return restaurante.productosNoCumplenCalidad();
    }
}
