package org.raido;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.raido.utils.WebDriverFactory;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) {
        log.info("--- Начинается выполнение нового теста. Настройка WebDriver для: {} ---", browser);
        driver = WebDriverFactory.createDriver(browser);

        driver.get("https://demo.reportportal.io");
        log.info("Открыт URL: https://demo.reportportal.io");

        driver.manage().window().maximize();
        log.debug("Разворачивание окна на весь экран.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            log.info("Завершение работы WebDriver. Статус теста: {}", result.getStatus());
            driver.quit();
        }
    }
}