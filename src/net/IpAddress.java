package net;

public class IpAddress {
    public static final int WIDTH = 4;

    public static String toString(byte[] address) {
        StringBuilder addressStr = new StringBuilder();
        for (int i = 0; i < 4; ++i)
        {
            int t = 0xFF & address[i];
            addressStr.append(".").append(t);
        }
        return addressStr.substring(1);
    }
}
