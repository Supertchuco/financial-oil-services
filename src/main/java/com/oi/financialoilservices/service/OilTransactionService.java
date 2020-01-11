package com.oi.financialoilservices.service;

import com.oi.financialoilservices.dto.InputOilTransactionDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.exception.GetOilTransactionOnDatabaseByLast30MinutesException;
import com.oi.financialoilservices.exception.GetOilTransactionRegistryException;
import com.oi.financialoilservices.exception.OilRegistryNotFoundException;
import com.oi.financialoilservices.exception.SaveOilTransactionRegistryException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class OilTransactionService {

    @Autowired
    private OilRepository oilRepository;

    @Autowired
    private OilTransactionRepository oilTransactionRepository;

    public OilTransaction persistOilTransactionOnDatabase(final InputOilTransactionDto inputOilTransactionDto) {
        log.info("Persist Oil transaction registry on database");
        inputOilTransactionDto.validate(inputOilTransactionDto);
        final Oil oil = oilRepository.findByOilId(inputOilTransactionDto.getOilId());
        try {
            return oilTransactionRepository.save(new OilTransaction(inputOilTransactionDto.getVolume(), inputOilTransactionDto.getPrice(),
                    inputOilTransactionDto.getOperation(), initializeOilObject(inputOilTransactionDto.getOilId())));
        } catch (OilRegistryNotFoundException oilRegistryNotFoundException) {
            throw oilRegistryNotFoundException;
        } catch (Exception exception) {
            log.error("Error to persist ne oil registry", exception);
            throw new SaveOilTransactionRegistryException();
        }
    }

    public OilTransaction getOilTransactionOnDatabase(final long oilTransactionId) {
        log.info("Get Oil transaction(s) registry on database");
        try {
            return oilTransactionRepository.findByTransactionId(oilTransactionId);
        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionRegistryException();
        }
    }

    public List<OilTransaction> getOilTransactionOnDatabase() {
        log.info("Get Oil transaction(s) registry on database");
        try {
            return oilTransactionRepository.findAll();

        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionRegistryException();
        }
    }

    public List<OilTransaction> getOilTransactionOnDatabaseByLast30Minutes() {
        log.info("Get Oil transaction(s) registry on database by last 30 minutes");
        try {
            return oilTransactionRepository.findByTransactionDateTimeBetween(now().minusMinutes(30), now());
        } catch (Exception exception) {
            log.error("Error to get oil transaction(s) registry", exception);
            throw new GetOilTransactionOnDatabaseByLast30MinutesException();
        }
    }

    private Oil initializeOilObject(final String oilId) {
        log.info("Initialize Oil object");
        final Oil oil = oilRepository.findByOilId(oilId);
        if (isNull(oil)) {
            log.error("Oil registry not found in database by id: {}", oilId);
            throw new OilRegistryNotFoundException();
        }
        return oil;
    }
}
