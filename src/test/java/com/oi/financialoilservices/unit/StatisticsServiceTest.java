package com.oi.financialoilservices.unit;

import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.exception.CalculateRevenueYieldStandardException;
import com.oi.financialoilservices.exception.CalculateVolumeWeightedOilPriceException;
import com.oi.financialoilservices.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
@SpringBootTest
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCalculateVolumeWeightedOilPriceWithSuccess() {
        List<OilTransaction> transactions = Arrays.asList(new OilTransaction(1L, BigDecimal.valueOf(10.20), null, null),
                new OilTransaction(2L, BigDecimal.valueOf(10.21), null, null), new OilTransaction(3L,
                        BigDecimal.valueOf(10.22), null, null));
        inputArray = new Object[]{transactions};
        ReflectionTestUtils.invokeMethod(statisticsService, "calculateVolumeWeightedOilPrice", inputArray);
    }

    @Test(expected = CalculateVolumeWeightedOilPriceException.class)
    public void shouldThrowCalculateVolumeWeightedOilPriceExceptionWhenSomeErrorHappenedDuringCalculateVolumeWeightedOilPrice() {
        List<OilTransaction> transactions = Arrays.asList(new OilTransaction());
        inputArray = new Object[]{transactions};
        ReflectionTestUtils.invokeMethod(statisticsService, "calculateVolumeWeightedOilPrice", inputArray);
    }

    @Test
    public void shouldCalculateRevenueYieldStandardWithSuccess() {
        assertEquals(BigDecimal.valueOf(1.13).setScale(2, RoundingMode.HALF_EVEN), statisticsService.calculateRevenueYield(22,
                BigDecimal.valueOf(19.50)));
    }

    @Test(expected = CalculateRevenueYieldStandardException.class)
    public void shouldThrowCalculateRevenueYieldStandardExceptionWhenSomeErrorHappenedDuringCalculateRevenueYieldStandard() {
        statisticsService.calculateRevenueYield(22, null);
    }

    @Test
    public void shouldCalculateRevenueYieldPremiumWithSuccess() {
        assertEquals(BigDecimal.valueOf(19.94).setScale(2, RoundingMode.HALF_EVEN), statisticsService.calculateRevenueYield(7,
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(17.55)));
    }

    @Test(expected = CalculateRevenueYieldStandardException.class)
    public void shouldThrowCalculateRevenueYieldStandardExceptionWhenSomeErrorHappenedDuringCalculateRevenueYieldPremium() {
        statisticsService.calculateRevenueYield(7, null);
    }

    @Test
    public void shouldCalculatePriceEarningsRatioWithSuccess() {
        assertEquals(BigDecimal.valueOf(2.47).setScale(2, RoundingMode.HALF_EVEN), statisticsService
                .calculatePriceEarningsRatio(BigDecimal.valueOf(12.35),
                5));
    }

    @Test(expected = CalculateRevenueYieldStandardException.class)
    public void shouldThrowCalculatePriceEarningsRatioExceptionWhenSomeErrorHappenedDuringCalculatePriceEarningsRatio() {
        statisticsService.calculateRevenueYield(7, null);
    }

    @Test
    public void shouldCalculateGeometricMeanWithSuccess() {
        assertEquals(BigDecimal.valueOf(7.34).setScale(2, RoundingMode.HALF_EVEN),
                statisticsService.calculateGeometricMean(Arrays.asList(BigDecimal.valueOf(9.20), BigDecimal.valueOf(4.21),
                        BigDecimal.valueOf(10.22))));
    }
}
