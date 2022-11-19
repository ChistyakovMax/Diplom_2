package site.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.stellarburgers.config.BaseClient;
import org.junit.Assert;

public class UserClient extends BaseClient {

    private final String AUTH = "/auth";
    private final String REGISTER = AUTH + "/register";
    private final String LOGIN = AUTH + "/login";

    private final String USER = AUTH + "/user";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Step("Cоздать пользователя, который уже зарегистрирован")
    public String createInvalid(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");
    }

    @Step("Cоздать пользователя и не заполнить одно из обязательных полей")
    public void createWithNullField(User user, String field) {
        user = initializeInvalid(user, field);
        String msg = createInvalid(user);
        Assert.assertEquals("Email, password and name are required fields", msg);
    }

    @Step("Инициализировать невалидного пользователя")
    public User initializeInvalid(User user, String field) {
        switch (field) {
            case ("email"):
                user.setEmail(null);
                return user;

            case ("password"):
                user.setPassword(null);
                return user;
            case ("name"):
                user.setName(null);
                return user;
            default:
                return user;
        }
    }

    @Step("Логин под валидным пользователем")
    public boolean loginExisting(UserCreds userCreds) {
        return getSpec()
                .body(userCreds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Логин с невалидными кредами")
    public String loginInvalid(UserCreds userCreds) {
        return getSpec()
                .body(userCreds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }

    @Step("Удалить пользователя")
    public void delete(String accessToken) {
        getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all()
                .assertThat()
                .statusCode(202);
    }

    @Step("Изменить данные пользователя с авторизацией")
    public ValidatableResponse changeWithAuthorization(User user, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Step("Изменить данные пользователя без авторизации")
    public String changeWithoutAuthorization(User user) {
        return getSpec()
                .body(user)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }

}
