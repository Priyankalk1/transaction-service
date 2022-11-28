package com.maveric.transactionservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PairClassDto {
    private com.maveric.transactionservice.dto.TransactionDto transactionDto;
    private com.maveric.transactionservice.dto.BalanceDto balanceDto;
}