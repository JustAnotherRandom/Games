package bigO.src.games.minesweeper;

/**
 * Created by Stanimir on 6/12/17.
 */
public class MineSweeper {

    private static final byte GAME_OVER = -5;
    private static final byte BOMB = -1;
    private static final byte VISITED = -2;

    private int[][] board;
    private int toVisit;

    int visited = 0;

    public MineSweeper(int[][] board) {
        this.board = board;
    }

    public MineSweeper(int[][] board, int number_bombs) {
        this.board = board;
        toVisit = board.length * board.length - number_bombs;
    }

    public static void main(String[] str) {
        int[][] board1 = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, -1},
                {0, 0, 0, -1},
        };

        int[][] board2 = new int[][]{
                {0, 0, 0, -1},
                {0, 0, -1, 0},
                {0, -1, 0, 0},
                {-1, 0, 0, 0},
        };
        MineSweeper game = new MineSweeper(board2, 4);


        game.play(new int[]{0, 0});
        game.print();

        game.play(new int[]{2, 2});
    }

    private void print() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                System.out.printf(board[row][col] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void play(int[] p) {

        int res = play(p, null, false);
        if (res == GAME_OVER) {
            System.out.printf("GAME OVER");
        }
        if (isGameWon()) {
            System.out.printf("VICTORY");
        }


    }

    private boolean isGameWon() {
        return visited == toVisit;
    }

    private int play(int[] point, int[] prev, boolean isDiag) {
        print();
        if (!isInBounds(point)) {
            return 0;
        }

        if (isVisited(point)) return 0;

        if (isBomb(point)) {
            if (isFirstClick(prev)) {
                return GAME_OVER;
            } else {
                return 1;
            }

        }

        if (isDiag) return 0;

        markVisited(point);
        visited++;
        System.out.println("");
        System.out.println(visited);

        int row = point[0];
        int col = point[1];

        int sum = 0;
        int[] n = getPoint(row - 1, col);
        sum += play(n, point, false);

        int[] nw = getPoint(row - 1, col - 1);
        sum += play(nw, point, true);

        int[] ne = getPoint(row - 1, col + 1);
        sum += play(ne, point, true);

        int[] s = getPoint(row + 1, col);
        sum += play(s, point, false);

        int[] sw = getPoint(row + 1, col - 1);
        sum += play(sw, point, true);

        int[] se = getPoint(row + 1, col + 1);
        sum += play(se, point, true);

        int[] w = getPoint(row, col - 1);
        sum += play(w, point, false);

        int[] e = getPoint(row, col + 1);
        sum += play(e, point, false);


        board[row][col] = sum > 0 ? sum : VISITED;


        return 0;

    }

    private boolean isVisited(int[] p) {
        return board[p[0]][p[1]] == VISITED || board[p[0]][p[1]] > 0;
    }

    private int[] getPoint(int x, int y) {
        return new int[]{x, y};
    }

    private void markVisited(int[] point) {
        board[point[0]][point[1]] = VISITED;
    }

    private boolean isFirstClick(int[] prev) {
        return prev == null;
    }

    private boolean isBomb(int[] point) {
        int x = point[0];
        int y = point[1];
        return board[x][y] == BOMB;
    }

    private boolean isInBounds(int[] point) {
        int x = point[0];
        int y = point[1];
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }

}
