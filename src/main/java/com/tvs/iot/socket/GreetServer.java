package com.tvs.iot.socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.net.*;
import java.io.*;

//@Configuration
public class GreetServer implements Runnable {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    private String host;
    private int port;

    private static boolean flag = true;

    public static void stopRunning()
    {
        flag = false;
    }

    public GreetServer() {}

    public GreetServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(){
        System.out.println("thread is running...");
        try {
            serverSocket = new ServerSocket(this.port, 20, InetAddress.getByName(this.host));
            clientSocket = serverSocket.accept();
            this.startServer(host, port);
        } catch (Exception e) {
            System.out.println("Error in Greent start " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void startServer(String host, int port) {
        try {
//            serverSocket = new ServerSocket(port, 20, InetAddress.getByName(host));
//            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);


//            BufferedReader stdIn =
//                    new BufferedReader(
//                            new InputStreamReader(System.in));

            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(new FileInputStream("/Users/sivadineshm/Documents/projects/iot/src/main/resources/filename.txt")));



            String userInput;
//            while ((userInput = stdIn.readLine()) != null) {
            while(flag) {
                out.println(stdIn.readLine());
Thread.sleep(5000);
                System.out.println("from server : " );
            }



        } catch(Exception e) {
            System.out.println("Error in Server >> " + e.getMessage());
        }

    }



    public static void closeServer() throws IOException {
        stopRunning();
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (!clientSocket.isClosed()) {
            clientSocket.close();
        }
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }

    }


    public void stop() {
        try {
         //   in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {

            System.out.println("Error in stop greet server " + e.getMessage());
        }

    }

//    public static void main(String[] args) {
//        GreetServer server = new GreetServer();
//        server.start(6666);
//    }

}