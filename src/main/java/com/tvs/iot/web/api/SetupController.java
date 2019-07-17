package com.tvs.iot.web.api;

import com.tvs.iot.business.GreetServer;
import com.tvs.iot.business.RFIDTagProcessor;
import com.tvs.iot.business.RFIDTagReaderClient;
import com.tvs.iot.domain.*;
import com.tvs.iot.service.SetupService;
import com.tvs.iot.service.StartupLoaders;
import com.tvs.iot.service.UserService;
import com.tvs.iot.util.APIResponse;
import com.tvs.iot.util.ConfigUtility;
import com.tvs.iot.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


@Slf4j
@RestController
@RequestMapping("/api")
public class SetupController {


    @Autowired
    SetupService setupservice;

    @Autowired
    StartupLoaders startupLoaders;

    @Autowired
    Helper helper;


    RFIDTagReaderClient rfidTagReaderClient = new RFIDTagReaderClient();

    @Autowired
    private ConfigUtility configUtil;

    public static final List<SseEmitter> emitters = Collections.synchronizedList( new ArrayList<>());







    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/getmodules",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Iterable<Model>> getSettings() {
        List<Model> modelsList = setupservice.getModels();

        return new ResponseEntity<Iterable<Model>>(modelsList, HttpStatus.OK);
    }



    @GetMapping(value = "/getworkstationimages/{modelid}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getWorkstationImages(@PathVariable("modelid") String modelid) {

        System.out.println("whats the value " + modelid);
            List<ModelWsDTO> modelWsDTOList = setupservice.getWorkstationImages(modelid);

        return new ResponseEntity(modelWsDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/getmodelwsinfo/{modelid}/{wsno}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getmodelwsinfo(@PathVariable("modelid") String modelid, @PathVariable("wsno") String wsno) {

        System.out.println("whats the value " + modelid);
        List<ModelWsDTO> modelWsDTOList = setupservice.getmodelwsinfo(modelid, wsno);

        return new ResponseEntity(modelWsDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/getmodelinfo/{modelid}",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity <Model> getModelInfo(@PathVariable("modelid") String modelid) {

        System.out.println("whats the value " + modelid);
        Model modeltmp = setupservice.getModelInfo(modelid).get(0);

        return new ResponseEntity(modeltmp, HttpStatus.OK);
    }



    @Transactional
    @RequestMapping(value = "/addmodel", method = RequestMethod.POST)
    public ResponseEntity<Integer> addModel(@RequestBody String stringToParse) throws IOException {

        System.out.println("IN >> addfeedbackdetails >>");
        JSONObject result = helper.getJsonObj(stringToParse);
        System.out.println("addfeedbackdetails PARAMS >> " + result.toJSONString());
        Model modeltmp = new Model();
        int count = 0;

        try {

            String partnumber = (String) result.get("partnumber");
            String module = (String) result.get("module");
            Long nooftags = (Long) result.get("nooftags");

            String loggedinuser = (String) result.get("loggedinuser");

            modeltmp.setPartnumber(partnumber);
            modeltmp.setModule(module);
            modeltmp.setNooftags((nooftags.intValue()));

            modeltmp.setCreatedby(Long.valueOf(loggedinuser));


            if(setupservice.checkPartNumberExists(partnumber) > 0) {
                return new ResponseEntity<Integer>(new Integer("-1"), HttpStatus.OK);
            }


            count = setupservice.addModel(modeltmp);


        } catch (Exception e) {
            log.error("Error in payTrainer "  + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<Integer>(new Integer(count), HttpStatus.OK);
        }

        return new ResponseEntity<Integer>(new Integer(count), HttpStatus.OK);
    }



    @Transactional
    @RequestMapping(value = "/addWorkstationimagemodule", method = RequestMethod.POST)

    public ResponseEntity addWorkstationImageModule(@RequestBody String stringToParse) throws IOException {
        int count = 0;

        System.out.println("IN >> addWorkstationImageModule >>");
        JSONObject result = helper.getJsonObj(stringToParse);
        System.out.println("addWorkstationImageModule PARAMS >> " + result.toJSONString());

        String modelid = (String) result.get("modelid");
        String assemblyimagename = (String) result.get("assemblyimagename");
        String workstation = (String) result.get("workstation");
        String loggedinuser = (String) result.get("loggedinuser");

        String data = (String) result.get("file");
        String filename = (String) result.get("filename");
        String fileext = helper.getExtension(filename);

        String base64Image = data.split(",")[1];

        //REF
        //byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

         ModelWs modelWstmp =  setupservice.addWorkstationImage(modelid, workstation, assemblyimagename, loggedinuser);


        try {

            if(modelWstmp.getId() != null) {


                //upload original image (for savety purpose) - for a file path and next line creates directory
                File paymentProofOriginalImageFile = new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + "original" + System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation);

                paymentProofOriginalImageFile.mkdirs();

                // on the above created directory write the original file
                Files.write(Paths.get((new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + "original" + System.getProperty("file.separator") + modelid).toString() + System.getProperty("file.separator") +  workstation + System.getProperty("file.separator") + filename)), imageBytes);

                // now create another path which is the actual file storage path
                File wsImageFile = new File(helper.getWorkStationImageModuleUploadFolder() +  System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation);
                // create directory
                wsImageFile.mkdirs();
                //delete all existing files so that we can overwrite
                helper.deleteFilesinDir(wsImageFile);


                // write the files back in the path
                // Files.write(Paths.get((new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation ).toString() + System.getProperty("file.separator") + modelWstmp.getId() + "." + fileext)), imageBytes);


                Files.write(Paths.get((new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation ).toString() + System.getProperty("file.separator") + "M-" + modelid + "-W-" + workstation + "." + fileext)), imageBytes);

                //setting image path to store in db
                // String uploadPaymentProofImagePath = System.getProperty("file.separator")  + "assets" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "tvs" + System.getProperty("file.separator") +modelid + System.getProperty("file.separator") + workstation + System.getProperty("file.separator") + modelWstmp.getId() + "." + fileext;
                String uploadPaymentProofImagePath = System.getProperty("file.separator")  + "assets" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "tvs" + System.getProperty("file.separator") +modelid + System.getProperty("file.separator") + workstation + System.getProperty("file.separator") + "M-" + modelid + "-W-" + workstation  + "." + fileext;
                 int updateCount = setupservice.updateModelWSImageUrl(uploadPaymentProofImagePath, String.valueOf(modelWstmp.getId()));


                 if(updateCount > 0) {
                     startupLoaders.startupLoadAll();
                 }

            }

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new APIResponse("FAILURE",HttpStatus.OK,""),HttpStatus.OK);
        }


        return new ResponseEntity<>(new APIResponse("SUCCESS", HttpStatus.OK, ""), HttpStatus.OK);
    }



    @Transactional
    @RequestMapping(value = "/editworkstationimagemodule", method = RequestMethod.POST)

    public ResponseEntity editWorkstationimagemodule(@RequestBody String stringToParse) throws IOException {
        int count = 0;

        System.out.println("IN >> editworkstationimagemodule >>");
        JSONObject result = helper.getJsonObj(stringToParse);
        System.out.println("editworkstationimagemodule PARAMS >> " + result.toJSONString());

        Long id = (Long) result.get("id");
        String modelid = (String) result.get("modelid");
        String assemblyimagename = (String) result.get("assemblyimagename");
        String workstation = (String) result.get("workstation");
        String loggedinuser = (String) result.get("loggedinuser");

        String data = (String) result.get("file");
        String filename = (String) result.get("filename");
        String fileext = helper.getExtension(filename);

        String base64Image = null;
        byte[] imageBytes = null;

        if(data != null) {
            base64Image = data.split(",")[1];
            //REF
            //byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
             imageBytes = Base64.getDecoder().decode(base64Image);
        }

      //  ModelWs modelWstmp =  setupservice.addWorkstationImage(modelid, workstation, assemblyimagename, loggedinuser);

        setupservice.updateModelWSImageName(assemblyimagename, String.valueOf(id));

        try {

            if(base64Image != null) {


                //upload original image (for savety purpose) - for a file path and next line creates directory
                File paymentProofOriginalImageFile = new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + "original" + System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation);

                paymentProofOriginalImageFile.mkdirs();

                // on the above created directory write the original file
                Files.write(Paths.get((new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + "original" + System.getProperty("file.separator") + modelid).toString() + System.getProperty("file.separator") +  workstation + System.getProperty("file.separator") + filename)), imageBytes);

                // now create another path which is the actual file storage path
                File wsImageFile = new File(helper.getWorkStationImageModuleUploadFolder() +  System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation);
                // create directory
                wsImageFile.mkdirs();
                //delete all existing files so that we can overwrite
                helper.deleteFilesinDir(wsImageFile);


                // write the files back in the path
                Files.write(Paths.get((new File(helper.getWorkStationImageModuleUploadFolder() + System.getProperty("file.separator") + modelid + System.getProperty("file.separator") + workstation ).toString() + System.getProperty("file.separator") + id + "." + fileext)), imageBytes);

                //setting image path to store in db
                String uploadPaymentProofImagePath = System.getProperty("file.separator")  + "assets" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "tvs" + System.getProperty("file.separator") +modelid + System.getProperty("file.separator") + workstation + System.getProperty("file.separator") + id+ "." + fileext;

                int updateCount = setupservice.updateModelWSImageUrl(uploadPaymentProofImagePath, String.valueOf(id));

            }

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new APIResponse("FAILURE",HttpStatus.OK,""),HttpStatus.OK);
        }


        return new ResponseEntity<>(new APIResponse("SUCCESS", HttpStatus.OK, ""), HttpStatus.OK);
    }


}


//return this.httpclient.get(`${this.restApiUrl}/api/getserver-status/${host}/${port}`)