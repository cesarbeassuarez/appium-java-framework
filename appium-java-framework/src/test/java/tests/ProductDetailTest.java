package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductsPage;
import pages.ProductDetailPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ProductDetailTest {

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