package util;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.model.Transaction;

public class ModelDtoTransformer {

    public static Transaction toEntity(TransactionDto dto) {
        return new Transaction(
                dto.get_id(),
                dto.getAccountId(),
                dto.getType(),
                dto.getAmount(),
                dto.getCreatedAt()
        );
    }

    public static TransactionDto toDto(Transaction model) {
        return new TransactionDto(
                model.get_id(),
                model.getAccountId(),
                model.getType(),
                model.getAmount(),
                model.getCreatedAt()
        );
    }
}