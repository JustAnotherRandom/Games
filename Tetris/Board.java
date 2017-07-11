package bigO.src.games.Tetris;

import bigO.src.games.Tetris.pieces.Piece;
import bigO.src.games.Tetris.pieces.PieceFactory;
import bigO.src.games.Tetris.pieces.TetrisPoint;
import bigO.src.games.Tetris.utils.GameUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stanimir on 7/8/17.
 */
public class Board {


    public static final int WIDTH = 10;
    public static final int HEIGH = 10;

    private TetrisPoint[][] well;
    private Piece activePiece;
    private Game game;

    public Board(Game g) {
        this.game = g;
        well = new TetrisPoint[HEIGH][WIDTH];
        for (int i = 0; i < WIDTH - 1; i++) {
            well[0][i] = new TetrisPoint(0, i, Color.red);
        }

        insertPiece(PieceFactory.generatePiece(this));

    }


    void clearRow(int r) {
        TetrisPoint[] row = well[r];
        boolean clearable = true;
        for (int i = 0; i < WIDTH; i++) {
            TetrisPoint val = row[i];
            if (val == null) {
                clearable = false;
                break;
            }
        }

        if (clearable)
            siftDown(r);
        //TODO notify GUI
    }


    void siftDown(int row) {

        for (int i = row + 1; i < HEIGH; i++) {
            well[i - 1] = well[i];
        }
    }

    public void insertPiece(Piece piece) {
        activePiece = piece;
        if (!canDropDown(0, 0)) {
            game.endGame();
        }
    }


    public void dropDown() {
        if (canDropDown(-1, 0)) {

            activePiece.dropDown(-1, 0);


        } else {
            fitPiece();
            TetrisPoint[] points = activePiece.getPoints(0, 0);
            for (TetrisPoint p : points) {
                clearRow(p.x);
            }

            insertPiece(PieceFactory.generatePiece(this));
        }
    }

    private void fitPiece() {
        TetrisPoint[] activePoints = activePiece.getPoints(0, 0);
        for (TetrisPoint p : activePoints) {
            well[p.x][p.y] = p;
        }
    }

    void rotateActivePiece() {
        activePiece.rotateClockWise();
    }


    private boolean canDropDown(int offsetX, int offsetY) {
        Piece current = activePiece;


        List<TetrisPoint> input = Arrays.asList(current.getPoints(offsetX, offsetY));
        for (TetrisPoint p : input) {

            if (!GameUtils.isInBounds(p, this) || well[p.x][p.y] != null) {
                return false;
            }
        }
        return true;
    }


    public Piece getActivePiece() {
        return activePiece;
    }

    public TetrisPoint[][] getWell() {
        return well;
    }

    public void move(int offsetY) {
        if (canDropDown(0, offsetY)) {
            activePiece.dropDown(0, offsetY);
        }
    }
}
