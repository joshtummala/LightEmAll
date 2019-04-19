import javalib.worldimages.*;

import java.awt.*;

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

    public WorldImage drawResult() {
        int score = this.time + this.moves;

        return new BesideImage(new OverlayImage(new TextImage(this.user, 20, Color.darkGray),
                new RectangleImage(100, 30, OutlineMode.SOLID, Color.gray)),
                new OverlayImage(new TextImage(Integer.toString(score), 20, Color.darkGray),
                        new RectangleImage(100, 30, OutlineMode.SOLID, Color.gray)));

    }
}

