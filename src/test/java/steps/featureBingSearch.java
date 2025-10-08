package steps;

import cucumberRunner.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.webDriverUtils;

import java.util.List;

public class featureBingSearch {

    //1.- Iniciamos el driver globalmente
    WebDriver driver = DriverFactory.getDriver();
    //2.- Variable para validar el cambio de URL
    public String paginaActualAntesDelClick;
    //3.- Aqui iniciamos la clase utils, la cual tendra metodos para una espera sincrona del elemento
    public webDriverUtils utils = new webDriverUtils();

    @Given("Ingreso a la página de inicio de Bing")
    public void ingresoALaPaginaDeInicioDeBing() {
        // 1.- Abrir bing
        driver.get("https://www.bing.cl");
    }


    @When("Busco el texto {string}")
    public void buscoElTexto(String textoBusqueda) {
        // 1.- Identificamos el buscador por "name"
        By buscadorLocator = By.name("q");
        // 2.- Validamos en un metodo que esperara a que sea visible el webElement, esperara 30 segundos
        WebElement searchBoxVisible = utils.esperarPorPresencia(buscadorLocator, 30);
        // 3.- Guardamos la actual URL para evaluar en un futuro la comparacion de URL
        paginaActualAntesDelClick = driver.getCurrentUrl();
        // 4.- Instrucciones al webElement
        searchBoxVisible.sendKeys(textoBusqueda);
        searchBoxVisible.sendKeys(Keys.ENTER);

    }

    @And("Hago clic en el primer resultado de la búsqueda")
    public void hagoClicEnElPrimerResultadoDeLaBusqueda(){
        // 1.- Identificamos el elemento que obtiene los resultados
        By resultadosLocator = By.xpath("//ol[@id='b_results']//li//h2//a");
        
        // 2.- Validamos en un metodo que esperara que el webElement lista sea visible, esperara 30 segundos.
        List<WebElement> resultados = utils.esperarPorPresenciaDeElementos(resultadosLocator, 30);

        // 3.- Validacion si la lista esta vacia
        if (resultados.isEmpty()) {
            throw new AssertionError("La lista de resultados está vacía después de la espera.");
        }

        // 4.- Daremos clic al primer resultado de la busqueda
        WebElement primerResultado = resultados.get(0);
        primerResultado.click();

        // 5.- Se genera una pestaña nueva, entonces cambiamos a esta ultima pestaña generada
        utils.cambiarAUltimaPestana();
    }

    @Then("Valido que he navegado a una nueva página")
    public void validoQueHeNavegadoAUnaNuevaPagina() {

        // 1.- Obtener la URL después de la búsqueda
        String paginaActualDespuesDelClick = driver.getCurrentUrl();

        // 2.- Print a las URLs para validar
        System.out.println("URL antes de la búsqueda: " + paginaActualAntesDelClick);
        System.out.println("URL después de la búsqueda: " + paginaActualDespuesDelClick);

        // 3.- Validar la navegación, comparamos si las dos URLs NO son iguales.
        Assert.assertNotEquals(
                "¡Fallo en la navegación! La URL no cambió después de la búsqueda. Posiblemente se quedó en la misma página.",
                paginaActualAntesDelClick, paginaActualDespuesDelClick);
        // 4.- Print para validar el cambio de url
        System.out.println(" Validación exitosa: La URL cambió, se navegó a una nueva página.");
    }
}
