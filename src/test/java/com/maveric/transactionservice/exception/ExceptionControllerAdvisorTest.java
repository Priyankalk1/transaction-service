package com.maveric.transactionservice.exception;

import com.maveric.transactionservice.dto.ErrorDto;
import feign.Feign;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ExceptionControllerAdvisorTest {

    private ExceptionControllerAdvisor controllerAdvisor = new ExceptionControllerAdvisor();

    @Test
    void handleTransactionNotFoundException() {
        TransactionNotFoundException exception = new TransactionNotFoundException("Transaction Not found");
        ErrorDto error = controllerAdvisor.handleTransactionNotFoundException(exception);
        assertEquals("404",error.getCode());
    }



    @Test
    void handlePathParamsVsInputParamsMismatchException() {
        PathParamsVsInputParamsMismatchException exception = new PathParamsVsInputParamsMismatchException("Not match");
        ErrorDto error = controllerAdvisor.handlePathParamsVsInputParamsMismatchException(exception);
        assertEquals("400",error.getCode());
    }

    @Test
    void handleInsufficientBalanceException() {
        InsufficientBalanceException exception = new InsufficientBalanceException("Insufficient balance");
        ErrorDto error = controllerAdvisor.handleInsufficientBalanceException(exception);
        assertEquals("400",error.getCode());
    }
    @Test
    void handleHttpRequestMethodNotSupportedException() {
        MethodParameter methodParameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("error");;
        ErrorDto error = controllerAdvisor.handleHttpRequestMethodNotSupportedException(exception);
        assertEquals("405",error.getCode());
    }

   @Test
    void handleHttpMessageNotReadableException()
   {
       HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Exception");
       ErrorDto error = controllerAdvisor.handleHttpMessageNotReadableException(exception);
       assertEquals("400",error.getCode());
   }

   @Test
    void handleOtherHttpException()
   {
       Exception exception = new Exception();
       ErrorDto error = controllerAdvisor.handleOtherHttpException(exception);
       assertEquals("500",error.getCode());
   }
}