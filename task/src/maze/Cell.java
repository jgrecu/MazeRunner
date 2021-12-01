package maze;

import java.io.Serializable;

public class Cell implements Serializable {
    private final int[] coordinates;
    private boolean isWall;
    private boolean isVisited;
    private int label;

    Cell(int x, int y) {
        this.coordinates = new int[]{x, y};
        this.isWall = true;
        this.isVisited = false;
        this.label = -1;
    }

    int getX() {
        return coordinates[0];
    }

    int getY() {
        return coordinates[1];
    }

    void setVisited() {
        isVisited = true;
    }

    void setUnvisited() {
        isVisited = false;
    }

    boolean isVisited() {
        return isVisited;
    }

    int getLabel() {
        return label;
    }

    void setLabel(int number) {
        label = number;
    }

    void setPass() {
        this.isWall = !isWall;
    }

    boolean isWall() {
        return isWall;
    }

    String show() {
        if (isWall()) {
            return "\u2588\u2588";
        } else {
            return "  ";
        }
    }
}
