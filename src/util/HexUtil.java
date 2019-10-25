package util;

import java.io.StringWriter;
import java.util.Arrays;

public class HexUtil {
    public static char nibbleToDigit(byte x) {
        char c = (char)(x & 0xf); // mask low nibble
        return(c > 9 ? (char)(c - 10 + 'a') : (char)(c + '0')); // int to hex char
    }

    public static String toString(byte b) {
        StringBuffer sb = new StringBuffer();
        sb.append(nibbleToDigit((byte)(b >> 4)));
        sb.append(nibbleToDigit(b));
        return sb.toString();
    }

    public static String toString(byte [] bytes) {
        StringWriter sw = new StringWriter();

        int length = bytes.length;
        if(length > 0) {
            for(int i = 0; i < length; i++) {
                sw.write(toString(bytes[i]));
                if(i != length - 1)
                    sw.write(" ");
            }
        }
        return(sw.toString());
    }

    public static int extractInteger(byte[] bytes, int pos, int cnt, boolean endian) {
        return bytesToDec(Arrays.copyOfRange(bytes, pos, pos+cnt), endian);
    }

    public static String bytesToHex(byte[] bytes, boolean endian) {
        StringBuilder s = new StringBuilder();
        if(!endian) {
            for (byte aByte : bytes) {
                s.append(String.format("%02X", aByte));
            }
        } else {
            for (int i=bytes.length-1;i>-1;i--) {
                s.append(String.format("%02X", bytes[i]));
            }
        }
        return s.toString();
    }

    public static int bytesToDec(byte[] bytes, boolean endian) {
        return Integer.parseInt(bytesToHex(bytes, endian),16);
    }

}
