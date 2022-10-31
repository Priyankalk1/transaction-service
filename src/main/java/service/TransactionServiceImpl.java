package service;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.TransactionNotFoundException;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.maveric.transactionservice.constants.Constants.getCurrentDateTime;
import static com.maveric.transactionservice.util.ModelDtoTransformer.toDto;
import static com.maveric.transactionservice.util.ModelDtoTransformer.toEntity;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository repository;


    @Override
    public List<TransactionDto> getTransactions() {
        List<Transaction> list= repository.findAll();
        List<TransactionDto> listDto = new ArrayList<TransactionDto>(list.size());
        for(Transaction transaction:list)
        {
            listDto.add(toDto(transaction));
        }
        return listDto;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        //Adding CreatedTime
        transactionDto.setCreatedAt(getCurrentDateTime());

        Transaction transaction = toEntity(transactionDto);
        Transaction transactionResult = repository.save(transaction);
        return  toDto(transactionResult);
    }

    @Override
    public TransactionDto getTransactionById(String transactionId) {
        Transaction transactionResult=repository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return toDto(transactionResult);
    }

    @Override
    public String deleteTransaction(String transactionId) {
        repository.deleteById(transactionId);
        return "Transaction deleted successfully.";
    }


}
