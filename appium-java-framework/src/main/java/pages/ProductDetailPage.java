package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage extends BasePage {

    // Locators
    private static final String PRODUCT_NAME = "com.saucelabs.mydemoapp.android:id/productTV";
    private static final String PRODUCT_PRICE = "com.saucelabs.mydemoapp.android:id/priceTV";
    private static final String QUANTITY = "com.saucelabs.mydemoapp.android:id/noTV";
    private static final String PLUS_BUTTON_ACC = "Increase item quantity";
    private static final String MINUS_BUTTON_ACC = "Decrease item quantity";
    private static final String ADD_TO_CART_ACC = "Tap to add product to cart";
    private static final String PRODUCT_IMAGE_ACC = "Displays selected product";

    public ProductDetailPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerNombreProducto() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(PRODUCT_NAME)
        )).getText();
    }

    public String obtenerPrecio() {
        return driver.findElement(AppiumBy.id(PRODUCT_PRICE)).getText();
    }

    public String obtenerCantidad() {
        return driver.findElement(AppiumBy.id(QUANTITY)).getText();
    }

    public void aumentarCantidad() {
        driver.findElement(AppiumBy.accessibilityId(PLUS_BUTTON_ACC)).click();
    }

    public void disminuirCantidad() {
        driver.findElement(AppiumBy.accessibilityId(MINUS_BUTTON_ACC)).click();
    }

    public void seleccionarColor(String color) {
        // "Black color", "Blue color", etc.
        driver.findElement(AppiumBy.accessibilityId(color + " color")).click();
    }

    // Reemplazá agregarAlCarrito() por estos dos métodos:

    public ProductDetailPage agregarAlCarrito() {
        driver.findElement(AppiumBy.accessibilityId(ADD_TO_CART_ACC)).click();
        return this;
    }

    public CartPage irAlCarrito() {
        driver.findElement(AppiumBy.accessibilityId("View cart")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalPriceTV")
        ));
        return new CartPage(driver, wait);
    }

    public String obtenerBadgeCarrito() {
        return driver.findElement(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")
        ).getText();
    }

    public ProductsPage volverAProducts() {
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("title")
        ));
        return new ProductsPage(driver, wait);
    }
}