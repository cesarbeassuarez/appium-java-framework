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

import org.testng.annotations.Listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;

@Listeners(AllureListener.class)
public class BaseTest {

    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected ProductsPage productsPage;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();

        // Configurable: local usa tu celular, CI usa emulador
        String udid = System.getProperty("udid", "ZY32FJFXNF");
        String appPath = System.getProperty("appPath", "apk/mda-2.2.0-25.apk");

        options.setUdid(udid);
        options.setApp(new File(appPath).getAbsolutePath());
        options.setCapability("appium:autoGrantPermissions", false);
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appium:appWaitActivity", "*");

        // ChromeDriver solo si se especifica (local Windows)
        String chromedriverPath = System.getProperty("chromedriverPath");
        if (chromedriverPath != null) {
            options.setCapability("appium:chromedriverExecutable",
                    new File(chromedriverPath).getAbsolutePath());
        }

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        productsPage = new ProductsPage(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("Displays all products of catalog")
        ));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            Allure.getLifecycle().addAttachment(
                    "Screenshot on failure", "image/png", ".png",
                    driver.getScreenshotAs(OutputType.BYTES)
            );
        }
        if (driver != null) {
            driver.quit();
        }
    }
}