package com.oi.financialoilservices.unit;

import com.oi.financialoilservices.service.OilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
@SpringBootTest
public class OilServiceTest {

    @Autowired
    private OilService oilService;

    @Test
    public void shouldCalculateVolumeWeightedOilPriceWithSuccess() {


        //assertEquals(INSTITUTION_NAME, exam.getInstitution().getName());
    }
}
