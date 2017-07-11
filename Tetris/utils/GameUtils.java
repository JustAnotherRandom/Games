package bigO.src.games.Tetris.utils;

import bigO.src.games.Tetris.Board;

import java.awt.*;

/**
 * Created by Stanimir on 7/11/17.
 */
public class GameUtils {

    public static  boolean isInBounds(Point p, Board b) {
        return p.x >= 0 && p.x < b.getWell().length && p.y >= 0 && p.y < b.getWell()[0].length;
    }

}
