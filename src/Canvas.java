import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener {
    public Screen gameScreen;

    public Canvas() {
        super();
        this.setBackground(Color.lightGray);
        this.gameScreen = new Screen(10, 20);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        // int row = this.gameScreen.checkLine();
        // if (row != -1) {
        //     this.gameScreen.removeRow(row);
        //     return 1;
        // }
        // return 0;
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

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}
}
