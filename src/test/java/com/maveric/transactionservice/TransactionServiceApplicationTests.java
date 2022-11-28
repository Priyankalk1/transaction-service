package com.maveric.transactionservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.transactionservice.dto.BalanceDto;
import com.maveric.transactionservice.dto.PairClassDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.enums.Type;
import com.maveric.transactionservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TransactionServiceApplicationTests {

	public static final String apiV1 = "/api/v1/accounts/1/transactions";
	@Test
	void testDoSomething() {
		assertTrue(true);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static Transaction getTransaction()
	{
		return  Transaction.builder()
				.accountId("1234")
				.type(Type.CREDIT)
				.amount(2000)
				.build();
	}
	public static TransactionDto getTransactionDto()
	{
		return  TransactionDto.builder()
				.accountId("1234")
				.type(Type.CREDIT)
				.amount(2000)
				.build();
	}

	public static BalanceDto getBalanceDto()
	{
		return  BalanceDto.builder()
				._id("1")
				.amount(3000)
				.build();
	}


}
