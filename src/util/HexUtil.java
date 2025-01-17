package util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    public static long bytesToLong(byte[] bytes, boolean endian) {
        return Long.parseLong(bytesToHex(bytes, endian),16);
    }

    public static int byteToDec(byte data, int pos1, int pos2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 7-pos1; i>7-pos2; i--) {
            sb.append(data >>> i & 1);
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
