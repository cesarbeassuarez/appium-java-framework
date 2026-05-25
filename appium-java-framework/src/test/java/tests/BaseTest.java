package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.ProductsPage;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected ProductsPage productsPage;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid("ZY32FJFXNF");
        options.setApp(new File("apk/mda-2.2.0-25.apk").getAbsolutePath());
        options.setCapability("appium:autoGrantPermissions", false);
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appium:appWaitActivity", "*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        productsPage = new ProductsPage(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("Displays all products of catalog")
        ));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}