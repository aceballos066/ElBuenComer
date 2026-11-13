package co.edu.uniquindio.poo.model;

public class EntregaSegura implements ServicioDomicilio {
    private static final double TARIFA_BASE = 7000;
    private static final double COSTO_KM = 1000;
    private static final double RECARGO_KM_EXTRA = 3000;
    private static final double DISTANCIA_RECARGO = 5;
    private static final double DISTANCIA_MAX = 20;

    public EntregaSegura() {
    }

    @Override
    public boolean aceptarRecorrido(double distanciaKm) {
        return distanciaKm >= 0 && distanciaKm <= DISTANCIA_MAX;
    }

    @Override
    public double calcularTarifa(double distanciaKm) {
        if (!aceptarRecorrido(distanciaKm)) {
            return 0;
        }

        double tarifa = TARIFA_BASE + (COSTO_KM * distanciaKm);
        if (distanciaKm > DISTANCIA_RECARGO) {
            tarifa += RECARGO_KM_EXTRA;
        }
        return tarifa;
    }
}