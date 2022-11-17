package site.stellarburgers.order;

import io.qameta.allure.Step;
import site.stellarburgers.config.BaseClient;

import java.util.ArrayList;
import java.util.List;

public class OrderClient extends BaseClient {

    private final String ORDERS = "/orders";

    private final String INGREDIENTS = "/ingredients";

    @Step("Получить данных об ингредиентах")
    public List<String> getRandomIngredients() {
        List<String> allTheIngredients = getSpec()
                .when()
                .get(INGREDIENTS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("data._id");

        List<String> randomIngredients = new ArrayList<>();
        randomIngredients.add(allTheIngredients.get(1));
        randomIngredients.add(allTheIngredients.get(2));
        randomIngredients.add(allTheIngredients.get(3));
        return randomIngredients;
    }

    @Step("Cоздать заказ без авторизации")
    public boolean create(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Cоздать заказ с авторизацией")
    public boolean create(Order order, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Создать заказ с невалидными хешами ингридиентов")
    public void createWithInvalidHash(Order order) {
        getSpec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(500);
    }

    @Step("Cоздать заказ без ингридиентов")
    public String createWithoutIngredients(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Получить заказ конкретного пользователя с авторизацией")
    public boolean getOrdersWithAuth(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Получить заказ конкретного пользователя без авторизации")
    public String getOrdersWithoutAuth() {
        return getSpec()
                .when()
                .get(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }

}
