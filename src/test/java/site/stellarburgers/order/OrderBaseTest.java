package site.stellarburgers.order;

import org.junit.Before;

public class OrderBaseTest {

    Order order;

    OrderClient orderClient;

    @Before
    public void setUp(){
        order = new Order();
        orderClient = new OrderClient();
    }

}
