package org.raido.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class);

    public static WebDriver createDriver(String browserName) {
        String browser = browserName.toLowerCase();
        log.info("Инициализация браузера: {}", browser);

        return switch (browser) {
            case "chrome" -> createChrome();
            case "firefox" -> createFirefox();
            case "edge" -> createEdge();
            default -> {
                log.error("Неподдерживаемый браузер: {}. Запуск Chrome по умолчанию.", browserName);
                yield createChrome();
            }
        };
    }

    private static WebDriver createChrome() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        log.debug("ChromeOptions настроены для подавления уведомлений о пароле.");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        log.debug("EdgeOptions настроены для подавления всплывающих окон и уведомлений.");
        return new EdgeDriver(options);
    }
}