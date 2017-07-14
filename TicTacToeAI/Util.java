package bigO.src.games.TicTacToeAI;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Stanimir on 7/12/17.
 */
public class Util {

    public static int readNextInt(Reader input) throws IOException {
        char[] data = new char[64];
        input.read(data);
        return Integer.valueOf(String.valueOf(data).trim());

    }


}
