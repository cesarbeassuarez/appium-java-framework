# 🔬 Appium + Java + Android

**A mobile automation framework built from scratch with Appium + Java + TestNG, targeting a real Android app on a physical device. Every architectural decision, every real error, and every iteration documented publicly.**

This is not a tutorial project. It's a working framework where `BaseTest` centralizes driver lifecycle, `WebDriverWait` replaces every `Thread.sleep`, W3C Actions draw on a canvas, and `getContextHandles()` switches between native and web contexts. 37 tests, 13 page objects, Allure Reports with `@Step` and screenshots on failure. Built by a QA engineer with 4+ years of experience testing enterprise ERP systems.

[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/cesarbeassuarez/appium-java-framework)

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Automation | Appium 2.x/3.x (java-client) |
| Driver | UiAutomator2 |
| Test Runner | TestNG |
| Reporting | Allure Reports (`@Step`, screenshots on failure) |
| Build | Maven |
| CI/CD | GitHub Actions (android-emulator-runner) |
| Report Hosting | GitHub Pages |
| Device (local) | Motorola G51 5G (physical, USB) |
| Device (CI) | Android emulator (API 30, x86_64) |
| AUT | Sauce Labs My Demo App (`mda-2.2.0-25.apk`) |

## ⚙️ CI/CD Pipeline

Every push to `main` triggers an automated pipeline that boots an Android emulator inside the GitHub Actions runner:

```
Push to main
    ↓
GitHub Actions (Ubuntu + KVM)
    ↓
Java 17 + Node.js 20 + Appium Server + UiAutomator2
    ↓
Android emulator (API 30, Pixel 6, x86_64)
    ↓
mvn clean test (37 tests)
    ↓
Allure Report generated
    ↓
Published to GitHub Pages
```

**Live report:** [cesarbeassuarez.github.io/appium-java-framework](https://cesarbeassuarez.github.io/appium-java-framework/)

> [!NOTE]
> Appium CI is fundamentally different from Selenium CI. Selenium runners have Chrome preinstalled — just run tests. Appium requires booting an entire Android ecosystem: SDK, emulator, Appium Server, UiAutomator2 driver, APK install. The pipeline took 8 iterations to get right. The report includes real failures: 3 tests break in CI because the emulator lacks Google Play Services (QR Scanner, Geo Location). That's expected — those features require hardware the emulator doesn't have.

## 📁 Project Structure

**Why this structure:**

- **`BaseTest` centralizes lifecycle** — driver creation, capabilities, teardown, and `WebDriverWait` live in one place. Test classes extend `BaseTest` and get `driver`, `wait`, and `productsPage` ready to use. `System.getProperty` with defaults makes the same code work on the physical Motorola and the CI emulator without changing anything.
- **`System.getProperty` for CI/local portability** — UDID, timeouts, ChromeDriver path, and wait durations are configurable via Maven `-D` flags. Local defaults point to the Motorola G51; CI passes emulator values. No environment files, no profiles, no conditional logic in tests.
- **One Page Object per screen** — same principle as the Selenium framework. If Sauce Labs changes a `resource-id`, one class changes.
- **`@Step` on every page method** — Allure reports show business actions ("Ingresar credenciales: bob@example.com / 10203040"), not method calls. Sub-steps nest automatically.
- **`chromedriver-win64/` at repo root** — WebView tests need a ChromeDriver matching the device's Chrome version. On Windows (local), it's loaded via capability. On CI (Linux), Appium auto-downloads it. The folder is only used locally.

```text
appium-java-framework/                    # repo root
├── .github/
│   └── workflows/
│       └── appium-tests.yml             # CI: emulator + Appium + tests + Allure to GitHub Pages
├── appium-java-framework/               # Maven project
│   ├── src/
│   │   ├── main/java/
│   │   │   └── pages/
│   │   │       ├── BasePage.java         # Protected driver/wait, swipe(), manejarPermiso()
│   │   │       ├── LoginPage.java        # Login form: credentials, errors, clear + re-enter
│   │   │       ├── ProductsPage.java     # Catalog: menu, scroll, product selection, navigation
│   │   │       ├── ProductDetailPage.java # Product: name, price, quantity, color, add to cart
│   │   │       ├── CartPage.java         # Cart: items, totals, remove, proceed to checkout
│   │   │       ├── CheckoutShippingPage.java  # Shipping form: 7 fields + navigation
│   │   │       ├── CheckoutPaymentPage.java   # Payment form: card details + navigation
│   │   │       ├── ReviewOrderPage.java       # Order review: products, shipping, payment, totals
│   │   │       ├── CheckoutCompletePage.java  # Confirmation: thank you + continue shopping
│   │   │       ├── DrawingPage.java      # Canvas: W3C Actions drawing, save, clear
│   │   │       ├── QRScannerPage.java    # QR scanner: camera permission handling
│   │   │       ├── GeoLocationPage.java  # Geolocation: location permission handling
│   │   │       └── WebViewPage.java      # WebView: URL input, context switch NATIVE↔WEBVIEW
│   │   └── test/java/
│   │       └── tests/
│   │           ├── AllureListener.java   # Allure listener: screenshot attachment on test failure
│   │           ├── BaseTest.java         # @BeforeMethod/@AfterMethod, capabilities, System.getProperty
│   │           ├── CartTest.java         # Add, verify totals, remove products
│   │           ├── CheckoutTest.java     # Full checkout: shipping → payment → review → complete
│   │           ├── GestosTest.java       # W3C Actions: draw on canvas, save, clear
│   │           ├── LifecycleTest.java    # App lifecycle: background, terminate, relaunch
│   │           ├── LoginTest.java        # Login: valid, invalid, clear+re-enter. DataProvider.
│   │           ├── PermisosTest.java     # System permissions: camera, location (accept/deny)
│   │           ├── PrimerTest.java       # First test from the series (open app, verify title)
│   │           ├── ProductDetailTest.java # Product detail verification. DataProvider.
│   │           └── WebViewTest.java      # WebView: context switch, login on saucedemo.com. DataProvider.
│   ├── apk/
│   │   └── mda-2.2.0-25.apk            # Sauce Labs My Demo App
│   ├── allure-results/                   # Generated by Allure (gitignored)
│   └── pom.xml                          # Dependencies: java-client, TestNG, Allure, AspectJ
├── chromedriver-win64/
│   └── chromedriver.exe                  # ChromeDriver 148 for WebView testing (local/Windows only)
└── README.md
```

---

## Folder responsibilities

### `pages/`
Page Object Model. Each screen of My Demo App is a Java class with private locators (`AppiumBy.id`, `AppiumBy.accessibilityId`) and public methods representing user actions. Every public method has `@Step` for Allure reporting. Tests never see a locator directly.

`BasePage` holds `driver` and `wait` as `protected` fields, plus shared methods: `swipe()` for W3C Actions-based scrolling and `manejarPermiso()` with varargs for handling Android system permission dialogs (camera, location, storage) in a single reusable method.

Navigation methods return the next page: `productsPage.irAlLogin()` returns a `LoginPage`. This enables chaining and makes test flow explicit.

### `tests/`
Test classes extending `BaseTest`. Each class covers a feature area. `BaseTest` handles:
- Driver creation with `UiAutomator2Options`
- `System.getProperty` for UDID, timeouts, app path, ChromeDriver path (local vs CI)
- `WebDriverWait` initialization
- Waiting for the app to load (Products screen visible)
- Driver teardown in `@AfterMethod`

DataProviders are inline in test classes (`LoginTest`, `ProductDetailTest`, `WebViewTest`) — kept close to the tests that use them. `AllureListener` implements TestNG's `ITestListener` to capture screenshots on test failure and attach them to the Allure report automatically.

### `apk/`
The test target. Sauce Labs My Demo App v2.2.0 (build 25). A native Android app with login, product catalog, cart, checkout, drawing canvas, QR scanner, geolocation, and WebView. Downloaded from [saucelabs/my-demo-app-android](https://github.com/saucelabs/my-demo-app-android).

### `chromedriver-win64/`
ChromeDriver matching Chrome 148 on the Motorola G51. Required for WebView context switching on Windows — Appium needs a ChromeDriver that matches the device's Chrome version. Loaded via `appium:chromedriverExecutable` capability. On CI (Linux), this folder is ignored and Appium auto-downloads the correct version.

### `.github/workflows/appium-tests.yml`
CI pipeline using `reactivecircus/android-emulator-runner`. Boots an API 30 emulator with KVM acceleration, installs Appium + UiAutomator2, runs all tests with CI-specific timeouts, generates Allure Report, and deploys to GitHub Pages. Took 8 iterations to get working.

## 🧭 Build Log

Each entry represents a real development iteration. Full context on decisions and tradeoffs documented on [my blog](https://cesarbeassuarez.dev/tag/appium/).

| # | Focus | Date |
|---|---|---|
| 1 | [Appium: decisiones técnicas para mobile automation](https://cesarbeassuarez.dev/appium-java-android-decisiones-tecnicas-mobile-automation/) — Appium vs Maestro vs Espresso. Why Java, why Android first. LATAM market analysis and technical decisions. | 02 May 2026 |
| 2 | [Setup completo: Node.js, Android Studio, Appium Server y emulador](https://cesarbeassuarez.dev/appium-setup-completo-emulador-android/) — Node.js, Android Studio, SDK, environment variables, Pixel 8 emulator, Appium 3.3.1, UiAutomator2. Real errors on 8GB RAM. | 04 May 2026 |
| 3 | [Primer test: abrir la app en el emulador con Appium](https://cesarbeassuarez.dev/appium-primer-test-maven-app-emulador/) — Maven project, Selenium/java-client conflict with ContextAware resolved, timeouts for 8GB RAM. First green test. | 05 May 2026 |
| 4 | [Appium Inspector: encontrar elementos en la app con dispositivo físico](https://cesarbeassuarez.dev/appium-inspector-locators-dispositivo-fisico/) — Motorola G51 via USB instead of emulator. Appium Inspector to explore elements, locators for Products and Login. Two real errors resolved. | 06 May 2026 |
| 5 | [Interacciones básicas: tap, input, scroll y assertions en Appium](https://cesarbeassuarez.dev/appium-interacciones-tap-input-scroll-assertions/) — Full login, scroll with UiScrollable, clear with re-enter and empty fields. 4 tests, 5 real errors. Thread.sleep included. | 06 May 2026 |
| 6 | [Waits y sincronización: reemplazando Thread.sleep por WebDriverWait](https://cesarbeassuarez.dev/appium-waits-sincronizacion-webdriverwait-java/) — 5 Thread.sleep eliminated, WebDriverWait with ExpectedConditions, a duplicate driver error, and time comparison. 4 tests, 0 sleeps. | 06 May 2026 |
| 7 | [Page Object Model en Appium](https://cesarbeassuarez.dev/appium-page-object-model-java-pom/) — LoginTest refactored to POM: BasePage, ProductsPage, LoginPage. Centralized locators, no navigation duplication. Same 4 tests, better code. | 14 May 2026 |
| 8 | [Product Detail y Cart en Appium: del catálogo al carrito](https://cesarbeassuarez.dev/appium-product-detail-cart-tests-java/) — ProductDetailPage and CartPage: select product, quantity, color, add to cart, verify totals, remove. 8 tests, 3 real errors. | 17 May 2026 |
| 9 | [Checkout completo en Appium: del carrito a la confirmación de compra](https://cesarbeassuarez.dev/appium-checkout-completo-java/) — 4 new pages for checkout flow: forms, scroll, waits post-scroll, invisible spaces in assertions. 16 tests, 9 pages, 0 failures. | 19 May 2026 |
| 10 | [W3C Actions en Appium: gestos programáticos con Drawing](https://cesarbeassuarez.dev/appium-w3c-actions-gestos-drawing/) — DrawingPage with W3C Actions for canvas drawing, sidebar menu, system permissions, Inspector Gestures and Recorder. 19 tests, 10 pages. | 22 May 2026 |
| 11 | [Permisos del sistema y ciclo de vida en Appium: QR Scanner, Geo Location y BaseTest](https://cesarbeassuarez.dev/appium-permisos-lifecycle-basetest/) — Android permission dialogs with manejarPermiso() varargs, BaseTest to eliminate duplication, runAppInBackground and terminateApp. 25 tests. | 25 May 2026 |
| 12 | [WebViews en Appium: cambio de contexto nativo a web](https://cesarbeassuarez.dev/appium-webview-contexto-nativo-web/) — ChromeDriver for Chrome 148, getContextHandles(), switch to WEBVIEW, login on saucedemo.com with web locators. From 25 to 30 green tests. | 25 May 2026 |
| 13 | [DataProvider en Appium: parametrización de tests con múltiples datos](https://cesarbeassuarez.dev/appium-dataprovider-testng-parametrizacion-tests/) — @DataProvider in Appium: 3 parametrized tests, 5 real errors, app findings. From 30 to 36 tests. | 27 May 2026 |
| 14 | [Allure Reports en Appium: reportes visuales con @Step y screenshots automáticos](https://cesarbeassuarez.dev/appium-allure-reports-step-screenshots-testng/) — @Step on 13 pages, automatic screenshot on failure, 3 real errors during integration. From 36 to 37 tests with professional reporting. | 28 May 2026 |
| 15 | [CI/CD con GitHub Actions: 37 tests de Appium corriendo en un emulador Android](https://cesarbeassuarez.dev/appium-cicd-github-actions-emulador-android/) — 8 attempts, real errors, KVM, 320px screen. Full pipeline with Android emulator, Allure Report on GitHub Pages. 89% in CI. | 31 May 2026 |

## 🔄 Selenium vs Appium — Key differences in this framework

| Concept | Selenium (Java) | Appium (Java) |
|---|---|---|
| Target | Web browsers (Chrome, Firefox, Edge) | Mobile apps (Android native, hybrid, WebView) |
| Driver | ChromeDriver (auto-managed by WebDriverManager) | UiAutomator2 via Appium Server (Node.js) |
| Server | Not needed (direct browser communication) | Appium Server required (client-server architecture) |
| Locators | `By.id`, `By.cssSelector`, `By.xpath` | `AppiumBy.id`, `AppiumBy.accessibilityId`, `UiScrollable` |
| Scroll | JavaScript `scrollIntoView()` | `UiScrollable` or W3C Actions with `PointerInput` |
| Gestures | Not applicable | W3C Actions: `PointerInput`, `Sequence`, `perform()` |
| Waits | `WebDriverWait` + `ExpectedConditions` | Same — shared Selenium dependency |
| Page Object Model | Same pattern | Same pattern, mobile locators |
| Context switching | Not needed (always in browser) | `NATIVE_APP` ↔ `WEBVIEW` with `getContextHandles()` |
| Permissions | Not applicable | Android system dialogs: camera, location, storage |
| App lifecycle | Not applicable | `runAppInBackground()`, `terminateApp()`, `activateApp()` |
| Device management | Browser on same machine | Physical device (USB) or emulator (AVD) |
| CI/CD complexity | Low (Chrome preinstalled in runners) | High (emulator + SDK + Appium Server + KVM) |
| Data-driven | TestNG `@DataProvider` + `Object[][]` | Same — shared TestNG |
| Reporting | Allure with `@Step` + screenshots | Same — shared Allure integration |
| Config | `config.properties` + `ConfigReader.java` | `System.getProperty` with defaults in `BaseTest` |
| WebView testing | Not applicable (already in browser) | ChromeDriver matching device Chrome version required |

## 🎯 What makes this different

- **Not a tutorial project.** Every decision reflects real testing experience on enterprise systems. 13 pages covering a complete e-commerce flow, not isolated examples.
- **Physical device + emulator + CI.** Tests run on a Motorola G51 via USB locally and on an Android emulator via GitHub Actions in CI. Same code, different configs via `System.getProperty`.
- **Documented tradeoffs.** I explain *why*, not just *how*. Why Appium over Maestro. Why W3C Actions over deprecated TouchAction. Why `manejarPermiso()` uses varargs instead of try/catch per screen.
- **Built in public.** 15 posts documenting every real error, every failed attempt, every iteration. The CI/CD pipeline took 8 tries — all documented.
- **CI/CD with Android emulator.** Not just "it works on my machine". The pipeline boots a real Android emulator in GitHub Actions with KVM acceleration.
- **Parallel series.** Each Appium concept is contextualized against the equivalent Selenium approach — see the [Selenium framework](https://github.com/cesarbeassuarez/selenium-java-framework) for the other side.

## 📝 Related content

- Blog (Appium series): [cesarbeassuarez.dev/tag/appium](https://cesarbeassuarez.dev/tag/appium/)
- Blog (Selenium series): [cesarbeassuarez.dev/tag/selenium](https://cesarbeassuarez.dev/tag/selenium/)
- Blog (Playwright series): [cesarbeassuarez.dev/tag/playwright](https://cesarbeassuarez.dev/tag/playwright/)
- Live report: [cesarbeassuarez.github.io/appium-java-framework](https://cesarbeassuarez.github.io/appium-java-framework/)
- Selenium framework: [github.com/cesarbeassuarez/selenium-java-framework](https://github.com/cesarbeassuarez/selenium-java-framework)
- Playwright framework: [github.com/cesarbeassuarez/playwright-typescript-framework](https://github.com/cesarbeassuarez/playwright-typescript-framework)
- LinkedIn: [linkedin.com/in/cesarbeassuarez](https://www.linkedin.com/in/cesarbeassuarez/)
