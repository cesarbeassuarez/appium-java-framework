package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
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

    @Step("Obtener nombre del producto")
    public String obtenerNombreProducto() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(PRODUCT_NAME)
        )).getText();
    }

    @Step("Obtener precio del producto")
    public String obtenerPrecio() {
        return driver.findElement(AppiumBy.id(PRODUCT_PRICE)).getText();
    }

    @Step("Obtener cantidad")
    public String obtenerCantidad() {
        return driver.findElement(AppiumBy.id(QUANTITY)).getText();
    }

    @Step("Aumentar cantidad")
    public void aumentarCantidad() {
        driver.findElement(AppiumBy.accessibilityId(PLUS_BUTTON_ACC)).click();
    }

    @Step("Disminuir cantidad")
    public void disminuirCantidad() {
        driver.findElement(AppiumBy.accessibilityId(MINUS_BUTTON_ACC)).click();
    }

    @Step("Seleccionar color: {color}")
    public void seleccionarColor(String color) {
        driver.findElement(AppiumBy.accessibilityId(color + " color")).click();
    }

    @Step("Agregar al carrito")
    public ProductDetailPage agregarAlCarrito() {
        driver.findElement(AppiumBy.accessibilityId(ADD_TO_CART_ACC)).click();
        return this;
    }

    @Step("Ir al carrito")
    public CartPage irAlCarrito() {
        driver.findElement(AppiumBy.accessibilityId("View cart")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalPriceTV")
        ));
        return new CartPage(driver, wait);
    }

    @Step("Obtener badge del carrito")
    public String obtenerBadgeCarrito() {
        return driver.findElement(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")
        ).getText();
    }

    @Step("Volver a Products")
    public ProductsPage volverAProducts() {
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("title")
        ));
        return new ProductsPage(driver, wait);
    }
}