package tests;

import org.testng.Assert;

import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductDetailPage;
import pages.ProductsPage;

public class CartTest extends BaseTest{

    @Test
    public void verificarProductoEnCarrito() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");
        detailPage.agregarAlCarrito();
        CartPage cartPage = detailPage.irAlCarrito();

        Assert.assertEquals(cartPage.obtenerNombreProducto(), "Sauce Labs Backpack");
        Assert.assertEquals(cartPage.obtenerPrecioProducto(), "$ 29.99");
        Assert.assertEquals(cartPage.obtenerCantidad(), "1");
        Assert.assertEquals(cartPage.obtenerTotalItems(), "1 Items");
        Assert.assertEquals(cartPage.obtenerTotalPrecio(), "$ 29.99");
    }
    @Test
    public void eliminarProductoDelCarrito() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");
        detailPage.agregarAlCarrito();
        CartPage cartPage = detailPage.irAlCarrito();

        Assert.assertEquals(cartPage.obtenerTotalItems(), "1 Items");

        cartPage.eliminarProducto();
        Assert.assertEquals(cartPage.obtenerTituloCarritoVacio(), "No Items");
    }
    @Test
    public void verificarCantidadMultipleEnCarrito() {
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");
        detailPage.aumentarCantidad(); // cantidad = 2
        detailPage.agregarAlCarrito();
        CartPage cartPage = detailPage.irAlCarrito();

        Assert.assertEquals(cartPage.obtenerCantidad(), "2");
        Assert.assertEquals(cartPage.obtenerTotalItems(), "2 Items");
        Assert.assertEquals(cartPage.obtenerTotalPrecio(), "$ 59.98");
    }

    @Test
    public void agregarDosProductosDistintos() {
        // Agregar primer producto
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");
        detailPage.agregarAlCarrito();
        ProductsPage products = detailPage.volverAProducts();

        // Agregar segundo producto
        detailPage = products.seleccionarProducto("Sauce Labs Backpack (yellow)");
        detailPage.agregarAlCarrito();
        CartPage cartPage = detailPage.irAlCarrito();

        Assert.assertEquals(cartPage.obtenerTotalItems(), "2 Items");
        Assert.assertEquals(cartPage.obtenerTotalPrecio(), "$ 59.98");
    }
}