package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage extends BasePage {

    // Locators
    private static final String MENU_HAMBURGUESA = "View menu";
    private static final String LOGIN_MENU_ITEM = "Login Menu Item";
    private static final String TITULO = "title";

    public ProductsPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerTitulo() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId(TITULO)
        ));
        return driver.findElement(AppiumBy.accessibilityId(TITULO)).getText();
    }

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

    public void scrollHastaProducto(String nombreProducto) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollTextIntoView(\"" + nombreProducto + "\")"
        ));
    }

    public String obtenerTextoProducto(String nombreProducto) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + nombreProducto + "\")"
        )).getText();
    }
}