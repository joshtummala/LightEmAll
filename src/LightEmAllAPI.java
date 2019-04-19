import java.sql.*;
import java.util.ArrayList;

// Acts as an API layer between the database and the application
// is used to retrieve data and update the database
public class LightEmAllAPI {
    DBUtils dbUtils; // the utils used to connect and execute statements in the database

    // constructor creates a new API with the default utils
    LightEmAllAPI() {
        this.dbUtils = new DBUtils();
    }
    // constructor creates a new API which the given utils
    LightEmAllAPI(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    // executes the given sql query on the connected database
    // throws an exception if the statement could not be executed
    public void executeStatement(String sql) throws SQLException {
            // get connection and initialize statement
            Connection con = dbUtils.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Cleanup
            stmt.close();
    }

    // executes the given insert statement on the connected database
    // catches an expection if the insert could not be made
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
            System.err.println("ERROR: Could not insert record: " + sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return key;
    }

    // retrieves all the users in the User table from the connected database
    // catches an exception, most likely if the User table does not exist
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

    // retrieves all the results of the given user in the connected database
    ArrayList<Result> retrieveResults(String user) {
        String sqlQuery = "select * from Results where user = \'" + user + "\'";
        return getResults(sqlQuery);
    }


    // retrieves all top 5 results from the database of board size with the given rows and cols
     ArrayList<Result> retreiveLeaderBoard(int rows, int cols) {
        String sqlQuery = "select * \n" +
                "from results\n" +
                "where Rrows = " + Integer.toString(rows) +
                " and Rcolumns = "+ Integer.toString(cols) + "\n" +
                "order by (Rtime + moves) ASC\n" +
                "limit 5;";
        return getResults(sqlQuery);
    }

    // retrieves all the results from the given query
    // catches an error if the sql query could not be executed
    ArrayList<Result> getResults(String sqlQuery) {
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
