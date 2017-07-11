package bigO.src.games.Tetris.pieces;

import bigO.src.games.Tetris.Board;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Stanimir on 7/10/17.
 */
public class PieceFactory {
    private static final Random RND = new Random();
    private static final Random RND_C = new Random();

    private static List<Class> PIECE_TYPES = new ArrayList<>(Arrays.asList(LShapedPiece.class, SquarePiece.class));//TODO how to do it dynamically

    public static Piece generatePiece(Board b) {

        Piece piece = null;
        try {
            piece = (BasePiece) PIECE_TYPES.get(RND.nextInt(PIECE_TYPES.size())).getConstructor(Board.class, Color.class).newInstance(b, Color.red);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return piece;
    }
}
