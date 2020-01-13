package com.oi.financialoilservices.exception;

import com.oi.financialoilservices.enumerator.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.TooManyMethods"})
public class ExceptionResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(final Exception exception, final WebRequest request) {
        log.error("Unexpected error", exception);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.UNEXPECTED_ERROR.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OilTypeRegistryNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleOilTypeRegistryNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_OIL_TYPE_REGISTRY_NOT_FOUND_ON_DATABASE.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaveOilRegistryException.class)
    public final ResponseEntity<ExceptionResponse> handleSaveOilRegistryException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_PERSIST_OIL_REGISTRY_ON_DATABASE.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GetOilException.class)
    public final ResponseEntity<ExceptionResponse> handleGetOilException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_GET_OIL_REGISTRY_ON_DATABASE.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OilRegistryNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleOilRegistryNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_OIL_REGISTRY_NOT_FOUND_ON_DATABASE.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaveOilTransactionRegistryException.class)
    public final ResponseEntity<ExceptionResponse> handleSaveOilTransactionRegistryException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_PERSIST_OIL_TRANSACTION_REGISTRY_ON_DATABASE.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOilTransactionOperationException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidOilTransactionOperationException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ERROR_INVALID_OIL_TRANSACTION_OPERATION.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
