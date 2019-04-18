import java.sql.*;

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
}
