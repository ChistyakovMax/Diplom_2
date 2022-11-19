package site.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

@DisplayName("Получение заказов")
public class GetOrdersTest extends OrderBaseTest {

    String accessToken;

    @After
    public void deleteUser(){
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Получение заказов как авторизованный пользователь")
    public void getOrdersAuthorizedReturnSuccess() {
        accessToken = OrderBaseTest.createUserAndOrder(order, orderClient, user, userClient);
        boolean isSuccess = orderClient.getOrdersWithAuth(accessToken);
        Assert.assertTrue(isSuccess);
    }

    @Test
    @DisplayName("Получение заказов как неавторизованный пользователь")
    public void getOrdersUnauthorizedReturn403() {
        String expected = orderClient.getOrdersWithoutAuth();
        Assert.assertEquals("You should be authorised", expected);
    }

}
