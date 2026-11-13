package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente() {
        return new Cliente("123", "Anderson", "300", "a@b.com", "Armenia");
    }

    @Test
    void historialDebeIniciarVacio() {
        assertTrue(cliente().obtenerHistorialPedidos().isEmpty());
    }

    @Test
    void agregarPedidoDebeGuardarloEnHistorial() {
        Cliente cliente = cliente();
        Pedido pedido = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        cliente.agregarPedido(pedido);
        assertEquals(1, cliente.obtenerHistorialPedidos().size());
    }

    @Test
    void agregarPedidoNoDebeDuplicarMismoCodigo() {
        Cliente cliente = cliente();
        Pedido pedido1 = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        Pedido pedido2 = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        cliente.agregarPedido(pedido1);
        cliente.agregarPedido(pedido2);
        assertEquals(1, cliente.obtenerHistorialPedidos().size());
    }

    @Test
    void historialDevueltoDebeSerUnaCopia() {
        Cliente cliente = cliente();
        Pedido pedido = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        cliente.agregarPedido(pedido);
        List<Pedido> copia = cliente.obtenerHistorialPedidos();
        copia.clear();
        assertEquals(1, cliente.obtenerHistorialPedidos().size());
    }

    @Test
    void gettersDebenRetornarDatosDelCliente() {
        Cliente cliente = cliente();
        assertEquals("123", cliente.getDocumento());
        assertEquals("Cristian", cliente.getNombreCompleto());
        assertEquals("300", cliente.getTelefono());
        assertEquals("a@b.com", cliente.getCorreo());
        assertEquals("Armenia", cliente.getDireccionResidencia());
    }
}
