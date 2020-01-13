package com.oi.financialoilservices.unit;

import com.oi.financialoilservices.dto.InputOilTransactionDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilTransaction;
import com.oi.financialoilservices.entity.OilType;
import com.oi.financialoilservices.enumerator.Operations;
import com.oi.financialoilservices.exception.GetOilTransactionRegistryException;
import com.oi.financialoilservices.exception.InvalidOilTransactionOperationException;
import com.oi.financialoilservices.exception.OilRegistryNotFoundException;
import com.oi.financialoilservices.exception.SaveOilTransactionRegistryException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTransactionRepository;
import com.oi.financialoilservices.service.OilTransactionService;
import com.oi.financialoilservices.service.StatisticsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
@SpringBootTest
public class OilTransactionServiceTest {

    @InjectMocks
    private OilTransactionService oilTransactionService;

    @Mock
    private OilRepository oilRepository;

    @Mock
    private OilTransactionRepository oilTransactionRepository;

    @Mock
    private StatisticsService statisticsService;

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPersistOilTransactionOnDatabaseWithSuccess() {
        doReturn(buildOilObject()).when(oilRepository).findByOilId(anyString());
        doReturn(new OilTransaction()).when(oilTransactionRepository).save(any(OilTransaction.class));
        inputArray = new Object[]{new InputOilTransactionDto(Operations.BUY.name(), 10, BigDecimal.valueOf(5), "OilIdTest")};
        assertNotNull(ReflectionTestUtils.invokeMethod(oilTransactionService, "persistOilTransactionOnDatabase", inputArray));
    }

    @Test(expected = OilRegistryNotFoundException.class)
    public void shouldThrowOilRegistryNotFoundExceptionWhenOilWasNotFoundDuringPersistOilTransactionOnDatabase() {
        doReturn(new OilTransaction()).when(oilTransactionRepository).save(any(OilTransaction.class));
        inputArray = new Object[]{new InputOilTransactionDto(Operations.BUY.name(), 10, BigDecimal.valueOf(5), "OilIdTest")};
        ReflectionTestUtils.invokeMethod(oilTransactionService, "persistOilTransactionOnDatabase", inputArray);
    }

    @Test(expected = SaveOilTransactionRegistryException.class)
    public void shouldThrowSaveOilTransactionRegistryExceptionWhenSomeErrorHappenedInOilTransactionRepositoryDuringPersistOilTransactionOnDatabase() {
        doReturn(buildOilObject()).when(oilRepository).findByOilId(anyString());
        doThrow(NullPointerException.class).when(oilTransactionRepository).save(any(OilTransaction.class));
        inputArray = new Object[]{new InputOilTransactionDto(Operations.BUY.name(), 10, BigDecimal.valueOf(5), "OilIdTest")};
        assertNotNull(ReflectionTestUtils.invokeMethod(oilTransactionService, "persistOilTransactionOnDatabase", inputArray));
    }

    @Test
    public void shouldExecuteOilTransactionOperationWithSuccess() {
        final Oil oil = buildOilObject();
        doReturn(oil).when(oilRepository).findByOilId(anyString());
        doReturn(new OilTransaction(1, 10, BigDecimal.valueOf(19.50), Operations.BUY.name(), oil, null)).when(oilTransactionRepository).save(any(OilTransaction.class));
        doReturn(BigDecimal.valueOf(5.05)).when(statisticsService).calculateRevenueYield(Mockito.any(Integer.class), Mockito.any(BigDecimal.class));
        doReturn(BigDecimal.valueOf(5.05)).when(statisticsService).calculateVolumeWeightedOilPriceProcess(Mockito.anyString());
        doReturn(BigDecimal.valueOf(5.05)).when(statisticsService).calculatePriceEarningsRatio(Mockito.any(BigDecimal.class), Mockito.any(Integer.class));
        assertNotNull(oilTransactionService.oilTransactionOperation(new InputOilTransactionDto(Operations.BUY.name(), 10, BigDecimal.valueOf(5), "OilIdTest")));
    }

    @Test(expected = InvalidOilTransactionOperationException.class)
    public void shouldThrowInvalidOperationExceptionWhenOperationIsInvalidInRequestBodyDuringPersistOilTransactionOnDatabase() {
        doReturn(buildOilObject()).when(oilRepository).findByOilId(anyString());
        oilTransactionService.oilTransactionOperation(new InputOilTransactionDto("INVALID OPERATION", 10, BigDecimal.valueOf(5), "OilIdTest"));
    }

    @Test
    public void shouldGetOilTransactionRegistryOnDatabaseByIdWithSuccess() {
        doReturn(new OilTransaction()).when(oilTransactionRepository).findByTransactionId(anyLong());
        Assert.assertNotNull(oilTransactionService.getOilTransactionOnDatabase(15));
    }

    @Test(expected = GetOilTransactionRegistryException.class)
    public void shouldThrowGetOilExceptionWhenSomeErrorHappenedInOilRepositoryDuringGetAllOilRegistryOnDatabase() {
        doThrow(GetOilTransactionRegistryException.class).when(oilTransactionRepository).findByTransactionId(anyLong());
        oilTransactionService.getOilTransactionOnDatabase(15);
    }

    @Test
    public void shouldGetAllOilTransactionRegistryOnDatabaseWithSuccess() {
        doReturn(Arrays.asList(new OilTransaction())).when(oilTransactionRepository).findAll();
        Assert.assertNotNull(oilTransactionService.getOilTransactionOnDatabase());
    }

    @Test(expected = GetOilTransactionRegistryException.class)
    public void shouldThrowGetAllOilExceptionWhenSomeErrorHappenedInOilRepositoryDuringGetAllOilRegistryOnDatabase() {
        doThrow(GetOilTransactionRegistryException.class).when(oilTransactionRepository).findAll();
        oilTransactionService.getOilTransactionOnDatabase();
    }

    @Test
    public void shouldInitializeInitializeOilObjectWithSuccess() {
        doReturn(buildOilObject()).when(oilRepository).findByOilId(anyString());
        inputArray = new Object[]{"oilId"};
        Assert.assertNotNull(ReflectionTestUtils.invokeMethod(oilTransactionService, "initializeOilObject", inputArray));
    }

    @Test(expected = OilRegistryNotFoundException.class)
    public void shouldThrowOilRegistryNotFoundExceptionWhenOilNotFound() {
        inputArray = new Object[]{"oilId"};
        ReflectionTestUtils.invokeMethod(oilTransactionService, "initializeOilObject", inputArray);
    }

    private Oil buildOilObject() {
        return new Oil("OilIdTest", new OilType("TypeTest", "TypeTest"), 1, BigDecimal.valueOf(19.50));
    }
}
