package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    @Test
    void calcularPrecioVentaDebeIncrementarVeintePorCiento() {
        PlatoFuerte plato = new PlatoFuerte(
                "P1", "Bandeja", "", 10000, 30,
                500, TipoPreparacion.ASADO, true
        );

        assertEquals(12000, plato.calcularPrecioVenta(), 0.001);
    }

    @Test
    void calidadDebeAceptarValoresMinimosPermitidos() {
        PlatoFuerte plato = new PlatoFuerte(
                "P1", "Plato", "", 10000, 10,
                200, TipoPreparacion.FRITO, true
        );

        assertTrue(plato.cumpleCondicionesCalidad());
    }

    @Test
    void calidadDebeAceptarValoresMaximosPermitidos() {
        PlatoFuerte plato = new PlatoFuerte(
                "P1", "Plato", "", 10000, 60,
                900, TipoPreparacion.HORNEADO, true
        );

        assertTrue(plato.cumpleCondicionesCalidad());
    }

    @Test
    void calidadDebeRechazarPesoFueraDeRango() {
        PlatoFuerte plato = new PlatoFuerte(
                "P1", "Plato", "", 10000, 30,
                199, TipoPreparacion.PLANCHA, true
        );

        assertFalse(plato.cumpleCondicionesCalidad());
    }

    @Test
    void calidadDebeRechazarCoccionNoAprobada() {
        PlatoFuerte plato = new PlatoFuerte(
                "P1", "Plato", "", 10000, 30,
                500, TipoPreparacion.ASADO, false
        );

        assertFalse(plato.cumpleCondicionesCalidad());
    }
}