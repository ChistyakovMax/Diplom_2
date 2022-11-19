package site.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Логин пользователя")
public class UserLoginTest extends UserBaseTest {
    UserCreds userCreds;

    @Before
    public void createUserCreds() {
        userCreds = UserCreds.getCreds(user);
    }

    @Test
    @DisplayName("логин под существующим пользователем")
    public void loginExistingUserSuccess() {
        userClient.create(user);
        boolean isSuccess = userClient.loginExisting(userCreds);
        Assert.assertTrue(isSuccess);
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    public void loginInvalidUserReturn401() {
        String msg = userClient.loginInvalid(userCreds);
        Assert.assertEquals("email or password are incorrect", msg);
    }
}
