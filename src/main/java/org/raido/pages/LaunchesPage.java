package org.raido.pages;

import org.openqa.selenium.TimeoutException;
import org.raido.utils.WaitUtils;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaunchesPage {
    private static final Logger log = LoggerFactory.getLogger(LaunchesPage.class);

    private final WebDriver driver;
    private final int waitTimeInSeconds;

    private final By dashboardButtonLocator = By.xpath("//a[@href='#default_personal/dashboard']");
    private final By userBlockLocator = By.xpath("//div[contains(@class, 'userBlock__user-block')]");

    public LaunchesPage(WebDriver driver) {
        this.driver = driver;
        this.waitTimeInSeconds = 3;
    }

    @Step("Переход на страницу Dashboard")
    public void clickDashboardPageButton() {
        log.info("Переход на страниц Dashboard");
        WaitUtils.waitForElementVisibility(driver, dashboardButtonLocator, waitTimeInSeconds);
        driver.findElement(dashboardButtonLocator).click();
    }

    @Step("Проверка что пользователь авторизован")
    public boolean isUserAuthorised() {
        try {
            log.debug("Ожидаем появления блока пользователя (авторизованного)");
            WaitUtils.waitForElementVisibility(driver, userBlockLocator, waitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            log.debug("Блока пользователя нет по таймауту: {}", waitTimeInSeconds);
            return false;
        }
    }
}
