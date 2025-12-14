package org.raido.pages;

import org.openqa.selenium.TimeoutException;
import org.raido.utils.WaitUtils;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LaunchesPage {
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
        WaitUtils.waitForElementVisibility(driver, dashboardButtonLocator, waitTimeInSeconds);
        driver.findElement(dashboardButtonLocator).click();
    }

    @Step("Проверка что пользователь авторизован")
    public boolean isUserAuthorised() {
        try {
            WaitUtils.waitForElementVisibility(driver, userBlockLocator, waitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
