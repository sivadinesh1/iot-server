package com.tvs.iot.util;

import com.tvs.iot.config.SystemConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;



@Component
@Slf4j
public class Helper {
    @Autowired
    private SystemConfiguration systemconfiguration;

    public static int hex_to_decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static String HexToDecimal(String hexa) {

        StringBuilder sbr = new StringBuilder();

        String hexdec_num;
        int dec_num, i = 1, j;
        int bin_num[] = new int[100];
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Hexadecimal Number : ");
      //  hexdec_num = scan.nextLine();

        hexdec_num = hexa;

        /* convert hexadecimal to decimal */
        dec_num = hex_to_decimal(hexdec_num);

        /* convert decimal to binary */
        while (dec_num != 0) {
            bin_num[i++] = dec_num % 2;
            dec_num = dec_num / 2;
        }

        System.out.print("Equivalent Binary Number is: ");
        for (j = i - 1; j > 0; j--) {
            System.out.print(bin_num[j]);
            sbr.append(bin_num[j]);
        }
        System.out.print("\n");
        return sbr.toString();
    }





    public static List getAllFilse(File curDir, String articledirectory) {
        List filelist = new ArrayList();
        File[] filesList = curDir.listFiles();
        log.debug("getAllFilse Inside : " + filesList.toString());
        for (File f : filesList) {
            if (f.isDirectory())
                log.debug("getAllFilse getfilename before change : " + f.getName());
            if (f.isFile()) {
                //filelist.add(curDir+ System.getProperty("file.separator") + f.getName());

                articledirectory = articledirectory.substring(articledirectory.indexOf("/img"), articledirectory.length());

                log.debug("getAllFilse get filename after change : " + articledirectory);

                filelist.add(new ImageMeta(f.getName(), articledirectory + System.getProperty("file.separator") + f.getName()));


            }
        }
        log.debug("Exit getAllFilse exit : " + filelist.toString());
        return filelist;
    }
    public static String getExtension(String fileName) {
        char ch;
        int len;
        if(fileName==null ||
                (len = fileName.length())==0 ||
                (ch = fileName.charAt(len-1))=='/' || ch=='\\' || //in the case of a directory
                ch=='.' ) //in the case of . or ..
            return "";
        int dotInd = fileName.lastIndexOf('.'),
                sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if( dotInd<=sepInd )
            return "";
        else
            return fileName.substring(dotInd+1).toLowerCase();
    }

    public static String getRandomAlphaNumericString(int length) {
        String randomStr = UUID.randomUUID().toString();

        while (randomStr.length() < length) {
            randomStr += UUID.randomUUID().toString();
        }
        return randomStr.replace("-", "").substring(0, length);
    }

    public static String dateConversion(String dt) {

        Instant timestamp = Instant.parse(dt);
        ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));


        log.debug(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime));
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime);
    }

    public static Date dateConversionStr2DtZone(String dt) {

        Instant timestamp = Instant.parse(dt);
        ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));

        return Date.from(isttime.toInstant());

    }

    public static String dateConversionDatetoString(Date date) {
        String DATE_FORMAT = "MMM d, yyyy";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);


        LocalDateTime ldt = date.toInstant()
                .atZone(ZoneId.of("Asia/Kolkata"))
                .toLocalDateTime();


        return ldt.format(formatter);
    }



    public static String dateConversionDDMMYYYYdash(String dt) {

        Instant timestamp = Instant.parse(dt);
        ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));


        log.debug(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime));
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime);
    }

    public static String dateConversionYYYYMMDDdash(String dt) {

        Instant timestamp = Instant.parse(dt);
        ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));


        log.debug(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(isttime));
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(isttime);
    }

    public static String dateConversionDDMMYYYYslash(String dt) {

        Instant timestamp = Instant.parse(dt);
        ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));


        log.debug(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(isttime));
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(isttime);
    }

    public static Date dateConversionStr2Dt(String dateInString) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(dateInString);
        } catch (Exception e) {
            log.debug("Error in dateConversion Str2Dt >> " + e.getMessage());
        }

        return date;
    }

    public static Date dateConversionStr2DtDDMMYYYY(String dateInString) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = formatter.parse(dateInString);
        } catch (Exception e) {
            log.debug("Error in dateConversion Str2Dt >> " + e.getMessage());
        }

        return date;
    }

    public static String TodayDateToStringConversionYYYYMMDD() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(new Date());
        return strDate;

    }


    public static Date getToday() {
        Calendar today = Calendar.getInstance();
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        today.setTimeZone(istTimeZone);
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        return todayDate;
    }


    public static Date getTodayDateTime() {
        Calendar today = Calendar.getInstance();
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        today.setTimeZone(istTimeZone);
        //  today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        return todayDate;
    }



    public List<String> getName(String name, List<String> list) {

        String[] usersArr = new String[list.size()];
        usersArr = list.toArray(usersArr);

        List<String> returnMatchName = new ArrayList<String>();
        String[] data = usersArr;
        for (String string : data) {
            if (string.toUpperCase().indexOf(name.toUpperCase()) != -1) {
                returnMatchName.add(string);
            }
        }

        return returnMatchName;
    }

    public JSONArray getJSONArray(String stringToParse) {
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        JSONArray jsonArray = null;
        String email = null;

        try {
            jsonArray = (JSONArray) parser.parse(stringToParse);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


    static public JSONObject getGeneric(String stringToParse) {
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        JSONArray jsonArray = null;
        String email = null;

        try {
            jsonArray = (JSONArray) parser.parse(stringToParse);

            result = (JSONObject) jsonArray.get(0);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public JSONObject getJsonObj(String stringToParse) {
        JSONParser parser = new JSONParser();
        JSONObject result = null;

        try {
            result = (JSONObject) parser.parse(stringToParse);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getJsonArrtoString(JSONArray jsonarr) {
        ArrayList arrayList = new ArrayList(jsonarr.size());

        for (int i = 0; i < jsonarr.size(); i++) {
            arrayList.add(jsonarr.get(i));
        }

        String commaSeparatedValue = String.join(",", arrayList);

        return commaSeparatedValue;
    }

    public long getJsonArrtoInt(JSONArray jsonarr) {
        return (long) jsonarr.get(0);

    }

    public void deleteFilesinDir(File dir) {

        for (File file : dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public String randomfourdigitnumber() {
        String val = "" + ((int) (Math.random() * 9000) + 1000);
        return val;
    }

    public static int getRandomNumberInts(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }






    public static String firstDayofMonth() {
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.SECOND, 0);

        Date firstDateOfCurrentMonth = aCalendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);

        String dayFirst = sdf.format(firstDateOfCurrentMonth);
        System.out.println(dayFirst);
        return dayFirst;
    }

    public static String lastDayofMonth() {
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Today            : " + sdf.format(today));
        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
        return sdf.format(lastDayOfMonth);
    }

//public static Period calculateAge(String dob) {
//    LocalDate today = LocalDate.now();
//    Date date1 = null;
//    Period p = null;
//
//        try {
//             date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
//            System.out.println("asdfasdfas" + "\t" + date1);
//
//
//            LocalDate birthday = date1.toInstant()
//                    .atZone(ZoneId.of("Asia/Kolkata"))
//                    .toLocalDate();
//
//             p = Period.between(birthday, today);
//        } catch (Exception e) {
//            System.out.println("error in age calculation " + e.getMessage());
//        }
//        return p;
//}

//    DateFormat formet=new SimpleDateFormat("yyyy-MM-dd");
//    Date date = (Date)formet.parse("1996-03-25");
//
//    int curr=calculateAge(date);
//    	System.out.println(curr);
//}
//
//    private static int calculateAge(Date date) {
//
//
//        Calendar birth =Calendar.getInstance();
//        birth.setTime(date);
//
//        Calendar sysdate=Calendar.getInstance();
//        sysdate.setTime(new Date());
//        return sysdate.get(Calendar.YEAR)- birth.get(Calendar.YEAR);
//    }


    public static int calculateAge(String dob) {
        Calendar sysdate = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();

        DateFormat formet=new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = (Date) formet.parse(dob);
            birth.setTime(date);
            sysdate.setTime(new Date());
        } catch (Exception e) {

        }
        return sysdate.get(Calendar.YEAR)- birth.get(Calendar.YEAR);
    }


    public static String formatDatetoyyyyMMdd(Date date) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);

        String formattedDate = sdf.format(date);

        return formattedDate;
    }


    public static String currentDate() {
        Date date = new Date();
        //String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);

        String currentdate = sdf.format(date);


        return currentdate;
    }

    public static Date getMeYesterday(){
        return new Date(System.currentTimeMillis()-24*60*60*1000);
    }

    public static String getMeYesterdayyyyMMdd(){
        Date dt = new Date(System.currentTimeMillis()-24*60*60*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);

        return sdf.format(dt);
    }

    public static String getLastDayofPreviousMonth() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(lastDateOfPreviousMonth);


    }


    public static String getFirstDayofPreviousMonths(int months) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -months);

        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousThreeMonth = aCalendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(firstDateOfPreviousThreeMonth);

    }

    public static String getLastDayofPreviousMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -months);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(cal.getTime());
    }


    public static String getFirstDayofPreviousMonth() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);

        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(firstDateOfPreviousMonth);

    }

    public static String getFirstDayofThreeMonths() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.MONTH, -3);

        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(firstDateOfPreviousMonth);

    }

    public static Date getPreviousWeekDate(){
        return new Date(System.currentTimeMillis()-7*24*60*60*1000);
    }

    public static Date getMeTomorrow(){
        return new Date(System.currentTimeMillis()+24*60*60*1000);
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterable.iterator(),
                        Spliterator.ORDERED
                ),
                false
        );
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public String[] getAllowedDomains() {
        return systemconfiguration.getAllowedDomains();
    }

    public static boolean hostAvailabilityCheck(String SERVER_ADDRESS, int TCP_SERVER_PORT) {
        System.out.println("test >>> ");
        try (Socket s = new Socket(SERVER_ADDRESS, TCP_SERVER_PORT)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

}








//
//
//https://www.w3resource.com/java-exercises/basic/java-basic-exercise-29.php
//https://www.w3resource.com/java-exercises/basic/java-basic-exercise-23.php
//https://codebeautify.org/decimal-binary-converter