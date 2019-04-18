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

        return new BesideImage(new OverlayImage(new TextImage(this.user, 20, Color.BLACK),
                new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.magenta)),
                new OverlayImage(new TextImage(Integer.toString(score), 20, Color.BLACK),
                        new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.magenta)));

    }
}

