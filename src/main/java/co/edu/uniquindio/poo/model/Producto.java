package co.edu.uniquindio.poo.model;


public abstract class Producto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacionMinutos;

    /**
     * Constructor de la clase Producto.
     *
     * @param codigo código único del producto
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precioBase precio base del producto
     * @param tiempoPreparacionMinutos tiempo estimado de preparación en minutos
     */
    public Producto(String codigo, String nombre, String descripcion, double precioBase,
                    int tiempoPreparacionMinutos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacionMinutos = tiempoPreparacionMinutos;
    }


    public abstract double calcularPrecioVenta();


    public abstract boolean cumpleCondicionesCalidad();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getTiempoPreparacionMinutos() {
        return tiempoPreparacionMinutos;
    }

    public void setTiempoPreparacionMinutos(int tiempoPreparacionMinutos) {
        this.tiempoPreparacionMinutos = tiempoPreparacionMinutos;
    }
}
