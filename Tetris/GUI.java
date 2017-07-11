package bigO.src.games.Tetris;

import bigO.src.games.Tetris.pieces.Piece;
import bigO.src.games.Tetris.pieces.TetrisPoint;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Stanimir on 7/8/17.
 */
public class GUI extends JFrame {

    private TetrisPoint[][] points;

    private Board board;

    private Game game;
    private TetrisImpl tetris;

    public GUI(Board board, TetrisImpl g) {
        this.board = board;
        this.points = points;
        this.tetris = g;

        JFrame f = new JFrame("TetrisImpl");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(12 * 26 + 10, 26 * 23 + 25);
        f.setVisible(true);


        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        System.out.println("CLicked UP");
                        tetris.rotatePiece();

//                        game.rotate(-1);
                        break;
//                    case KeyEvent.VK_DOWN:
//                        game.rotate(+1);
//                        break;
                    case KeyEvent.VK_LEFT:
                        tetris.move(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        tetris.move(+1);
                        break;
//                    case KeyEvent.VK_SPACE:
//                        game.dropDown();
//                        game.score += 1;
//                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });


    }

    void draw() {

    }

    static class P {
        int x;
        int y;

        P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            P p = (P) o;
            return x == p.x &&
                    y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public void update() {
        TetrisPoint[][] well = board.getWell();
        Piece activePiece = board.getActivePiece();
        Set<P> temp = new HashSet<>();
        for (TetrisPoint pp : activePiece.getPoints(0, 0)) {
            temp.add(new P(pp.x, pp.y));

        }


        for (int i = well.length - 1; i >= 0; i--) {
            for (int j = 0; j < well.length; j++) {
                TetrisPoint p = well[i][j];
                if (p != null || temp.contains(new P(i, j))){
                    System.out.print(".X ");
                }else{
                    System.out.print(" - ");
                }

            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
