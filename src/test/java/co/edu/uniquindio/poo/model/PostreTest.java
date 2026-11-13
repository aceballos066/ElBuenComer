package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    @Test
    void calcularPrecioVentaSinRefrigeracionDebeIncrementarQuincePorCiento() {
        Postre postre = new Postre(
                "PO1", "Torta", "", 10000, 10,
                200, 20, false
        );

        assertEquals(11500, postre.calcularPrecioVenta(), 0.001);
    }

    @Test
    void postreRefrigeradoDebeAgregarCincoPorCientoAdicional() {
        Postre postre = new Postre(
                "PO1", "Cheesecake", "", 10000, 10,
                200, 4, true
        );

        assertEquals(12000, postre.calcularPrecioVenta(), 0.001);
    }

    @Test
    void postreRefrigeradoDebeAceptarRangoDeCeroASeis() {
        Postre min = new Postre(
                "PO1", "Postre", "", 10000, 10,
                80, 0, true
        );

        Postre max = new Postre(
                "PO2", "Postre", "", 10000, 10,
                500, 6, true
        );

        assertTrue(min.cumpleCondicionesCalidad());
        assertTrue(max.cumpleCondicionesCalidad());
    }

    @Test
    void postreNoRefrigeradoDebeAceptarRangoDeQuinceATreinta() {
        Postre min = new Postre(
                "PO1", "Postre", "", 10000, 10,
                80, 15, false
        );

        Postre max = new Postre(
                "PO2", "Postre", "", 10000, 10,
                500, 30, false
        );

        assertTrue(min.cumpleCondicionesCalidad());
        assertTrue(max.cumpleCondicionesCalidad());
    }

    @Test
    void calidadDebeRechazarTemperaturaIncorrecta() {
        Postre postre = new Postre(
                "PO1", "Postre", "", 10000, 10,
                200, 7, true
        );

        assertFalse(postre.cumpleCondicionesCalidad());
    }
}
