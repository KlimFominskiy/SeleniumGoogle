package com.google;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import pages.GooglePageFactory;

import java.util.HashMap;

public class GoogleTests extends BaseGoogleTests{

    /**
     * Тест-кейс 1.1. Проверка результата поиска.
     * @param resultPageNumber порядковый номер страницы
     * @param searchWord поисковый запрос
     * @param expectedSearchResult ожидаемая ссылка страницы в результате запроса
     * @author Фоминский Клим
     */
    @DisplayName("Проверка результата поиска")
    @CsvSource({"1,Гладиолусы,wikipedia.org"})
    @ParameterizedTest
    public void googleSearchLink(int resultPageNumber, String searchWord, String expectedSearchResult) {
        GooglePageFactory googlePageFactory = PageFactory.initElements(chromeDriver, GooglePageFactory.class);

        googlePageFactory.find(searchWord);

        Assertions.assertFalse(googlePageFactory.getLinks().isEmpty(),
                "Не найдено результатов, содержащих поисковый запрос "+searchWord);

        Assertions.assertTrue(googlePageFactory.getLinks().get(resultPageNumber - 1).getText().contains(expectedSearchResult),
                "Страница № "+resultPageNumber+" не содержит ссылку "+expectedSearchResult+" после поискового запроса "+searchWord);
    }

    /**
     * Тест-кейс 1.2. Проверка курсов валют.
     * @param resultPageNumber порядковый номер страницы
     * @param searchWord поисковый запрос
     * @param expectedSearchResult ожидаемая ссылка страницы в результате запроса
     * @param sectionName название проверямой секции
     * @author Фоминский Клим
     */
    @DisplayName("Проверка курсов валют")
    @CsvSource({"1,Открытие,Банк Открытие: кредит наличными — под 4% каждому ...,Курс обмена"})
    @ParameterizedTest
    public void googleOpenCurrency(int resultPageNumber, String searchWord, String expectedSearchResult, String sectionName) {
        GooglePageFactory googlePageFactory = PageFactory.initElements(chromeDriver, GooglePageFactory.class);

        googlePageFactory.find(searchWord);
        Assertions.assertFalse(googlePageFactory.getLinks().isEmpty(),
                "Не найдено результатов после поиска по слову " + searchWord);
        Assertions.assertTrue(googlePageFactory.getLinksHeaders().get(resultPageNumber - 1).getText().contains(expectedSearchResult),
                "Текст заголовка ссылки страницы № " + resultPageNumber + " не содержит " + expectedSearchResult + " при запросе " + searchWord);

        long chromeDriversNumber = chromeDriver.getWindowHandles().size();
        Actions actions = new Actions(chromeDriver);
        actions.keyDown(Keys.CONTROL)
                .click(googlePageFactory.getLinks().get(resultPageNumber - 1))
                .keyUp(Keys.CONTROL)
                .perform();

        String lastWindowsHandle = chromeDriver.getWindowHandles()
                .toArray()[chromeDriver.getWindowHandles().size()-1]
                .toString();

        Assertions.assertTrue(chromeDriversNumber < chromeDriver.getWindowHandles().size(),
                "Новая вкладка не открылась");
        chromeDriver.switchTo().window(lastWindowsHandle);

        Assertions.assertEquals(googlePageFactory.getCurrencyExchangeBlock().getText(),sectionName,
                "Название блока отлично от "+sectionName);

        Assertions.assertTrue(googlePageFactory.getExchangeItemCurrency().size()/3 >= 3,
                "Блок курса обмена содержит менее трёх курсов");

        HashMap<String, Float> saleRates = new HashMap<>();
        HashMap<String, Float> purchaseRates = new HashMap<>();
        for (int i = 0; i < googlePageFactory.getExchangeItemCurrency().size(); i += 3) {
            purchaseRates.put(googlePageFactory.getExchangeItemCurrency().get(i).getText(),
                    Float.parseFloat(googlePageFactory.getExchangeItemCurrency().get(i + 1).getText()));
            saleRates.put(googlePageFactory.getExchangeItemCurrency().get(i).getText(),
                    Float.parseFloat(googlePageFactory.getExchangeItemCurrency().get(i + 2).getText()));
        }
        Assertions.assertTrue(purchaseRates.get("USD") < saleRates.get("USD"),
                "Курс покупки доллара больше или равен курсу продажи доллара");
        Assertions.assertTrue(purchaseRates.get("EUR") < saleRates.get("EUR"),
                "Курс покупки евро больше или равен курсу продажи евро");
    }

    /**
     * Тест-кейс 1.3. Проверка таблицы.
     * @param resultPageNumber порядковый номер страницы
     * @param searchWord поисковый запрос
     * @param expectedSearchResult ожидаемый заголовок страницы в результате запроса
     * @param expectedUrl ожидаемый адрес страницы
     * @author Фоминский Клим
     */
    @DisplayName("Проверка таблицы")
    @CsvSource({"1,таблица,Таблица,wikipedia.org"})
    @ParameterizedTest
    public void checkTable(int resultPageNumber, String searchWord, String expectedSearchResult, String expectedUrl){
        GooglePageFactory googlePageFactory = PageFactory.initElements(chromeDriver, GooglePageFactory.class);

        googlePageFactory.find(searchWord);

        Actions actions = new Actions(chromeDriver);
        actions.click(googlePageFactory.getLinks().get(resultPageNumber - 1)).perform();
        expectedSearchResult = googlePageFactory.getLinksHeaders().get(resultPageNumber - 1).getText();
        Assertions.assertTrue(chromeDriver.getCurrentUrl().contains(expectedUrl),
                "Открылась страница с отличным адресом от "+expectedUrl+" при клике на заголовок "+expectedSearchResult);

        Assertions.assertTrue(googlePageFactory.getProgrammingTeachersTableFirstRow().getText().equals("Сергей Владимирович"),
                "Первым в списке идёт не Сергей Владимирович");
        Assertions.assertTrue(googlePageFactory.getProgrammingTeachersTableLastRow().getText().equals("Сергей Адамович"),
                "Последним в списке идёт не Сергей Адамович");
    }
}