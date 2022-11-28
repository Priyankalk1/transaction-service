package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.dto.ErrorDto;
import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static com.maveric.transactionservice.constants.Constants.INCORRECT_URL_CODE;
import static com.maveric.transactionservice.constants.Constants.INCORRECT_URL_MESSAGE;

@Controller
public class TransactionErrorController implements ErrorController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TransactionErrorController.class);
    @GetMapping("/error")
    public ResponseEntity<ErrorDto> errorHandler(HttpServletRequest req) {
        ErrorDto error = new ErrorDto();
        error.setCode(INCORRECT_URL_CODE);
        error.setMessage(INCORRECT_URL_MESSAGE);
        log.error(INCORRECT_URL_CODE+"->"+INCORRECT_URL_MESSAGE);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}