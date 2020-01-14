package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.Oil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OilRepository extends CrudRepository<Oil, Long> {

    Oil findByOilId(String oidId);

    List<Oil> findAll();
}


