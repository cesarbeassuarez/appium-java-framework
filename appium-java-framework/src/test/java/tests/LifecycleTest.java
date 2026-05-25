package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LifecycleTest extends BaseTest {

    @Test
    public void appEnBackground() {
        // Verificar que estamos en Products
        Assert.assertTrue(productsPage.obtenerTitulo().contains("Products"));

        // Mandar la app a background por 5 segundos
        driver.runAppInBackground(Duration.ofSeconds(5));

        // Verificar que al volver, sigue en Products
        Assert.assertTrue(productsPage.obtenerTitulo().contains("Products"));
    }

    @Test
    public void terminateYReactivar() {
        String appPackage = "com.saucelabs.mydemoapp.android";

        // Cerrar la app completamente
        driver.terminateApp(appPackage);

        // Reabrir la app
        driver.activateApp(appPackage);

        // Verificar que arranca de nuevo en Products
        Assert.assertTrue(productsPage.obtenerTitulo().contains("Products"));
    }
}