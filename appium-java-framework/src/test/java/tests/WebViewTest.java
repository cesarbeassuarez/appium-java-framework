package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.WebViewPage;

import java.util.Set;

public class WebViewTest extends BaseTest {

    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com";

    @DataProvider(name = "loginWebExitoso")
    public Object[][] loginWebExitoso() {
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"visual_user",   "secret_sauce"}
        };
    }

    @DataProvider(name = "loginWebFallido")
    public Object[][] loginWebFallido() {
        return new Object[][] {
                {"locked_out_user", "secret_sauce",  "Sorry, this user has been locked out"},
                {"standard_user",   "wrong_password", "Username and password do not match"}
        };
    }

    @Test
    public void navegarAWebView() {
        WebViewPage webViewPage = productsPage.irAWebView();
        Assert.assertEquals(webViewPage.obtenerTitulo(), "Webview");
    }

    @Test
    public void cargarUrlYVerificarContextos() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);

        // Esperar a que el contexto WEBVIEW esté disponible
        wait.until(d -> webViewPage.obtenerContextos().size() > 1);

        Set<String> contextos = webViewPage.obtenerContextos();
        Assert.assertTrue(contextos.contains("NATIVE_APP"));
        Assert.assertTrue(contextos.contains("WEBVIEW_com.saucelabs.mydemoapp.android"));
    }

    @Test
    public void switchAWebViewYVerificarTitulo() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        Assert.assertEquals(webViewPage.obtenerTituloWeb(), "Swag Labs");

        webViewPage.cambiarANativo();
        Assert.assertEquals(webViewPage.obtenerContextoActual(), "NATIVE_APP");
    }

    @Test(dataProvider = "loginWebExitoso")
    public void loginExitosoEnWebView(String username, String password) {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        webViewPage.loginEnWeb(username, password);
        Assert.assertTrue(webViewPage.estaEnInventario());

        webViewPage.cambiarANativo();
    }

    @Test(dataProvider = "loginWebFallido")
    public void loginFallidoEnWebView(String username, String password, String errorEsperado) {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        webViewPage.loginEnWeb(username, password);
        String error = webViewPage.obtenerErrorLoginWeb();
        Assert.assertTrue(error.contains(errorEsperado),
                "Esperaba: " + errorEsperado + " | Obtuvo: " + error);

        webViewPage.cambiarANativo();
    }
}