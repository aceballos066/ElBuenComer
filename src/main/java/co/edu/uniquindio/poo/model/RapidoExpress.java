package co.edu.uniquindio.poo.model;

public class RapidoExpress implements ServicioDomicilio {

    private static final double TARIFA_BASE = 5000;
    private static final double COSTO_KM = 1500;
    private static final double DISTANCIA_MAX = 12;

    public RapidoExpress() {
    }

    @Override
    public boolean aceptarRecorrido(double distanciaKm) {
        return distanciaKm > 0 && distanciaKm <= DISTANCIA_MAX;
    }

    @Override
    public double calcularTarifa(double distanciaKm) {
        if (!aceptarRecorrido(distanciaKm)) {
            return 0;
        }

        return TARIFA_BASE + (COSTO_KM * distanciaKm);
    }
}