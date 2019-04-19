import java.sql.*;

public class DBUtils {
    String url; // the path for the database connection
    String user; // the connection's username
    String password; // the connection's password
    Connection con = null; // the connection to the database

    // constructor for creating a new DBUtils with specified fields
    public DBUtils(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // constructor for creating a new DBUtils with a certain url, username and password
    public DBUtils() {
        // this url will work if you have the database set up in the localhost port 3306 for mysql
        this.url = "jdbc:mysql://localhost:3306/LightEmAll?allowPublic" +
                "KeyRetrieval=true&useSSL=false&serverTimezone=EST5EDT";
        // a connection with the following username and password with all
        // privileges to the LightEmAll schema will be needed for this
        this.user = "cs2510";
        this.password = "fundies123";
    }

    // gets a connection to the database with the fields in this
    public Connection getConnection() {
            this.con = null;
            try {
                this.con = DriverManager.getConnection(this.url, this.user, this.password);
                return this.con;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        return this.con;
    }

    // closes the connection to the database
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
