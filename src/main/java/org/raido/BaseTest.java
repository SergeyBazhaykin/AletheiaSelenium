package org.raido;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected static WebDriver driver;

    @BeforeMethod
    public void setup() {
        log.info("--- Начинается выполнение нового теста. Настройка WebDriver. ---");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        driver = new ChromeDriver(options);

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