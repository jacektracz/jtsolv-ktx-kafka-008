package com.github.jtsolvtransactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtsolvtransactions.model.JTSolvBankTransaction;
import com.github.jtsolvtransactions.model.JTSolvBankTransactionBuilder;
import com.github.jtsolvtransactions.storage.JTSolvProduceTransactions;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;


public class JTSolvBankTransactionProducer {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws InterruptedException {

        List<JTSolvBankTransaction> data1 = JTSolvProduceTransactions.getData();

        data1.stream()
                .map(
                        bankTransaction -> new ProducerRecord<>(
                                "bank-transactions",
                                bankTransaction.getBalanceId(),
                                toJson(bankTransaction)))
                .peek(t->dbg("obtain-transaction:" + t.key() + " " + t.value()))
                .forEach(record -> send(getProducer(), record));

        JTSolvBankTransaction bankTransaction = JTSolvBankTransactionBuilder.toDefaultBuilder()
                .id(UUID.randomUUID().toString())
                .balanceId(3L)
                .time(new Date())
                .amount(new BigDecimal(-10_000)).build();

        send(getProducer(), new ProducerRecord<>("bank-transactions",
                bankTransaction.getBalanceId(),
                toJson(bankTransaction)));

        while(true) {
            Thread.sleep(4000L);
            Stream.of(JTSolvBankTransactionBuilder.toDefaultBuilder()
                            .id(UUID.randomUUID().toString())
                            .balanceId(3L)
                            .time(new Date())
                            .amount(new BigDecimal(-10_000)).build())
                    .peek(t -> dbg("Sending new transaction: {}" +  t.toString()))
                    .map(t ->new ProducerRecord<>("bank-transactions", t.getBalanceId(), toJson(t)))
                    .forEach(record -> send(getProducer(), record));
        }
    }

    private static Producer<Long, String> getProducer() {
        return getProducer1();
    }

    private static Producer<Long, String> getProducer1(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092"); // Kafka server
        properties.put("key.serializer", LongSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        Producer<Long, String> bankTransactionProducer2 = new KafkaProducer<>(properties);
        return bankTransactionProducer2;
    }

    private static Producer<Long, String> getProducer2(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092"); // Kafka server
        properties.put("key.serializer", LongSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        Producer<Long, String> bankTransactionProducer2 = new KafkaProducer<>(properties);
        return bankTransactionProducer2;
    }

    private static void send(Producer<Long, String> bankTransactionProducer, ProducerRecord<Long, String> record) {
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


