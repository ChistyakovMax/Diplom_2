package site.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserChangeDataParamTest extends UserBaseTest{

    private final String changedField;

    private final String typeOfRequest;



    public UserChangeDataParamTest(String changedField, String typeOfRequest) {
        this.changedField = changedField;
        this.typeOfRequest = typeOfRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {"email", "auth"},
                {"name", "auth"},
                {"both", "auth"},
                {"none", "auth"},

                {"email", "notAuth"},
                {"name", "notAuth"},
                {"both", "notAuth"},
                {"none", "notAuth"},
        };
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    public void changeUserData(){
        //создаем юзера
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        //меняем поля у локального юзера
        switch (changedField){
            case ("email"):
                user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
                break;
            case ("name"):
                user.setName(RandomStringUtils.randomAlphanumeric(10));
                break;
            case ("both"):
                user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
                user.setName(RandomStringUtils.randomAlphanumeric(10));
                break;
            default:
                break;
        }
        //меняем поля на сервере
        switch (typeOfRequest){
            case ("auth"):
                //меняем поля
                ValidatableResponse responseAfterChange = userClient.changeWithAuthorization(user, accessToken);

                //запоминаем значения новых полей
                String newName = responseAfterChange.extract().path("user.name");
                String newEmail = responseAfterChange.extract().path("user.email");

                //удостоверяемся, что поля изменены
                Assert.assertEquals(user.getEmail().toLowerCase(), newEmail);
                Assert.assertEquals(user.getName(), newName);

                break;

            case ("notAuth"):
                String msg = userClient.changeWithoutAuthorization(user);
                Assert.assertEquals("You should be authorised", msg);
                break;
        }

    }
}
