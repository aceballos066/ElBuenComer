package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Pedido;
import co.edu.uniquindio.poo.model.Restaurante;

import java.util.List;

public class ClienteController {

    private final Restaurante restaurante;

    public ClienteController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public boolean registrarCliente(Cliente cliente) {
        return restaurante.registrarCliente(cliente);
    }

    public Cliente buscarCliente(String documento) {
        return restaurante.buscarCliente(documento);
    }

    public List<Cliente> obtenerClientes() {
        return restaurante.getListRestauranteClientes();
    }

    public List<Pedido> obtenerHistorial(String documento) {
        Cliente cliente = buscarCliente(documento);

        if (cliente == null) {
            return List.of();
        }

        return cliente.obtenerHistorialPedidos();
    }
}
