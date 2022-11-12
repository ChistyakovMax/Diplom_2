package site.stellarburgers.user;

import org.junit.After;
import org.junit.Before;

public class UserBaseTest {
    User user;

    UserClient userClient;

    String accessToken;

    @Before
    public void setUp(){
        user = User.getRandomUser();
        userClient = new UserClient();
    }

    @After
    public void tearDown(){
        if (accessToken != null){
            userClient.delete(accessToken);
        }
    }

}
