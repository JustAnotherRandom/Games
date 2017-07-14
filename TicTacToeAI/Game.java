package bigO.src.games.TicTacToeAI;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * http://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html
 */
public class Game {

    public static final void main(String[] args) {
        Game game = new Game();
        game.initPlayers();
        game.start();

    }

    public static final int SIZE = 3;

    public static Map<Integer, int[]> POSITIONS = new HashMap<>(9);

    private Map<Integer, Player> players = new HashMap<>();


    static {
        int index = 1;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                POSITIONS.put(index++, new int[]{row, col});
            }
        }
    }

    private int emptyPositions = (int) Math.pow(SIZE, 2);
    private boolean gameRunning = true;
    private int[][] well = new int[SIZE][SIZE];
    private Reader input;
    private Random rnd = new Random();

    public Game() {
        int ind = 1;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                well[row][col] = ind++;
            }
        }
        input = new InputStreamReader(System.in);
        print();
    }


    private void initPlayers() {
        Player player = new Human(this, Seed.X);
        Player computer = new Computer(this, Seed.O);
        players.put(player.getSeed().getVal(), player);
        players.put(computer.getSeed().getVal(), computer);

    }

    private void start() {
        Player activePlayer = getFirstPlayer();

        while (gameRunning) {
            activePlayer.playTurn();
            print();
            activePlayer = getOponent(activePlayer);
        }
    }

    private Player getFirstPlayer() {
        int randomP = rnd.nextInt(players.keySet().size());
        return (Player) players.values().toArray()[randomP];
    }

    private Player getOponent(Player activePlayer) {
        return players.get(Seed.X.val) == activePlayer ? players.get(Seed.O.val) : players.get(Seed.X.val);//find a better way
    }


    void insertElementAt(int[] selectedPosition, int identifier) throws InvalidMoveException {
        if (!isEmpty(selectedPosition)) throw new InvalidMoveException(" ");

        Player player = players.get(identifier);
        System.out.println("Player " + player.getSeed());
        well[selectedPosition[0]][selectedPosition[1]] = identifier;
        emptyPositions--;

        hasPlayerWon(player);


    }

    private void hasPlayerWon(Player player) {
        if (hasWon(player.getSeed())) {
            System.out.println("Winner: " + player.getSeed());
            gameRunning = false;
        }
    }

    private boolean isEmpty(int[] point) {
        return POSITIONS.containsKey(well[point[0]][point[1]]);
    }

    boolean hasMorePositions() {
        return emptyPositions > 0;
    }


    public void print() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int val = well[i][j];

                if (POSITIONS.containsKey(val)) {
                    System.out.print(" " + val + " ");
                } else
                    System.out.print(" " + players.get(val).getSeed() + " ");

            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean canPlay(int[] selectedPosition) {
        return POSITIONS.containsKey(well[selectedPosition[0]][selectedPosition[1]]);
    }

    public int getUserInput() throws IOException {
        return Util.readNextInt(input);
    }

    public List<int[]> getAvailablePositions() {//TOOD can be optimized
        List<int[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (POSITIONS.containsKey(well[i][j])) {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }
        return availablePositions;
    }

    public int[][] getBoard() {
        return well;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100               // diagonals
    };

    /**
     * Returns true if thePlayer wins
     */
    public boolean hasWon(Seed thePlayer) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < Game.SIZE; ++row) {
            for (int col = 0; col < Game.SIZE; ++col) {
                if (well[row][col] == thePlayer.getVal()) {
                    pattern |= (1 << (row * Game.SIZE + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }
}



