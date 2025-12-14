package org.raido.tests;

import org.raido.BaseTest;
import org.raido.pages.LaunchesPage;
import org.raido.pages.LoginPage;
import org.raido.utils.TestDataProviders;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Story;
import io.qameta.allure.Owner;
import io.qameta.allure.Description;

import java.util.Map;

public class LoginPositiveTest extends BaseTest {
    @Story("Позитивный тест на логин")
    @Owner("SergeyB")
    @Description("Попытка входа на сайт с корректными данными")
    @Test(description = "Позитивный тест на логин",
            dataProvider = "correctLoginData", dataProviderClass = TestDataProviders.class)
    public void loginPositive(String correctLogin, String correctPassword) {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(driver);
        LaunchesPage launchesPage = new LaunchesPage(driver);

        loginPage.login(correctLogin, correctPassword);

        loginPage.clickLogin();

        softAssert.assertTrue(launchesPage.isUserAuthorised(),
                "После попытки логина с верными данными пользователь не залогинен");

        softAssert.assertAll();
    }
}
