package com.tvs.iot.service.serviceimpl;

import com.tvs.iot.domain.Model;
import com.tvs.iot.domain.ModelWs;
import com.tvs.iot.domain.ModelWsDTO;
import com.tvs.iot.domain.Setting;
import com.tvs.iot.jparepository.ModelWsRepository;
import com.tvs.iot.service.SetupService;
import com.tvs.iot.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@Slf4j
public class SetupServiceImpl implements SetupService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ModelWsRepository modelWsRepositoryDao;



    public List<Model> getModels() {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select * from model m where isactive = 'Y' ");

        Query query = entityManager.createNativeQuery(sbr.toString(), Model.class);
        return query.getResultList();


    }



    public List<Model> getModelInfo(String modelid) {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select * from model m where id = ? ");

        Query query = entityManager.createNativeQuery(sbr.toString(), Model.class);
        query.setParameter(1, modelid);


        return query.getResultList();


    }



    public int addModel(Model model) {

        Query query = entityManager.createNativeQuery(" INSERT INTO model ( partnumber, module, nooftags, isactive, createdby, createddatetime) " +
                "VALUES\n" +
                " ( ?, ?, ?, ?, ?, ?); ");

        query.setParameter(1, model.getPartnumber());
        query.setParameter(2, model.getModule());
        query.setParameter(3, model.getNooftags());
        query.setParameter(4, 'Y');

        query.setParameter(5, model.getCreatedby());
        query.setParameter(6, Helper.getTodayDateTime());




        int rowcount = query.executeUpdate();

        return rowcount;

    }





    public ModelWs addWorkstationImage(String modelid, String workstation, String assemblyimagename, String loggedinuserid) {

        ModelWs modelWs = new ModelWs();

        modelWs.setModelid(modelid);
        modelWs.setWorkstation(workstation);
        modelWs.setAssemblyimageurl("");
        modelWs.setAssemblyimagename(assemblyimagename);
        modelWs.setCreatedby(Long.parseLong(loggedinuserid));
        modelWs.setCreateddatetime(Helper.getTodayDateTime());

        ModelWs modelwstemp = modelWsRepositoryDao.save(modelWs);

        return modelwstemp;

    }



    public int updateModelWSImageName(String assemblyimagename, String id) {
        System.out.println("1111 " + assemblyimagename);
        System.out.println("1111 MM 22 " + id);

        Query query = entityManager.createNativeQuery("update modelws set assemblyimagename = ? where id = ? ");

        query.setParameter(1, assemblyimagename);
        query.setParameter(2, id);

        int rowcount = query.executeUpdate();

        return rowcount;

    }



    public int updateModelWSImageUrl(String imgurl, String id) {
        Query query = entityManager.createNativeQuery("update modelws set assemblyimageurl = ? where id = ? ");

        query.setParameter(1, imgurl);
        query.setParameter(2, id);

        int rowcount = query.executeUpdate();

        return rowcount;

    }


    public int checkPartNumberExists(String partnumber) {

        int count = 0;

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select count(*) as cnt from model where partnumber = ? and isactive = 'Y' ");

        Query query = entityManager.createNativeQuery(sbr.toString());

        query.setParameter(1, partnumber);

        java.math.BigInteger countStar =(java.math.BigInteger) query.getSingleResult();

        System.out.println("in value " + countStar.intValue());

        return countStar.intValue();


    }





    public List<ModelWsDTO> getWorkstationImages(String modelid) {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select modelid, partnumber, module, nooftags, isactive, workstation, assemblyimageurl, assemblyimagename from " +
                "model m, " +
                "modelws mws " +
                "where " +
                "m.id = mws.modelid and " +
                "modelid = ? ");

        Query query = entityManager.createNativeQuery(sbr.toString());
        query.setParameter(1, modelid);


        List<Object[]> queryRes = query.getResultList();
        ModelWsDTO modelWsDTO = null;
        List<ModelWsDTO> modelWsDTOList = new ArrayList();

        for (Object[] row : queryRes) {
            modelWsDTO = new ModelWsDTO();

            modelWsDTO.setModelid(((java.math.BigInteger) row[0]).longValue());
            modelWsDTO.setPartnumber(((String) row[1]));
            modelWsDTO.setModule(((String) row[2]));
            modelWsDTO.setNooftags(String.valueOf(((Integer) row[3])));
            modelWsDTO.setIsactive(((String) row[4]));
            modelWsDTO.setWorkstation(((String) row[5]));
            modelWsDTO.setAssemblyimageurl(((String) row[6]));
            modelWsDTO.setAssemblyimagename(((String) row[7]));

            modelWsDTOList.add(modelWsDTO);

        }

        return modelWsDTOList;


    }



    public List<ModelWsDTO> getmodelwsinfo(String modelid, String wsno) {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select modelid, partnumber, module, nooftags, isactive, workstation, assemblyimageurl, assemblyimagename, mws.id from " +
                "model m, " +
                "modelws mws " +
                "where " +
                "m.id = mws.modelid and " +
                "modelid = ? and workstation = ? ");

        Query query = entityManager.createNativeQuery(sbr.toString());
        query.setParameter(1, modelid);
        query.setParameter(2, wsno);

        List<Object[]> queryRes = query.getResultList();
        ModelWsDTO modelWsDTO = null;
        List<ModelWsDTO> modelWsDTOList = new ArrayList();

        for (Object[] row : queryRes) {
            modelWsDTO = new ModelWsDTO();

            modelWsDTO.setModelid(((java.math.BigInteger) row[0]).longValue());
            modelWsDTO.setPartnumber(((String) row[1]));
            modelWsDTO.setModule(((String) row[2]));
            modelWsDTO.setNooftags(String.valueOf(((Integer) row[3])));
            modelWsDTO.setIsactive(((String) row[4]));
            modelWsDTO.setWorkstation(((String) row[5]));
            modelWsDTO.setAssemblyimageurl(((String) row[6]));
            modelWsDTO.setAssemblyimagename(((String) row[7]));
            modelWsDTO.setId(((java.math.BigInteger) row[8]).longValue());

            modelWsDTOList.add(modelWsDTO);

        }

        return modelWsDTOList;


    }

}
