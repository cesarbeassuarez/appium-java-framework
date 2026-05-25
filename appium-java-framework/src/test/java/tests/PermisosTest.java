package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GeoLocationPage;
import pages.QRScannerPage;

public class PermisosTest extends BaseTest {

    @Test
    public void navegarAQRScanner() {
        QRScannerPage qrPage = productsPage.irAQRScanner();
        Assert.assertEquals(qrPage.obtenerTitulo(), "QR Code Scanner");
    }

    @Test
    public void navegarAGeoLocation() {
        GeoLocationPage geoPage = productsPage.irAGeoLocation();
        Assert.assertEquals(geoPage.obtenerTitulo(), "Geo Location");
    }

    @Test
    public void verificarCoordenadasGeoLocation() {
        GeoLocationPage geoPage = productsPage.irAGeoLocation();
        String latitud = geoPage.obtenerLatitud();
        String longitud = geoPage.obtenerLongitud();

        Assert.assertFalse(latitud.isEmpty(), "Latitud no debería estar vacía");
        Assert.assertFalse(longitud.isEmpty(), "Longitud no debería estar vacía");
    }

    @Test
    public void startStopObserving() {
        GeoLocationPage geoPage = productsPage.irAGeoLocation();
        geoPage.tapStartObserving();

        String latitud = geoPage.obtenerLatitud();
        Assert.assertFalse(latitud.isEmpty(), "Latitud no debería estar vacía después de Start");

        geoPage.tapStopObserving();
        // TODO: verificar qué cambia visualmente al hacer Stop
    }
}