import java.sql.*;
import java.util.ArrayList;

public class LightEmAllAPI {
    DBUtils dbUtils;

    // constructor creates a new API which the given utils
    public LightEmAllAPI(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void executeStatement(String sql) {
        try {
            // get connection and initialize statement
            Connection con = dbUtils.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Cleanup
            stmt.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Could not execute statement: "+sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public int insertStatement(String sql) {
        int key = -1;
        try {

            // get connection and initialize statement
            Connection con = dbUtils.getConnection();
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            // extract auto-incremented ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) key = rs.getInt(1);

            // Cleanup
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Could not insert record: "+sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return key;
    }

    ArrayList<User> retrieveUsers() {
        String sqlQuery = "select * from User";
        ArrayList<User> users = new ArrayList<User>();
        Connection conn;
        try {
            conn = this.dbUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                ArrayList<Result> results = this.retrieveResults(rs.getString("username"));
                users.add(new User(rs.getString("username"),
                        rs.getString("password"), results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.dbUtils.closeConnection();
        return users;
    }

    ArrayList<Result> retrieveResults(String user) {
        String sqlQuery = "select * from Results where user = \'" + user + "\'";
        ArrayList<Result> results = new ArrayList<Result>();
        Connection conn;
        try {
            conn = this.dbUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                results.add(new Result(rs.getString("user"),
                        rs.getInt("Rrows"), rs.getInt("Rcolumns"),
                        rs.getInt("Rtime"), rs.getInt("moves")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.dbUtils.closeConnection();
        return results;
    }
}
