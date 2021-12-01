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
        private static final Scanner scanner = new Scanner(System.in);
        private static Maze maze = null;
        private static String fileName;
        private static boolean hasMaze = false;

    public static void main(String[] args) {
        boolean isGameOver = false;

        while (!isGameOver) {
            printMenu();

            int action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                case 1:
                    System.out.println("Please, enter the size of a maze");
                    String[] input = scanner.nextLine().strip().split("\\s+");
                    if (input.length < 2) {
                        int size = Integer.parseInt(input[0]);
                        maze = new Maze(size, size);
                    } else {
                        int height = Integer.parseInt(input[0]);
                        int width = Integer.parseInt(input[1]);
                        maze = new Maze(height, width);
                    }

                    hasMaze = true;
                    maze.printMaze();
                    break;
                case 2:
                    System.out.println("Enter the file name:");
                    fileName = scanner.nextLine();

                    try {
                        maze = (Maze) Serialiser.loadMaze(fileName);
                        hasMaze = true;
                        //System.out.println("Maze loaded successfully!");
                    } catch (Exception e) {
                        hasMaze = false;
                        System.out.println("Cannot load the maze. It has an invalid format");
                    }
                    break;
                case 3:
                    if (!hasMaze) {
                        System.out.println("Incorrect option. Please try again");
                    } else {
                        System.out.println("Enter the file name:");
                        fileName = scanner.nextLine();

                        try {
                            Serialiser.saveMaze(maze, fileName);
                            //System.out.println("Maze saved successfully!");
                        } catch (Exception e) {
                            System.out.println("ERROR! Not successfull. " + e.getMessage());
                        }
                    }
                    break;
                case 4:
                    if (hasMaze && maze != null) {
                        maze.printMaze();
                    }
                    break;
                case 5:
                    if (hasMaze) {
                        maze.findTheEscape();
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    isGameOver = true;
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze");

        if (hasMaze) {
            System.out.println("3. Save the maze\n" +
                    "4. Display the maze\n" +
                    "5. Find the escape");
        }
        System.out.println("0. Exit");
    }

}
