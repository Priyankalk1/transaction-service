package com.maveric.transactionservice.mapper;

import com.maveric.transactionservice.controller.TransactionController;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransaction;
import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransactionDto;
import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperImplTest {

    private TransactionMapperImpl transactionMapper = new TransactionMapperImpl();

    @Test
    void map() {
        Transaction transaction = transactionMapper.map(getTransactionDto());
        assertEquals(getTransaction().get_id(),transaction.get_id());
    }

    @Test
    void testMap() {
        TransactionDto transactionDto = transactionMapper.map(getTransaction());
        assertEquals(getTransactionDto().get_id(),transactionDto.get_id());
    }

    @Test
    void mapToModel() {
        List<Transaction> transaction = transactionMapper.mapToModel(Arrays.asList(getTransactionDto(),getTransactionDto()));
        assertEquals(2,transaction.size());
    }

    @Test
    void mapToModel_failure() {
        List<Transaction> transaction = transactionMapper.mapToModel(Arrays.asList());
        assertEquals(0,transaction.size());
    }

    @Test
    void mapToDto() {
        List<TransactionDto> transactionDto = transactionMapper.mapToDto(Arrays.asList(getTransaction(),getTransaction()));
        assertEquals(2,transactionDto.size());
    }

    @Test
    void mapToDto_failure() {
        List<TransactionDto> transactionDto = transactionMapper.mapToDto(Arrays.asList());
        assertEquals(0,transactionDto.size());
    }
}