package tests;

import org.testng.Assert;

import org.testng.annotations.Test;

import pages.ProductDetailPage;

public class ProductDetailTest extends BaseTest{

    @Test
    public void verificarDetalleProducto() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");

        Assert.assertEquals(detailPage.obtenerNombreProducto(), "Sauce Labs Backpack");
        Assert.assertEquals(detailPage.obtenerPrecio(), "$ 29.99");
        Assert.assertEquals(detailPage.obtenerCantidad(), "1");
    }
    @Test
    public void modificarCantidad() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");

        Assert.assertEquals(detailPage.obtenerCantidad(), "1");

        detailPage.aumentarCantidad();
        Assert.assertEquals(detailPage.obtenerCantidad(), "2");

        detailPage.aumentarCantidad();
        Assert.assertEquals(detailPage.obtenerCantidad(), "3");

        detailPage.disminuirCantidad();
        Assert.assertEquals(detailPage.obtenerCantidad(), "2");
    }

    @Test
    public void seleccionarColor() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");

        // Seleccionar color azul
        detailPage.seleccionarColor("Blue");
        // Si no lanza excepción, el elemento existe y fue tocado

        // Seleccionar color negro
        detailPage.seleccionarColor("Black");
    }

    @Test
    public void agregarAlCarrito() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");

        detailPage.agregarAlCarrito();
        Assert.assertEquals(detailPage.obtenerBadgeCarrito(), "1");

        detailPage.agregarAlCarrito();
        Assert.assertEquals(detailPage.obtenerBadgeCarrito(), "2");
    }
}