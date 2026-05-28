package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "credencialesValidas")
    public Object[][] credencialesValidas() {
        return new Object[][] {
                {"bob@example.com", "10203040"}
        };
    }

    @DataProvider(name = "credencialesInvalidas")
    public Object[][] credencialesInvalidas() {
        return new Object[][] {
                {"", "",                "username", "Username is required"},
                {"bob@example.com", "", "password", "Enter Password"},
                {"", "10203040",        "username", "Username is required"}
        };
    }

    @Test(dataProvider = "credencialesValidas")
    public void loginExitoso(String username, String password) {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarCredenciales(username, password);
        ProductsPage resultado = loginPage.tapLogin();

        Assert.assertEquals(resultado.obtenerTitulo(), "Products",
                "No volvió a Products después del login");
    }

    @Test(dataProvider = "credencialesInvalidas")
    public void loginConCredencialesInvalidas(String username, String password, String campoError, String errorEsperado) {
        LoginPage loginPage = productsPage.irAlLogin();

        if (!username.isEmpty()) {
            loginPage.ingresarUsername(username);
        }
        if (!password.isEmpty()) {
            loginPage.ingresarPassword(password);
        }

        loginPage.tapLoginEsperandoError();

        String errorMostrado;
        if (campoError.equals("password")) {
            errorMostrado = loginPage.obtenerErrorPassword();
        } else {
            errorMostrado = loginPage.obtenerErrorUsername();
        }

        Assert.assertEquals(errorMostrado, errorEsperado);
    }

    @Test
    public void scrollHastaProducto() {
        productsPage.scrollHastaProducto("Sauce Labs Onesie");
        String producto = productsPage.obtenerTextoProducto("Sauce Labs Onesie");
        Assert.assertEquals(producto, "Sauce Labs Onesie");
    }

    @Test
    public void loginConClearYReingreso() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarUsername("usuario_equivocado");
        loginPage.limpiarUsername();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        ProductsPage resultado = loginPage.tapLogin();
        Assert.assertEquals(resultado.obtenerTitulo(), "Products");
    }

    @Test
    public void verificarTituloIncorrecto_falloIntencional() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarCredenciales("[email protected]", "10203040");
        loginPage.tapLogin();

        // Assertion que falla a propósito
        Assert.assertEquals(productsPage.obtenerTitulo(), "Texto Incorrecto",
                "Fallo intencional para probar screenshot en Allure");
    }
}