package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.BalanceDto;
import com.maveric.transactionservice.dto.PairClassDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.TransactionNotFoundException;

import java.util.List;

public interface TransactionService {
    public List<TransactionDto> getTransactions(Integer page, Integer pageSize);

    public List<TransactionDto> getTransactionsByAccountId(Integer page, Integer pageSize,String accountId);
    public PairClassDto createTransaction(String accountId, TransactionDto transaction, BalanceDto balanceDto);
    public TransactionDto getTransactionById(String transactionId) throws TransactionNotFoundException;
    public String deleteTransaction(String transactionId) throws TransactionNotFoundException;

    public String deleteTransactionByAccountId(String accountId);
}
