package bigO.src.games.Tetris.pieces;

import bigO.src.games.Tetris.Board;

import java.awt.*;

/**
 * Created by Stanimir on 7/7/17.
 */
public class LShapedPiece extends BasePiece {

    private static int[][][] CORDS = new int[][][]{
            {{1, 1}, {1, 0}, {0, 0}, {-1, 0}},
            {{-1, 1}, {0, 1}, {0, 0}, {0, -1}},
            {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}},
            {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };



    public LShapedPiece(Board b, Color c) {
        super(b, c);
        Point[][] board = b.getWell();
        col = board[0].length / 2;
        if (board[0].length % 2 == 0) --col;
        row = (board.length - 1) - 1;


    }


    @Override
    protected int[][] getCordinates(Orientation orientation) {
        return CORDS[orientation.getValue()];
    }


}
