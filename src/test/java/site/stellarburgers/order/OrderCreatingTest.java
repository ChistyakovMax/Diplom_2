package site.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

public class OrderCreatingTest extends OrderBaseTest {

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void test() {
        order.addIngredient("61c0c5a71d1f82001bdaaa6d");
        order.addIngredient("61c0c5a71d1f82001bdaaa6f");
        order.addIngredient("61c0c5a71d1f82001bdaaa72");
        boolean isSuccess = orderClient.create(order);
        Assert.assertTrue(isSuccess);
    }


    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createWithoutIngredientsReturn400() {
        String msg = orderClient.createWithoutIngredients(order);
        Assert.assertEquals("Ingredient ids must be provided", msg);
    }

    @Test
    @DisplayName("Создание заказа с невалидными хешами ингридиентов")
    public void createWithInvalidHashReturn500() {
        order.addIngredient("1");
        order.addIngredient("2");
        orderClient.createWithInvalidHash(order);
    }

}
