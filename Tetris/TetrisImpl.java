package bigO.src.games.Tetris;

/**
 * Created by Stanimir on 7/7/17.
 */
public class TetrisImpl implements Game, Tetris {

    private Board board;
    private GUI gui;


    private volatile boolean isGameRunning = true;

    Thread ticker = new Thread() {
        @Override
        public void run() {

            while (isGameRunning) {
                try {
                    Thread.sleep(1000);
                    board.dropDown();
                    gui.update();
                } catch (InterruptedException e) {
                }
            }
        }
    };


    public TetrisImpl() {

        board = new Board(this);
        gui = new GUI(board, this);
        gui.update();//todo

    }

    @Override
    public void start() {
        ticker.start();
    }

    @Override
    public void endGame() {
        isGameRunning = false;
        System.out.println("The game has been ended");
    }

    @Override
    public void pauseGame() {
        isGameRunning = false;
    }

    @Override
    public void resumeGame() {
        isGameRunning = true;
    }

    @Override
    public void move(int offset) {
        board.move(offset);
        gui.update();
    }

    @Override
    public void rotatePiece() {
        board.rotateActivePiece();
        gui.update();
    }

    @Override
    public void skipFewRows() {

    }

    @Override
    public void hitBottom() {

    }
}
