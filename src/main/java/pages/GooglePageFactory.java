package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class GooglePageFactory {

    /**
     * Поле для настройки ожидания во время выполнения тестов.
     * @author Фоминский Клим
     */
    private WebDriverWait wait;

    /**
     * Веб-элемент поля поиска.
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//textarea[@type='search']")
    WebElement searchField;

    /**
     * Веб-элемент кнопки поиска.
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//div[count(child::*)=2]//input[@name='btnK']")
    WebElement searchButton;

    /**
     * Ссылки страниц результата поиска.
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//a[@jsname='UWckNb']//cite")
    List<WebElement> links;

    /**
     * Заголовки страниц результата поиска.
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//a[@jsname='UWckNb']//h3")
    List<WebElement> linksHeaders;

    /**
     * Блок обмена валют
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//*[contains(@class,'CurrencyExchangeBlock')]//h2[contains(@class,'CurrencyExchange_currency-exchange-title')]")
    WebElement currencyExchangeBlock;

    /**
     * Веб-элементы блока обмена валют
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//div[contains(@class,'CurrencyExchangeItem_currency-exchange-item-wrapper__b_tuv')]")
    List<WebElement> exchangeItemCurrency;

    /**
     * Первая строка таблицы Преподаватели кафедры программирования
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//table[contains(*,'Преподаватели кафедры программирования')]/tbody/tr[2]/td")
    WebElement programmingTeachersTableFirstRow;

    /**
     * Последняя строка таблицы Преподаватели кафедры программирования
     * @author Фоминский Клим
     */
    @FindBy(how = How.XPATH, using = "//table[contains(*,'Преподаватели кафедры программирования')]/tbody/tr[last()]/td")
    WebElement programmingTeachersTableLastRow;

    /**
     * Конструктор выполняет настройку webdriver chrome и инициализирует wait.
     * @param chromeDriver веб-драйвер для взаимодействия с веб-браузером Chrome.
     * @author Фоминский Клим.
     */
    public GooglePageFactory(WebDriver chromeDriver) {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
    }

    /**
     * Метод выполняет поисковый запрос.
     * @param word запрос.
     * @author Фоминский Клим
     */
    public void find(String word){
        searchField.click();
        searchField.sendKeys(word);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[count(child::*)=2]//input[@name='btnK']")));
        searchButton.click();
    }

    /**
     * Метод ждёт, когда ссылки на страницы результата поиска станут видимы и возвращает их.
     * @return ссылки на страницы результата поиска.
     * @author Фоминский Клим
     */
    public List<WebElement> getLinks() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='search']")));
        return links;
    }

    /**
     * Метод ждёт, когда заголовки ссылок на страницы результата поиска станут видимы и возвращает их.
     * @return заголовки ссылок на страницы результата поиска
     * @author Фоминский Клим
     */
    public List<WebElement> getLinksHeaders() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@jsname='UWckNb']//h3")));
        return linksHeaders;
    }

    /**
     * Метод ждёт, когда блок обмена валют станет видимым и возвращает его.
     * @return блок обмена валют.
     * @author Фоминский Клим
     */
    public WebElement getCurrencyExchangeBlock(){
        wait.until(ExpectedConditions.visibilityOf(currencyExchangeBlock));
        return currencyExchangeBlock;
    }

    /**
     * Метод ждёт, когда элементы блока обмена валют станут видимы и возвращает их.
     * @return элементы блока обмена валют
     * @author Фоминский Клим
     */
    public List<WebElement> getExchangeItemCurrency() {
        wait.until(ExpectedConditions.visibilityOfAllElements(exchangeItemCurrency));
        return exchangeItemCurrency;
    }

    /**
     * Метод ждёт, когда первая строка таблицы Преподаватели кафедры программирования станет видима и возвращает её.
     * @return первая строка таблицы Преподаватели кафедры программирования
     * @author Фоминский Клим
     */
    public WebElement getProgrammingTeachersTableFirstRow() {
        wait.until(ExpectedConditions.visibilityOf(programmingTeachersTableFirstRow));
        return programmingTeachersTableFirstRow;
    }

    /**
     * Метод ждёт, когда последняя строка таблицы Преподаватели кафедры программирования станет видима и возвращает её.
     * @return последняя строка таблицы Преподаватели кафедры программирования
     * @author Фоминский Клим
     */
    public WebElement getProgrammingTeachersTableLastRow() {
        wait.until(ExpectedConditions.visibilityOf(programmingTeachersTableLastRow));
        return programmingTeachersTableLastRow;
    }
}
