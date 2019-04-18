public class Result {
    String user;
    int rows;
    int columns;
    int time;
    int moves;

    Result(String user, int rows, int columns, int time, int moves) {
        this.user = user;
        this.rows = rows;
        this.columns = columns;
        this.time = time;
        this.moves = moves;
    }

}