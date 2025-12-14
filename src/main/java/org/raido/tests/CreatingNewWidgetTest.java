package org.raido.tests;

import org.raido.BaseTest;
import org.raido.pages.DashboardPage;
import org.raido.pages.LaunchesPage;
import org.raido.pages.LoginPage;

import org.raido.utils.TestDataProviders;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Story;
import io.qameta.allure.Owner;
import io.qameta.allure.Description;

public class CreatingNewWidgetTest extends BaseTest {
    @Story("Тест создания нового виджета")
    @Owner("SergeyB")
    @Description("Попытка авторизации и создания виджета со случайным именем")
    @Test(description = "Тест создания нового виджета",
            dataProvider = "correctLoginData", dataProviderClass = TestDataProviders.class)
    public void creatingNewWidget(String correctLogin, String correctPassword) {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(driver);
        LaunchesPage launchesPage = new LaunchesPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        String widgetName = "testWidget" + System.currentTimeMillis();

        loginPage.login(correctLogin, correctPassword);
        loginPage.clickLogin();

        launchesPage.clickDashboardPageButton();

        dashboardPage.clickExistingDashboard();
        dashboardPage.openNewWidgetCreationWindow();
        dashboardPage.pickNewWidgetTypeStepOne();
        dashboardPage.clickNextStep();
        dashboardPage.pickNewWidgetTypeStepTwo();
        dashboardPage.clickNextStep();
        dashboardPage.inputWidgetName(widgetName);
        dashboardPage.clickAddWidget();

        softAssert.assertTrue(dashboardPage.isWidgetAdded(widgetName),
                "После попытки создания виджета " + widgetName + ", данный виджет не найден на странице дашборда");

        softAssert.assertAll();

    }
}
