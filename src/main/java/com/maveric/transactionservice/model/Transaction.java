package com.maveric.transactionservice.model;

import com.maveric.transactionservice.enums.Type;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Transaction")
public class Transaction {

    @Id
    private String _id;
    private String accountId;
    private Type type;
    private Number amount;
    private LocalDateTime createdAt;



}
