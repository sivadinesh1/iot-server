package com.tvs.iot.jparepository;

import com.tvs.iot.domain.ModelWs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelWsRepository extends CrudRepository<ModelWs, Long> {

    List<ModelWs> findAll();
}
