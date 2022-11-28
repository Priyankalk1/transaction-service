package com.maveric.transactionservice.exception;

import com.maveric.transactionservice.dto.ErrorDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static com.maveric.transactionservice.constants.Constants.*;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionControllerAdvisor.class);

    String exceptionString="";
    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDto handleTransactionNotFoundException(TransactionNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(TRANSACTION_NOT_FOUND_CODE);
        errorDto.setMessage(exception.getMessage());
        exceptionString = exception.getMessage();
        log.error("{}->{}",TRANSACTION_NOT_FOUND_CODE,exceptionString);
        return errorDto;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        errorDto.setMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        exceptionString = ex.getMessage();
        log.error("{}->{}->{}",BAD_REQUEST_CODE,ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),exceptionString);
        return errorDto;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDto handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(METHOD_NOT_ALLOWED_CODE);
        errorDto.setMessage(METHOD_NOT_ALLOWED_MESSAGE);
        log.error("{} -> {}",METHOD_NOT_ALLOWED_CODE,METHOD_NOT_ALLOWED_MESSAGE);
        return errorDto;
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDto handleHttpFeignException(
            FeignException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(SERVICE_UNAVAILABLE_CODE);
        errorDto.setMessage(SERVICE_UNAVAILABLE_MESSAGE);
        exceptionString = ex.getMessage();
        log.error("{} -> {} -> {}",SERVICE_UNAVAILABLE_CODE,SERVICE_UNAVAILABLE_MESSAGE,exceptionString);
        return errorDto;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleHttpMessageNotReadableException (
            HttpMessageNotReadableException ex) throws NullPointerException {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        String message = ex.getMessage()==null?"":ex.getMessage(); //NOSONAR
        if (message.contains("com.maveric.transactionservice.constants.Type")) //NOSONAR
            errorDto.setMessage(INVALID_INPUT_TYPE);
        else
            errorDto.setMessage(INVALID_INPUT_MESSAGE);
        log.error("{}-> {}",BAD_REQUEST_CODE,message);
        return errorDto;
    }

    @ExceptionHandler(PathParamsVsInputParamsMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handlePathParamsVsInputParamsMismatchException(PathParamsVsInputParamsMismatchException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        errorDto.setMessage(exception.getMessage());
        exceptionString = exception.getMessage();
        log.error("{} ->{}",BAD_REQUEST_CODE,exceptionString);
        return errorDto;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleInsufficientBalanceException(InsufficientBalanceException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(BAD_REQUEST_CODE);
        errorDto.setMessage(exception.getMessage());
        exceptionString = exception.getMessage();
        log.error("{} {}",BAD_REQUEST_CODE,exceptionString);
        return errorDto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorDto handleOtherHttpException(Exception exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(INTERNAL_SERVER_ERROR_CODE);
        errorDto.setMessage(INTERNAL_SERVER_ERROR_MESSAGE);
        exceptionString = exception.getMessage();
        log.error("{} {}-> {}",INTERNAL_SERVER_ERROR_CODE,INTERNAL_SERVER_ERROR_MESSAGE,exceptionString);
        return errorDto;
    }

}
