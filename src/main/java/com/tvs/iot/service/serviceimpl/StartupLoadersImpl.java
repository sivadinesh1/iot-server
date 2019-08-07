package com.tvs.iot.service.serviceimpl;

import com.tvs.iot.business.AppSettings;

import com.tvs.iot.domain.ModelWs;
import com.tvs.iot.domain.Setting;
import com.tvs.iot.service.StartupLoaders;
import com.tvs.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartupLoadersImpl implements StartupLoaders {

    @Autowired
    UserService userService;

    public static HashMap hashMap = new HashMap();
    AppSettings settings = new AppSettings();


//    @PostConstruct
    public void startupLoadAll(){
        try {
            System.out.println("called startup Load All");
            List<Setting> settingList = userService.getSettings();


            for (Setting s : settingList) {

                if (s.getKey_settings().equalsIgnoreCase("WORKSTATIONS")) {
                    settings.setNo_of_workstations(s.getValue_settings());

                }

                if (s.getKey_settings().equalsIgnoreCase("RFID_TAG_BIT_SIZE")) {
                    System.out.println("inside RFID_TAG_BIT_SIZE");
                    settings.setRfid_tag_bit_size(s.getValue_settings());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Start up loading .. " + e.getMessage());
        }


        try {
            System.out.println("called startup Load All");
            List<ModelWs> lists = userService.loadModelWSMapping();



            for (ModelWs s : lists) {

                //modelnumber + "~" +wsnumber
                System.out.println("LOAIDNG ... " + s.getModelid() + "~" + s.getWorkstation());


                hashMap.put(s.getModelid() + "~" + s.getWorkstation(), s.getAssemblyimageurl() + "~" + s.getAssemblyimagename());

            }
            settings.setHashMap(hashMap);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Start up loading .. " + e.getMessage());
        }



    }
}


