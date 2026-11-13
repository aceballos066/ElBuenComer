package co.edu.uniquindio.poo.model;

public class Postre extends Producto {

    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion,
                  double precioBase, int tiempoPreparacionMinutos,
                  double pesoGramos, double temperaturaAlmacenamiento,
                  boolean requiereRefrigeracion) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMinutos);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public double calcularPrecioVenta() {
        double precioVenta = getPrecioBase() * 1.15;

        if (requiereRefrigeracion) {
            precioVenta += getPrecioBase() * 0.05;
        }

        return precioVenta;
    }

    @Override
    public boolean cumpleCondicionesCalidad() {
        if (pesoGramos < 80 || pesoGramos > 500) {
            return false;
        }

        if (requiereRefrigeracion) {
            return temperaturaAlmacenamiento >= 0
                    && temperaturaAlmacenamiento <= 6;
        }

        return temperaturaAlmacenamiento >= 15
                && temperaturaAlmacenamiento <= 30;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }
}
