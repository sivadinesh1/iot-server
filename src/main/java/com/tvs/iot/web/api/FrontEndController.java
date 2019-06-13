package com.tvs.iot.web.api;


import com.tvs.iot.domain.Setting;
import com.tvs.iot.domain.User;
import com.tvs.iot.domain.UserDTO;
import com.tvs.iot.service.UserService;
import com.tvs.iot.service.serviceimpl.UserServiceImpl;
import com.tvs.iot.socket.GreetClient;
import com.tvs.iot.socket.GreetServer;
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


//    @Autowired
//    GreetClient greetClient;

    @Autowired
    private ConfigUtility configUtil;

    public static final List<SseEmitter> emitters = Collections.synchronizedList( new ArrayList<>());

    @RequestMapping("/test")
    public String home() {

        String email = configUtil.getProperty("emailid");

        return "Spring boot is working!";
    }




    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/test-data", headers="Accept=*/*",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter getFeedbackTest() {
        String test = "";
        SseEmitter emitter = new SseEmitter();
        try {
            test = GreetClient.getHexval();
            System.out.println(">><<<<<<<<<<< " + test);

//            PrintWriter out = response.write("data: message"+value+"\n\n");
//            out.flush();

            emitter.send(test, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            System.out.println("test " + e.getMessage());
        }
return  emitter;

      //  return new ResponseEntity(emitter, HttpStatus.OK);
    }




    @GetMapping(value = "/test1",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public ResponseEntity getFeedback() {
        String test = "";
        System.out.println("iam one");
        for(int i=0; i<10; i++) {
            System.out.println("iam looped >> " + i);
            test = "data: "+ System.currentTimeMillis() +"\n\n";

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return new ResponseEntity(test, HttpStatus.OK);
    }



    @GetMapping(value = "/jobs",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public ResponseEntity getJobs() {
        String test = "defaut";

        long start_time = System.currentTimeMillis();
        long wait_time = 10000;
        long end_time = start_time + wait_time;


        Stack<String> STACK = new Stack<String>();
        STACK.push("Welcome 1");
        STACK.push("To");
        STACK.push("Geeks");
        STACK.push("For");
        STACK.push("Geeks");

        System.out.println("MMMM >> " );
        //while (System.currentTimeMillis() < end_time) {

            for (int i = 0; i < 500000; i++) {

            System.out.println("inside loop >> " + STACK);

            test = "data: " + STACK.peek() + " && " + System.currentTimeMillis() +"\n\n";

            String temppop = STACK.pop();
            System.out.println("STACK AGAIN loop >> " + STACK);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        return new ResponseEntity(test, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signin(@RequestBody String stringToParse, BindingResult bindingResult) {
        String json = "";
        User user = null;
        UserDTO userDTO = null;
        try {
        System.out.println("TEST >>" + stringToParse);

        System.out.println("TEST 1 >> ");
        JSONObject result = Helper.getJsonObj(stringToParse);
        System.out.println("TEST22 >>" + result);

        String username = (String) result.get("username");
        String password = (String) result.get("password");

        System.out.println("username " + username);
        System.out.println("password " + password);



          userDTO = userservice.signin(username, password);

            System.out.println("TSETS >> " + userDTO);


    } catch (Exception e ) {
        System.out.println("Error " + e.getMessage());
        e.printStackTrace();
    }

        if(userDTO.getUserid() != null) {
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
    @PostMapping(value = "/start-server",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getStartServer(@RequestBody String stringToParse, BindingResult bindingResult) {
        System.out.println("TEST >>" + stringToParse);
        String statusval = "";

        System.out.println("TEST 1 >> ");
        JSONObject result = Helper.getGeneric(stringToParse);
        System.out.println("TEST22 >>" + result);

        String host = (String) result.get("host");
        String port = (String) result.get("port");

        System.out.println("username " + host);
        System.out.println("password " + port);


       // greetServer.startServer(host, Integer.parseInt(port));
        boolean status = Helper.hostAvailabilityCheck(host, Integer.parseInt(port));
        System.out.println("server status >> " + status);

        if(status) {
            statusval = "Server Starting Failed";
        } else {
            statusval = "Server Started";
        }

        return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, statusval ), HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/stop-server",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getStopServer(@RequestBody String stringToParse, BindingResult bindingResult) {
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

        GreetServer.closeServer();
    } catch (Exception e ) {
        System.out.println("err " + e.getMessage());
    }


        boolean status = Helper.hostAvailabilityCheck(host, Integer.parseInt(port));
        System.out.println("server status >> " + status);

        if(status) {
            statusval = "Failed to Stop Server";
        } else {
            statusval = "Server Stopped";
        }

        return new ResponseEntity<>(new APIResponse("success", HttpStatus.OK, "" ), HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/server-status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getServerStatus(@RequestBody String stringToParse, BindingResult bindingResult) {
        System.out.println("TEST >>" + stringToParse);

        String statusval = "";

        System.out.println("TEST 1 &&& >> ");
        JSONObject result = Helper.getGeneric(stringToParse);
        System.out.println("TEST22 >>" + result);

        String host = (String) result.get("host");
        String port = (String) result.get("port");

        System.out.println("username &&& " + host);
        System.out.println("password &&& " + port);

        System.out.println("username &&& " + host);


        boolean status = Helper.hostAvailabilityCheck(host, Integer.parseInt(port));
        System.out.println("server status >> " + status);

        if(status) {

            statusval = "Running";
            return new ResponseEntity<>(new APIResponse("running", HttpStatus.OK, "" ), HttpStatus.OK);
        } else {
            System.out.println("check ");
            statusval = "Not Running";
            return new ResponseEntity<>(new APIResponse("notrunning", HttpStatus.OK, "" ), HttpStatus.OK);
        }


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

            Thread.sleep(1000);

            //greetServer.startServer(host, Integer.parseInt(port));
            System.out.println("tests......");

            Thread t2 =new Thread(new GreetClient(host, Integer.parseInt(port)));
            t2.start();

            //greetClient.startConnection(host, Integer.parseInt(port));

        } catch (Exception e ) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }


        System.out.println("tests...####...");


        return new ResponseEntity<>(new APIResponse("running-simulation", HttpStatus.OK, statusval ), HttpStatus.OK);

    }

}


//return this.httpclient.get(`${this.restApiUrl}/api/getserver-status/${host}/${port}`)