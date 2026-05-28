package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QRScannerPage extends BasePage {

    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/qrCodeTV";

    public QRScannerPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Obtener título de QR Scanner")
    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }
}