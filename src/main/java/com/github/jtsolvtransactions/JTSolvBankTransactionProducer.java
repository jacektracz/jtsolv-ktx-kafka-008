package com.github.jtsolvtransactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtsolvtransactions.model.JTSolvBankTransaction;
import com.github.jtsolvtransactions.model.JTSolvBankTransactionBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;


public class JTSolvBankTransactionProducer {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<Long, String> bankTransactionProducer =
                new KafkaProducer<>(Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092",
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
                ));


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
        data1.stream()
                .map(bankTransaction -> new ProducerRecord<>("bank-transactions", bankTransaction.getBalanceId(), toJson(bankTransaction)))
                .forEach(record -> send(bankTransactionProducer, record));

        JTSolvBankTransaction bankTransaction = JTSolvBankTransactionBuilder.toDefaultBuilder()
                .id(UUID.randomUUID().toString())
                .balanceId(3L)
                .time(new Date())
                .amount(new BigDecimal(-10_000)).build();

        send(bankTransactionProducer, new ProducerRecord<>("bank-transactions", bankTransaction.getBalanceId(), toJson(bankTransaction)));

        while(true) {
            Thread.sleep(4000L);
            Stream.of(JTSolvBankTransactionBuilder.toDefaultBuilder()
                            .id(UUID.randomUUID().toString())
                            .balanceId(3L)
                            .time(new Date())
                            .amount(new BigDecimal(-10_000)).build())
                    .peek(t -> dbg("Sending new transaction: {}" +  t.toString()))
                    .map(t ->new ProducerRecord<>("bank-transactions", t.getBalanceId(), toJson(t)))
                    .forEach(record -> send(bankTransactionProducer, record));
        }


    }


    private static void send(KafkaProducer<Long, String> bankTransactionProducer, ProducerRecord<Long, String> record) {
        try {
            bankTransactionProducer.send(record).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    private static String toJson(JTSolvBankTransaction bankTransaction) {
        try {
            return OBJECT_MAPPER.writeValueAsString(bankTransaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dbg(String txt){
        System.out.println(txt);
    }

}


