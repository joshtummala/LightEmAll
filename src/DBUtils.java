import java.sql.*;
import java.util.ArrayList;

public class DBUtils {


    String url;
    String user;
    String password;
    Connection con = null;

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

    // performs an insert, update or delete on the database according to the given sql statement
    // and returns the id of the modified item, otherwise returns -1
    public int modify(String sqlQuery) throws SQLException {
        System.out.println("SQL STATEMENT: " + sqlQuery);
        int key = -1;

        // get connection and initialize statement
        Connection con = getConnection();
        Statement stmt = con.createStatement();

        stmt.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);

        // extract auto-incremented ID
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) key = rs.getInt(1);

        // Cleanup
        rs.close();
        stmt.close();
        return key;
    }

}
