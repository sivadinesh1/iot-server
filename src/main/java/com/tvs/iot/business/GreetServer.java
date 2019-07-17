package com.tvs.iot.business;


import java.net.*;
import java.io.*;


public class GreetServer implements Runnable {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static FileInputStream stdIn;

    private String host;
    private int port;

    private static boolean flag = true;

    public static void stopRunning()
    {
        flag = false;
        System.out.println("insdie stop running");
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
            flag = true;

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            stdIn = new FileInputStream("/Users/sivadineshm/Documents/projects/iot/src/main/resources/filename.txt");



            java.util.Scanner scanner = new java.util.Scanner(stdIn, "UTF-8").useDelimiter("~");

            //String theString = "";
            while (scanner.hasNext()) {

                String theString = scanner.next();
                out.println(theString);
                out.flush();
                //Thread.sleep(9000);
                System.out.println("SERVER STRING WRITE " + theString);

            }

            } catch(Exception e){
            e.printStackTrace();
                System.out.println("Error in Server >> " + e.getMessage());
            }

        }



    public static void closeServer() throws IOException {
        System.out.println("inside close server");
        stopRunning();

            if (out != null) {
                System.out.println("inside out ");
                out.close();
            }

            if (stdIn != null) {
                System.out.println("inside close server stdin");
                stdIn.close();
            }


            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (!serverSocket.isClosed()) {
                System.out.println("inside close server sserver socket");
                serverSocket.close();
            }
    }

    public void stop() {
        try {
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error in stop greet server " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        GreetServer greetServer = new GreetServer("127.0.0.1", 2111);
        greetServer.run();
    }

}