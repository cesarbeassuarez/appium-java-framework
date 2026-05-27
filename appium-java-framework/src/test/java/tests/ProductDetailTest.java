package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ProductDetailPage;

public class ProductDetailTest extends BaseTest {

    @DataProvider(name = "productos")
    public Object[][] productos() {
        return new Object[][] {
                {"Sauce Labs Backpack",     "$ 29.99"},
                {"Sauce Labs Backpack (orange)",   "$ 29.99"},
                {"Sauce Labs Backpack (yellow)",       "$ 29.99"}
        };
    }

    @Test(dataProvider = "productos")
    public void verificarDetalleProducto(String nombreProducto, String precioEsperado) {
        ProductDetailPage detailPage = productsPage.seleccionarProducto(nombreProducto);

        Assert.assertEquals(detailPage.obtenerNombreProducto(), nombreProducto);
        Assert.assertEquals(detailPage.obtenerPrecio(), precioEsperado);
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
        detailPage.seleccionarColor("Blue");
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