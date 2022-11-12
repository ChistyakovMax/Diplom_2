package site.stellarburgers.user;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class User {

    private String email;
    private String password;
    private String name;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@gmail.com",
                "P@ssw0rd",
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
}
