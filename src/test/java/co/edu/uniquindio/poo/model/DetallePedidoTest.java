package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    private Producto producto() {
        return new PlatoFuerte(
                "P1", "Plato", "", 10000, 30,
                500, TipoPreparacion.ASADO, true
        );
    }

    @Test
    void debeConservarProductoYCantidad() {
        Producto producto = producto();

        DetallePedido detalle =
                new DetallePedido(producto, 2);

        assertSame(producto, detalle.producto());
        assertEquals(2, detalle.cantidad());
    }

    @Test
    void debeCalcularSubtotalParaUnaUnidad() {
        DetallePedido detalle =
                new DetallePedido(producto(), 1);

        assertEquals(
                12000,
                detalle.calcularSubtotal(),
                0.001
        );
    }

    @Test
    void debeCalcularSubtotalMultiplicandoPorCantidad() {
        DetallePedido detalle =
                new DetallePedido(producto(), 3);

        assertEquals(
                36000,
                detalle.calcularSubtotal(),
                0.001
        );
    }

    @Test
    void debeRechazarCantidadCero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DetallePedido(producto(), 0)
        );
    }

    @Test
    void debeRechazarProductoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DetallePedido(null, 1)
        );
    }
}