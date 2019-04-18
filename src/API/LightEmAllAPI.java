package API;

public interface LightEmAllAPI {
    // executes the given sql statement
    public void executeStatement(String sql);
    // inserts the given sql statement and returns the id of the inserted object
    public int insertStatement(String sql);
}
