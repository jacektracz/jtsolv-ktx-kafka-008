package com.github.jtsolvtransactions.storage;

import com.github.jtsolvtransactions.model.JTSolvBankTransaction;
import com.github.jtsolvtransactions.model.JTSolvBankTransactionBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JTSolvProduceTransactions {

    public static List<JTSolvBankTransaction> getData(){
        List<JTSolvBankTransaction> data1 = List.of(
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .time(new Date())
                        .concept("Income")
                        .amount(new BigDecimal(4000))
                        .build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(2L)
                        .time(new Date())
                        .amount(new BigDecimal(3000)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Amazon")
                        .time(new Date())
                        .amount(new BigDecimal(-50)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Rent")
                        .time(new Date())
                        .amount(new BigDecimal(-1000)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Electricity")
                        .time(new Date())
                        .amount(new BigDecimal(-100)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Wallmart")
                        .time(new Date())
                        .amount(new BigDecimal(-60)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Vodafone")
                        .time(new Date())
                        .amount(new BigDecimal(-25)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Amazon")
                        .time(new Date())
                        .amount(new BigDecimal(-20)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Netflix")
                        .time(new Date())
                        .amount(new BigDecimal(-10)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Transport")
                        .time(new Date())
                        .amount(new BigDecimal(-10)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .concept("Transport")
                        .time(new Date())
                        .amount(new BigDecimal(-10)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(4L)
                        .time(new Date())
                        .amount(new BigDecimal(2000)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(4L)
                        .time(new Date())
                        .amount(new BigDecimal(-2500)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(3L)
                        .time(new Date())
                        .amount(new BigDecimal(1000)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(1L)
                        .time(new Date())
                        .amount(new BigDecimal(-500)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(2L)
                        .time(new Date())
                        .amount(new BigDecimal(-4000)).build(),
                JTSolvBankTransactionBuilder.toDefaultBuilder()
                        .id(UUID.randomUUID().toString())
                        .balanceId(3L)
                        .time(new Date())
                        .amount(new BigDecimal(-500)).build()
        );
        return data1;

    }

}
