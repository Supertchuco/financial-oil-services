package com.oi.financialoilservices.unit;

import com.oi.financialoilservices.dto.InputOilDto;
import com.oi.financialoilservices.entity.Oil;
import com.oi.financialoilservices.entity.OilType;
import com.oi.financialoilservices.exception.GetOilException;
import com.oi.financialoilservices.exception.OilTypeRegistryNotFoundException;
import com.oi.financialoilservices.exception.SaveOilRegistryException;
import com.oi.financialoilservices.repository.OilRepository;
import com.oi.financialoilservices.repository.OilTypeRepository;
import com.oi.financialoilservices.service.OilService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
@SpringBootTest
public class OilServiceTest {

    @InjectMocks
    private OilService oilService;

    @Mock
    private OilTypeRepository oilTypeRepository;

    @Mock
    private OilRepository oilRepository;

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPersistOilRegistryWithSuccess() {
        doReturn(new OilType("OilIdTest", "test")).when(oilTypeRepository).findByOilType(anyString());
        doReturn(new Oil()).when(oilRepository).save(any(Oil.class));
        assertNotNull(oilService.persistOilRegistry(new InputOilDto("OilIdTest", "typeTest", 1, 1, BigDecimal.valueOf(10.21))));
    }

    @Test(expected = OilTypeRegistryNotFoundException.class)
    public void shouldThrowOilTypeRegistryNotFoundExceptionWhenOilTypeNotFoundDuringPersistOilRegistry() {
        doThrow(OilTypeRegistryNotFoundException.class).when(oilTypeRepository).findByOilType(anyString());
        doReturn(new Oil()).when(oilRepository).save(any(Oil.class));
        oilService.persistOilRegistry(new InputOilDto("OilIdTest", "typeTest", 1, 1, BigDecimal.valueOf(10.21)));
    }

    @Test(expected = SaveOilRegistryException.class)
    public void shouldThrowSaveOilRegistryExceptionWhenSomeErrorHappenedInOilRepository() {
        doThrow(NullPointerException.class).when(oilRepository).save(any(Oil.class));
        doReturn(new OilType("OilIdTest", "test")).when(oilTypeRepository).findByOilType(anyString());
        oilService.persistOilRegistry(new InputOilDto("OilIdTest", "typeTest", 1, 1, BigDecimal.valueOf(10.21)));
    }

    @Test
    public void shouldGetOilRegistryOnDatabaseByIdWithSuccess() {
        doReturn(new Oil()).when(oilRepository).findByOilId(anyString());
        assertNotNull(oilService.getOilRegistryOnDatabase("oilIdTest"));
    }

    @Test(expected = GetOilException.class)
    public void shouldThrowGetOilExceptionWhenSomeErrorHappenedInOilRepositoryDuringGetOilRegistryOnDatabaseById() {
        doThrow(GetOilException.class).when(oilRepository).findByOilId(anyString());
        assertNotNull(oilService.getOilRegistryOnDatabase("oilIdTest"));
    }

    @Test
    public void shouldGetAllOilRegistryOnDatabaseWithSuccess() {
        doReturn(Arrays.asList(new Oil())).when(oilRepository).findAll();
        assertNotNull(oilService.getOilRegistryOnDatabase());
    }

    @Test(expected = GetOilException.class)
    public void shouldThrowGetOilExceptionWhenSomeErrorHappenedInOilRepositoryDuringGetAllOilRegistryOnDatabase() {
        doThrow(GetOilException.class).when(oilRepository).findAll();
        assertNotNull(oilService.getOilRegistryOnDatabase());
    }

    @Test
    public void shouldInitializeOilTypeObjectWithSuccess() {
        doReturn(new OilType("OilIdTest", "test")).when(oilTypeRepository).findByOilType(anyString());
        inputArray = new Object[]{"IdType"};
        assertNotNull(ReflectionTestUtils.invokeMethod(oilService, "initializeOilTypeObject", inputArray));
    }

    @Test(expected = OilTypeRegistryNotFoundException.class)
    public void shouldThrowOilTypeRegistryNotFoundExceptionWhenOilTypeNotFound() {
        inputArray = new Object[]{"IdType"};
        ReflectionTestUtils.invokeMethod(oilService, "initializeOilTypeObject", inputArray);
    }
}
