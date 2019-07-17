package com.tvs.iot.service;

import com.tvs.iot.domain.Model;
import com.tvs.iot.domain.ModelWs;
import com.tvs.iot.domain.ModelWsDTO;
import com.tvs.iot.domain.Setting;

import java.util.List;

public interface SetupService {
    public List<Model> getModels();
    public int addModel(Model model);

    public int checkPartNumberExists(String partnumber);

    public List<ModelWsDTO> getWorkstationImages(String modelid);

    public List<Model> getModelInfo(String modelid);

    public ModelWs addWorkstationImage(String modelid, String workstation, String assemblyimagename, String loggedinuserid);

    public int updateModelWSImageUrl(String imgurl, String id);

    public List<ModelWsDTO> getmodelwsinfo(String modelid, String wsno);

    public int updateModelWSImageName(String assemblyimagename, String id);

}


