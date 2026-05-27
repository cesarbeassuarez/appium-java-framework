package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    // Locators
    private static final String USERNAME_FIELD = "com.saucelabs.mydemoapp.android:id/nameET";
    private static final String PASSWORD_FIELD = "com.saucelabs.mydemoapp.android:id/passwordET";
    private static final String LOGIN_BUTTON = "Tap to login with given credentials";
    private static final String USERNAME_ERROR = "com.saucelabs.mydemoapp.android:id/nameErrorTV";
    private static final String PASSWORD_ERROR = "com.saucelabs.mydemoapp.android:id/passwordErrorTV";

    public LoginPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public LoginPage ingresarUsername(String username) {
        driver.findElement(AppiumBy.id(USERNAME_FIELD)).sendKeys(username);
        return this;
    }

    public LoginPage ingresarPassword(String password) {
        driver.findElement(AppiumBy.id(PASSWORD_FIELD)).sendKeys(password);
        return this;
    }

    public LoginPage limpiarUsername() {
        driver.findElement(AppiumBy.id(USERNAME_FIELD)).clear();
        return this;
    }

    public LoginPage ingresarCredenciales(String username, String password) {
        ingresarUsername(username);
        ingresarPassword(password);
        return this;
    }
    public ProductsPage tapLogin() {
        driver.findElement(AppiumBy.accessibilityId(LOGIN_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("title")
        ));
        return new ProductsPage(driver, wait);
    }
    public LoginPage tapLoginEsperandoError() {
        driver.findElement(AppiumBy.accessibilityId(LOGIN_BUTTON)).click();
        return this;
    }
    public String obtenerErrorUsername() {
        return driver.findElement(AppiumBy.id(USERNAME_ERROR)).getText();
    }
    public String obtenerErrorPassword() {
        return driver.findElement(AppiumBy.id(PASSWORD_ERROR)).getText();
    }
}