package bigO.src.games.TicTacToeAI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanimir on 7/12/17.
 */
public class Computer implements Player {
    private Game game;
    private Seed seed;


    public Computer(Game game, Seed seed) {
        this.game = game;
        this.seed = seed;
    }

    @Override
    public void playTurn() {
        int[] res = minimax(game.getAvailablePositions().size(), this.seed);
        try {
            game.insertElementAt(new int[]{res[1], res[2]}, seed.val);
        } catch (InvalidMoveException e) {
            //should never happend
            e.printStackTrace();
        }
    }


    @Override
    public Seed getSeed() {
        return seed;
    }


    private int[] minimax(int depth, Seed playerSeed) {

        // Generate possible next moves in a List of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        int bestScore = (playerSeed == seed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        int[][] cells = game.getBoard();
        Seed oponent = Seed.getOponent(seed);

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                // Try this move for the current "player"
                int old = cells[move[0]][move[1]];
                cells[move[0]][move[1]] = playerSeed.getVal();

                if (playerSeed == seed) {  // mySeed (computer) is maximizing player
                    currentScore = minimax(depth - 1, oponent)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = minimax(depth - 1, seed)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                cells[move[0]][move[1]] = old;
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }

    private int evaluatem() {

        int score = 0;

        //rows
        score += evLine(0, 0, 0, 1, 0, 2);
        score += evLine(1, 0, 1, 1, 1, 2);
        score += evLine(2, 0, 2, 1, 2, 2);


        //cols
        score += evLine(0, 0, 1, 0, 2, 0);
        score += evLine(0, 1, 1, 1, 2, 1);
        score += evLine(0, 2, 1, 2, 2, 2);

        score += evLine(0, 0, 1, 1, 2, 2);
        score += evLine(0, 2, 1, 1, 2, 0);

        return score;
    }

    public static final int SCORE_OF_TWO = 5;
    public static final int SCORE_OF_THREE = 10;

    private int evLine(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[][] well = game.getBoard();

        if (well[x1][y1] == well[x2][y2] && well[x2][y2] == well[x3][y3]) {
            return SCORE_OF_THREE;
        }

        if (well[x1][y1] == well[x2][y2] || well[x2][y2] == well[x3][y3]) {
            return SCORE_OF_TWO;
        }

        return 0;
    }


    //###

    /**
     * Find all valid next moves.
     * Return List of moves in int[2] of {row, col} or empty list if gameover
     */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

        // If gameover, i.e., no next move
        if (game.hasWon(seed) || game.hasWon(Seed.getOponent(seed))) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        return game.getAvailablePositions();
    }

    /**
     * The heuristic evaluation function for the current board
     *
     * @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     * -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     * 0 otherwise
     */
    private int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /**
     * The heuristic evaluation function for the given line of 3 cells
     *
     * @Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
     * -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
     * 0 otherwise
     */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;
        Seed oponent = Seed.getOponent(seed);
        int[][] cells = game.getBoard();

        // First cell
        if (cells[row1][col1] == seed.getVal()) {
            score = 1;
        } else if (cells[row1][col1] == oponent.getVal()) {
            score = -1;
        }

        // Second cell
        if (cells[row2][col2] == seed.getVal()) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (cells[row2][col2] == oponent.getVal()) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (cells[row3][col3] == seed.getVal()) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (cells[row3][col3] == oponent.getVal()) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }


}
