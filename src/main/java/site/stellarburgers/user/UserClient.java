package site.stellarburgers.user;

import io.qameta.allure.Step;
import site.stellarburgers.config.BaseClient;

public class UserClient extends BaseClient {

    private final String REGISTER = "/auth/register";

    @Step("Создание пользователя")
    public boolean create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

}
