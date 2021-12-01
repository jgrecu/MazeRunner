package maze;

import java.io.*;
import java.util.Scanner;

public class Menu {
    static final Scanner sc = new Scanner(System.in);
    private Maze maze;

    void show() {
        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");
            if (maze != null) {
                System.out.println("3. Save the maze");
                System.out.println("4. Display the maze");
                System.out.println("5. Find the escape");
            }
            System.out.println("0. Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    generateNewMaze();
                    break;
                case "2":
                    loadMaze();
                    break;
                case "3":
                    if (maze != null) {
                        saveMaze();
                        break;
                    }
                    incorrectOption();
                    break;
                case "4":
                    if (maze != null) {
                        displayMaze();
                        break;
                    }
                    incorrectOption();
                    break;
                case "5":
                    if (maze != null) {
                        findEscape();
                        break;
                    }
                    incorrectOption();
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    incorrectOption();
            }
        }
    }

    private void incorrectOption() {
        System.out.println("Incorrect option. Please try again");
    }

    private void generateNewMaze() {
        System.out.println("Please, enter the size of a maze");
        int size = Integer.parseInt(sc.nextLine());
        maze = new Maze(size);
        maze.primsAlgorithm();
        maze.setPath();
        maze.drawMaze();
        System.out.println();
    }

    private void loadMaze() {
        final String filePath = sc.nextLine();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            maze = (Maze) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " does not exist");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load the maze. It has an invalid format");
        }
    }

    private void saveMaze() {
        final String filePath = sc.nextLine();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(maze);
        } catch (IOException ignored) {
            System.out.println("Cannot save the maze.");
        }
    }

    private void displayMaze() {
        maze.drawMaze();
    }

    private void findEscape() {
        MazeSolver solver = new MazeSolver(maze);
        if (maze.getExit().isVisited()) {
            maze.setUnvisited();
        }
        solver.solveMaze();
        solver.drawSolvedMaze();
    }
}