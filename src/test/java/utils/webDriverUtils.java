package utils;

import cucumberRunner.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class webDriverUtils {

    //1.- Obtenemos el driver
    WebDriver driver = DriverFactory.getDriver();

    //2.- Declaracion metodo para validar que esperara X tiempo por X elemento, si existe retorna el objeto y si no existe , lanzara error
    public WebElement esperarPorPresencia(By locator, int segundosEspera) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(segundosEspera));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Elemento no encontrado en el DOM después de " + segundosEspera + " segundos -> " + locator, e);
        } catch (Exception e) {
            throw new RuntimeException("Error al esperar el elemento -> " + locator, e);
        }
    }
    //3.- Igual al metodo anterior pero para listas
    public List<WebElement> esperarPorPresenciaDeElementos(By locator, int segundosEspera) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(segundosEspera));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Elementos no encontrados en el DOM después de " + segundosEspera + " segundos -> " + locator, e);
        }   catch (Exception e) {
            throw new RuntimeException("Elementos no visibles después de " + segundosEspera + " segundos -> " + locator, e);
        }
    }

    public void cambiarAUltimaPestana() {

        // 1. Obtener todos los identificadores de las pestañas abiertas
        Set<String> handles =  driver.getWindowHandles();

        // 2. Convertir el Set a una lista. El orden en la lista es el orden de apertura
        ArrayList<String> pestanas = new ArrayList<>(handles);

        // 3. Obtener el índice de la última pestaña
        int indiceUltimaPestana = pestanas.size() - 1;

        // 4. Obtener el identificador real de la última pestaña
        String ultimaPestanaHandle = pestanas.get(indiceUltimaPestana);

        // 5. Cambiar el enfoque del driver
        driver.switchTo().window(ultimaPestanaHandle);
        driver.getPageSource();

        System.out.println("Enfoque de WebDriver cambiado a la última pestaña generada.");
    }
}