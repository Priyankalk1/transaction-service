package com.maveric.transactionservice.controller;

import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes=TransactionErrorController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(TransactionErrorController.class)
class TransactionErrorControllerTest {

    @Autowired
    private MockMvc mock;


    @org.junit.jupiter.api.Test
    void errorHandler() throws Exception {
        mock.perform(get("/error"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}