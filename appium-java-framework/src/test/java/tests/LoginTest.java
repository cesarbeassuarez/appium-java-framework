package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class LoginTest {

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
    public void loginExitoso() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        ProductsPage resultado = loginPage.tapLogin();

        String titulo = resultado.obtenerTitulo();
        Assert.assertEquals(titulo, "Products",
                "No volvió a la pantalla de Products después del login");
    }

    @Test
    public void scrollHastaProducto() {
        productsPage.scrollHastaProducto("Sauce Labs Onesie");

        String producto = productsPage.obtenerTextoProducto("Sauce Labs Onesie");
        Assert.assertEquals(producto, "Sauce Labs Onesie",
                "No encontró el producto después del scroll");
    }

    @Test
    public void loginConClearYReingreso() {
        LoginPage loginPage = productsPage.irAlLogin();

        loginPage.ingresarUsername("usuario_equivocado");
        loginPage.limpiarUsername();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        ProductsPage resultado = loginPage.tapLogin();

        String titulo = resultado.obtenerTitulo();
        Assert.assertEquals(titulo, "Products");
    }

    @Test
    public void loginConCamposVacios() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.tapLoginEsperandoError();

        String errorUsername = loginPage.obtenerErrorUsername();
        Assert.assertEquals(errorUsername, "Username is required",
                "No mostró error de username vacío");
    }
}