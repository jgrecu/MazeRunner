package maze;

public class Main {
    public static void main(String[] args) {
        int[][] maze = createMaze();
        printMaze(maze);
    }

    private static int[][] createMaze() {
        int[][] array;

        array = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
//        Random rand = new Random();
//
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[0].length; j++) {
//                if (i == 0 || i == 9) {
//                    array[i][j] = 1;
//                } else {
//                    array[i][j] = rand.nextInt(2);
//                }
//            }
//        }
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
