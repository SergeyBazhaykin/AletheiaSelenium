package org.raido.utils;

import org.testng.annotations.DataProvider;

import java.util.Map;

public class TestDataProviders {
    @DataProvider(name = "correctLoginData")
    public Object[][] getLoginDataCorrect() {

        return new Object[][] {
                {"default", "1q2w3e"}
        };
    }

    @DataProvider(name = "incorrectLoginData")
    public static Object[][] getWrongCredentials() {
        return new Object[][] {
                {"invalidUser", "wrongPass"}
        };
    }
}
