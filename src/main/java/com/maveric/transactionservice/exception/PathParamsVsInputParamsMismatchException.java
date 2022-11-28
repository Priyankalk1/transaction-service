package com.maveric.transactionservice.exception;

public class PathParamsVsInputParamsMismatchException extends RuntimeException{
    public PathParamsVsInputParamsMismatchException(String message) {
        super(message);
    }
}
