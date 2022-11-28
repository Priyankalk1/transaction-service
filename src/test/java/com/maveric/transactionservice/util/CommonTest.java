package com.maveric.transactionservice.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.maveric.transactionservice.util.Common.getCurrentDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CommonTest {


    @Test
    void getCurrentDateTime_Test() {
        LocalDateTime time = java.time.LocalDateTime.now();
        assertEquals(getCurrentDateTime().toLocalDate(),time.toLocalDate());
    }
}