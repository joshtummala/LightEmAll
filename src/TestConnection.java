import java.util.ArrayList;

// used to test the connection with the database using JDBC
public class TestConnection {
    public static void main(String[] args) {
        LightEmAllAPI api = new LightEmAllAPI(new DBUtils(
                "jdbc:mysql://localhost:3306/LightEmAll?" +
                        "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EST5EDT",
                "cs2510", "fundies123"));
        ArrayList<User> users = api.retrieveUsers();
        for (User user : users) {
            System.out.println(user.username);
        }
    }
}
