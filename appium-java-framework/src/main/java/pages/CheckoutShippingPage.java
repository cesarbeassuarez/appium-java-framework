package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutShippingPage extends BasePage {

    // Locators
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/checkoutTitleTV";
    private static final String SUBTITLE = "com.saucelabs.mydemoapp.android:id/enterShippingAddressTV";
    private static final String FULL_NAME = "com.saucelabs.mydemoapp.android:id/fullNameET";
    private static final String ADDRESS_1 = "com.saucelabs.mydemoapp.android:id/address1ET";
    private static final String ADDRESS_2 = "com.saucelabs.mydemoapp.android:id/address2ET";
    private static final String CITY = "com.saucelabs.mydemoapp.android:id/cityET";
    private static final String STATE = "com.saucelabs.mydemoapp.android:id/stateET";
    private static final String ZIP_CODE = "com.saucelabs.mydemoapp.android:id/zipET";
    private static final String COUNTRY = "com.saucelabs.mydemoapp.android:id/countryET";
    private static final String TO_PAYMENT_ACC = "Saves user info for checkout";

    public CheckoutShippingPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Obtener título de Shipping")
    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }

    @Step("Obtener subtítulo de Shipping")
    public String obtenerSubtitulo() {
        return driver.findElement(AppiumBy.id(SUBTITLE)).getText();
    }

    @Step("Ingresar nombre completo: {fullName}")
    public void ingresarFullName(String fullName) {
        driver.findElement(AppiumBy.id(FULL_NAME)).clear();
        driver.findElement(AppiumBy.id(FULL_NAME)).sendKeys(fullName);
    }

    @Step("Ingresar dirección 1: {address}")
    public void ingresarAddress1(String address) {
        driver.findElement(AppiumBy.id(ADDRESS_1)).clear();
        driver.findElement(AppiumBy.id(ADDRESS_1)).sendKeys(address);
    }

    @Step("Ingresar dirección 2: {address}")
    public void ingresarAddress2(String address) {
        driver.findElement(AppiumBy.id(ADDRESS_2)).clear();
        driver.findElement(AppiumBy.id(ADDRESS_2)).sendKeys(address);
    }

    @Step("Ingresar ciudad: {city}")
    public void ingresarCity(String city) {
        driver.findElement(AppiumBy.id(CITY)).clear();
        driver.findElement(AppiumBy.id(CITY)).sendKeys(city);
    }

    @Step("Ingresar estado: {state}")
    public void ingresarState(String state) {
        driver.findElement(AppiumBy.id(STATE)).clear();
        driver.findElement(AppiumBy.id(STATE)).sendKeys(state);
    }

    @Step("Ingresar código postal: {zipCode}")
    public void ingresarZipCode(String zipCode) {
        driver.findElement(AppiumBy.id(ZIP_CODE)).clear();
        driver.findElement(AppiumBy.id(ZIP_CODE)).sendKeys(zipCode);
    }

    @Step("Ingresar país: {country}")
    public void ingresarCountry(String country) {
        driver.findElement(AppiumBy.id(COUNTRY)).clear();
        driver.findElement(AppiumBy.id(COUNTRY)).sendKeys(country);
    }

    @Step("Completar formulario de envío")
    public void completarFormulario(String fullName, String address1, String city,
                                    String zipCode, String country) {
        ingresarFullName(fullName);
        ingresarAddress1(address1);
        ingresarCity(city);
        ingresarZipCode(zipCode);
        ingresarCountry(country);
    }

    @Step("Ir a Payment")
    public CheckoutPaymentPage irAPayment() {
        driver.findElement(AppiumBy.accessibilityId(TO_PAYMENT_ACC)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")
        ));
        return new CheckoutPaymentPage(driver, wait);
    }
}