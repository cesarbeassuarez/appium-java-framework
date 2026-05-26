package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.WebViewPage;

import java.util.Set;

public class WebViewTest extends BaseTest {

    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com";

    @Test
    public void navegarAWebView() {
        WebViewPage webViewPage = productsPage.irAWebView();
        Assert.assertEquals(webViewPage.obtenerTitulo(), "Webview");
    }

    @Test
    public void cargarUrlYVerificarContextos() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);

        Set<String> contextos = webViewPage.obtenerContextos();
        Assert.assertTrue(contextos.contains("NATIVE_APP"));
        Assert.assertTrue(contextos.contains("WEBVIEW_com.saucelabs.mydemoapp.android"));
    }

    @Test
    public void switchAWebViewYVerificarTitulo() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        String titulo = webViewPage.obtenerTituloWeb();
        Assert.assertEquals(titulo, "Swag Labs");

        webViewPage.cambiarANativo();
        Assert.assertEquals(webViewPage.obtenerContextoActual(), "NATIVE_APP");
    }

    @Test
    public void loginExitosoEnWebView() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        webViewPage.loginEnWeb("standard_user", "secret_sauce");
        Assert.assertTrue(webViewPage.estaEnInventario());

        webViewPage.cambiarANativo();
    }

    @Test
    public void loginFallidoEnWebView() {
        WebViewPage webViewPage = productsPage.irAWebView();
        webViewPage.cargarUrl(SAUCEDEMO_URL);
        webViewPage.cambiarAWebView();

        webViewPage.loginEnWeb("standard_user", "wrong_password");
        String error = webViewPage.obtenerErrorLoginWeb();
        Assert.assertTrue(error.contains("Username and password do not match"));

        webViewPage.cambiarANativo();
    }
}