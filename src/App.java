import javax.swing.JFrame;

// class for the gui
class Window extends JFrame {
    // game constants
    private int WIDTH;
    private int HEIGHT;

    private int frameRate;
    private int second;

    // game variables
    private int linesCleared;

    // the canvas that the game will be drawn on
    private Canvas canvas;
    
    public Window(String title) {
        // call JFrame's constructor
        super(title);

        // set constants
        this.WIDTH = 216 + 100;
        this.HEIGHT = 438;
        this.frameRate = 1;
        this.second = 1000000000 / 2;

        // game variables
        this.linesCleared = 0;

        // set the frame properties
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a new canvas and add a key listener
        this.canvas = new Canvas();
        this.addKeyListener(canvas);
        this.add(this.canvas);

        // make frame visible
        this.setVisible(true);
    }

    // method to run the game
    public void runGame() {
        long start = System.nanoTime(); // starts the timer
        while (true) {

            // checks to see if the timer has reached about 15 miliseconds, this gives you about 66fps
            if (System.nanoTime() - start >= this.second / frameRate) {
                // move the shape down then repaint the screen
                this.canvas.moveDown();
                this.canvas.repaint();

                // check if a line if full and clear it
                int row = this.canvas.checkLine();
                if (row != -1) {
                    this.canvas.removeRow(row);
                    this.linesCleared++;
                    this.canvas.setLinesCleared(this.linesCleared);
                    this.canvas.setScore(this.canvas.getScore() + 10);

                    // speed up the game every line cleared
                    this.frameRate += 0.5;

                    System.out.println(this.linesCleared);
                }

                // reset the clock
                start = System.nanoTime();
            }
        }
    }
}

// driver code
public class App {
    public static void main(String[] args) throws Exception {
        Window win = new Window("Tetris");
        win.runGame();
    }
}
