package com.github.jtsolvtransactions.topology;

import com.github.jtsolvtransactions.model.JTSolvBankTransaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.util.Optional;

public class TransactionTimeExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var bankTransaction = (JTSolvBankTransaction) record.value();
        return Optional.ofNullable(bankTransaction.getBankTransactionTime())
                .map(it -> it.toInstant().toEpochMilli())
                .orElse(partitionTime);
    }
}
