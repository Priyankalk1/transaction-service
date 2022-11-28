package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.BalanceDto;
import com.maveric.transactionservice.dto.PairClassDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.InsufficientBalanceException;
import com.maveric.transactionservice.exception.PathParamsVsInputParamsMismatchException;
import com.maveric.transactionservice.exception.TransactionNotFoundException;
import com.maveric.transactionservice.mapper.TransactionMapper;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.maveric.transactionservice.constants.Constants.*;
import static com.maveric.transactionservice.util.Common.getCurrentDateTime;

@Service
public class TransactionServiceImpl implements TransactionService{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionMapper mapper;

    @Override
    public List<TransactionDto> getTransactions(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Transaction> pageResult = repository.findAll(paging);
        if(pageResult.hasContent()) {
            List<Transaction> transaction = pageResult.getContent();
            return mapper.mapToDto(transaction);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Integer page, Integer pageSize,String accountId) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Transaction> pageResult = repository.findByAccountId(paging,accountId);
        if(pageResult.hasContent()) {
            List<Transaction> transaction = pageResult.getContent();
            log.info("Retrieved list of transactions for given Account Id");
            return mapper.mapToDto(transaction);
        } else {
            log.info("No Transaction info found!");
            return new ArrayList<>();
        }
    }

    @Override
    public PairClassDto createTransaction(String accountId, TransactionDto transactionDto, BalanceDto balanceDto) {
        Transaction transactionResult = new Transaction();
        if(accountId.equals(transactionDto.getAccountId())) {
            if(balanceDto.get_id()==null)
            {
                log.error("No Balance information available for given Account Id");
                throw new InsufficientBalanceException("No Balance information available for this account Id-"+transactionDto.getAccountId());
            }
            switch(transactionDto.getType())  //NOSONAR
            {
                case CREDIT ->
                {
                    //Adding CreatedTime
                    transactionDto.setCreatedAt(getCurrentDateTime());

                    Transaction transaction = mapper.map(transactionDto);
                    transactionResult = repository.save(transaction);
                    balanceDto.setAmount(balanceDto.getAmount().doubleValue()+transactionDto.getAmount().doubleValue());
                }
                case DEBIT ->
                {
                    if (balanceDto.getAmount().doubleValue() > transactionDto.getAmount().doubleValue()) {
                        //Adding CreatedTime
                        transactionDto.setCreatedAt(getCurrentDateTime());

                        Transaction transaction = mapper.map(transactionDto);
                        transactionResult = repository.save(transaction);
                        balanceDto.setAmount(balanceDto.getAmount().doubleValue()-transactionDto.getAmount().doubleValue());

                    } else {
                        log.error("Cannot perform transaction, Insufficient balance in account!");
                        throw new InsufficientBalanceException("Oops! Insufficient balance in your account!");
                    }
                }
            }

        }
        else {
            log.error("Account Id does not exist! Cannot create transaction.");
            throw new PathParamsVsInputParamsMismatchException("Account Id not found! Cannot create transaction.");
        }
        log.info("Transaction created successfully!");
        return new PairClassDto(mapper.map(transactionResult),balanceDto);
    }

    @Override
    public TransactionDto getTransactionById(String transactionId) {
        Transaction transactionResult=repository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE+transactionId));
        return mapper.map(transactionResult);
    }

    @Override
    public String deleteTransaction(String transactionId) {
        if(!repository.findById(transactionId).isPresent())
        {
            log.error("Transaction to be deleted is not found in DB.");
            throw new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE+transactionId);
        }
        repository.deleteById(transactionId);
        return TRANSACTION_DELETED_SUCCESS;
    }

    @Override
    public String deleteTransactionByAccountId(String accountId) {
        repository.deleteByAccountId(accountId);
        return TRANSACTION_DELETED_SUCCESS;
    }


}
