package maze;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Maze implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int height;
    private final int width;
    private int heightEdges;
    private int widthEdges;
    private int totalNodes;
    private int entranceX;
    private int entranceY;
    private int exitX;
    private int exitY;

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
        /*heightEdges = (height - 1) / 2;
        widthEdges = (width - 1) / 2;*/
        heightEdges = (int) Math.ceil(((double) height - 2) / 2);
        widthEdges = (int) Math.ceil(((double) width - 2) / 2);
        totalNodes = heightEdges * widthEdges;

        adjacencyMatrix = new int[totalNodes][totalNodes];

        //iterate through all the possible nodes and create relationships with their right and upper neighbors
        int node = 0;

        for (int i = 0; i < heightEdges; i++) {
            for (int j = 0; j < widthEdges; j++) {
                if (j < widthEdges - 1) {
                    // if it's not the last column, add an edge to its right neighbor
                    // since the adjacencyMatrix is symmetric, the same value is added to its symmetric counterpart
                    int edgeWeight = random.nextInt(height * width) + 1;
                    adjacencyMatrix[node][node + 1] = edgeWeight;
                    adjacencyMatrix[node + 1][node] = edgeWeight;
                }

                if (i > 0) {
                    // if it's not the first row, add an edge to its upper neighbor
                    // since the adjacencyMatrix is symmetric, the same value is added to its symmetric counterpart
                    int edgeTop = random.nextInt(width * height) + 1;
                    int topNeighbor = node - widthEdges;
                    adjacencyMatrix[node][topNeighbor] = edgeTop;
                    adjacencyMatrix[topNeighbor][node] = edgeTop;
                }

                if (node == totalNodes - 1) {
                    break;
                } else {
                    node++;
                }
            }
        }
    }

    private void buildMinSpanningTree() {
        // Build Minimum Spanning Tree with Prim's algorithm.
        minSpanningTree = new int[adjacencyMatrix.length][adjacencyMatrix[0].length];
        Set<Integer> addedNodes = new HashSet<>();
        addedNodes.add(0);

        while (addedNodes.size() < adjacencyMatrix.length) {
            int minValues = 2 * (int) Math.pow(adjacencyMatrix.length, 2);

            int nextNode = 0;
            int currentNode = 0;

            for (int eachNode : addedNodes) {
                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    // Check each node in the set for the value that it is not zero and that it is not in the set
                    if (adjacencyMatrix[eachNode][i] < minValues && adjacencyMatrix[eachNode][i] > 0) {
                        if (!addedNodes.contains(i)) {
                            minValues = adjacencyMatrix[eachNode][i];
                            currentNode = eachNode;
                            nextNode = i;
                        }
                    }
                }
            }

            //adds the next node (the one with the lower weight of all the available ones)
            addedNodes.add(nextNode);
            //adds the current edge to the tree with a value of 1
            minSpanningTree[currentNode][nextNode] = 1;
            minSpanningTree[nextNode][currentNode] = 1;
        }
    }

    private void fillMaze() {
        maze = new int[height][width];

        /*
            in this maze, the value of 0 means a pass and the value of 1 means a wall
         */

        //fills the maze with walls
        for (int[] row : maze) {
            Arrays.fill(row, 1);
        }

        //iterate over every node to add passes to the connections
        int currentNode = 0;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                //since all the nodes are added to the impair columns and rows (because the first one -0- is a wall)
                if (i != 0 && j != 0 && i != this.height - 1 && j != this.width - 1) {
                    if (i % 2 != 0 && j % 2 != 0) { //if it's a node
                        maze[i][j] = 0; // each node is also a pass

                        /*
                        iterate over the "currentNode" row of the minimum spanning tree to see which
                        node the "currentNode" is attached to.
                         */

                        for (int k = 0; k < minSpanningTree.length; k++) {
                            if (minSpanningTree[currentNode][k] == 1) {

                                // both of these equations were obtained empirically with trial and error.
                                int nodeRow = getNodeRow(k);
                                int nodeColumn = getNodeColumn(k);

                                //convert the space between the current node and the found node to a pass
                                //the node itself will become a pass when the condition
                                //written above is true (i % 2 != 0 && j % 2 != 0)

                                int edgeRow = (int) Math.floor(((double) nodeRow + i) / 2);
                                int edgeCol = (int) Math.floor(((double) nodeColumn + j) / 2);

                                maze[edgeRow][edgeCol] = 0;
                            }
                        }
                        currentNode++;
                    }
                }
            }
        }

        //Generate the entrance and exit next to any of the nodes
        int entranceNode = random.nextInt(widthEdges); //any of the first nodes
        int exitNode = this.totalNodes - random.nextInt(widthEdges); // any of the last few

        int entranceNodeColumn = getNodeColumn(entranceNode);
        int entranceNodeRow = getNodeRow(entranceNode);

        int exitNodeColumn = getNodeColumn(exitNode);
        int exitNodeRow = getNodeRow(exitNode);

        maze[entranceNodeRow - 1][entranceNodeColumn] = 0;
        entranceX = entranceNodeRow - 1;
        entranceY = entranceNodeColumn;

        /*
        in the case of the exit it can happen that if the height is a pair number, there will be 2 rows of
        walls, so I have to check that as well.
         */

        maze[exitNodeRow + 1][exitNodeColumn] = 0;
        exitX = exitNodeRow + 1;
        exitY = exitNodeColumn;

        if (exitNodeRow + 2 != this.height) {
            maze[exitNodeRow + 2][exitNodeColumn] = 0;
            exitX = exitNodeRow + 2;
            exitY = exitNodeColumn;
        }
    }

    public void printMaze() {
        if (this.maze == null) {
            System.out.println("The maze is null.");
        } else {
            for (int[] row : maze) {
                for (int cell : row) {
                    String block = cell == 1 ? "\u2588\u2588" : "  ";
                    System.out.printf("%s", block);
                }
                System.out.println();
            }
        }
    }

    public void findTheEscape() {
        int[][] pathMaze = Arrays.copyOf(maze, maze.length);
        System.out.println("Maze: " + Arrays.deepToString(maze));
        System.out.println("Copy: " + Arrays.deepToString(pathMaze));
        System.out.println("=========================================");

        char[] known = new char[minSpanningTree.length];
        int[] cost = new int[minSpanningTree.length];
        int[] path = new int[minSpanningTree.length];

        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(path, Integer.MAX_VALUE);

        int index = 0;
        known[0] = 'T';
        cost[0] = 0;
        path[0] = -1;

        pathMaze[exitX][exitY] = 2;
        pathMaze[entranceX][entranceY] = 2;

        for (int j = 0; j < minSpanningTree.length; j++) {
            for (int i = 1; i < minSpanningTree.length; i++) {
                if (minSpanningTree[index][i] > 0 && known[i] != 'T') {
                    if (cost[i] > cost[index] + minSpanningTree[index][i]) {
                        cost[i] = cost[index] + minSpanningTree[index][i];
                        path[i] = index;
                    }
                }
            }

            int min = Integer.MAX_VALUE;

            for (int i = 0; i < cost.length; i++) {
                if (known[i] != 'T' && cost[i] < min) {
                    min = cost[i];
                    index = i;
                }
            }
            known[index] = 'T';
        }

        index = path.length - 1;

//        Set<Integer> resultSet = new HashSet<>();

        while (index != 0) {
//            resultSet.add(path[index]);
            index = path[index];
            //int i = (index / heightEdges * 2) + 1;
            //int j = (index % heightEdges * 2) + 1;
            int i = getNodeRow(index);
            int j = getNodeColumn(index);

            pathMaze[i][j] = 2;
            System.out.println("I: " + i + " J: " + j);

            if (j + 2 < width && pathMaze[i][j + 2] == 2 && pathMaze[i][j + 1] != 1) {
                pathMaze[i][j + 1] = 2;
            } else if(j - 2 > 0 && pathMaze[i][j - 2] == 2 && pathMaze[i][j - 1] != 1) {
                pathMaze[i][j - 1] = 2;
            } else if(i + 2 < height && pathMaze[i + 2][j] == 2 && pathMaze[i + 1][j] != 1) {
                pathMaze[i + 1][j] = 2;
            } else {
                pathMaze[i - 1][j] = 2;
            }
        }

        System.out.println("Maze: " + Arrays.deepToString(maze));
        System.out.println("Copy: " + Arrays.deepToString(pathMaze));
        for (int[] line : pathMaze) {
            for (int num : line) {
                String block = num == 1 ? "\u2588\u2588" : num == 2 ? "//" :  "  ";
                System.out.printf("%s", block);
            }

            System.out.println();
        }
    }

    private int getNodeColumn(int nodeNumber) {
        return 1 + 2 * (nodeNumber - this.widthEdges * (int) Math.floor((nodeNumber / (double) this.widthEdges)));
    }

    private int getNodeRow(int nodeNumber) {
        return (int) Math.floor((nodeNumber / (double) this.widthEdges)) * 2 + 1;
    }
}