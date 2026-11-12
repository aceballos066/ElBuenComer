package co.edu.uniquindio.poo.model;


public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    /**
     * Constructor de la clase PlatoFuerte.
     */
    public PlatoFuerte(String codigo, String nombre, String descripcion,
                       double precioBase, int tiempoPreparacionMinutos,
                       double pesoGramos, TipoPreparacion tipoPreparacion,
                       boolean coccionAprobada) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMinutos);

        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }


    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }


    @Override
    public boolean cumpleCondicionesCalidad() {
        return tipoPreparacion != null
                && pesoGramos >= 200
                && pesoGramos <= 900
                && getTiempoPreparacionMinutos() >= 10
                && getTiempoPreparacionMinutos() <= 60
                && coccionAprobada;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }
}