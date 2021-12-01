package maze;

import java.util.ArrayDeque;
import java.util.Deque;

public class MazeSolver {
    private Deque<Cell> stack;
    private final Maze maze;
    private final Cell start;
    private final Cell exit;

    MazeSolver(Maze maze) {
        this.maze = maze;
        start = maze.getStart();
        exit = maze.getExit();
    }

    void solveMaze() {
        stack = new ArrayDeque<>();
        stack.addFirst(start);

        while (true) {
            int x = stack.peekFirst().getX();
            int y = stack.peekFirst().getY();

            if (stack.peekFirst() == exit) {
                break;
            }

            if (!maze.getCell(x + 1, y).isWall() && !maze.getCell(x + 1, y).isVisited()) {
                stack.addFirst(maze.getCell(x + 1, y));
                maze.getCell(x + 1, y).setVisited();
            } else if (x > 0 && !maze.getCell(x - 1, y).isWall() && !maze.getCell(x - 1, y).isVisited()) {
                stack.addFirst(maze.getCell(x - 1, y));
                maze.getCell(x - 1, y).setVisited();
            } else if (!maze.getCell(x, y + 1).isWall() && !maze.getCell(x, y + 1).isVisited()) {
                stack.addFirst(maze.getCell(x, y + 1));
                maze.getCell(x, y + 1).setVisited();
            } else if (y > 0 && !maze.getCell(x, y - 1).isWall() && !maze.getCell(x, y - 1).isVisited()) {
                stack.addFirst(maze.getCell(x, y - 1));
                maze.getCell(x, y - 1).setVisited();
            } else {
                stack.pollFirst();
            }
        }
    }

    void drawSolvedMaze() {
        for (Cell[] row : maze.getMaze()) {
            for (Cell value : row) {
                if (value.isWall()) {
                    System.out.print("\u2588\u2588");
                } else if (stack.contains(value)) {
                    System.out.print("//");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}