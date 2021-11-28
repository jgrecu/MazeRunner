package maze;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Maze {
    private final int height;
    private final int width;
    private int heightEdges;
    private int widthEdges;

    private int[][] adjacencyMatrix;
    private int[][] minSpanningTree;
    private int[][] maze;

    private Random random = new Random();

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        buildMaze();
    }

    private void buildMaze() {
        buildAdjacencyMatrix();
        buildMinSpanningTree();
        fillMaze();
    }

    private void buildAdjacencyMatrix() {
        heightEdges = (height - 1) / 2;
        widthEdges = (width - 1) / 2;

        adjacencyMatrix = new int[heightEdges * widthEdges][heightEdges * widthEdges];

        int node = 0;
        int edgeWeight;

        //exclude right node
        for (int i = 0; i < adjacencyMatrix.length - 1; i++) {
            // Fill right path
            edgeWeight = getNextRandom();

            if (node + 1 < ((node / widthEdges) + 1) * widthEdges) {
                adjacencyMatrix[node][node + 1] = edgeWeight;
                adjacencyMatrix[node + 1][node] = edgeWeight;
            }

            // Fill lower path
            edgeWeight = getNextRandom();

            if (node + widthEdges < adjacencyMatrix.length) {
                adjacencyMatrix[node][node + widthEdges] = edgeWeight;
                adjacencyMatrix[node + widthEdges][node] = edgeWeight;
            }

            node++;
        }
    }

    private void buildMinSpanningTree() {
        // Build Minimum Spanning Tree with Prim's algorithm.
        minSpanningTree = new int[adjacencyMatrix.length][adjacencyMatrix.length];
        Set<Integer> addedNodes = new HashSet<>();
        addedNodes.add(0);
        int nextNode = 0;
        int currentNode = 0;

        while (addedNodes.size() < adjacencyMatrix.length) {
            int minValues = adjacencyMatrix.length * adjacencyMatrix.length;

            for (int eachNode : addedNodes) {
                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    if (adjacencyMatrix[eachNode][i] < minValues && adjacencyMatrix[eachNode][i] > 0) {
                        if (!addedNodes.contains(i)) {
                            minValues = adjacencyMatrix[eachNode][i];
                            currentNode = eachNode;
                            nextNode = i;
                        }
                    }
                }
            }

            addedNodes.add(nextNode);
            minSpanningTree[currentNode][nextNode] = 1;
            minSpanningTree[nextNode][currentNode] = 1;
        }
    }

    private void fillMaze() {
        maze = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }

        maze[1][0] = 0;
        int currentEdge = 0;
        int mazeRow = 1;
        int mazeCol = 1;

        while (currentEdge < heightEdges * widthEdges) {
            if (currentEdge + 1 < (currentEdge / widthEdges + 1) * widthEdges && minSpanningTree[currentEdge][currentEdge + 1] == 1) {
                maze[mazeRow][mazeCol] = 0;
                maze[mazeRow][mazeCol + 1] = 0;
                maze[mazeRow][mazeCol + 2] = 0;
            }
            if (currentEdge + widthEdges < heightEdges * widthEdges) {
                if (minSpanningTree[currentEdge][currentEdge + widthEdges] == 1) {
                    maze[mazeRow][mazeCol] = 0;
                    maze[mazeRow + 1][mazeCol] = 0;
                    maze[mazeRow + 2][mazeCol] = 0;
                }
            }

            if (currentEdge + 1 <= (currentEdge / widthEdges + 1) * widthEdges - 1) {
                mazeCol += 2;
            } else {
                mazeRow += 2;
                mazeCol = 1;
            }

            currentEdge++;
            if (currentEdge == heightEdges * widthEdges) {
                maze[mazeRow - 2][width - 1] = 0;
            }
        }
    }

    private int getNextRandom() {
        return random.nextInt(height * width);
    }

    public void printMaze() {
        for (int[] row : maze) {
            for (int cell : row) {
                String block = cell == 1 ? "\u2588\u2588" : "  ";
                System.out.printf("%s", block);
            }
            System.out.println();
        }
    }
}