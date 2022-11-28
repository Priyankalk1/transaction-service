package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.BalanceDto;
import com.maveric.transactionservice.dto.PairClassDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.enums.Type;
import com.maveric.transactionservice.exception.InsufficientBalanceException;
import com.maveric.transactionservice.exception.PathParamsVsInputParamsMismatchException;
import com.maveric.transactionservice.exception.TransactionNotFoundException;
import com.maveric.transactionservice.mapper.TransactionMapperImpl;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionMapperImpl mapper;

    @Mock
    private TransactionRepository repository;

    @Mock
    private Page pageResult;

    @InjectMocks
    private TransactionServiceImpl service;


    @Test
    void getTransactions() {
        Page<Transaction> pagedResponse = new PageImpl(Arrays.asList(getTransaction(),getTransaction()));
        when(repository.findAll(any(Pageable.class))).thenReturn(pagedResponse);
        when(mapper.mapToDto(any())).thenReturn(Arrays.asList(getTransactionDto(),getTransactionDto()));

        List<TransactionDto> transactions = service.getTransactions(1,1);

        assertEquals("1234", transactions.get(0).getAccountId());
        assertEquals(Type.CREDIT, transactions.get(1).getType());

    }

    @Test
    void getTransactions_failure() {
        Page<Transaction> pagedResponse = new PageImpl(Arrays.asList());
        when(repository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        List<TransactionDto> transactions = service.getTransactions(1,1);

        assertEquals(0, transactions.size());

    }

    @Test
    void getTransactionsByAccountId() {
        Page<Transaction> pagedResponse = new PageImpl(Arrays.asList(getTransaction(),getTransaction()));
        when(repository.findByAccountId(any(Pageable.class),eq("1234"))).thenReturn(pagedResponse);
        //when(pageResult.hasContent()).thenReturn(true);
        //when(pageResult.getContent()).thenReturn(Arrays.asList(getTransaction(),getTransaction()));
        when(mapper.mapToDto(any())).thenReturn(Arrays.asList(getTransactionDto(),getTransactionDto()));

        List<TransactionDto> transactionDto = service.getTransactionsByAccountId(1,1,"1234");

        assertEquals("1234", transactionDto.get(0).getAccountId());
        assertEquals(Type.CREDIT, transactionDto.get(1).getType());
    }

    @Test
    void getTransactionsByAccountId_failure() {
        Page<Transaction> pagedResponse = new PageImpl(Arrays.asList());
        when(repository.findByAccountId(any(Pageable.class),eq("1234"))).thenReturn(pagedResponse);

        List<TransactionDto> transactions = service.getTransactionsByAccountId(1,1,"1234");

        assertEquals(0, transactions.size());

    }

    @Test
    void createTransaction_CREDIT() {
        when(mapper.map(any(TransactionDto.class))).thenReturn(getTransaction());
        when(mapper.map(any(Transaction.class))).thenReturn(getTransactionDto());
        when(repository.save(any())).thenReturn(getTransaction());

        PairClassDto pairClassDto = service.createTransaction("1234",getTransactionDto(),getBalanceDto());

        assertSame(pairClassDto.getTransactionDto().getAccountId(), getTransaction().getAccountId());
    }
    @Test
    void createTransaction_DEBIT() {
        Transaction transaction = getTransaction();
        transaction.setType(Type.DEBIT);
        TransactionDto transactionDto = getTransactionDto();
        transactionDto.setType(Type.DEBIT);
        when(mapper.map(any(TransactionDto.class))).thenReturn(transaction);
        when(mapper.map(any(Transaction.class))).thenReturn(transactionDto);
        when(repository.save(any())).thenReturn(transaction);

        PairClassDto pairClassDto = service.createTransaction("1234",transactionDto,getBalanceDto());

        assertSame(pairClassDto.getTransactionDto().getAccountId(), getTransaction().getAccountId());
    }

    @Test
    void createTransaction_InsufficientBalance() {
        Transaction transaction = getTransaction();
        transaction.setType(Type.DEBIT);
        TransactionDto transactionDto = getTransactionDto();
        transactionDto.setType(Type.DEBIT);
        BalanceDto balanceDto = getBalanceDto();
        balanceDto.setAmount(1000);

        Throwable error = assertThrows(InsufficientBalanceException.class,()->service.createTransaction("1234",transactionDto,balanceDto));
        assertEquals("Oops! Insufficient balance in your account!",error.getMessage());
    }

    @Test
    void createTransaction_AccountIdNotFound() {

        Throwable error = assertThrows(PathParamsVsInputParamsMismatchException.class,()->service.createTransaction("123",getTransactionDto(),getBalanceDto()));  //NOSONAR
        assertEquals("Account Id not found! Cannot create transaction.",error.getMessage());
    }

    @Test
    void createTransaction_BalanceDtoNotFound() {
        BalanceDto balanceDto = getBalanceDto();
        balanceDto.set_id(null);
        Throwable error = assertThrows(InsufficientBalanceException.class,()->service.createTransaction("1234",getTransactionDto(),balanceDto));  //NOSONAR
        assertEquals("No Balance information available for this account Id-1234",error.getMessage());
    }

    @Test
    void getTransactionById() {
        when(repository.findById("123")).thenReturn(Optional.of(getTransaction()));
        when(mapper.map(any(Transaction.class))).thenReturn(getTransactionDto());

        TransactionDto transactionDto = service.getTransactionById("123");

        assertSame(transactionDto.getType(),getTransactionDto().getType());
    }

    @Test
    void deleteTransaction() {
        when(repository.findById("123")).thenReturn(Optional.of(getTransaction()));
        willDoNothing().given(repository).deleteById("123");

        String transactionDto = service.deleteTransaction("123");

        assertSame( "Transaction deleted successfully.",transactionDto);
    }

    @Test
    void deleteTransaction_failue() {
        when(repository.findById("1234")).thenReturn(Optional.empty());

        Throwable error = assertThrows(TransactionNotFoundException.class,()->service.deleteTransaction("1234"));
        assertEquals("Transaction not found for Id-1234",error.getMessage());
    }

    @Test
    void deleteTransactionByAccountId() {
        when(repository.deleteByAccountId("123")).thenReturn("Transaction deleted successfully.");
        String transactionDto = service.deleteTransactionByAccountId("123");

        assertSame( "Transaction deleted successfully.",transactionDto);
    }
}