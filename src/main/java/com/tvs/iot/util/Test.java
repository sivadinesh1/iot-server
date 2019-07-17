package com.tvs.iot.util;

import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        String t = "Geek";

        toHex(t);
    }

    public static String toHex(String arg) {

        System.out.println("Test >> " + String.format("%040x", new BigInteger(1, arg.getBytes())));

        return String.format("%096x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

}
