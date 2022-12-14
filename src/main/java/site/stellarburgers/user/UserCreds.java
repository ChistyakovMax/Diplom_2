package site.stellarburgers.user;

import lombok.Data;

@Data
public class UserCreds {
    private String email;

    private String password;


    public UserCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCreds getCreds(User user) {
        return new UserCreds(user.getEmail(), user.getPassword());
    }

}
