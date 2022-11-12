package site.stellarburgers.order;

import org.junit.Assert;
import org.junit.Before;
import site.stellarburgers.user.User;
import site.stellarburgers.user.UserClient;

public class OrderBaseTest {

    Order order;

    OrderClient orderClient ;

    User user;

    UserClient userClient = new UserClient();

    @Before
    public void setUp(){
        order = new Order();
        orderClient = new OrderClient();
    }

    public static String createUserAndOrder(Order order, OrderClient orderClient, User user, UserClient userClient){
        user = User.getRandomUser();

        String accessToken = userClient.create(user).extract().path("accessToken");
        order.setIngredients(orderClient.getRandomIngredients());

        boolean isSuccess = orderClient.create(order, accessToken);
        Assert.assertTrue(isSuccess);
        return accessToken;
    }

}
