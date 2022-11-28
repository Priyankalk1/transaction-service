package com.maveric.transactionservice.dto;
import com.maveric.transactionservice.enums.Type;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionDto {

    private String _id;
    @NotBlank(message = "Account Id is mandatory")
    private String accountId;
    @NotNull(message = "Type is mandatory - 'CREDIT' or 'DEBIT'")
    private Type type;
    @NotNull(message = "Amount is mandatory")
    @Min(value=0,message = "Amount cannot be less than zero")
    private Number amount;
    private LocalDateTime createdAt;
}
