package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    private Restaurante restauranteBase() {
        Restaurante restaurante = new Restaurante("El Buen Comer", "900", "Armenia");
        restaurante.agregarServicioDomicilio(new RapidoExpress());
        restaurante.agregarServicioDomicilio(new EntregaSegura());
        return restaurante;
    }

    private Cliente cliente(String documento) {
        return new Cliente(documento, "Cliente " + documento, "300", documento + "@mail.com", "Armenia");
    }

    private Producto plato(String codigo, double precioBase) {
        return new PlatoFuerte(codigo, "Plato " + codigo, "", precioBase, 30,
                500, TipoPreparacion.ASADO, true);
    }

    @Test
    void noDebeRegistrarProductosNiClientesDuplicados() {
        Restaurante restaurante = restauranteBase();
        assertTrue(restaurante.registrarProducto(plato("P1", 10000)));
        assertFalse(restaurante.registrarProducto(plato("P1", 12000)));

        assertTrue(restaurante.registrarCliente(cliente("1")));
        assertFalse(restaurante.registrarCliente(cliente("1")));
    }

    @Test
    void debeConsultarMenuYDetectarProductosSinCalidad() {
        Restaurante restaurante = restauranteBase();
        Producto valido = plato("P1", 10000);
        Producto invalido = new PlatoFuerte("P2", "Malo", "", 10000, 30,
                100, TipoPreparacion.ASADO, true);
        restaurante.registrarProducto(valido);
        restaurante.registrarProducto(invalido);

        assertEquals(2, restaurante.consultarProductos().size());
        assertEquals(1, restaurante.productosNoCumplenCalidad().size());
        assertSame(invalido, restaurante.productosNoCumplenCalidad().get(0));
    }

    @Test
    void registrarYConfirmarPedidoDebeAgregarloAlHistorialDelClienteUnaSolaVez() {
        Restaurante restaurante = restauranteBase();
        Cliente cliente = cliente("1");
        Producto producto = plato("P1", 10000);
        restaurante.registrarCliente(cliente);
        restaurante.registrarProducto(producto);

        Pedido pedido = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        pedido.agregarDetalle(producto, 2);

        assertTrue(restaurante.registrarPedido(pedido));
        assertTrue(restaurante.confirmarPedido("PE1"));
        assertFalse(restaurante.confirmarPedido("PE1"));
        assertEquals(1, cliente.obtenerHistorialPedidos().size());
    }

    @Test
    void pedidoConProductoFueraDeCalidadNoDebeRegistrarse() {
        Restaurante restaurante = restauranteBase();
        Cliente cliente = cliente("1");
        Producto producto = new Bebida("B1", "Bebida", "", 5000, 2,
                500, 20, true, false);
        restaurante.registrarCliente(cliente);
        restaurante.registrarProducto(producto);

        Pedido pedido = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        pedido.agregarDetalle(producto, 1);
        assertFalse(restaurante.registrarPedido(pedido));
    }

    @Test
    void ventasIngresosProductoMasVendidoYCategoriaMayorIngresoDebenIgnorarCancelados() {
        Restaurante restaurante = restauranteBase();
        Cliente cliente = cliente("1");
        restaurante.registrarCliente(cliente);

        Producto plato = plato("P1", 10000); // 12000
        Producto bebida = new Bebida("B1", "Bebida", "", 10000, 2,
                500, 5, true, false); // 11000
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(bebida);

        Pedido venta1 = new Pedido("PE1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        venta1.agregarDetalle(plato, 2); // 24000
        venta1.agregarDetalle(bebida, 1); // 11000
        assertTrue(restaurante.registrarPedido(venta1));
        assertTrue(restaurante.confirmarPedido("PE1"));

        Pedido venta2 = new Pedido("PE2", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        venta2.agregarDetalle(bebida, 3); // 33000
        venta2.asignarServicioDomicilio(new RapidoExpress(), 2); // 8000
        assertTrue(restaurante.registrarPedido(venta2));
        assertTrue(restaurante.confirmarPedido("PE2"));
        assertTrue(restaurante.actualizarEstadoPedido("PE2", EstadoPedido.ENTREGADO));

        Pedido cancelado = new Pedido("PE3", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        cancelado.agregarDetalle(plato, 100);
        assertTrue(restaurante.registrarPedido(cancelado));
        assertTrue(restaurante.actualizarEstadoPedido("PE3", EstadoPedido.CANCELADO));

        assertEquals(2, restaurante.consultarVentas().size());
        assertEquals(68000, restaurante.calcularIngresosRestaurante(), 0.001);
        assertEquals(8000, restaurante.calcularIngresosDomicilios(), 0.001);
        assertSame(bebida, restaurante.productoMasVendidoPorUnidades());
        assertEquals("BEBIDA", restaurante.categoriaMayorIngreso());
    }
}