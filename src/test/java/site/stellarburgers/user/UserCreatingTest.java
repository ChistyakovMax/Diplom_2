package site.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

//Создание пользователя
public class UserCreatingTest extends UserBaseTest {

    @Test
    @DisplayName("Cоздать уникального пользователя")
    public void createUserValidDataUserCreated() {
        boolean isSuccess = userClient.create(user);
        Assert.assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Cоздать пользователя, который уже зарегистрирован")
    public void createUserExistsReturn403() {
        userClient.create(user);
        String msg = userClient.createInvalid(user);
        Assert.assertEquals("User already exists", msg);
    }

    @Test
    @DisplayName("Создать пользователя с полем email == null")
    public void createUserWithNullEmailReturn403() {
        userClient.createWithNullField(user, "email");
    }

    @Test
    @DisplayName("Сздать пользователя с полем password == null")
    public void createUserWithNullPasswordReturn403(){
        userClient.createWithNullField(user, "password");
    }

    @Test
    @DisplayName("Создать пользоваеля с полем name == null")
    public void createUserWithNullNameField(){
        userClient.createWithNullField(user, "name");
    }
}