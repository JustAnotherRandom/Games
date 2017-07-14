package bigO.src.games.TicTacToeAI;

/**
 * Created by Stanimir on 7/13/17.
 */
public class InvalidMoveException extends Exception {

    private static final String TEMPLATE_MESSAGE = "The selected move is invalid";

    public InvalidMoveException(String value) {
        super(String.format(TEMPLATE_MESSAGE, value));
    }
}
