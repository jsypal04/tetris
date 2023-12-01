public class Shape {
    private int[][] coords;

    public Shape(int[][] initCoords) {
        this.coords = initCoords;
    }

    public void rotate() {
        for (int i = 1; i < this.coords.length; i++) {
            int deltaX = this.coords[i][1] - this.coords[0][1];
            int deltaY = this.coords[i][0] - this.coords[0][0];

            this.coords[i][1] = this.coords[0][1] + deltaY;
            this.coords[i][0] = this.coords[0][0] - deltaX;
        }
    }

    public void moveSideways(int direction) {
        if (direction == 37) {
            for (int i = 0; i < this.coords.length; i++) {
                this.coords[i][1] -= 1;
            }
        }
        else if (direction == 39) {
            for (int i = 0; i < this.coords.length; i++) {
                this.coords[i][1] += 1;
            }
        }
    }

    public void moveDown() {
        for (int i = 0; i < this.coords.length; i++) {
            this.coords[i][0] += 1;
        }
    }

    public int[][] getCoords() {
        return this.coords;
    }
}
