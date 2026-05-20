package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutCompletePage extends BasePage {

    // Locators
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/completeTV";
    private static final String THANK_YOU = "com.saucelabs.mydemoapp.android:id/thankYouTV";
    private static final String SWAG_MESSAGE = "com.saucelabs.mydemoapp.android:id/swagTV";
    private static final String ORDER_MESSAGE = "com.saucelabs.mydemoapp.android:id/orderTV";
    private static final String CONTINUE_SHOPPING_ACC = "Tap to open catalog";

    public CheckoutCompletePage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }

    public String obtenerThankYou() {
        return driver.findElement(AppiumBy.id(THANK_YOU)).getText();
    }

    public String obtenerSwagMessage() {
        return driver.findElement(AppiumBy.id(SWAG_MESSAGE)).getText();
    }

    public String obtenerOrderMessage() {
        return driver.findElement(AppiumBy.id(ORDER_MESSAGE)).getText();
    }

    public ProductsPage continuarComprando() {
        driver.findElement(AppiumBy.accessibilityId(CONTINUE_SHOPPING_ACC)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("title")
        ));
        return new ProductsPage(driver, wait);
    }
}