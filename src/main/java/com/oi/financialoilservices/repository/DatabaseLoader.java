package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final OilRepository oilRepository;

    private final OilTypeRepository oilTypeRepository;

    @Autowired
    public DatabaseLoader(final OilRepository oilRepo, final OilTypeRepository oilTypeRepo) {
        this.oilRepository = oilRepo;
        this.oilTypeRepository = oilTypeRepo;
    }

    @Override
    public void run(String... strings) {

        final OilType oilTypeStandard = new OilType("Standard", "Standard type");
        this.oilTypeRepository.save(oilTypeStandard);

        final OilType oilTypePremium = new OilType("Premium", "Premium type");
        this.oilTypeRepository.save(oilTypeStandard);

        this.oilRepository.save(new Oil("AAC", oilTypeStandard, 1, BigDecimal.valueOf(42)));
        this.oilRepository.save(new Oil("REW", oilTypeStandard, 7, BigDecimal.valueOf(47)));
        this.oilRepository.save(new Oil("BWO", oilTypeStandard, 17, BigDecimal.valueOf(61)));
        this.oilRepository.save(new Oil("TIM", oilTypePremium, 5, BigDecimal.valueOf(111)));
        this.oilRepository.save(new Oil("QFC", oilTypeStandard, 22, BigDecimal.valueOf(123)));
    }

}
