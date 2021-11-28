package maze;

import java.util.Scanner;

/**
 * Rules:
 * 1. There should be walls around the maze, except for two cells: entrance and exit.
 * 2. Any empty cell must be accessible from the entrance or exit of the maze. It is not possible to walk along the
 * maze diagonally, only vertically and horizontally.
 * 3. There's got to be a path from the entrance to the exit. It doesn't matter what is considered an entrance and
 * what is an exit as they are interchangeable.
 * 4. The maze should not contain 3x3 blocks consisting of walls only. Try to fill the entire maze area with pathways.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the size of a maze");
        String[] input = scanner.nextLine().strip().split("\\s+");
        int height = Integer.parseInt(input[0]);
        int width = Integer.parseInt(input[1]);

        Maze maze = new Maze(height, width);
        maze.printMaze();
    }

}
