package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un cliente registrado en el restaurante.
 */
public class Cliente {
    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccionResidencia;
    private List<Pedido> listClientePedidos;

    /**
     * Constructor de la clase Cliente.
     */
    public Cliente(String documento, String nombreCompleto, String telefono,
                   String correo, String direccionResidencia) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccionResidencia = direccionResidencia;
        this.listClientePedidos = new ArrayList<>();
    }

    /**
     * Agrega un pedido confirmado al historial del cliente evitando duplicados
     * por código.
     *
     * @param pedido pedido a agregar
     */
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            return;
        }

        boolean existe = false;
        for (Pedido pedidoGuardado : listClientePedidos) {
            if (pedidoGuardado.getCodigo().equals(pedido.getCodigo())) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            listClientePedidos.add(pedido);
        }
    }

    /**
     * Obtiene una copia del historial de pedidos del cliente.
     *
     * @return lista de pedidos del cliente
     */
    public List<Pedido> obtenerHistorialPedidos() {
        return new ArrayList<>(listClientePedidos);
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }
}
