package tests;

import org.testng.Assert;

import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginTest extends BaseTest{

    @Test
    public void loginExitoso() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        ProductsPage resultado = loginPage.tapLogin();

        String titulo = resultado.obtenerTitulo();
        Assert.assertEquals(titulo, "Products",
                "No volvió a la pantalla de Products después del login");
    }

    @Test
    public void scrollHastaProducto() {
        productsPage.scrollHastaProducto("Sauce Labs Onesie");

        String producto = productsPage.obtenerTextoProducto("Sauce Labs Onesie");
        Assert.assertEquals(producto, "Sauce Labs Onesie",
                "No encontró el producto después del scroll");
    }

    @Test
    public void loginConClearYReingreso() {
        LoginPage loginPage = productsPage.irAlLogin();

        loginPage.ingresarUsername("usuario_equivocado");
        loginPage.limpiarUsername();
        loginPage.ingresarCredenciales("bob@example.com", "10203040");
        ProductsPage resultado = loginPage.tapLogin();

        String titulo = resultado.obtenerTitulo();
        Assert.assertEquals(titulo, "Products");
    }

    @Test
    public void loginConCamposVacios() {
        LoginPage loginPage = productsPage.irAlLogin();
        loginPage.tapLoginEsperandoError();

        String errorUsername = loginPage.obtenerErrorUsername();
        Assert.assertEquals(errorUsername, "Username is required",
                "No mostró error de username vacío");
    }
}