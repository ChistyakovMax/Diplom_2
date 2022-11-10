package site.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

public class OrderCreatingTest extends OrderBaseTest{

    @Test
    @DisplayName("")
    public void test(){
        order.addIngredient("60d3463f7034a000269f45e9");
        order.addIngredient("60d3463f7034a000269f45e7");
        boolean isSuccess = orderClient.create(order);
        Assert.assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createWithoutIngredientsReturn400(){
        String msg = orderClient.createWithoutIngredients(order);
        Assert.assertEquals("Ingredient ids must be provided", msg);
    }

    @Test
    @DisplayName("Создание заказа с невалидными хешами ингридиентов")
    public void createWithInvalidHashReturn500(){
        order.addIngredient("1");
        order.addIngredient("2");
        orderClient.createWithInvalidHash(order);
    }

}
