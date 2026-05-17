package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductDetailPage;
import pages.ProductsPage;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class CartTest {

    private AndroidDriver driver;
    private WebDriverWait wait;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid("ZY32FJFXNF");
        options.setApp(new File("apk/mda-2.2.0-25.apk").getAbsolutePath());
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appium:appWaitActivity", "*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        productsPage = new ProductsPage(driver, wait);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

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