package site.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

//Создание пользователя
public class UserCreatingTest extends UserBaseTest{

    @Test
    @DisplayName("Cоздать уникального пользователя")
    public void createUserValidDataUserCreated(){
        boolean isSuccess = userClient.create(user);
        Assert.assertTrue(isSuccess);
    }

}
