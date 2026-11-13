package co.edu.uniquindio.poo.model;

public record DetallePedido(Producto producto, int cantidad) {

    public DetallePedido {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }

    public double calcularSubtotal() {
        return producto.calcularPrecioVenta() * cantidad;
    }
}