package com.tvs.iot.web.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvs.iot.business.RFIDTagProcessor;
import com.tvs.iot.business.RFIDTagReaderClient;
import com.tvs.iot.domain.ModelWsDTO;
import com.tvs.iot.domain.Setting;
import com.tvs.iot.domain.User;
import com.tvs.iot.domain.UserDTO;
import com.tvs.iot.service.UserService;
import com.tvs.iot.service.serviceimpl.UserServiceImpl;

import com.tvs.iot.business.GreetServer;
import com.tvs.iot.util.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class FrontEndController {


    @Autowired
    UserService userservice;


    RFIDTagReaderClient rfidTagReaderClient = new RFIDTagReaderClient();

    @Autowired
    private ConfigUtility configUtil;

    public static final List<SseEmitter> emitters = Collections.synchronizedList( new ArrayList<>());

    @RequestMapping("/test")
    public String home() {

        String email = configUtil.getProperty("emailid");

        return "Spring boot is working!";
    }




    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/live-data", headers="Accept=*/*",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter getLiveRFIDTags() {
        String data = "";
        SseEmitter emitter = new SseEmitter();
        try {
        //    System.out.println(">><<<<<<<<<<< TEST ");

            data = rfidTagReaderClient.getRFIDStringVal();
       //     System.out.println(">><<<<<<<<<<< " + data);

            if(data != null) {
          //      System.out.println("inside null check");
                emitter.send(data, MediaType.APPLICATION_JSON);
                emitter.complete();
            }



        } catch (Exception e) {
            System.out.println("ERROR in getLiveRFIDTags " + e.getMessage());
        }
        return  emitter;

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/live-data/{workstation}", headers="Accept=*/*",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter getLiveData(@PathVariable("workstation") String workstation) {
       StringBuilder data = new StringBuilder();

        SseEmitter emitter = new SseEmitter();
        try {
            ModelWsDTO modelWsDTO = RFIDTagProcessor.getLiveData(workstation);

            if(modelWsDTO != null) {

                data.append("WSST").append(modelWsDTO.getWorkstation());
                data.append("WSED");

                data.append("IMGST").append(modelWsDTO.getAssemblyimagename());
                data.append("IMGED");

                data.append("IMGURLST").append(modelWsDTO.getAssemblyimageurl());
                data.append("IMGURLED");

                System.out.println("[[data]] " + data.toString());

                emitter.send(data.toString(), MediaType.TEXT_EVENT_STREAM);
                emitter.complete();
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR in getLiveRFIDTags " + e.getMessage());
        }
        return  emitter;

    }




    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signin(@RequestBody String stringToParse, BindingResult bindingResult) {
        String json = "";
        User user = null;
        UserDTO userDTO = null;
        try {

        JSONObject result = Helper.getJsonObj(stringToParse);

        String username = (String) result.get("username");
        String password = (String) result.get("password");


        userDTO = userservice.signin(username, password);


    } catch (Exception e ) {
        System.out.println("Error " + e.getMessage());
        e.printStackTrace();
    }

        if(userDTO.getUserid() != null) {


            ObjectMapper mapper = new ObjectMapper();

            try {
                json = mapper.writeValueAsString(userDTO);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, json), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new APIResponse("failure", HttpStatus.OK, json), HttpStatus.OK);
        }


    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/getsettings",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Iterable<Setting>> getSettings() {
        List<Setting> settingList = userservice.getSettings();

        return new ResponseEntity<Iterable<Setting>>(settingList, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/getwscount",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Integer> getwscount() {
        int value = userservice.getwscount();

        return new ResponseEntity<Integer>(Integer.valueOf(value), HttpStatus.OK);
    }






    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/stop-server",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getStopServer(@RequestBody String stringToParse, BindingResult bindingResult) {
        String statusval = "";

        JSONObject result = Helper.getGeneric(stringToParse);

        String host = (String) result.get("host");
        String port = (String) result.get("port");


    try {
        System.out.println("closing connections");
        GreetServer.closeServer();
        rfidTagReaderClient.stopClientSocket();
    } catch (Exception e ) {
        System.out.println("err " + e.getMessage());
    }




        return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, "" ), HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/start-client",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getStartClient() {

        try {
            System.out.println("starting client connections");
            Thread tclient =new Thread(new RFIDTagReaderClient("192.168.0.1", 2112));
            tclient.start();

        } catch (Exception e ) {
            System.out.println("err " + e.getMessage());
        }

        return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, "" ), HttpStatus.OK);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(
            value = "/update-settings/{id}/{value}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateSettings(@PathVariable("id") String id, @PathVariable("value") String value) {
        String json = "";
        User user = null;
        int rowCount = 0;

        System.out.println("id >> " + id);
        System.out.println("value >> " + value);

        try {
            rowCount  = userservice.updateSettings(id, value);

        } catch (Exception e ) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, Integer.toString(rowCount) ), HttpStatus.OK);


    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/start-simulation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getStartSimulation(@RequestBody String stringToParse, BindingResult bindingResult) {
        System.out.println("TEST >>" + stringToParse);
        String statusval = "";

        System.out.println("TEST 1 >> ");
        JSONObject result = Helper.getGeneric(stringToParse);
        System.out.println("TEST22 >>" + result);

        String host = (String) result.get("host");
        String port = (String) result.get("port");

        System.out.println("username " + host);
        System.out.println("password " + port);

        try {
            Thread t1 =new Thread(new GreetServer(host, Integer.parseInt(port)));
            t1.start();

            Thread.sleep(500);


            Thread t2 =new Thread(new RFIDTagReaderClient(host, Integer.parseInt(port)));
            t2.start();



        } catch (Exception e ) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }


        return new ResponseEntity<>(new APIResponse("running-simulation", HttpStatus.OK, statusval ), HttpStatus.OK);

    }

}


//return this.httpclient.get(`${this.restApiUrl}/api/getserver-status/${host}/${port}`)