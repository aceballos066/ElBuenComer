package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.EntregaSegura;
import co.edu.uniquindio.poo.model.EstadoPedido;
import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.Producto;
import co.edu.uniquindio.poo.model.RapidoExpress;
import co.edu.uniquindio.poo.model.Restaurante;
import co.edu.uniquindio.poo.model.ServicioDomicilio;

import java.util.List;

public class PedidoController {

    private final Restaurante restaurante;

    public PedidoController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Cliente buscarCliente(String documento) {
        return restaurante.buscarCliente(documento);
    }

    public Producto buscarProducto(String codigo) {
        return restaurante.buscarProducto(codigo);
    }

    public boolean registrarPedido(Pedido pedido) {
        return restaurante.registrarPedido(pedido);
    }

    public Pedido buscarPedido(String codigo) {
        return restaurante.buscarPedido(codigo);
    }

    public boolean confirmarPedido(String codigo) {
        return restaurante.confirmarPedido(codigo);
    }

    public boolean actualizarEstadoPedido(String codigo, EstadoPedido estado) {
        return restaurante.actualizarEstadoPedido(codigo, estado);
    }

    public List<Pedido> obtenerPedidos() {
        return restaurante.getListRestaurantePedidos();
    }

    public ServicioDomicilio crearServicioDomicilio(String nombre) {
        if ("RAPIDO_EXPRESS".equals(nombre)) {
            return new RapidoExpress();
        }

        if ("ENTREGA_SEGURA".equals(nombre)) {
            return new EntregaSegura();
        }

        return null;
    }
}
