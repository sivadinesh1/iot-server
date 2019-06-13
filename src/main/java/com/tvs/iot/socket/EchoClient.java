package com.tvs.iot.socket;

import com.tvs.iot.util.Helper;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {




        try {
                Socket echoSocket = new Socket("127.0.0.1", 6666);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));

                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in));

            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println("Hexa VALUE: " + userInput);


                System.out.println("DECIMAL value >> " + Helper.HexToDecimal(userInput));

            }



        } catch (UnknownHostException e) {

            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " );
            System.exit(1);
        }
    }
}

