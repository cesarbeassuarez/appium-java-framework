package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Arrays;

public class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Swipe de ({startX},{startY}) a ({endX},{endY})")
    public void swipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs),
                PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    @Step("Manejar diálogo de permisos")
    public void manejarPermiso(String... botonesPermiso) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        for (String boton : botonesPermiso) {
            try {
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.id(boton)
                )).click();
                return;
            } catch (Exception e) {
                // Este botón no apareció, probar el siguiente
            }
        }
    }
}