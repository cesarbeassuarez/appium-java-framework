package tests;

import org.testng.Assert;

import org.testng.annotations.Test;
import pages.DrawingPage;


public class GestosTest extends BaseTest{

    // --- Drawing tests ---

    @Test
    public void navegarADrawing() {
        DrawingPage drawingPage = productsPage.irADrawing();
        Assert.assertEquals(drawingPage.obtenerTitulo(), "Drawing");
    }

    @Test
    public void dibujarYGuardar() {
        DrawingPage drawingPage = productsPage.irADrawing();

        // Coordenadas dentro del canvas (bounds: 38,471 → 1042,2142)
        drawingPage.dibujar(200, 600, 800, 1200);

        String mensaje = drawingPage.guardarYObtenerMensaje();
        Assert.assertEquals(mensaje, "Drawing saved successfully to gallery");
    }

    @Test
    public void dibujarLimpiarYGuardar() {
        DrawingPage drawingPage = productsPage.irADrawing();

        drawingPage.dibujar(200, 600, 800, 1200);
        drawingPage.limpiarCanvas();

        String mensaje = drawingPage.guardarYObtenerMensaje();
        Assert.assertEquals(mensaje, "Drawing saved successfully to gallery");
    }

}