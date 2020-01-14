package com.oi.financialoilservices.service;

import com.oi.financialoilservices.dto.InputOilDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilType;
import com.oi.financialoilservices.exception.GetOilException;
import com.oi.financialoilservices.exception.OilTypeRegistryNotFoundException;
import com.oi.financialoilservices.exception.SaveOilRegistryException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class OilService {

    @Autowired
    private OilRepository oilRepository;

    @Autowired
    private OilTypeRepository oilTypeRepository;

    public Oil persistOilRegistry(final InputOilDto inputOilDto) {
        log.info("Persist Oil registry on database");
        try {
            final OilType oilType = initializeOilTypeObject(inputOilDto.getOilTypeId());
            final Oil oil = isNull(inputOilDto.getVariableRevenue()) ? new Oil(inputOilDto.getOilId(), oilType,
                    inputOilDto.getFixedRevenue(), inputOilDto.getOilBarrelValue()) : new Oil(inputOilDto.getOilId(),
                    oilType, inputOilDto.getFixedRevenue(), inputOilDto.getVariableRevenue(), inputOilDto.getOilBarrelValue());
            return oilRepository.save(oil);
        } catch (OilTypeRegistryNotFoundException oilTypeRegistryNotFoundException) {
            throw oilTypeRegistryNotFoundException;
        } catch (Exception exception) {
            log.error("Error to persist oil registry on database", exception);
            throw new SaveOilRegistryException();
        }
    }

    public Oil getOilRegistryOnDatabase(final String oilId) {
        log.info("Get Oil registry on database");
        try {
            return oilRepository.findByOilId(oilId);
        } catch (Exception exception) {
            log.error("Error to get oil registry on database", exception);
            throw new GetOilException();
        }
    }

    public List<Oil> getOilRegistryOnDatabase() {
        log.info("Get all Oil registries on database");
        try {
            return oilRepository.findAll();
        } catch (Exception exception) {
            log.error("Error to get all oil registries on database", exception);
            throw new GetOilException();
        }
    }

    private OilType initializeOilTypeObject(final String oilTypeId) {
        log.info("Initialize Oil object");
        final OilType oilType = oilTypeRepository.findByOilType(oilTypeId);
        if (isNull(oilType)) {
            log.error("Oil type registry not found in database by id: {}", oilTypeId);
            throw new OilTypeRegistryNotFoundException();
        }
        return oilType;
    }
}
