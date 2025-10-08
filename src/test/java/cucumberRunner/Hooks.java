package cucumberRunner;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {
    public static WebDriver driver = DriverFactory.getDriver();

    @Before
    public void setUp() {
        //1.- Declaracion Before
        driver = DriverFactory.getDriver();
        //2.- Comando extra para asegurar que desde la ejecucion cargara inmediatamente el pageSource.
        driver.getPageSource();

    }

    @After
    public void tearDown() {
        //1.- Declaracion After
        DriverFactory.quitDriver();
    }

    // 1.- Inicio sin options
   // public static WebDriver getDriver() {
   //     return driver;
   // }

    // 2.- Inicio con options
    public static WebDriver getDriver() {
        // 1. Configurar las opciones para la robustez
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--no-sandbox");             // Útil en Linux/CI
        options.addArguments("--disable-dev-shm-usage");  // Para entornos con recursos limitados
        options.addArguments("--disable-gpu");            // Ayuda a prevenir fallos de renderizado
        options.addArguments("--start-maximized");        // Inicia el navegador en pantalla completa

        // 2. Crear y devolver el driver con las opciones

        return new ChromeDriver(options);

    }
}