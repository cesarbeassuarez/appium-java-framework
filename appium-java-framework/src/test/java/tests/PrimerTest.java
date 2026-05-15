package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class PrimerTest {

    private AndroidDriver driver;
	
	@BeforeMethod
	public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid("ZY32FJFXNF");
        options.setCapability("appium:appWaitActivity", "*");
        options.setApp(new File("apk/mda-2.2.0-25.apk").getAbsolutePath());
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(120));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
	}

    @Test
    public void verificarAppAbre() {
        // Verificar que la app se abrió (el driver tiene una sesión activa)
        Assert.assertNotNull(driver.getSessionId(), "La sesión de Appium no se creó");
        System.out.println("Session ID: " + driver.getSessionId());
        System.out.println("App abierta correctamente en el emulador");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}