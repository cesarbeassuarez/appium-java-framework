package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class DrawingPage extends BasePage {

    // Pantalla
    private static final String TITLE = "com.saucelabs.mydemoapp.android:id/drawingTV";
    private static final String CANVAS = "com.saucelabs.mydemoapp.android:id/signature_pad";
    private static final String CLEAR_BTN = "com.saucelabs.mydemoapp.android:id/clearBtn";
    private static final String SAVE_BTN = "com.saucelabs.mydemoapp.android:id/saveBtn";

    // Diálogo de permisos (sistema Android)
    private static final String PERMISSION_ALLOW = "com.android.permissioncontroller:id/permission_allow_button";
    private static final String PERMISSION_DENY = "com.android.permissioncontroller:id/permission_deny_button";

    // Diálogo de confirmación
    private static final String ALERT_TITLE = "com.saucelabs.mydemoapp.android:id/alertTitle";
    private static final String ALERT_MESSAGE = "android:id/message";
    private static final String ALERT_OK = "android:id/button1";

    public DrawingPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Obtener título de Drawing")
    public String obtenerTitulo() {
        return driver.findElement(AppiumBy.id(TITLE)).getText();
    }

    @Step("Dibujar trazo de ({startX},{startY}) a ({endX},{endY})")
    public void dibujar(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence trazo = new Sequence(finger, 1);

        trazo.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        trazo.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        trazo.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), endX, endY));
        trazo.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(trazo));
    }

    @Step("Limpiar canvas")
    public void limpiarCanvas() {
        driver.findElement(AppiumBy.id(CLEAR_BTN)).click();
    }

    @Step("Tap en Save")
    public void tapSave() {
        driver.findElement(AppiumBy.id(SAVE_BTN)).click();
    }

    @Step("Aceptar permiso de almacenamiento")
    public void aceptarPermiso() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.id(PERMISSION_ALLOW)
            )).click();
        } catch (Exception e) {
            // El permiso ya fue otorgado antes, no aparece
        }
    }

    @Step("Obtener mensaje de confirmación")
    public String obtenerMensajeConfirmacion() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(ALERT_MESSAGE)
        )).getText();
    }

    @Step("Obtener título de alerta")
    public String obtenerTituloAlerta() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(ALERT_TITLE)
        )).getText();
    }

    @Step("Cerrar alerta")
    public void cerrarAlerta() {
        driver.findElement(AppiumBy.id(ALERT_OK)).click();
    }

    @Step("Guardar dibujo y obtener mensaje")
    public String guardarYObtenerMensaje() {
        tapSave();
        String mensaje = obtenerMensajeConfirmacion();
        cerrarAlerta();
        return mensaje;
    }
}