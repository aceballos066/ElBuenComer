package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    @Test
    void debeAceptarRecorridoExactamenteDeVeinteKilometros() {
        assertTrue(new EntregaSegura().aceptarRecorrido(20));
    }

    @Test
    void debeRechazarRecorridoMayorAVeinteKilometros() {
        assertFalse(new EntregaSegura().aceptarRecorrido(20.1));
    }

    @Test
    void tarifaHastaCincoKilometrosNoDebeTenerRecargo() {
        assertEquals(12000, new EntregaSegura().calcularTarifa(5), 0.001);
    }

    @Test
    void tarifaMayorACincoKilometrosDebeAgregarTresMil() {
        assertEquals(16000, new EntregaSegura().calcularTarifa(6), 0.001);
    }

    @Test
    void tarifaEnCeroKilometrosDebeSerSieteMil() {
        assertEquals(7000, new EntregaSegura().calcularTarifa(0), 0.001);
    }
}
