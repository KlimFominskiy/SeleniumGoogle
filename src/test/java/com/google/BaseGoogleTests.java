package com.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseGoogleTests {

    /**
     * Веб-драйвер для взаимодействия с веб-браузером Chrome.
     * @author Фоминский Клим
     */
    protected WebDriver chromeDriver;

    /**
     * Метод открывает браузер в окне, расстягивает его на весь экран и переходит на сайт https://www.google.com/ перед каждым тестом.
     * @author Фоминский Клим
     */
    @BeforeEach
    protected void beforeEach(){
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.get("https://www.google.com/");
    }

    /**
     * Метод закрывает браузер после каждого теста.
     * @author Фоминский Клим
     */
    @AfterEach
    protected void afterEach(){
        chromeDriver.quit();
    }
}
