package com.maveric.transactionservice.repository;

import com.maveric.transactionservice.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository repository;

    @Test
    public void testSave() {
        Transaction transaction = repository.save(getTransaction());
        assertEquals("1234",transaction.getAccountId());
    }

    @Test
    public void testFindAll() {
        List<Transaction> transaction = repository.findAll();
        assertNotNull(transaction);
        assert(transaction.size()>0);
    }

}
