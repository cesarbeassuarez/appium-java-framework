import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class LoginTest {

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid("ZY32FJFXNF");
        options.setApp(new File("apk/mda-2.2.0-25.apk").getAbsolutePath());
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appium:appWaitActivity", "*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @Test
    public void loginExitoso() throws InterruptedException {
        // 1. Tap en menú hamburguesa
        driver.findElement(AppiumBy.accessibilityId("View menu")).click();
        Thread.sleep(1000);

        // 2. Tap en "Log In" dentro del menú
        driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();
        Thread.sleep(1000);

        // 3. Ingresar username
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")).sendKeys("bod@example.com");

        // 4. Ingresar password
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET")).sendKeys("10203040");

        // 5. Tap en botón Login
        driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

        // 6. Esperar que cargue Products
        Thread.sleep(3000);

        // 7. Verificar que volvió a Products
        String titulo = driver.findElement(AppiumBy.accessibilityId("title")).getText();
        Assert.assertEquals(titulo, "Products", "No volvió a la pantalla de Products después del login");

    }

    @Test
    public void scrollHastaProducto() throws InterruptedException {
        // Scroll hasta un producto que no está visible
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollTextIntoView(\"Sauce Labs Onesie\")"
        ));

        // Verificar que el producto es visible
        String producto = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Sauce Labs Onesie\")"
        )).getText();

        Assert.assertEquals(producto, "Sauce Labs Onesie", "No encontró el producto después del scroll");
    }

    @Test
    public void loginConClearYReingreso() throws InterruptedException {
        // 1. Ir al login
        driver.findElement(AppiumBy.accessibilityId("View menu")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();
        Thread.sleep(1000);

        // 2. Ingresar username incorrecto
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")).sendKeys("usuario_equivocado");

        // 3. Limpiar y poner el correcto
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")).clear();
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")).sendKeys("bod@example.com");

        // 4. Password y login
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET")).sendKeys("10203040");
        driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

        // 5. Verificar
        Thread.sleep(3000);
        String titulo = driver.findElement(AppiumBy.accessibilityId("title")).getText();
        Assert.assertEquals(titulo, "Products");
    }

    @Test
    public void loginConCamposVacios() throws InterruptedException {
        // 1. Ir al login
        driver.findElement(AppiumBy.accessibilityId("View menu")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();
        Thread.sleep(1000);

        // 2. Tap en Login sin ingresar nada
        driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

        // 3. Verificar mensaje de error en username
        String errorUsername = driver.findElement(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameErrorTV")
        ).getText();

        Assert.assertEquals(errorUsername, "Username is required", "No mostró error de username vacío");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}