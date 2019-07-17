package com.tvs.iot.business;

import java.io.*;
import java.net.Socket;

public class GreetClient1 {

    public static void main(String[] args) {
        GreetClient1 test = new GreetClient1();
        DataInputStream dout = null;
        try {
            System.out.println("111");
            //Socket echoSocket = new Socket("192.168.0.1", 2112);
            Socket echoSocket = new Socket("127.0.0.1", 2111);

             dout=new DataInputStream(echoSocket.getInputStream());

            byte[] b = new byte[25];

            while ((dout.read(b)) != -1) {

                System.out.println("MSG from Reader >> " + b);

            }




            echoSocket.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }



    }
}
