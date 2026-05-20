package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CheckoutTest {

    private AndroidDriver driver;
    private WebDriverWait wait;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setApp(new File("apk/mda-2.2.0-25.apk").getAbsolutePath());
        options.setAutomationName("UiAutomator2");
        options.setAutoGrantPermissions(true);
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appium:appWaitActivity", "*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        productsPage = new ProductsPage(driver, wait);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- Helper: lleva desde Products hasta CheckoutShippingPage ---
    private CheckoutShippingPage navegarHastaShipping() {
        // Login
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        productsPage = loginPage.tapLogin();

        // Seleccionar producto → agregar → carrito → checkout
        ProductDetailPage detailPage = productsPage.seleccionarProducto("Sauce Labs Backpack");
        detailPage.agregarAlCarrito();
        CartPage cartPage = detailPage.irAlCarrito();
        return cartPage.proceedToCheckout();
    }

    // --- Helper: llena shipping y payment con datos de prueba ---
    private ReviewOrderPage navegarHastaReview() {
        CheckoutShippingPage shippingPage = navegarHastaShipping();
        shippingPage.completarFormulario(
                "Cesar Beas", "Av Colon 1234", "Cordoba", "5000", "Argentina"
        );
        CheckoutPaymentPage paymentPage = shippingPage.irAPayment();
        paymentPage.completarFormulario(
                "Cesar Beas", "3258126984521346", "03/29", "123"
        );
        return paymentPage.irAReviewOrder();
    }

    @Test
    public void checkoutCompleto() {
        ReviewOrderPage reviewPage = navegarHastaReview();
        CheckoutCompletePage completePage = reviewPage.realizarPedido();

        Assert.assertEquals(completePage.obtenerTitulo(), "Checkout Complete");
    }

    @Test
    public void verificarDatosEnReview() {
        ReviewOrderPage reviewPage = navegarHastaReview();

        // Producto
        Assert.assertEquals(reviewPage.obtenerNombreProducto(), "Sauce Labs Backpack");
        Assert.assertEquals(reviewPage.obtenerPrecioProducto(), "$ 29.99");

        // Dirección
        Assert.assertEquals(reviewPage.obtenerNombreDireccion(), "Cesar Beas");
        Assert.assertEquals(reviewPage.obtenerCiudad().trim(), "Cordoba,");
        Assert.assertEquals(reviewPage.obtenerPais(), "Argentina, 5000");

        // Pago
        Assert.assertEquals(reviewPage.obtenerTitularTarjeta(), "Cesar Beas");

        reviewPage.scrollHastaTotal();

        // Totales
        Assert.assertEquals(reviewPage.obtenerTotalItems(), "1 Items");
        Assert.assertEquals(reviewPage.obtenerCostoEnvio(), "$5.99");
        Assert.assertEquals(reviewPage.obtenerTotalAmount(), "$ 35.98");
    }

    @Test
    public void verificarCheckoutComplete() {
        ReviewOrderPage reviewPage = navegarHastaReview();
        CheckoutCompletePage completePage = reviewPage.realizarPedido();

        Assert.assertEquals(completePage.obtenerTitulo(), "Checkout Complete");
        Assert.assertEquals(completePage.obtenerThankYou(), "Thank you for your order");
        Assert.assertEquals(completePage.obtenerSwagMessage(), "Your new swag is on its way");
        Assert.assertEquals(completePage.obtenerOrderMessage(),
                "Your order has been dispatched and will arrive as fast as the pony gallops!");
    }

    @Test
    public void continuarComprandoVuelveAProducts() {
        ReviewOrderPage reviewPage = navegarHastaReview();
        CheckoutCompletePage completePage = reviewPage.realizarPedido();
        ProductsPage products = completePage.continuarComprando();

        Assert.assertEquals(products.obtenerTitulo(), "Products");
    }
}