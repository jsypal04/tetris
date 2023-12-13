import java.util.HashMap;
import java.util.Random;

public class Screen {
    private int WIDTH;
    private int HEIGHT;
    private int[][] model;
    private Shape activeShape;
    private Shape nextShape;
    private HashMap<String, int[][]> shapes;
    private String[] shapeNames = {"line", "square", "elbow1", "elbow2", "l1", "l2"};

    /**
     * @param width - number of cells wide (NOT PIXELS)
     * @param height - number of cells tall (NOT PIXELS)
     */
    public Screen(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.model = new int[this.HEIGHT][this.WIDTH];
        this.shapes = new HashMap<String, int[][]>();

        for (int i = 0; i < this.HEIGHT; i++) {
            for (int j = 0; j < this.WIDTH; j++) {
                this.model[i][j] = 0;
            }
        }

        int[][] line = {{0, 4}, {0, 3}, {0, 5}, {0, 6}};
        int[][] square = {{0, 4}, {0, 5}, {1, 4}, {1, 5}};
        int[][] elbow1 = {{0, 4}, {0, 3}, {1, 4}, {1, 5}};
        int[][] elbow2 = {{0, 5}, {1, 5}, {1, 4}, {0, 6}};
        int[][] l1 = {{0, 4}, {0, 3}, {0, 5}, {1, 5}};
        int[][] l2 = {{0, 4}, {0, 3}, {1, 3}, {0, 5}};
        this.shapes.put("line", line);
        this.shapes.put("square", square);
        this.shapes.put("elbow1", elbow1);
        this.shapes.put("elbow2", elbow2);
        this.shapes.put("l1", l1);
        this.shapes.put("l2", l2);

        this.nextShape = this.createShape();
        this.activeShape = this.createShape();
        this.drawShape(this.activeShape.getCoords());
    }

    /**
     * @param coords - the coordinates of the shape you are drawing
     */
    public void drawShape(int[][] coords) {
        for (int i = 0; i < coords.length; i++) {
            this.model[coords[i][0]][coords[i][1]] = 1;
        }
    }

    public void clear() {
        for (int i = 0; i < this.HEIGHT; i++) {
            for (int j = 0; j < this.WIDTH; j++) {
                if (this.model[i][j] != 2) {
                    this.model[i][j] = 0;
                }
            }
        }
    }

    /**
     * @return - returns a false if the shape can be moved down or truw if it has landed
     */
    public boolean shapeHasLanded() {
        int[][] coords = this.activeShape.getCoords(); 

        for (int i = 0; i < coords.length; i++) {
            if (coords[i][0] == this.HEIGHT - 1) {
                return true;
            }
            else if (this.model[coords[i][0] + 1][coords[i][1]] == 2) {
                return true;
            }
        }
        return false;
    }

    public void moveDown() {
        if (this.shapeHasLanded()) {
            int[][] coords = this.activeShape.getCoords();
            for (int i = 0; i < coords.length; i++) {
                this.model[coords[i][0]][coords[i][1]] = 2;
            }
        
            this.activeShape = this.nextShape;
            this.nextShape = this.createShape();
            this.drawShape(this.activeShape.getCoords());
        }
        else {
            this.activeShape.moveDown();
            this.clear();
            this.drawShape(this.activeShape.getCoords());
        }
    }

    public void rotateShape() {
        try {
            this.activeShape.rotate();
            this.clear();
            this.drawShape(this.activeShape.getCoords());
        }
        catch (Exception ArrayIndexOutOfBoundsException) {
            this.activeShape.rotate();

            // start buggy code
            boolean offscreen = true;
            int[][] coords = this.activeShape.getCoords();
            while (offscreen) {
                int count = 0;
                for (int i = 0; i < coords.length; i++) {
                    if (coords[i][1] < 0) {
                        this.moveSideways(39);
                    }
                    else if (coords[i][1] >= this.WIDTH) {
                        this.moveSideways(37);
                    }
                    else {
                        count++;
                    }
                }
                offscreen = count < 4;
            } // end buggy code
        }
    }

    /**
     * @param direction - the key code of either the left of right arrow
     */
    public void moveSideways(int direction) {
        try {
            this.activeShape.moveSideways(direction);
            this.clear();
            this.drawShape(this.activeShape.getCoords());
        }
        catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Hit a wall.");
        }
    }

    public boolean checkLeft() {
        int[][] coords = this.activeShape.getCoords();
        for (int i = 0; i < coords.length; i++) {
            if (coords[i][1] == 0 || (coords[i][1] != 0 && this.model[coords[i][0]][coords[i][1] - 1] == 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRight() {
        int[][] coords = this.activeShape.getCoords();
        for (int i = 0; i < coords.length; i++) {
            if (coords[i][1] == this.WIDTH - 1 || (coords[i][1] != this.WIDTH - 1 && this.model[coords[i][0]][coords[i][1] + 1] == 2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return - the row index of the full line, -1 if there is no full line
     */
    public int checkLine() {
        for (int row = 0; row < this.HEIGHT; row++) {
            int sum = 0;
            for (int col = 0; col < this.WIDTH; col++) {
                sum += this.model[row][col];
            }
            if (sum == 20) {
                return row;
            }
        }
        return -1;
    }

    /**
     * @param row - the row index to be removed
     * removes the row index and moves the rest of the stuff down to fill in the gaps
     */
    public void removeRow(int row) {
        // for each row above the inputed row
        for (int r = row; r > 0; r--) {
            // for each element in the above row
            for (int col = 0; col < this.WIDTH; col++) {
                // if the row is the top row, set the element to 0
                this.model[r][col] = this.model[r - 1][col];
            }
        }
    }

    public Shape createShape() {
        Random rand = new Random();
        String name = this.shapeNames[rand.nextInt(this.shapeNames.length)];
        // copy the coordinates into a new array, this ensures that the start coordinates don't get overidden when the shape moves
        int[][] newCoords = new int[4][2];
        int[][] startCoords = this.shapes.get(name);
        for (int i = 0; i < startCoords.length; i++) {
            newCoords[i][0] = startCoords[i][0];
            newCoords[i][1] = startCoords[i][1];
        }
        System.out.println(name);
        return new Shape(newCoords, name);
    }

    public void print() {
        for (int i = 0; i < this.HEIGHT; i++) {
            for (int j = 0; j < this.WIDTH; j++) {
                System.out.print(this.model[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public int getModelIndex(int row, int col) {
        return this.model[row][col];
    }

    public Shape getNextShape() {
        return this.nextShape;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public int getWidth() {
        return this.WIDTH;
    }
}
