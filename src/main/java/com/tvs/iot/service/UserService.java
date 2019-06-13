package com.tvs.iot.service;

import com.tvs.iot.domain.Setting;
import com.tvs.iot.domain.User;
import com.tvs.iot.domain.UserDTO;

import java.util.List;


public interface UserService {


    public UserDTO signin(String username, String password);

    public  List<Setting>  getSettings();

    public int updateSettings(String id, String value_settings);

    public int  getwscount();

}


