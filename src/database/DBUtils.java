package database;

import Objects.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class DBUtils {


    String url;
    String user;
    String password;
    Connection con = null;

    public DBUtils(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DBUtils() {
        JSONParser parser = new JSONParser();
        try {
            URL path = this.getClass().getResource("config.json");
            File f = new File(path.getFile());
            Object obj = parser.parse(new FileReader(f));
            JSONObject jsonObject = (JSONObject) obj;
            this.url = (String) jsonObject.get("url");
            this.user = (String) jsonObject.get("username");
            this.password = (String) jsonObject.get("password");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            System.err.println("either file not found or file cannot be parsed into json");
        }
    }

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

    private void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * this methods could be used for INSERT, DELETE, or UPDATE; or create a new table;
     *
     * @param sqlQuery a sql query
     * @return an int, return -1 if this modification does not succeed; otherwise,it's fine
     */
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
