package util;

import java.io.StringWriter;

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
        int value = 0;

        if(!endian) {
            for(int i=0; i<cnt; i++)
                value |= ((bytes[pos + cnt - i - 1] & 0xff) << 8 * i);
        } else {
            for(int i=cnt; i > 0; i--)
                value |= ((bytes[pos + cnt - i - 1] & 0xff) << 8 * (i+1)%cnt);
        }

        return value;
    }

    public static int hexToDec(String s) {
        return Integer.parseInt(s,16);
    }
}
