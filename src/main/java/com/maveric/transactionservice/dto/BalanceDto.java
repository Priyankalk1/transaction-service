package com.maveric.transactionservice.dto;

import com.maveric.transactionservice.enums.Currency;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDto {
    private String  _id;
    private Number amount;
    private String accountId;
    private Currency currency;

}
