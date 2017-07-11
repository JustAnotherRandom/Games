package bigO.src.games.Tetris;

/**
 * Created by Stanimir on 7/10/17.
 */
public interface Game {
    void start();

    void endGame();

    void pauseGame();

    void resumeGame();

    void move(int offset);
}
