package org.raido.pages;

import org.raido.utils.WaitUtils;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage {
    private static final Logger log = LoggerFactory.getLogger(DashboardPage.class);

    private final WebDriver driver;
    private final int waitTimeInSeconds;

    private final By demoDashboardLocator = By.xpath("//div[contains(@class, 'pageLayout__page-content')]//a[contains(@class, 'gridCell__grid-cell')]");
    private final By addNewWidgetButtonLocator = By.xpath("//button[contains(@class, 'ghostButton__ghost-button')]//span[text()='Add new widget']");
    private final By firstElementWidgetTypeStepOneLocator = By.xpath("//form[contains(@class, 'wizardFirstStepForm')]//div[text()='Launch statistics chart']");
    private final By nextStepButtonLocator = By.xpath("//div[contains(@class, 'wizardControlsSection__buttons-block')]//span[text()='Next step']");
    private final By addButtonLocator = By.xpath("//div[contains(@class, 'wizardControlsSection__buttons-block')]//button[text()='Add']");
    private final By configureWidgetTypeStepTwoLocator = By.xpath("//div[contains(@class, 'filtersList__filter-list')]//span[contains(@class, 'inputRadio__toggler')]");
    private final By widgetNameInputLocator = By.xpath("//input[@placeholder='Enter widget name']");

    private final String createdWidgetTitleLocatorTemplate = "//div[contains(@class, 'widgets-grid')]//div[text()='%s']";

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.waitTimeInSeconds = 3;
    }

    @Step("Переход на существующий дашбоард")
    public void clickExistingDashboard() {
        log.info("Переход на существующий дашбоард");
        WaitUtils.waitForElementClickable(driver, demoDashboardLocator, waitTimeInSeconds);
        driver.findElement(demoDashboardLocator).click();
    }

    @Step("Открытие окна создания нового виджета")
    public void openNewWidgetCreationWindow() {
        log.debug("Открытие окна создания нового виджета");
        WaitUtils.waitForElementClickable(driver, addNewWidgetButtonLocator, waitTimeInSeconds);
        driver.findElement(addNewWidgetButtonLocator).click();
    }

    @Step("Выбор типа нового виджета на первом шаге")
    public void pickNewWidgetTypeStepOne() {
        log.debug("Выбор типа нового виджета на первом шаге");
        WaitUtils.waitForElementClickable(driver, firstElementWidgetTypeStepOneLocator, waitTimeInSeconds);
        driver.findElement(firstElementWidgetTypeStepOneLocator).click();
    }

    @Step("Переход к следующему шагу")
    public void clickNextStep() {
        log.info("Переход к следующему шагу настроек виджета");
        WaitUtils.waitForElementClickable(driver, nextStepButtonLocator, waitTimeInSeconds);
        driver.findElement(nextStepButtonLocator).click();
    }

    @Step("Выбор типа нового виджета на втором шаге")
    public void pickNewWidgetTypeStepTwo() {
        log.debug("Выбор типа нового виджета на втором шаге");
        WaitUtils.waitForElementClickable(driver, configureWidgetTypeStepTwoLocator, waitTimeInSeconds);
        driver.findElement(configureWidgetTypeStepTwoLocator).click();
    }

    @Step("Ввод названия нового виджета ({widgetName})")
    public void inputWidgetName(String widgetName) {
        log.info("Ввод названия нового виджета: {}", widgetName);
        WaitUtils.waitForElementVisibility(driver, widgetNameInputLocator, waitTimeInSeconds);
        WebElement inputName = driver.findElement(widgetNameInputLocator);
        inputName.clear();
        inputName.sendKeys(widgetName);
    }

    @Step("Нажание на кнопку добавления нового виджета (после создания)")
    public void clickAddWidget() {
        log.debug("Нажание на кнопку создания нового виджета");
        WaitUtils.waitForElementClickable(driver, addButtonLocator, waitTimeInSeconds);
        driver.findElement(addButtonLocator).click();
    }

    @Step("Проверка, что виджет с названием '{widgetName}' добавлен на дашбоард")
    public boolean isWidgetAdded(String widgetName) {
        By widgetLocator = By.xpath(String.format(createdWidgetTitleLocatorTemplate, widgetName));

        try {
            log.debug("Ожидаем виджет ({}) на дашборде", widgetName);
            WaitUtils.waitForElementVisibility(driver, widgetLocator, waitTimeInSeconds);
            return true;
        } catch (TimeoutException e) {
            log.debug("Виджета нет по таймауту: {}", waitTimeInSeconds);
            return false;
        }
    }
}
