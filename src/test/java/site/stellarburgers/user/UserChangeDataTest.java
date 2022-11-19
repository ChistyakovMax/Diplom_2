package site.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Изменение данных пользователя")
public class UserChangeDataTest extends UserBaseTest {

    ValidatableResponse response;
    ValidatableResponse responseAfterChange;

    @Before
    public void start() {
        //создаем юзера
        response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение поля email у авторизированного юзера")
    public void changeEmailUserAuthorisedEmailChanged() {
        user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
        responseAfterChange = userClient.changeWithAuthorization(user, accessToken);
        //удостоверяемся, что поля изменены
        String newEmail = responseAfterChange.extract().path("user.email");
        Assert.assertEquals(user.getEmail().toLowerCase(), newEmail);
    }

    @Test
    @DisplayName("Изменение поля name у авторизированного юзера")
    public void changeNameUserAuthorisedNameChanged() {
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        responseAfterChange = userClient.changeWithAuthorization(user, accessToken);
        //удостоверяемся, что поля изменены
        String newName = responseAfterChange.extract().path("user.name");
        Assert.assertEquals(user.getName(), newName);
    }

    @Test
    @DisplayName("Изменение полей email и name у авторизированного юзера")
    public void changeEmailAndNameUserAuthorisedNameChanged() {
        user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        responseAfterChange = userClient.changeWithAuthorization(user, accessToken);
        //удостоверяемся, что поля изменены
        String newName = responseAfterChange.extract().path("user.name");
        String newEmail = responseAfterChange.extract().path("user.email");
        Assert.assertEquals(user.getEmail().toLowerCase(), newEmail);
        Assert.assertEquals(user.getName(), newName);
    }

    @Test
    @DisplayName("Отсутствие изменения полей у авторизированного юзера")
    public void changeNothingUserAuthorisedNothingChanged() {
        responseAfterChange = userClient.changeWithAuthorization(user, accessToken);
        //удостоверяемся, что поля НЕ изменены
        String newName = responseAfterChange.extract().path("user.name");
        String newEmail = responseAfterChange.extract().path("user.email");
        Assert.assertEquals(user.getEmail().toLowerCase(), newEmail);
        Assert.assertEquals(user.getName(), newName);
    }

    //в тестах без авторизации после попытки изменения полей сразу проверяем сообщение с ошибкой
    @Test
    @DisplayName("Изменение поля email у неавторизированного юзера")
    public void changeEmailUserNotAuthorisedGetErr() {
        user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
        String msg = userClient.changeWithoutAuthorization(user);
        Assert.assertEquals("You should be authorised", msg);
    }

    @Test
    @DisplayName("Изменение поля name у неавторизированного юзера")
    public void changeNameUserNotAuthorisedGetErr() {
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        String msg = userClient.changeWithoutAuthorization(user);
        Assert.assertEquals("You should be authorised", msg);
    }

    @Test
    @DisplayName("Изменение полей email и name у неавторизированного юзера")
    public void changeEmailAndNameUserNotAuthorisedGetErr() {
        user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        String msg = userClient.changeWithoutAuthorization(user);
        Assert.assertEquals("You should be authorised", msg);
    }

    @Test
    @DisplayName("Отсутствие изменения полей у неавторизированного юзера")
    public void changeNothingUserNotAuthorisedGetErr() {
        String msg = userClient.changeWithoutAuthorization(user);
        Assert.assertEquals("You should be authorised", msg);
    }

}
