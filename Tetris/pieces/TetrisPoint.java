package bigO.src.games.Tetris.pieces;

import java.awt.*;

/**
 * Created by Stanimir on 7/8/17.
 */
public class TetrisPoint extends Point {

    public Color color;

    public TetrisPoint(int x, int y, Color c) {
        super(x, y);
        this.color = c;
    }

    public TetrisPoint(int x, int y) {
        super(x, y);
    }

    public Color getColor() {
        return color;
    }

    public int getYY() {
        return (int) super.getY();
    }


}
