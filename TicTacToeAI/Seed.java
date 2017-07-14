package bigO.src.games.TicTacToeAI;

/**
 * Created by Stanimir on 7/12/17.
 */
enum Seed {
    X(-1), O(-2);

    int val;

    Seed(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static Seed getOponent(Seed seed) {
        return seed == Seed.X ? Seed.O : Seed.X;
    }
}
