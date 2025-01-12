package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class BankTransactionBuilder {
    private String id;
    private Long balanceId;
    private String concept;
    private BigDecimal amount;
    private Date time;
    private BankTransaction.BankTransactionState stateValue;


    private boolean stateSet;

    public static BankTransactionBuilder toDefaultBuilder() {
        return new BankTransactionBuilder();
    }

    BankTransactionBuilder() {
    }

    public BankTransactionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public BankTransactionBuilder balanceId(Long balanceId) {
        this.balanceId = balanceId;
        return this;
    }

    public BankTransactionBuilder concept(String concept) {
        this.concept = concept;
        return this;
    }

    public BankTransactionBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public BankTransactionBuilder time(Date time) {
        this.time = time;
        return this;
    }

    public BankTransactionBuilder state(BankTransaction.BankTransactionState state) {
        this.stateValue = state;
        this.stateSet = true;
        return this;
    }

    public BankTransaction build() {
        BankTransaction.BankTransactionState stateValue = this.stateValue;
        if (!this.stateSet) {
            stateValue = BankTransaction.getDfaultSstate();
        }
        return new BankTransaction(this.id, this.balanceId, this.concept, this.amount, this.time, stateValue);
    }

    public String toString() {
        return "BankTransaction.BankTransactionBuilder(id=" + this.id + ", balanceId=" + this.balanceId + ", concept=" + this.concept + ", amount=" + this.amount + ", time=" + this.time + ", state$value=" + this.stateValue + ")";
    }
}