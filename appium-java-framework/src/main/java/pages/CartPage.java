package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    // Locators
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/productTV";
    private static final String ITEM_NAME = "com.saucelabs.mydemoapp.android:id/titleTV";
    private static final String ITEM_PRICE = "com.saucelabs.mydemoapp.android:id/priceTV";
    private static final String ITEM_QUANTITY = "com.saucelabs.mydemoapp.android:id/noTV";
    private static final String TOTAL_ITEMS = "com.saucelabs.mydemoapp.android:id/itemsTV";
    private static final String TOTAL_PRICE = "com.saucelabs.mydemoapp.android:id/totalPriceTV";
    private static final String REMOVE_ITEM_ACC = "Removes product from cart";
    private static final String PROCEED_CHECKOUT_ACC = "Confirms products for checkout";

    public CartPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }

    public String obtenerNombreProducto() {
        return driver.findElement(AppiumBy.id(ITEM_NAME)).getText();
    }

    public String obtenerPrecioProducto() {
        return driver.findElement(AppiumBy.id(ITEM_PRICE)).getText();
    }

    public String obtenerCantidad() {
        return driver.findElement(AppiumBy.id(ITEM_QUANTITY)).getText();
    }

    public String obtenerTotalItems() {
        return driver.findElement(AppiumBy.id(TOTAL_ITEMS)).getText();
    }

    public String obtenerTotalPrecio() {
        return driver.findElement(AppiumBy.id(TOTAL_PRICE)).getText();
    }

    public CartPage eliminarProducto() {
        driver.findElement(AppiumBy.accessibilityId(REMOVE_ITEM_ACC)).click();
        return this;
    }

    private static final String NO_ITEMS_TITLE = "com.saucelabs.mydemoapp.android:id/noItemTitleTV";

    public String obtenerTituloCarritoVacio() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(NO_ITEMS_TITLE)
        )).getText();
    }
}