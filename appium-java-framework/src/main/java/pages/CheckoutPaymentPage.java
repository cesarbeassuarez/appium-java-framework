package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPaymentPage extends BasePage {

    // Locators
    private static final String SUBTITLE = "com.saucelabs.mydemoapp.android:id/enterPaymentMethodTV";
    private static final String FULL_NAME = "com.saucelabs.mydemoapp.android:id/nameET";
    private static final String CARD_NUMBER = "com.saucelabs.mydemoapp.android:id/cardNumberET";
    private static final String EXPIRATION_DATE = "com.saucelabs.mydemoapp.android:id/expirationDateET";
    private static final String SECURITY_CODE = "com.saucelabs.mydemoapp.android:id/securityCodeET";
    private static final String BILLING_CHECKBOX = "com.saucelabs.mydemoapp.android:id/billingAddressCB";
    private static final String REVIEW_ORDER_ACC = "Saves payment info and launches screen to review checkout data";

    public CheckoutPaymentPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerSubtitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(SUBTITLE)
        )).getText();
    }

    public void ingresarFullName(String fullName) {
        driver.findElement(AppiumBy.id(FULL_NAME)).clear();
        driver.findElement(AppiumBy.id(FULL_NAME)).sendKeys(fullName);
    }

    public void ingresarCardNumber(String cardNumber) {
        driver.findElement(AppiumBy.id(CARD_NUMBER)).clear();
        driver.findElement(AppiumBy.id(CARD_NUMBER)).sendKeys(cardNumber);
    }

    public void ingresarExpirationDate(String expirationDate) {
        driver.findElement(AppiumBy.id(EXPIRATION_DATE)).clear();
        driver.findElement(AppiumBy.id(EXPIRATION_DATE)).sendKeys(expirationDate);
    }

    public void ingresarSecurityCode(String securityCode) {
        driver.findElement(AppiumBy.id(SECURITY_CODE)).clear();
        driver.findElement(AppiumBy.id(SECURITY_CODE)).sendKeys(securityCode);
    }

    public void toggleBillingAddress() {
        driver.findElement(AppiumBy.id(BILLING_CHECKBOX)).click();
    }

    public void completarFormulario(String fullName, String cardNumber,
                                    String expirationDate, String securityCode) {
        ingresarFullName(fullName);
        ingresarCardNumber(cardNumber);
        ingresarExpirationDate(expirationDate);
        ingresarSecurityCode(securityCode);
    }

    public ReviewOrderPage irAReviewOrder() {
        driver.findElement(AppiumBy.accessibilityId(REVIEW_ORDER_ACC)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalAmountTV")
        ));
        return new ReviewOrderPage(driver, wait);
    }
}