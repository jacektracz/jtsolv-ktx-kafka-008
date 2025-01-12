package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class JTSolvBankTransactionBuilder {
    private String id;
    private Long balanceId;
    private String concept;
    private BigDecimal amount;
    private Date time;
    private JTSolvBankTransaction.BankTransactionState stateValue;


    private boolean stateSet;

    public static JTSolvBankTransactionBuilder toDefaultBuilder() {
        return new JTSolvBankTransactionBuilder();
    }

    JTSolvBankTransactionBuilder() {
    }

    public JTSolvBankTransactionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public JTSolvBankTransactionBuilder balanceId(Long balanceId) {
        this.balanceId = balanceId;
        return this;
    }

    public JTSolvBankTransactionBuilder concept(String concept) {
        this.concept = concept;
        return this;
    }

    public JTSolvBankTransactionBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public JTSolvBankTransactionBuilder time(Date time) {
        this.time = time;
        return this;
    }

    public JTSolvBankTransactionBuilder state(JTSolvBankTransaction.BankTransactionState state) {
        this.stateValue = state;
        this.stateSet = true;
        return this;
    }

    public JTSolvBankTransaction build() {
        JTSolvBankTransaction.BankTransactionState stateValue = this.stateValue;
        if (!this.stateSet) {
            stateValue = JTSolvBankTransaction.getDfaultSstate();
        }
        return new JTSolvBankTransaction(this.id, this.balanceId, this.concept, this.amount, this.time, stateValue);
    }

    public String toString() {
        return "BankTransaction.BankTransactionBuilder(id=" + this.id + ", balanceId=" + this.balanceId + ", concept=" + this.concept + ", amount=" + this.amount + ", time=" + this.time + ", state$value=" + this.stateValue + ")";
    }
}