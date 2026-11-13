package co.edu.uniquindio.poo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    @Test
    void calcularPrecioVentaDebeIncrementarDiezPorCiento() {
        Bebida bebida = new Bebida("B1", "Agua", "", 10000, 2,
                500, 5, true, false);
        assertEquals(11000, bebida.calcularPrecioVenta(), 0.001);
    }

    @Test
    void preparacionEspecialDebeAgregarTresMilPesos() {
        Bebida bebida = new Bebida("B1", "Café especial", "", 10000, 5,
                300, 70, false, true);
        assertEquals(14000, bebida.calcularPrecioVenta(), 0.001);
    }

    @Test
    void bebidaFriaDebeAceptarTemperaturasEntreCeroYDiez() {
        Bebida bebidaMin = new Bebida("B1", "Fría", "", 5000, 1,
                150, 0, true, false);
        Bebida bebidaMax = new Bebida("B2", "Fría", "", 5000, 1,
                1500, 10, true, false);
        assertTrue(bebidaMin.cumpleCondicionesCalidad());
        assertTrue(bebidaMax.cumpleCondicionesCalidad());
    }

    @Test
    void bebidaCalienteDebeAceptarTemperaturasEntreCincuentaYCincoYOchentaYCinco() {
        Bebida bebidaMin = new Bebida("B1", "Caliente", "", 5000, 1,
                150, 55, false, false);
        Bebida bebidaMax = new Bebida("B2", "Caliente", "", 5000, 1,
                1500, 85, false, false);
        assertTrue(bebidaMin.cumpleCondicionesCalidad());
        assertTrue(bebidaMax.cumpleCondicionesCalidad());
    }

    @Test
    void calidadDebeRechazarTemperaturaIncorrecta() {
        Bebida bebida = new Bebida("B1", "Fría", "", 5000, 1,
                500, 11, true, false);
        assertFalse(bebida.cumpleCondicionesCalidad());
    }
}