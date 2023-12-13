import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener {
    public Screen gameScreen;
    private int linesCleared = 0;
    private int score = 0;

    public Canvas() {
        super();
        this.setBackground(Color.lightGray);
        this.gameScreen = new Screen(10, 20);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(200, 0, 200, 438);
        g.drawString("Lines cleared: " + Integer.toString(this.linesCleared), 210, 30);
        g.drawString("Score: " + Integer.toString(this.score), 210, 80);
        g.drawString("Next: ", 210, 180);

        for (int i = 0; i < this.gameScreen.getHeight(); i++) {
            for (int j = 0; j < this.gameScreen.getWidth(); j++) {
                if (this.gameScreen.getModelIndex(i, j) == 1 || this.gameScreen.getModelIndex(i, j) == 2) {
                    g.setColor(Color.red);
                    g.fill3DRect(j * 20, i * 20, 20, 20, true);
                }
            }
        }
    }

    public void moveDown() {
        this.gameScreen.moveDown();
    }

    public int checkLine() {
        return this.gameScreen.checkLine();
    }

    public void removeRow(int row) {
        this.gameScreen.removeRow(row);
    }

    // key handler method
    public void keyReleased(KeyEvent e) {
        // if the up key is pressed
        if (e.getKeyCode() == 38) {
            this.gameScreen.rotateShape();
            this.repaint();
        }
        // if the left key is pressed
        else if (e.getKeyCode() == 37 && !gameScreen.checkLeft()) {
            this.gameScreen.moveSideways(e.getKeyCode());
            this.repaint();
        }
        // if the right key is pressed
        else if (e.getKeyCode() == 39 && !this.gameScreen.checkRight()) {
            this.gameScreen.moveSideways(e.getKeyCode());
            this.repaint();
        }
        // if the space bar is pressed
        else if (e.getKeyCode() == 32) {
            // do something
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}
}
