package bigO.src.games.Tetris.pieces;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanimir on 7/7/17.
 */
public interface Piece {

    enum Orientation {
        ONE(0),
        TWO(1),
        THREE(2),
        FOUR(3);

        private int orientation;

        private static Map<Integer, Orientation> ORIENTATION_KINDS = new HashMap<>(4);

        static {
            for (Orientation o : EnumSet.allOf(Orientation.class)) {
                ORIENTATION_KINDS.put(o.orientation, o);
            }
        }

        Orientation(int o) {
            this.orientation = o;
        }

        int getValue() {
            return orientation;
        }

        public static Orientation forValue(int v) {
            return ORIENTATION_KINDS.get(v);
        }
    }

    void rotateClockWise();

    void dropDown(int offsetX, int offsetY);


    TetrisPoint[] getPoints(int offset, int offsetY);


    Orientation getOrientation();

}
