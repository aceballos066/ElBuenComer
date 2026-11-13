package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    @Test
    void debeAceptarRecorridoDeCeroKilometros() {
        assertTrue(new RapidoExpress().aceptarRecorrido(0));
    }

    @Test
    void debeAceptarRecorridoExactamenteDeDoceKilometros() {
        assertTrue(new RapidoExpress().aceptarRecorrido(12));
    }

    @Test
    void debeRechazarRecorridoMayorADoceKilometros() {
        assertFalse(new RapidoExpress().aceptarRecorrido(12.1));
    }

    @Test
    void tarifaEnCeroKilometrosDebeSerCincoMil() {
        assertEquals(
                5000,
                new RapidoExpress().calcularTarifa(0),
                0.001
        );
    }

    @Test
    void tarifaDeDiezKilometrosDebeSerVeinteMil() {
        assertEquals(
                20000,
                new RapidoExpress().calcularTarifa(10),
                0.001
        );
    }
}
