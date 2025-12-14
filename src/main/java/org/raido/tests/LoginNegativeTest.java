package org.raido.tests;

import org.raido.BaseTest;
import org.raido.pages.LoginPage;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Story;
import io.qameta.allure.Owner;
import io.qameta.allure.Description;

public class LoginNegativeTest extends BaseTest {
    @Story("Негативный тест на логин")
    @Owner("SergeyB")
    @Description("Попытка входа на сайт с некорректными данными")
    @Test(description = "Негативный тест на логин")
    public void loginNegative() {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(driver);

        String wrongLogin = "invalidUser";
        String wrongPassword = "wrongPass";

        loginPage.login(wrongLogin, wrongPassword);

        loginPage.clickLogin();

        softAssert.assertTrue(!loginPage.isRedirectedToDefaultPersonal(),
                "После попытки логина с неверными данными пользователь не остался на этой же странице");

        softAssert.assertTrue(loginPage.isWrongLoginMessageDisplayed(),
                    "Нет индикации ошибки на странице авторизации под полем логина");

        softAssert.assertTrue(loginPage.isWrongPasswordMessageDisplayed(),
                "Нет индикации ошибки на странице авторизации под полем пароля");

        softAssert.assertTrue(loginPage.isBadCredentialsMessageDisplayed(),
                "Нет индикации ошибки на странице авторизации внизу страницы");

        softAssert.assertAll();
    }
}
