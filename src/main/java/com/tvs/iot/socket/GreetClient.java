package com.tvs.iot.socket;

import com.tvs.iot.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//@Configuration
public class GreetClient implements Runnable {

    private String host;
    private int port;

    private static String hexval;

    public GreetClient() {}

    public GreetClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run(){
        startConnection(this.host, this.port);
    }

  public void startConnection(String host, int port) {
      System.out.println("clien " + host + port);

      try {
          Socket echoSocket = new Socket(host, port);
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
                this.hexval = userInput;

              System.out.println("DECIMAL value >> " + Helper.HexToDecimal(userInput));

          }



      } catch (UnknownHostException e) {

          System.exit(1);
      } catch (IOException e) {
          System.err.println("Couldn't get I/O for the connection to " );
          System.exit(1);
      }
  }

    public static String getHexval() {
        return  hexval;

    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

//    public void startConnection(String ip, int port) {
//        try {
//            clientSocket = new Socket(ip, port);
//
//            out = new PrintWriter(clientSocket.getOutputStream(), true);
//
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//
//
//        } catch (IOException e) {
//            System.out.println("Error ? " + e.getMessage());
//        }
//
//    }



//    public String sendMessage(String msg) {
//        try {
//            out.println(msg);
//            System.out.println("message > " + msg);
//            return in.readLine();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error ???? " + e.getMessage());
        }
    }



}






