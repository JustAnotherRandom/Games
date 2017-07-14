package bigO.src.games.TicTacToeAI;

import java.io.IOException;

/**
 * Created by Stanimir on 7/12/17.
 */
public class Human implements Player {
    private Game game;
    private Seed seed;


    public Human(Game game, Seed seed) {
        this.game = game;
        this.seed = seed;
    }

    @Override
    public void playTurn() {

        System.out.println("Enter move");

        int userInput = 0;
        try {
            userInput = game.getUserInput();
            int[] selectedPosition = Game.POSITIONS.get(userInput);

            game.insertElementAt(selectedPosition, seed.val);


        } catch (IOException | InvalidMoveException e) {
            System.out.println(e.getMessage());
            this.playTurn();
        }

    }

    @Override
    public Seed getSeed() {
        return seed;
    }
}
