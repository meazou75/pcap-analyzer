package util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {
    public static byte[] extractDataFromFile(String filepath) throws IOException {
        RandomAccessFile f = new RandomAccessFile(filepath, "r");
        byte[] buffer = new byte[(int) f.length()];
        f.readFully(buffer);
        return buffer;
    }
}
