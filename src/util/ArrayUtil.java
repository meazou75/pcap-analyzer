package util;

import java.util.Arrays;

public class ArrayUtil {
    public static byte[] sliceBytes (byte[] buffer, int pos, int len) {
        return Arrays.copyOfRange(buffer, pos, pos + len);
    }

    public static byte[] sliceBytes (byte[] buffer, int pos, int len, boolean mode) {
        if(mode) {
            return Arrays.copyOfRange(buffer, pos, len);
        }
        return Arrays.copyOfRange(buffer, pos, pos + len);
    }
}
