package com.tvs.iot.business;


import com.tvs.iot.domain.ModelWsDTO;
import com.tvs.iot.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class RFIDTagProcessor {

    @Autowired
    AppSettings settings;

    public static String currentJig = "";
    public static String currentModel = "";


    //public static int cnt = Integer.valueOf(AppSettings.getNo_of_workstations());
    public static int cnt = 10;
  //  public static int Rfid_tag_bit_size = 96;
     public static int Rfid_tag_bit_size = Integer.parseInt(AppSettings.getRfid_tag_bit_size());


    /**
     * shifting ModelWsDTO (Model - Workstation) array elements.
     * If #Workstation is 10, then create 10 array objects of ModelWsDTO
     */
    public static ModelWsDTO[] modelWsDTOSArray = new ModelWsDTO[cnt];

    public static Queue<ModelWsDTO> queues = new LinkedList();


    public  static  void processRFIDTag(String rfidInput) {
        System.out.println(" ******* NEW TAG ******* ");
        System.out.println("Received TAG " + rfidInput + "LEN [" + rfidInput.trim().length() + "]");

        try {
                // checks if the RFID is a JIG or Model
                String isJigOrModel = isJigorModel(rfidInput);

                System.out.println("Identifying if its a Jig (or) Model :  It's  - " + isJigOrModel);

                if(isJigOrModel.equalsIgnoreCase("jig")) {

                    // get JIG Seq# to decide if the shift has happened or not
                    String newJigSequenceNumber = getJigSequenceNumber(rfidInput);

                    System.out.println("[[[JIG]]] My Sequence # : " + newJigSequenceNumber);

                    if(newJigSequenceNumber != currentJig) {
                        if(!currentModel.equalsIgnoreCase("")){
                            shiftWorkStations(currentModel);
                        }

                    }


                    currentJig = newJigSequenceNumber;

                } else if (isJigOrModel.equalsIgnoreCase("model")) {
                    currentModel = getModelNumber(rfidInput);
                    System.out.println("[[[MODEL]]] My Model # : " + currentModel);

                }

        } catch (Exception e ) {
            e.printStackTrace();
            System.out.println("Error in RFIDTagProcessor " + e.getMessage());
        }

    }


    public static void shiftWorkStations(String currentModel) {
        System.out.println("Array >> " + modelWsDTOSArray.length);

        /**
         * Shift (move) each of the array (modelWsDTOSArray) elements(ModelWsDTO) one UP.
         * first element is populated after the loop with latest model details
         */


        for(int i = cnt; i > 0; i--)
        {
            ModelWsDTO temp1 = modelWsDTOSArray[i-1];

            if(temp1 != null) {

                String mapStr = (String) AppSettings.getHashMap().get(temp1.getModelid() + "~" + String.valueOf(Integer.parseInt(temp1.getWorkstation()) + 1));

                String imgurl = mapStr.substring(0,mapStr.indexOf("~"));
                String modelname = mapStr.substring(mapStr.indexOf("~") + 1, mapStr.length());

                ModelWsDTO temp = new ModelWsDTO();

                temp.setModelid(temp1.getModelid());
                temp.setModule(modelname);
                temp.setAssemblyimagename(modelname);
                temp.setAssemblyimageurl(imgurl);
                temp.setWorkstation(String.valueOf(Integer.parseInt(temp1.getWorkstation()) + 1));
                modelWsDTOSArray[i] = temp;
            }
        }
        System.out.println("Arrays before last Element " + Arrays.toString(modelWsDTOSArray));

        ModelWsDTO temp = new ModelWsDTO();
      //  String modelnumber = getModelNumber(rfidInput);
        String modelnumber = currentModel;
        System.out.println("[[ NEW MODEL NUMBER ARRIVED ]] " + modelnumber);

        String mapStr = (String) AppSettings.getHashMap().get(modelnumber + "~" + 1);

        String imgurl = mapStr.substring(0,mapStr.indexOf("~"));
        String modelname = mapStr.substring(mapStr.indexOf("~") + 1, mapStr.length());


        temp.setModelid(Long.parseLong(modelnumber));
        temp.setModule(modelname);
        temp.setAssemblyimageurl(imgurl);
        temp.setAssemblyimagename(modelname);
        temp.setWorkstation("1");
        temp.setTagtype("model");
        modelWsDTOSArray[0] = temp;

        System.out.println("Arrays Element " + Arrays.toString(modelWsDTOSArray));

    }


    public static ModelWsDTO getLiveData(String workstation) {
        ModelWsDTO modelWsDTO = null;
        try {
            int iworkstation = Integer.parseInt(workstation);
            modelWsDTO = modelWsDTOSArray[iworkstation];
        } catch (Exception e) {
            e.printStackTrace();
        }


        return modelWsDTO;
    }


    /**
     * extracts 12 (9 - 20) and get the model number
     * @param rfidInput
     * @return
     */
    public static String getModelNumber(String rfidInput) {
        String tempModelNumber = rfidInput.substring(10,21);
        System.out.println("Model String " + tempModelNumber);
        String modelnumber = String.valueOf(Helper.binary_to_decimal(tempModelNumber.trim()));
        return modelnumber;

    }


    /**
     * extracts 32 (9-40) digits and gets the Jig Sequence number
     * @param rfidInput
     * @return
     */
    public static String getJigSequenceNumber(String rfidInput) {
        String tempSequenceNumber = rfidInput.substring(10,41);
        System.out.println("Calculating Jig Seq # " + tempSequenceNumber);
        String seq = String.valueOf(Helper.binary_to_decimal(tempSequenceNumber.trim()));

        return seq;

    }

    /**
     * Takes first 0-8 characters, converts it to decimal and finds if it is 0 or 1. if its 0 then model else its jig
     * @param rfidInput
     * @return
     */
    public static String isJigorModel(String rfidInput) {
        System.out.println("TEST >> " + rfidInput);
        String tempTagType = rfidInput.substring(0,9);
        System.out.println("TEST >> " + tempTagType);
        if(Helper.binary_to_decimal(tempTagType.trim()) == 0) {
            return "model";
        } else if(Helper.binary_to_decimal(tempTagType.trim()) == 1) {
            return "jig";
        } else {
            return "invalid tag";
        }


    }







}
