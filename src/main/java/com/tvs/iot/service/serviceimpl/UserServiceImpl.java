package com.tvs.iot.service.serviceimpl;

import com.tvs.iot.domain.ModelWs;
import com.tvs.iot.domain.Setting;
import com.tvs.iot.domain.User;
import com.tvs.iot.domain.UserDTO;
import com.tvs.iot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@Service
@Transactional
@Slf4j




public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    public UserDTO signin(String username, String password) {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select  u.userid, firstname, lastname, r.name role ");
        sbr.append(" from ");
        sbr.append(" user u, ");
        sbr.append(" role r, ");
        sbr.append(" user_role ur ");
        sbr.append(" where ");
        sbr.append(" u.userid = ur.user_id and ");
        sbr.append(" ur.role_id = r.role_id and ");
        sbr.append(" username = ? and password = ? ");


        Query query = entityManager.createNativeQuery(sbr.toString());
        query.setParameter(1, username);
        query.setParameter(2, password);

        System.out.println("user name > " + username);
        System.out.println("pass > " + password);


        List<Object[]> queryRes = query.getResultList();
        UserDTO userDTO = new UserDTO();

        for (Object[] row : queryRes) {


            userDTO.setUserid((java.math.BigInteger) row[0]);
            userDTO.setFirstname((String) row[1]);
            userDTO.setLastname((String) row[2]);
            userDTO.setRole((String) row[3]);
        }


        return userDTO;

    }

    public List<Setting>  getSettings() {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select * from settings ");

        Query query = entityManager.createNativeQuery(sbr.toString(), Setting.class);
        return query.getResultList();


    }

    public List<ModelWs>  loadModelWSMapping() {

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select * from modelws ");

        Query query = entityManager.createNativeQuery(sbr.toString(), ModelWs.class);
        return query.getResultList();


    }


    public int  getwscount() {

        int value = 0;

        StringBuilder sbr = new StringBuilder();

        sbr.append(" select value_settings from settings where key_settings = 'WORKSTATIONS' ");

        Query query = entityManager.createNativeQuery(sbr.toString());

           value = Integer.parseInt((String)query.getSingleResult());

        return value;


    }


    public int updateSettings(String id, String value_settings) {
        System.out.println("id " + id);
        System.out.println("value " + value_settings);
        Query query = entityManager.createNativeQuery("update settings set value_settings = ? where id = ? ");
        query.setParameter(1, value_settings);
        query.setParameter(2, id);
        int rowcount = query.executeUpdate();
        System.out.println("rou cound " + rowcount);
        return rowcount;
    }


}


