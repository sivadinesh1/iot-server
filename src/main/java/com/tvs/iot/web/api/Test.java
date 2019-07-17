package com.tvs.iot.web.api;

public class Test {
    public static void main(String[] args) {
        long binaryNumber;
        String s = "00000";
    try {
        binaryNumber = Long.parseLong(s);

        System.out.println("binary value " + binaryNumber);
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("err log " + e.getMessage());
    }


    }
}
