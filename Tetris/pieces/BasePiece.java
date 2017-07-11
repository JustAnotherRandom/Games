package bigO.src.games.Tetris.pieces;

import bigO.src.games.Tetris.Board;
import bigO.src.games.Tetris.utils.GameUtils;

import java.awt.*;

/**
 * Created by Stanimir on 7/7/17.
 */
public abstract class BasePiece implements Piece {

    public static final int NUM_POINTS = 4;

    protected Board board;

    protected int row;
    protected int col;

    protected Orientation orientation = Orientation.ONE;
    protected Color color;

    public BasePiece(Board b, Color c) {
        this.board = b;
    }


    @Override
    public void rotateClockWise() {

        TetrisPoint[][] well = board.getWell();

        boolean canRotate = true;


        Orientation rotatedOrientation = Orientation.forValue((orientation.getValue() + 1) % 4);


        for (int[] c : getCordinates(rotatedOrientation)) {
            TetrisPoint p = getPoint(c, 0, 0);

            if (!isInBounds(p) || well[p.x][p.y] != null) {
                canRotate = false;
                break;
            }
        }
        if (canRotate) {
            this.orientation = rotatedOrientation;
        }


    }

    @Override
    public void dropDown(int offsetX, int offsetY) {
        row += offsetX;
        col += offsetY;
    }

    private TetrisPoint getPoint(int[] cords, int offsetX, int offsetY) {
        return new TetrisPoint(row + cords[0] + offsetX, col + cords[1] + offsetY, color);
    }

    public TetrisPoint[] getPoints(int offsetX, int offsetY) {
        TetrisPoint[] points = new TetrisPoint[4];
        int i = 0;
        for (int[] c : getCordinates(orientation)) {
            points[i++] = getPoint(c, offsetX, offsetY);
        }

        return points;
    }

    private boolean isInBounds(Point p) {
        return GameUtils.isInBounds(p, board);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    protected abstract int[][] getCordinates(Orientation orientation);
}
