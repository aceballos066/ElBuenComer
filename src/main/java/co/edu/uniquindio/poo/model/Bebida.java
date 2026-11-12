package co.edu.uniquindio.poo.model;


public class Bebida extends Producto {
    private int volumenMililitros;
    private double temperaturaServicio;
    private boolean esFria;
    private boolean requierePreparacionEspecial;

    /**
     * Constructor de la clase Bebida.
     */
    public Bebida(String codigo, String nombre, String descripcion, double precioBase,
                  int tiempoPreparacionMinutos, int volumenMililitros,
                  double temperaturaServicio, boolean esFria,
                  boolean requierePreparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMinutos);
        this.volumenMililitros = volumenMililitros;
        this.temperaturaServicio = temperaturaServicio;
        this.esFria = esFria;
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }

    /**
     * Las bebidas incrementan 10 % sobre el precio base y, cuando requieren
     * preparación especial, adicionan un recargo fijo de $3.000.
     */
    @Override
    public double calcularPrecioVenta() {
        double precioVenta = getPrecioBase() * 1.10;
        if (requierePreparacionEspecial) {
            precioVenta += 3000;
        }
        return precioVenta;
    }

    /**
     * Verifica volumen entre 150 y 1.500 ml. Una bebida fría debe estar entre
     * 0 °C y 10 °C; una bebida caliente entre 55 °C y 85 °C.
     */
    @Override
    public boolean cumpleCondicionesCalidad() {
        if (volumenMililitros < 150 || volumenMililitros > 1500) {
            return false;
        }

        if (esFria) {
            return temperaturaServicio >= 0 && temperaturaServicio <= 10;
        }

        return temperaturaServicio >= 55 && temperaturaServicio <= 85;
    }

    public int getVolumenMililitros() {
        return volumenMililitros;
    }

    public void setVolumenMililitros(int volumenMililitros) {
        this.volumenMililitros = volumenMililitros;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public boolean isEsFria() {
        return esFria;
    }

    public void setEsFria(boolean esFria) {
        this.esFria = esFria;
    }

    public boolean isRequierePreparacionEspecial() {
        return requierePreparacionEspecial;
    }

    public void setRequierePreparacionEspecial(boolean requierePreparacionEspecial) {
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }
}