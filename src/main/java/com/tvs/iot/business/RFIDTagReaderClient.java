package com.tvs.iot.business;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tvs.iot.domain.ModelWsDTO;

import java.io.DataInputStream;
import java.net.Socket;

public class RFIDTagReaderClient implements Runnable {

    private String host;
    private int port;
    private DataInputStream dout;
    private  String RFIDStringVal;

    static Socket echoSocket = null;

     public static int Rfid_tag_bit_size = 98;

   // public static int Rfid_tag_bit_size = Integer.parseInt(AppSettings.getRfid_tag_bit_size());


    public RFIDTagReaderClient() {}

    public RFIDTagReaderClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run(){
        startConnection(this.host, this.port);
    }

  public  void startConnection(String host, int port) {


      try {
           echoSocket = new Socket(host, port);
        //  Socket echoSocket = new Socket("192.168.0.1", 2112);

          dout = new DataInputStream(echoSocket.getInputStream());

          byte[] rfidInput = new byte[Rfid_tag_bit_size];

          int data = 0;
            while ((data = dout.read(rfidInput)) != -1) {
                RFIDStringVal = new String(rfidInput);

                if(data == Rfid_tag_bit_size) {

                  RFIDTagProcessor.processRFIDTag(RFIDStringVal);

                }

            }



      } catch (Exception e) {
          e.printStackTrace();
          System.err.println("Error in RFIDTagReaderClient " + e.getMessage());


      }
  }

  public  void  stopClientSocket() {
        try {
            RFIDStringVal = null;
            echoSocket.close();
        } catch (Exception e) {
            System.out.println("error in stop client socket " + e.getMessage());
        }

  }



    public  String getRFIDStringVal() {
        return  RFIDStringVal;

    }


    public static void main(String[] args) {
        RFIDTagReaderClient comm = new RFIDTagReaderClient();
//        comm.startConnection("127.0.0.1", 2111);
    }



}






