package site.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

@DisplayName("Получение заказов")
public class GetOrdersTest extends OrderBaseTest {

    @Test
    @DisplayName("Получение заказов как авторизованный пользователь")
    public void getOrdersAuthorizedReturnSuccess() {
        String accessToken = OrderBaseTest.createUserAndOrder(order, orderClient, user, userClient);
        boolean isSuccess = orderClient.getOrdersWithAuth(accessToken);
        Assert.assertTrue(isSuccess);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Получение заказов как неавторизованный пользователь")
    public void getOrdersUnauthorizedReturn403() {
        String expected = orderClient.getOrdersWithoutAuth();
        Assert.assertEquals("You should be authorised", expected);
    }

}
