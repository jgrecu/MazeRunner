package maze;

import java.io.*;

public class Serialiser {

    static void saveMaze(Maze maze, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(maze);
        oos.close();
        bos.close();
        fos.close();
    }

    static Object loadMaze(String fileName) throws IOException, ClassNotFoundException {
        Object maze = null;

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        maze = ois.readObject();
        ois.close();
        bis.close();
        fis.close();

        return maze;
    }
}
