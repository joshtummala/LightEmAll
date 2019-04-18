import java.util.ArrayList;

public class User {
    String username;
    String password;
    ArrayList<Result> results;
    boolean isLoggedIn;

    User(String username, String password, ArrayList<Result> results) {
        this.username = username;
        this.password = password;
        this.results = results;
        this.isLoggedIn = false;
    }

    // EFFECT: changes isLoggedIn if the username and password are the same as the ones in this
    void login(String username, String password) {
        this.isLoggedIn = this.username.equals(username)
                && this.password.equals(password);
    }
}