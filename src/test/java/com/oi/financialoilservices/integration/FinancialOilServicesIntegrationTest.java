package com.oi.financialoilservices.integration;

import com.oi.financialoilservices.enumerator.ErrorMessages;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SuppressWarnings({"PMD.TooManyMethods", "checkstyle:AbbreviationAsWordInName"})
public class FinancialOilServicesIntegrationTest {

    private static final String BASE_ENDPOINT = "http://localhost:8090/oil-service";
    private static final String OIL_ENDPOINT = "/oil";
    private static final String OIL_TRANSACTION_ENDPOINT = "/oilTransaction";
    private static final String OIL_STATISTICS_ENDPOINT = "/statistics";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String payload;

    private HttpEntity<String> entity;

    private ResponseEntity<String> response;

    private String examId;

    private static String readJSON(String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    public void shouldReturn200WhenInsertNewOilRegistryWithSuccess() {
        String payload = readJSON("request/insertOilSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn400WhenInsertNewOilRegistryWithInvalidOilType() {
        String payload = readJSON("request/insertOilWithInvalidOilType.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains(ErrorMessages.ERROR_OIL_TYPE_REGISTRY_NOT_FOUND_ON_DATABASE.getMessage()));
    }

    @Test
    public void shouldReturn200AndOilRegistryWhenGetOilRegistryByIdWithSuccess() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_ENDPOINT).concat("/TIM"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final String result = "{\"oilId\":\"TIM\",\"oilType\":{\"oilType\":\"Premium\",\"description\":\"Premium type\"},\"fixedRevenue\":5,\"variableRevenue\":7,\"oilBarrelValue\":111.00}";
        assertTrue(response.getBody().contains(result));
    }

    @Test
    public void shouldReturn200AndOilRegistriesWhenGetAllOilRegistriesWithSuccess() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_ENDPOINT), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final String result = "[{\"oilId\":\"AAC\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":1,\"variableRevenue\":null,\"oilBarrelValue\":42.00},{\"oilId\":\"REW\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":7,\"variableRevenue\":null,\"oilBarrelValue\":47.00},{\"oilId\":\"BWO\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":17,\"variableRevenue\":null,\"oilBarrelValue\":61.00},{\"oilId\":\"TIM\",\"oilType\":{\"oilType\":\"Premium\",\"description\":\"Premium type\"},\"fixedRevenue\":5,\"variableRevenue\":7,\"oilBarrelValue\":111.00},{\"oilId\":\"QFC\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":22,\"variableRevenue\":null,\"oilBarrelValue\":123.00}]";
        assertTrue(response.getBody().contains(result));
    }

    @Test
    public void shouldReturn200WhenInsertNewOilTransactionRegistryWithSuccess() {
        String payload = readJSON("request/insertOilTransactionSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_TRANSACTION_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn400WhenInsertNewOilTransactionRegistryWithInvalidOil() {
        String payload = readJSON("request/insertOilTransactionWithInvalidOil.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_TRANSACTION_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains(ErrorMessages.ERROR_OIL_REGISTRY_NOT_FOUND_ON_DATABASE.getMessage()));
    }

    @Test
    public void shouldReturn400WhenInsertNewOilTransactionRegistryWithInvalidOperation() {
        String payload = readJSON("request/insertOilTransactionWithInvalidOperation.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_TRANSACTION_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains(ErrorMessages.ERROR_INVALID_OIL_TRANSACTION_OPERATION.getMessage()));
    }

    @Test
    public void shouldReturn200AndOilRegistryWhenGetOilTransactionRegistryByIdWithSuccess() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_TRANSACTION_ENDPOINT).concat("/99"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final String result = "{\"transactionId\":99,\"volume\":11,\"price\":50.25,\"operation\":\"Buy\",\"oil\":{\"oilId\":\"REW\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":7,\"variableRevenue\":null,\"oilBarrelValue\":47.00},\"transactionDateTime\":\"2020-01-12T22:37:06.691\"}";
        assertTrue(response.getBody().contains(result));
    }

    @Test
    public void shouldReturn200AndOilRegistriesWhenGetAllOilTransactionRegistriesWithSuccess() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_TRANSACTION_ENDPOINT), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final String result = "{\"transactionId\":90,\"volume\":11,\"price\":48.25,\"operation\":\"Sell\",\"oil\":{\"oilId\":\"BWO\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":17,\"variableRevenue\":null,\"oilBarrelValue\":61.00},\"transactionDateTime\":\"2020-01-12T22:37:06.691\"},{\"transactionId\":99,\"volume\":11,\"price\":50.25,\"operation\":\"Buy\",\"oil\":{\"oilId\":\"REW\",\"oilType\":{\"oilType\":\"Standard\",\"description\":\"Standard type\"},\"fixedRevenue\":7,\"variableRevenue\":null,\"oilBarrelValue\":47.00},\"transactionDateTime\":\"2020-01-12T22:37:06.691\"}";
        assertTrue(response.getBody().contains(result));
    }

    @Test
    public void shouldReturn200WhenGetGeometricMeanWithSuccess() {
        HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(OIL_STATISTICS_ENDPOINT).concat("/geometricMean"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("49.24"));
    }

}
