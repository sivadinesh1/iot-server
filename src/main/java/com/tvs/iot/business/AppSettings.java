package com.tvs.iot.business;

import lombok.Data;

import java.util.HashMap;


@Data
public class AppSettings {

    public static HashMap getHashMap() {
        return hashMap;
    }

    public static void setHashMap(HashMap hashMap) {
        AppSettings.hashMap = hashMap;
    }

    public static HashMap hashMap = new HashMap();

    public static String no_of_workstations;

    public static String getNo_of_workstations() {
        return no_of_workstations;
    }

    public static void setNo_of_workstations(String no_of_workstations) {
        AppSettings.no_of_workstations = no_of_workstations;
    }

    public static String getRfid_tag_bit_size() {
        return rfid_tag_bit_size;
    }

    public static void setRfid_tag_bit_size(String rfid_tag_bit_size) {
        AppSettings.rfid_tag_bit_size = rfid_tag_bit_size;
    }

    public  static String rfid_tag_bit_size;

    public AppSettings() {

//        hashMap.put("1~1", "/url/modelcode_ws_01_01.jpeg~M1 - WS 1 - Taiwan Petrol 2S");
//        hashMap.put("1~2", "/url/modelcode_ws_01_02.jpeg~M1 - WS 2 - Taiwan Petrol 2S");
//        hashMap.put("1~3", "/url/modelcode_ws_01_03.jpeg~M1 - WS 3 - Taiwan Petrol 2S");
//        hashMap.put("1~4", "/url/modelcode_ws_01_04.jpeg~M1 - WS 4 - Taiwan Petrol 2S");
//        hashMap.put("1~5", "/url/modelcode_ws_01_05.jpeg~M1 - WS 5 - Taiwan Petrol 2S");
//        hashMap.put("1~6", "/url/modelcode_ws_01_06.jpeg~M1 - WS 6 - Taiwan Petrol 2S");
//        hashMap.put("1~7", "/url/modelcode_ws_01_07.jpeg~M1 - WS 7 - Taiwan Petrol 2S");
//        hashMap.put("1~8", "/url/modelcode_ws_01_08.jpeg~M1 - WS 8 - Taiwan Petrol 2S");
//        hashMap.put("1~9", "/url/modelcode_ws_01_09.jpeg~M1 - WS 9 - Taiwan Petrol 2S");
//        hashMap.put("1~10", "/url/modelcode_ws_01_10.jpeg~M1 - WS 10 - Taiwan Petrol 2S");
//
//        hashMap.put("2~1", "/url/modelcode_ws_02_01.jpeg~M2 - WS 1 - Taiwan Petrol 4S");
//        hashMap.put("2~2", "/url/modelcode_ws_02_02.jpeg~M2 - WS 2 - Taiwan Petrol 4S");
//        hashMap.put("2~3", "/url/modelcode_ws_02_03.jpeg~M2 - WS 3 - Taiwan Petrol 4S");
//        hashMap.put("2~4", "/url/modelcode_ws_02_04.jpeg~M2 - WS 4 - Taiwan Petrol 4S");
//        hashMap.put("2~5", "/url/modelcode_ws_02_05.jpeg~M2 - WS 5 - Taiwan Petrol 4S");
//        hashMap.put("2~6", "/url/modelcode_ws_02_06.jpeg~M2 - WS 6 - Taiwan Petrol 4S");
//        hashMap.put("2~7", "/url/modelcode_ws_02_07.jpeg~M2 - WS 7 - Taiwan Petrol 4S");
//        hashMap.put("2~8", "/url/modelcode_ws_02_08.jpeg~M2 - WS 8 - Taiwan Petrol 4S");
//        hashMap.put("2~9", "/url/modelcode_ws_02_09.jpeg~M2 - WS 9 - Taiwan Petrol 4S");
//        hashMap.put("2~10", "/url/modelcode_ws_02_10.jpeg~M2 - WS 10 - Taiwan Petrol 4S");
//
//        hashMap.put("3~1", "/url/modelcode_ws_03_01.jpeg~M3 - WS 1 - Japan Petrol 2S");
//        hashMap.put("3~2", "/url/modelcode_ws_03_02.jpeg~M3 - WS 2 - Japan Petrol 2S");
//        hashMap.put("3~3", "/url/modelcode_ws_03_03.jpeg~M3 - WS 3 - Japan Petrol 2S");
//        hashMap.put("3~4", "/url/modelcode_ws_03_04.jpeg~M3 - WS 4 - Japan Petrol 2S");
//        hashMap.put("3~5", "/url/modelcode_ws_03_05.jpeg~M3 - WS 5 - Japan Petrol 2S");
//        hashMap.put("3~6", "/url/modelcode_ws_03_06.jpeg~M3 - WS 6 - Japan Petrol 2S");
//        hashMap.put("3~7", "/url/modelcode_ws_03_07.jpeg~M3 - WS 7 - Japan Petrol 2S");
//        hashMap.put("3~8", "/url/modelcode_ws_03_08.jpeg~M3 - WS 8 - Japan Petrol 2S");
//        hashMap.put("3~9", "/url/modelcode_ws_03_09.jpeg~M3 - WS 9 - Japan Petrol 2S");
//        hashMap.put("3~10", "/url/modelcode_ws_03_10.jpeg~M3 - WS 10 - Japan Petrol 2S");
//
//
//        hashMap.put("4~1", "/url/modelcode_ws_04_01.jpeg~M4 - WS 1 - Japan Petrol 4S");
//        hashMap.put("4~2", "/url/modelcode_ws_04_02.jpeg~M4 - WS 2 - Japan Petrol 4S");
//        hashMap.put("4~3", "/url/modelcode_ws_04_03.jpeg~M4 - WS 3 - Japan Petrol 4S");
//        hashMap.put("4~4", "/url/modelcode_ws_04_04.jpeg~M4 - WS 4 - Japan Petrol 4S");
//        hashMap.put("4~5", "/url/modelcode_ws_04_05.jpeg~M4 - WS 5 - Japan Petrol 4S");
//        hashMap.put("4~6", "/url/modelcode_ws_04_06.jpeg~M4 - WS 6 - Japan Petrol 4S");
//        hashMap.put("4~7", "/url/modelcode_ws_04_07.jpeg~M4 - WS 7 - Japan Petrol 4S");
//        hashMap.put("4~8", "/url/modelcode_ws_04_08.jpeg~M4 - WS 8 - Japan Petrol 4S");
//        hashMap.put("4~9", "/url/modelcode_ws_04_09.jpeg~M4 - WS 9 - Japan Petrol 4S");
//        hashMap.put("4~10", "/url/modelcode_ws_04_10.jpeg~M4 - WS 10 - Japan Petrol 4S");
//
//        hashMap.put("5~1", "/url/modelcode_ws_05_01.jpeg~M5 - WS 1 - India Petrol 2S");
//        hashMap.put("5~2", "/url/modelcode_ws_05_02.jpeg~M5 - WS 2 - India Petrol 2S");
//        hashMap.put("5~3", "/url/modelcode_ws_05_03.jpeg~M5 - WS 3 - India Petrol 2S");
//        hashMap.put("5~4", "/url/modelcode_ws_05_04.jpeg~M5 - WS 4 - India Petrol 2S");
//        hashMap.put("5~5", "/url/modelcode_ws_05_05.jpeg~M5 - WS 5 - India Petrol 2S");
//        hashMap.put("5~6", "/url/modelcode_ws_05_06.jpeg~M5 - WS 6 - India Petrol 2S");
//        hashMap.put("5~7", "/url/modelcode_ws_05_07.jpeg~M5 - WS 7 - India Petrol 2S");
//        hashMap.put("5~8", "/url/modelcode_ws_05_08.jpeg~M5 - WS 8 - India Petrol 2S");
//        hashMap.put("5~9", "/url/modelcode_ws_05_09.jpeg~M5 - WS 9 - India Petrol 2S");
//        hashMap.put("5~10", "/url/modelcode_ws_05_10.jpeg~M5 - WS 10 - India Petrol 2S");
//
//
//        hashMap.put("6~1", "/url/modelcode_ws_06_01.jpeg~M6 - WS 1 - India Petrol 4S");
//        hashMap.put("6~2", "/url/modelcode_ws_06_02.jpeg~M6 - WS 2 - India Petrol 4S");
//        hashMap.put("6~3", "/url/modelcode_ws_06_03.jpeg~M6 - WS 3 - India Petrol 4S");
//        hashMap.put("6~4", "/url/modelcode_ws_06_04.jpeg~M6 - WS 4 - India Petrol 4S");
//        hashMap.put("6~5", "/url/modelcode_ws_06_05.jpeg~M6 - WS 5 - India Petrol 4S");
//        hashMap.put("6~6", "/url/modelcode_ws_06_06.jpeg~M6 - WS 6 - India Petrol 4S");
//        hashMap.put("6~7", "/url/modelcode_ws_06_07.jpeg~M6 - WS 7 - India Petrol 4S");
//        hashMap.put("6~8", "/url/modelcode_ws_06_08.jpeg~M6 - WS 8 - India Petrol 4S");
//        hashMap.put("6~9", "/url/modelcode_ws_06_09.jpeg~M6 - WS 9 - India Petrol 4S");
//        hashMap.put("6~10", "/url/modelcode_ws_06_10.jpeg~M6 - WS 10 - India Petrol 4S");
//


  //      AppSettings.setHashMap(hashMap);

    }

}
