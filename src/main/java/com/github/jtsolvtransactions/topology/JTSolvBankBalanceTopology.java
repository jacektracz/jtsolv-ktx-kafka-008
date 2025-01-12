package com.github.jtsolvtransactions.topology;

import com.github.jtsolvtransactions.model.JTSolvBankBalance;
import com.github.jtsolvtransactions.model.JTSolvBankTransaction;
import com.github.jtsolvtransactions.model.JTSolvJsonSerde;
import com.github.jtsolvtransactions.model.JTSolvPossibleFraudAlert;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

public class JTSolvBankBalanceTopology {

    public static final String BANK_TRANSACTIONS = "bank-transactions";
    public static final String BANK_BALANCES = "bank-balances";
    public static final String REJECTED_TRANSACTIONS = "rejected-transactions";
    public static final String BANK_BALANCES_STORE = "bank-balances-store";
    private static final Long FRAUD_ALERT_THRESHOLD = 10L;

    public static Topology buildTopology() {
        Serde<JTSolvBankTransaction> bankTransactionSerde = new JTSolvJsonSerde<>(JTSolvBankTransaction.class);
        Serde<JTSolvBankBalance> bankBalanceSerde = new JTSolvJsonSerde<>(JTSolvBankBalance.class);
        Serde<JTSolvPossibleFraudAlert> possibleFraudAlertSerde = new JTSolvJsonSerde<>(JTSolvPossibleFraudAlert.class);
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<Long, JTSolvBankBalance> bankBalancesStream = streamsBuilder.stream(BANK_TRANSACTIONS,
                Consumed.with(Serdes.Long(), bankTransactionSerde))
                .groupByKey()
                .aggregate(JTSolvBankBalance::new,
                        (key, value, aggregate) -> aggregate.process(value),
                        Materialized.<Long, JTSolvBankBalance, KeyValueStore<Bytes, byte[]>>as(BANK_BALANCES_STORE)
                            .withKeySerde(Serdes.Long())
                            .withValueSerde(bankBalanceSerde)
                )
                .toStream();

        bankBalancesStream
                .to(BANK_BALANCES, Produced.with(Serdes.Long(), bankBalanceSerde));

        KStream<Long, JTSolvBankTransaction> rejectedTransactionsStream = bankBalancesStream
                .mapValues((readOnlyKey, value) -> value.getLatestTransactions().first())
                .filter((key, value) -> value.bankTransactionState == JTSolvBankTransaction.BankTransactionState.REJECTED);

        rejectedTransactionsStream
                .to(REJECTED_TRANSACTIONS, Produced.with(Serdes.Long(), bankTransactionSerde));

        return streamsBuilder.build();
    }

    private static void dbg(String txt){
        System.out.println(txt);
    }

}


