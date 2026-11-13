package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private Cliente cliente;
    private ModalidadEntrega modalidadEntrega;
    private EstadoPedido estado;
    private double distanciaKm;
    private ServicioDomicilio theServicioDomicilioPedido;
    private List<DetallePedido> listPedidoDetalles;

    public Pedido(String codigo, LocalDate fecha, Cliente cliente,
                  ModalidadEntrega modalidadEntrega) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidadEntrega = modalidadEntrega;
        this.estado = EstadoPedido.PENDIENTE;
        this.distanciaKm = 0;
        this.listPedidoDetalles = new ArrayList<>();
    }

    public boolean agregarDetalle(Producto producto, int cantidad) {
        if (estado != EstadoPedido.PENDIENTE || producto == null || cantidad <= 0) {
            return false;
        }

        listPedidoDetalles.add(new DetallePedido(producto, cantidad));
        return true;
    }

    public boolean asignarServicioDomicilio(ServicioDomicilio servicio, double distanciaKm) {
        if (estado != EstadoPedido.PENDIENTE
                || modalidadEntrega != ModalidadEntrega.DOMICILIO
                || servicio == null
                || !servicio.aceptarRecorrido(distanciaKm)) {
            return false;
        }

        this.theServicioDomicilioPedido = servicio;
        this.distanciaKm = distanciaKm;
        return true;
    }

    public boolean cumpleCondicionesConfirmacion() {
        if (estado != EstadoPedido.PENDIENTE
                || codigo == null || codigo.isBlank()
                || fecha == null
                || cliente == null
                || modalidadEntrega == null
                || listPedidoDetalles.isEmpty()) {
            return false;
        }

        for (DetallePedido detalle : listPedidoDetalles) {
            if (detalle.cantidad() <= 0
                    || !detalle.producto().cumpleCondicionesCalidad()) {
                return false;
            }
        }

        if (modalidadEntrega == ModalidadEntrega.DOMICILIO) {
            return theServicioDomicilioPedido != null
                    && theServicioDomicilioPedido.aceptarRecorrido(distanciaKm);
        }

        return modalidadEntrega == ModalidadEntrega.RESTAURANTE;
    }

    public double calcularSubtotalProductos() {
        double subtotal = 0;

        for (DetallePedido detalle : listPedidoDetalles) {
            subtotal += detalle.calcularSubtotal();
        }

        return subtotal;
    }

    public double calcularCostoDomicilio() {
        if (modalidadEntrega != ModalidadEntrega.DOMICILIO
                || theServicioDomicilioPedido == null) {
            return 0;
        }

        return theServicioDomicilioPedido.calcularTarifa(distanciaKm);
    }

    public double calcularTotal() {
        return calcularSubtotalProductos() + calcularCostoDomicilio();
    }

    public boolean confirmar() {
        if (!cumpleCondicionesConfirmacion()) {
            return false;
        }

        estado = EstadoPedido.CONFIRMADO;
        return true;
    }

    public boolean marcarEntregado() {
        if (estado != EstadoPedido.CONFIRMADO) {
            return false;
        }

        estado = EstadoPedido.ENTREGADO;
        return true;
    }

    public boolean cancelar() {
        if (estado == EstadoPedido.ENTREGADO
                || estado == EstadoPedido.CANCELADO) {
            return false;
        }

        estado = EstadoPedido.CANCELADO;
        return true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ModalidadEntrega getModalidadEntrega() {
        return modalidadEntrega;
    }

    public void setModalidadEntrega(ModalidadEntrega modalidadEntrega) {
        this.modalidadEntrega = modalidadEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public ServicioDomicilio getTheServicioDomicilioPedido() {
        return theServicioDomicilioPedido;
    }

    public List<DetallePedido> getListPedidoDetalles() {
        return new ArrayList<>(listPedidoDetalles);
    }
}