package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import pages.QRScannerPage;
import pages.GeoLocationPage;
import pages.WebViewPage;

public class ProductsPage extends BasePage {

    // Locators
    private static final String MENU_HAMBURGUESA = "View menu";
    private static final String LOGIN_MENU_ITEM = "Login Menu Item";
    private static final String TITULO = "title";

    private static final String MENU_ITEM_ID = "com.saucelabs.mydemoapp.android:id/itemTV";

    private static final String PERMISO_ALLOW = "com.android.permissioncontroller:id/permission_allow_foreground_only_button";
    private static final String PERMISO_DENY = "com.android.permissioncontroller:id/permission_deny_button";


    public ProductsPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Obtener título de Products")
    public String obtenerTitulo() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId(TITULO)
        ));
        return driver.findElement(AppiumBy.accessibilityId(TITULO)).getText();
    }

    @Step("Navegar al Login desde menú")
    public LoginPage irAlLogin() {
        driver.findElement(AppiumBy.accessibilityId(MENU_HAMBURGUESA)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId(LOGIN_MENU_ITEM)
        ));
        driver.findElement(AppiumBy.accessibilityId(LOGIN_MENU_ITEM)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET")
        ));
        return new LoginPage(driver, wait);
    }

    @Step("Scroll hasta producto: {nombreProducto}")
    public void scrollHastaProducto(String nombreProducto) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollTextIntoView(\"" + nombreProducto + "\")"
        ));
    }

    @Step("Obtener texto del producto: {nombreProducto}")
    public String obtenerTextoProducto(String nombreProducto) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + nombreProducto + "\")"
        )).getText();
    }

    @Step("Seleccionar producto: {nombreProducto}")
    public ProductDetailPage seleccionarProducto(String nombreProducto) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + nombreProducto + "\"))"
                )
        ));

        driver.findElement(AppiumBy.xpath(
                "//android.widget.TextView[@text='" + nombreProducto + "']" +
                        "/parent::android.view.ViewGroup" +
                        "/android.widget.ImageView[@content-desc='Product Image']"
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("Tap to add product to cart")
        ));

        return new ProductDetailPage(driver, wait);
    }

    @Step("Abrir menú hamburguesa")
    public void abrirMenu() {
        driver.findElement(AppiumBy.accessibilityId(MENU_HAMBURGUESA)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(MENU_ITEM_ID)
        ));
    }

    @Step("Navegar a Drawing")
    public DrawingPage irADrawing() {
        abrirMenu();
        driver.findElements(AppiumBy.id(MENU_ITEM_ID)).stream()
                .filter(e -> e.getText().equals("Drawing"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró 'Drawing' en el menú"))
                .click();

        manejarPermiso(PERMISO_ALLOW, "com.android.permissioncontroller:id/permission_allow_button");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawingTV")
        ));
        return new DrawingPage(driver, wait);
    }

    @Step("Navegar a QR Scanner")
    public QRScannerPage irAQRScanner() {
        abrirMenu();
        driver.findElements(AppiumBy.id(MENU_ITEM_ID)).stream()
                .filter(e -> e.getText().equals("QR Code Scanner"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró 'QR Code Scanner' en el menú"))
                .click();

        manejarPermiso(PERMISO_ALLOW);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/qrCodeTV")
        ));
        return new QRScannerPage(driver, wait);
    }

    @Step("Navegar a QR Scanner (denegando permiso)")
    public QRScannerPage irAQRScannerDenegando() {
        abrirMenu();
        driver.findElements(AppiumBy.id(MENU_ITEM_ID)).stream()
                .filter(e -> e.getText().equals("QR Code Scanner"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró 'QR Code Scanner' en el menú"))
                .click();

        manejarPermiso(PERMISO_DENY);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/qrCodeTV")
        ));
        return new QRScannerPage(driver, wait);
    }

    @Step("Navegar a Geo Location")
    public GeoLocationPage irAGeoLocation() {
        abrirMenu();
        driver.findElements(AppiumBy.id(MENU_ITEM_ID)).stream()
                .filter(e -> e.getText().equals("Geo Location"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró 'Geo Location' en el menú"))
                .click();

        manejarPermiso(PERMISO_ALLOW);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/locationTV")
        ));
        return new GeoLocationPage(driver, wait);
    }

    @Step("Navegar a WebView")
    public WebViewPage irAWebView() {
        abrirMenu();
        driver.findElements(AppiumBy.id(MENU_ITEM_ID)).stream()
                .filter(e -> e.getText().equals("WebView"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró 'Webview' en el menú"))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.saucelabs.mydemoapp.android:id/webViewTV")
        ));
        return new WebViewPage(driver, wait);
    }
}