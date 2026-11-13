package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Cliente cliente() {
        return new Cliente(
                "123",
                "Cliente",
                "300",
                "c@c.com",
                "Armenia"
        );
    }

    private Producto productoValido() {
        return new PlatoFuerte(
                "P1", "Plato", "", 10000, 30,
                500, TipoPreparacion.ASADO, true
        );
    }

    @Test
    void pedidoDebeIniciarPendiente() {
        Pedido pedido = new Pedido(
                "PE1",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.RESTAURANTE
        );

        assertEquals(
                EstadoPedido.PENDIENTE,
                pedido.getEstado()
        );
    }

    @Test
    void agregarDetalleDebeExigirCantidadPositiva() {
        Pedido pedido = new Pedido(
                "PE1",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.RESTAURANTE
        );

        assertFalse(
                pedido.agregarDetalle(productoValido(), 0)
        );

        assertTrue(
                pedido.agregarDetalle(productoValido(), 2)
        );

        assertEquals(
                1,
                pedido.getListPedidoDetalles().size()
        );
    }

    @Test
    void calcularTotalDebeMultiplicarCantidades() {
        Pedido pedido = new Pedido(
                "PE1",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.RESTAURANTE
        );

        pedido.agregarDetalle(productoValido(), 2);

        assertEquals(
                24000,
                pedido.calcularSubtotalProductos(),
                0.001
        );

        assertEquals(
                24000,
                pedido.calcularTotal(),
                0.001
        );
    }

    @Test
    void domicilioDebeSumarseAlTotalCuandoElRecorridoEsAceptado() {
        Pedido pedido = new Pedido(
                "PE1",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.DOMICILIO
        );

        pedido.agregarDetalle(productoValido(), 1);

        assertTrue(
                pedido.asignarServicioDomicilio(
                        new RapidoExpress(),
                        2
                )
        );

        assertEquals(
                8000,
                pedido.calcularCostoDomicilio(),
                0.001
        );

        assertEquals(
                20000,
                pedido.calcularTotal(),
                0.001
        );
    }

    @Test
    void estadosDebenRespetarConfirmacionEntregaYCancelacion() {
        Pedido pedido = new Pedido(
                "PE1",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.RESTAURANTE
        );

        pedido.agregarDetalle(productoValido(), 1);

        assertTrue(pedido.confirmar());
        assertFalse(pedido.confirmar());
        assertTrue(pedido.marcarEntregado());
        assertFalse(pedido.cancelar());

        Pedido cancelado = new Pedido(
                "PE2",
                LocalDate.now(),
                cliente(),
                ModalidadEntrega.RESTAURANTE
        );

        cancelado.agregarDetalle(productoValido(), 1);

        assertTrue(cancelado.cancelar());
        assertFalse(cancelado.confirmar());
        assertFalse(cancelado.marcarEntregado());
    }
}