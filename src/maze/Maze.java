package maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Maze implements Serializable {
    private static final Random random = new Random();
    private final ArrayList<Cell> nodes; // list of all nodes/vertices
    private final Cell[][] maze; // initial maze
    private final int numberOfVertices;
    private final int size;
    private ArrayList<Cell[]> MST; // list of vertices in minimum spanning tree
    private int[][] adjMatrix; // adjacency matrix
    private Cell start;
    private Cell exit;

    Maze(int size) {
        this.size = size;
        this.nodes = new ArrayList<>();
        this.maze = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }

        setEntranceAndExit();
        setVerticesInInitialMatrix();
        this.numberOfVertices = nodes.size();
        createAdjMatrix();
    }

    Cell getStart() {
        return start;
    }

    Cell getExit() {
        return exit;
    }

    Cell getCell(int x, int y) {
        return maze[x][y];
    }

    Cell[][] getMaze() {
        return maze;
    }


    private void setEntranceAndExit() {
        maze[0][1].setPass();
        start = getCell(0, 1);
        if (size % 2 != 0) {
            maze[size - 1][size - 2].setPass();
            exit = getCell(size - 1, size - 2);
        } else {
            maze[size - 1][size - 2].setPass();
            maze[size - 2][size - 2].setPass();
            maze[size - 3][size - 2].setPass();
            exit = getCell(size - 1, size - 2);

        }
    }

    private void setVerticesInInitialMatrix() {
        int nodeCounter = 0;
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                if (j % 2 != 0 && i % 2 != 0) {
                    maze[i][j].setPass();
                    maze[i][j].setLabel(nodeCounter++);
                    nodes.add(maze[i][j]);
                }
            }
        }
    }

    private void createAdjMatrix() {
        adjMatrix = new int[nodes.size()][nodes.size()];
        int nSize = size % 2 != 0 ? (size - 2) / 2 + 1 : (size - 2) / 2;

        for (int i = 0; i < nSize; i++) {
            for (int j = 0; j < nSize; j++) {
                int n = i * nSize + j;
                if (j > 0) {
                    adjMatrix[n - 1][n] = random.nextInt(9) + 1;
                    adjMatrix[n][n - 1] = adjMatrix[n - 1][n];
                }
                if (i > 0) {
                    adjMatrix[n - nSize][n] = random.nextInt(9) + 1;
                    adjMatrix[n][n - nSize] = adjMatrix[n - nSize][n];
                }
            }
        }
    }

    void primsAlgorithm() {
        ArrayList<Cell> reached = new ArrayList<>(); // list of reached vertices
        ArrayList<Cell> unreached = new ArrayList<>(); // list of unreached vertices
        MST = new ArrayList<>();
        int rIndex = 0;
        int uIndex = 0;

        // fill unreached list with nodes
        for (int i = 0; i < numberOfVertices; i++) {
            unreached.add(nodes.get(i));
        }

        // add first (root) node to the list
        reached.add(unreached.get(0));
        unreached.remove(0);

        while (!unreached.isEmpty()) {
            int max = Integer.MAX_VALUE;

            for (int i = 0; i < reached.size(); i++) {
                for (int j = 0; j < unreached.size(); j++) {
                    int v1 = reached.get(i).getLabel();
                    int v2 = unreached.get(j).getLabel();

                    int d = adjMatrix[v1][v2];

                    if (d != 0 && d < max) {
                        max = d;
                        rIndex = i;
                        uIndex = j;
                    }
                }
            }

            MST.add(new Cell[]{reached.get(rIndex), unreached.get(uIndex)});
            reached.add(unreached.get(uIndex));
            unreached.remove(uIndex);
        }
    }

    void setPath() {
        for (Cell[] value : MST) {
            int x1 = value[0].getX();
            int y1 = value[0].getY();
            int x2 = value[1].getX();
            int y2 = value[1].getY();

            if (x1 == x2) {
                maze[x1][Math.max(y1, y2) - 1].setPass();
            }
            if (y1 == y2) {
                maze[Math.max(x1, x2) - 1][y1].setPass();
            }
        }
    }

    void drawMaze() {
        for (Cell[] row : maze) {
            for (Cell value : row) {
                System.out.print(value.show());
            }
            System.out.println();
        }
    }

    void setUnvisited() {
        for (Cell[] row : maze) {
            for (Cell value : row) {
                value.setUnvisited();
            }
        }
    }
}
