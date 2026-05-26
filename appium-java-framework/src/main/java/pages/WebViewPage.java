package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class WebViewPage extends BasePage {

    // Elementos nativos (pantalla WebView de My Demo App)
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/webViewTV";
    private static final String URL_INPUT = "com.saucelabs.mydemoapp.android:id/urlET";
    private static final String GO_BTN = "com.saucelabs.mydemoapp.android:id/goBtn";

    // Contexto WebView
    private static final String WEBVIEW_CONTEXT = "WEBVIEW_com.saucelabs.mydemoapp.android";
    private static final String NATIVE_CONTEXT = "NATIVE_APP";

    public WebViewPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }

    public void cargarUrl(String url) {
        var campo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(URL_INPUT)
        ));
        campo.clear();
        campo.sendKeys(url);

        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id(GO_BTN)
        )).click();
    }

    public Set<String> obtenerContextos() {
        return driver.getContextHandles();
    }

    public void cambiarAWebView() {
        wait.until(d -> driver.getContextHandles().size() > 1);
        driver.context(WEBVIEW_CONTEXT);
    }

    public void cambiarANativo() {
        driver.context(NATIVE_CONTEXT);
    }

    public String obtenerContextoActual() {
        return driver.getContext();
    }

    // --- Métodos en contexto WEBVIEW (locators web) ---

    public String obtenerTituloWeb() {
        return driver.getTitle();
    }

    public void loginEnWeb(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("user-name")
        )).sendKeys(username);

        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    public String obtenerErrorLoginWeb() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")
        )).getText();
    }

    public boolean estaEnInventario() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.className("inventory_list")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}