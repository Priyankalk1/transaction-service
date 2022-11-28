package com.maveric.transactionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {
    String code;
    String message;
}
