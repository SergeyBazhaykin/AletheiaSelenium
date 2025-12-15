package org.raido.pages;

import org.raido.utils.WaitUtils;

import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private final WebDriver driver;
    private final int waitTimeInSeconds;
    private final int shortWaitTimeInSeconds;

    private final By inputLoginLocator = By.xpath("//input[@placeholder='Login']");
    private final By inputPasswordLocator = By.xpath("//input[@placeholder='Password']");
    private final By buttonLoginLocator = By.xpath("//button[@type='submit']");
    private final By errorMessageLoginLocator = By.xpath("//div[contains(@class, 'loginForm__login-field')]//span[text()='Bad Credentials']");
    private final By errorMessagePasswordLocator = By.xpath("//div[contains(@class, 'loginForm__password-field')]//span[text()='Bad Credentials']");
    private final By errorBadCredentialsLocator = By.xpath("//div[contains(@class, 'notificationList__notification-list')]//h2[text()='An error occurred while connecting to server: You do not have enough permissions. Bad credentials']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitTimeInSeconds = 3;
        this.shortWaitTimeInSeconds = 1;
    }

    @Step("Ввод логина: {login} и пароля: {password}")
    public void login(String login, String password) {
        log.debug("Ввод логина: {}", login);
        WaitUtils.waitForElementVisibility(driver, inputLoginLocator, waitTimeInSeconds);
        WebElement inputLogin = driver.findElement(inputLoginLocator);
        inputLogin.clear();
        inputLogin.sendKeys(login);

        log.debug("Ввод пароля: {}", password);
        WaitUtils.waitForElementVisibility(driver, inputPasswordLocator, waitTimeInSeconds);
        WebElement inputPassword = driver.findElement(inputPasswordLocator);
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    @Step("Нажатие кнопки Login")
    public void clickLogin() {
        log.info("Попытка авторизации");
        WaitUtils.waitForElementClickable(driver, buttonLoginLocator, waitTimeInSeconds);
        driver.findElement(buttonLoginLocator).click();
    }

    @Step("Проверка вывода сообщения под полем о неверном логине")
    public boolean isWrongLoginMessageDisplayed() {
        try {
            log.debug("Ожидаем сообщение под полем о неверном логине");
            WaitUtils.waitForElementVisibility(driver, errorMessageLoginLocator, shortWaitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            log.debug("Сообщения о логине нет по таймауту: {}", shortWaitTimeInSeconds);
            return false;
        }
    }

    @Step("Проверка вывода сообщения под полем о неверном пароле")
    public boolean isWrongPasswordMessageDisplayed() {
        try {
            log.debug("Ожидаем сообщение под полем о неверном пароле");
            WaitUtils.waitForElementVisibility(driver, errorMessagePasswordLocator, shortWaitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            log.debug("Сообщения о пароле нет по таймауту: {}", shortWaitTimeInSeconds);
            return false;
        }
    }

    @Step("Проверка вывода сообщения о неверных данных входа внизу страницы")
    public boolean isBadCredentialsMessageDisplayed() {
        try {
            log.debug("Ожидаем сообщение о неверных данных входа внизу страницы");
            WaitUtils.waitForElementVisibility(driver, errorBadCredentialsLocator, waitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            log.debug("Сообщения о неверных данных нет по таймауту: {}", waitTimeInSeconds);
            return false;
        }
    }

    @Step("Проверка изменения адреса страницы после логина на 'default_personal'")
    public boolean isRedirectedToDefaultPersonal() {
        log.debug("Ожидаем изменения адреса страницы после логина");
        return WaitUtils.waitForUrlToContain(driver, "default_personal", waitTimeInSeconds);
    }
}
