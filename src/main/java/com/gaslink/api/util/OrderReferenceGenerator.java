package com.gaslink.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrderReferenceGenerator {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private OrderReferenceGenerator() {}

    public static String generate() {
        String date = LocalDate.now().format(DATE_FMT);
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase();
        return "GAS-" + date + "-" + suffix;
    }
}
