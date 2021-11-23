package maze;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Rules:
 * 1. There should be walls around the maze, except for two cells: entrance and exit.
 * 2. Any empty cell must be accessible from the entrance or exit of the maze. It is not possible to walk along the
 *    maze diagonally, only vertically and horizontally.
 * 3. There's got to be a path from the entrance to the exit. It doesn't matter what is considered an entrance and
 *    what is an exit as they are interchangeable.
 * 4. The maze should not contain 3x3 blocks consisting of walls only. Try to fill the entire maze area with pathways.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the size of a maze");
        String[] input = scanner.nextLine().strip().split("\\s+");
        int rows = Integer.parseInt(input[0]);
        int columns = Integer.parseInt(input[1]);

        int[][] maze = createMaze(rows, columns);

        printMaze(maze);
    }

    private static int[][] createMaze(int rows, int columns) {
        int[][] array = new int[rows][columns];
        for (int[] row : array) {
            Arrays.fill(row, 1);
        }

        /*array = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};*/
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (i == 0 || i == array.length - 1) {
                    array[i][j] = 1;
                } else {
                    array[i][j] = rand.nextInt(2);
                }
            }
        }
        return array;
    }

    private static void printMaze(int[][] maze) {
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "\u2588\u2588":"  ");
            }
            System.out.println();
        }
    }
    
}
