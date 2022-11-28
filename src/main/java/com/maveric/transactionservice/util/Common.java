package com.maveric.transactionservice.util;

import java.time.LocalDateTime;

public class Common {
    private Common()
    {

    }
    public static LocalDateTime getCurrentDateTime() {
        return (java.time.LocalDateTime.now());
    }
}
