package site.stellarburgers.order;

import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import io.qameta.allure.Step;
import site.stellarburgers.config.BaseClient;

public class OrderClient extends BaseClient {

    private final String ORDERS = "/orders";

    @Step("Cоздать заказа")
    public boolean create(Order order){
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

    @Step("Создать заказ с невалидными хешами ингридиентов")
    public void createWithInvalidHash(Order order){
        getSpec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(500);
    }

    @Step("Cоздать заказа без ингридиентов")
    public String createWithoutIngredients(Order order){
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

}