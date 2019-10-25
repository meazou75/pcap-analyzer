package net;

public class IpAddress {
    public static final int WIDTH = 4;

    public static String toString(byte[] address) {
        String addressStr = "";
        for (int i = 0; i < 4; ++i)
        {
            int t = 0xFF & address[i];
            addressStr += "." + t;
        }
        return addressStr.substring(1);
    }
}
