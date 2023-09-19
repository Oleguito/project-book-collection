package fileutils;

import java.io.*;
import java.util.List;

public class FileUtils {
    
    public static void objectToFile(Object object, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Object objectFromFile(String fileName) {
        Object object;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    
    // public static List <Object> readObjectsFromFile(String fileName) {
    //     List <Object> objects =
    // }
    
}
