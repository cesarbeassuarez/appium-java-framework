package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeoLocationPage extends BasePage {

    // Pantalla
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/locationTV";
    private static final String START_BTN = "com.saucelabs.mydemoapp.android:id/startBtn";
    private static final String STOP_BTN = "com.saucelabs.mydemoapp.android:id/stopBtn";
    private static final String LINK = "com.saucelabs.mydemoapp.android:id/linkedTV";

    private static final String LATITUDE_TV = "com.saucelabs.mydemoapp.android:id/latitudeTV";
    private static final String LONGITUDE_TV = "com.saucelabs.mydemoapp.android:id/longitudeTV";

    public GeoLocationPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String obtenerTitulo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(TITLE)
        )).getText();
    }

    public String obtenerLatitud() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(LATITUDE_TV)
        )).getText();
    }

    public String obtenerLongitud() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(LONGITUDE_TV)
        )).getText();
    }

    public void tapStartObserving() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id(START_BTN)
        )).click();
    }

    public void tapStopObserving() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id(STOP_BTN)
        )).click();
    }
}