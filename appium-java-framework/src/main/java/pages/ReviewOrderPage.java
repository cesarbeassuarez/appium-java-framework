package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReviewOrderPage extends BasePage {

    // Locators - Producto
    private static final String PRODUCT_NAME = "com.saucelabs.mydemoapp.android:id/titleTV";
    private static final String PRODUCT_PRICE = "com.saucelabs.mydemoapp.android:id/priceTV";

    // Locators - Dirección
    private static final String DELIVERY_NAME = "com.saucelabs.mydemoapp.android:id/fullNameTV";
    private static final String DELIVERY_ADDRESS = "com.saucelabs.mydemoapp.android:id/addressTV";
    private static final String DELIVERY_CITY = "com.saucelabs.mydemoapp.android:id/cityTV";
    private static final String DELIVERY_COUNTRY = "com.saucelabs.mydemoapp.android:id/countryTV";

    // Locators - Pago
    private static final String CARD_HOLDER = "com.saucelabs.mydemoapp.android:id/cardHolderTV";
    private static final String CARD_NUMBER = "com.saucelabs.mydemoapp.android:id/cardNumberTV";
    private static final String EXPIRATION_DATE = "com.saucelabs.mydemoapp.android:id/expirationDateTV";

    // Locators - Envío
    private static final String DHL_DELIVERY = "com.saucelabs.mydemoapp.android:id/dhlTV";
    private static final String SHIPPING_COST = "com.saucelabs.mydemoapp.android:id/amountTV";
    private static final String ESTIMATED_ARRIVAL = "com.saucelabs.mydemoapp.android:id/arrivalTV";

    // Locators - Totales
    private static final String TOTAL_ITEMS = "com.saucelabs.mydemoapp.android:id/itemNumberTV";
    private static final String TOTAL_AMOUNT = "com.saucelabs.mydemoapp.android:id/totalAmountTV";
    private static final String PLACE_ORDER_ACC = "Completes the process of checkout";

    public ReviewOrderPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Producto
    public String obtenerNombreProducto() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(PRODUCT_NAME)
        )).getText();
    }

    public String obtenerPrecioProducto() {
        return driver.findElement(AppiumBy.id(PRODUCT_PRICE)).getText();
    }

    // Dirección
    public String obtenerNombreDireccion() {
        return driver.findElement(AppiumBy.id(DELIVERY_NAME)).getText();
    }

    public String obtenerDireccion() {
        return driver.findElement(AppiumBy.id(DELIVERY_ADDRESS)).getText();
    }

    public String obtenerCiudad() {
        return driver.findElement(AppiumBy.id(DELIVERY_CITY)).getText();
    }

    public String obtenerPais() {
        return driver.findElement(AppiumBy.id(DELIVERY_COUNTRY)).getText();
    }

    // Pago
    public String obtenerTitularTarjeta() {
        return driver.findElement(AppiumBy.id(CARD_HOLDER)).getText();
    }

    public String obtenerNumeroTarjeta() {
        return driver.findElement(AppiumBy.id(CARD_NUMBER)).getText();
    }

    public String obtenerFechaExpiracion() {
        return driver.findElement(AppiumBy.id(EXPIRATION_DATE)).getText();
    }

    // Envío
    public String obtenerMetodoEnvio() {
        return driver.findElement(AppiumBy.id(DHL_DELIVERY)).getText();
    }

    public String obtenerCostoEnvio() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(SHIPPING_COST)
        )).getText();
    }

    public String obtenerTiempoEstimado() {
        return driver.findElement(AppiumBy.id(ESTIMATED_ARRIVAL)).getText();
    }

    // Totales
    public String obtenerTotalItems() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TOTAL_ITEMS)
        )).getText();
    }

    public String obtenerTotalAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TOTAL_AMOUNT)
        )).getText();
    }

    public CheckoutCompletePage realizarPedido() {
        driver.findElement(AppiumBy.accessibilityId(PLACE_ORDER_ACC)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/completeTV")
        ));
        return new CheckoutCompletePage(driver, wait);
    }

    public void scrollHastaTotal() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollTextIntoView(\"$5.99\")"
        ));
    }
}