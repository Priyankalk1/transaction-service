package service;

import com.maveric.transactionservice.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    public List<TransactionDto> getTransactions();
    public TransactionDto createTransaction(TransactionDto transaction);
    public TransactionDto getTransactionById(String transactionId);
    public String deleteTransaction(String transactionId);
}
