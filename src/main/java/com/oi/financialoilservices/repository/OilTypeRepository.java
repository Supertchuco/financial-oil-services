package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.OilType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OilTypeRepository extends CrudRepository<OilType, Long> {

    OilType findByOilType(String oidType);
}


