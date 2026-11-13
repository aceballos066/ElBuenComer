package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Restaurante {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private List<Producto> listRestauranteProductos;
    private List<Cliente> listRestauranteClientes;
    private List<Pedido> listRestaurantePedidos;
    private List<ServicioDomicilio> listRestauranteServiciosDomicilio;

    /**
     * Co nstructor de la clase Restaurante.
     */
    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.listRestauranteProductos = new ArrayList<>();
        this.listRestauranteClientes = new ArrayList<>();
        this.listRestaurantePedidos = new ArrayList<>();
        this.listRestauranteServiciosDomicilio = new ArrayList<>();
    }


    public boolean registrarProducto(Producto producto) {
        if (producto == null || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        listRestauranteProductos.add(producto);
        return true;
    }


    public Producto buscarProducto(String codigo) {
        for (Producto producto : listRestauranteProductos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }


    public List<Producto> consultarProductos() {
        return new ArrayList<>(listRestauranteProductos);
    }


    public List<Producto> productosNoCumplenCalidad() {
        List<Producto> productos = new ArrayList<>();
        for (Producto producto : listRestauranteProductos) {
            if (!producto.cumpleCondicionesCalidad()) {
                productos.add(producto);
            }
        }
        return productos;
    }


    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        listRestauranteClientes.add(cliente);
        return true;
    }


    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : listRestauranteClientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }


    public boolean agregarServicioDomicilio(ServicioDomicilio servicio) {
        if (servicio == null) {
            return false;
        }

        for (ServicioDomicilio servicioGuardado : listRestauranteServiciosDomicilio) {
            if (servicioGuardado.getClass().equals(servicio.getClass())) {
                return false;
            }
        }

        listRestauranteServiciosDomicilio.add(servicio);
        return true;
    }


    public boolean registrarPedido(Pedido pedido) {
        if (pedido == null || buscarPedido(pedido.getCodigo()) != null
                || pedido.getEstado() != EstadoPedido.PENDIENTE
                || pedido.getCliente() == null
                || buscarCliente(pedido.getCliente().getDocumento()) == null
                || pedido.getListPedidoDetalles().isEmpty()) {
            return false;
        }

        for (DetallePedido detalle : pedido.getListPedidoDetalles()) {
            if (detalle.cantidad() <= 0
                    || buscarProducto(detalle.producto().getCodigo()) == null
                    || !detalle.producto().cumpleCondicionesCalidad()) {
                return false;
            }
        }

        if (pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO) {
            ServicioDomicilio servicio = pedido.getTheServicioDomicilioPedido();
            if (servicio == null || !servicio.aceptarRecorrido(pedido.getDistanciaKm())
                    || !servicioContratado(servicio)) {
                return false;
            }
        }

        listRestaurantePedidos.add(pedido);
        return true;
    }


    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : listRestaurantePedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }


    public boolean confirmarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null || pedido.getEstado() != EstadoPedido.PENDIENTE
                || pedido.getCliente() == null
                || buscarCliente(pedido.getCliente().getDocumento()) == null) {
            return false;
        }

        for (DetallePedido detalle : pedido.getListPedidoDetalles()) {
            if (buscarProducto(detalle.producto().getCodigo()) == null
                    || detalle.cantidad() <= 0
                    || !detalle.producto().cumpleCondicionesCalidad()) {
                return false;
            }
        }

        if (pedido.getModalidadEntrega() == ModalidadEntrega.DOMICILIO) {
            ServicioDomicilio servicio = pedido.getTheServicioDomicilioPedido();
            if (servicio == null || !servicioContratado(servicio)
                    || !servicio.aceptarRecorrido(pedido.getDistanciaKm())) {
                return false;
            }
        }

        if (!pedido.confirmar()) {
            return false;
        }

        pedido.getCliente().agregarPedido(pedido);
        return true;
    }


    public boolean actualizarEstadoPedido(String codigo, EstadoPedido estado) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null || estado == null) {
            return false;
        }

        return switch (estado) {
            case CONFIRMADO -> confirmarPedido(codigo);
            case ENTREGADO -> pedido.marcarEntregado();
            case CANCELADO -> pedido.cancelar();
            case PENDIENTE -> false;
        };
    }


    public List<Pedido> consultarVentas() {
        List<Pedido> ventas = new ArrayList<>();
        for (Pedido pedido : listRestaurantePedidos) {
            if (pedido.getEstado() == EstadoPedido.CONFIRMADO
                    || pedido.getEstado() == EstadoPedido.ENTREGADO) {
                ventas.add(pedido);
            }
        }
        return ventas;
    }


    public double calcularIngresosRestaurante() {
        double total = 0;
        for (Pedido pedido : consultarVentas()) {
            total += pedido.calcularSubtotalProductos();
        }
        return total;
    }


    public double calcularIngresosDomicilios() {
        double total = 0;
        for (Pedido pedido : consultarVentas()) {
            total += pedido.calcularCostoDomicilio();
        }
        return total;
    }


    public Producto productoMasVendidoPorUnidades() {
        Map<String, Integer> unidadesPorProducto = new LinkedHashMap<>();

        for (Pedido pedido : consultarVentas()) {
            for (DetallePedido detalle : pedido.getListPedidoDetalles()) {
                String codigo = detalle.producto().getCodigo();
                int acumulado = unidadesPorProducto.getOrDefault(codigo, 0);
                unidadesPorProducto.put(codigo, acumulado + detalle.cantidad());
            }
        }

        String codigoGanador = null;
        int mayorCantidad = -1;
        for (Map.Entry<String, Integer> entry : unidadesPorProducto.entrySet()) {
            if (entry.getValue() > mayorCantidad) {
                mayorCantidad = entry.getValue();
                codigoGanador = entry.getKey();
            }
        }

        return codigoGanador == null ? null : buscarProducto(codigoGanador);
    }


    public String categoriaMayorIngreso() {
        double ingresoPlatos = 0;
        double ingresoBebidas = 0;
        double ingresoPostres = 0;

        for (Pedido pedido : consultarVentas()) {
            for (DetallePedido detalle : pedido.getListPedidoDetalles()) {
                double subtotal = detalle.calcularSubtotal();
                if (detalle.producto() instanceof PlatoFuerte) {
                    ingresoPlatos += subtotal;
                } else if (detalle.producto() instanceof Bebida) {
                    ingresoBebidas += subtotal;
                } else if (detalle.producto() instanceof Postre) {
                    ingresoPostres += subtotal;
                }
            }
        }

        if (ingresoPlatos == 0 && ingresoBebidas == 0 && ingresoPostres == 0) {
            return "";
        }

        if (ingresoPlatos >= ingresoBebidas && ingresoPlatos >= ingresoPostres) {
            return "PLATO_FUERTE";
        }
        if (ingresoBebidas >= ingresoPlatos && ingresoBebidas >= ingresoPostres) {
            return "BEBIDA";
        }
        return "POSTRE";
    }


    private boolean servicioContratado(ServicioDomicilio servicio) {
        for (ServicioDomicilio servicioGuardado : listRestauranteServiciosDomicilio) {
            if (servicioGuardado.getClass().equals(servicio.getClass())) {
                return true;
            }
        }
        return false;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getListRestauranteClientes() {
        return new ArrayList<>(listRestauranteClientes);
    }

    public List<Pedido> getListRestaurantePedidos() {
        return new ArrayList<>(listRestaurantePedidos);
    }

    public List<ServicioDomicilio> getListRestauranteServiciosDomicilio() {
        return new ArrayList<>(listRestauranteServiciosDomicilio);
    }
}