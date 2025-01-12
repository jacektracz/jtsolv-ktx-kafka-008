package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeSet;




public class BankBalance {

    private Long id;

    private BigDecimal amount = BigDecimal.ZERO;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")

    private Date lastUpdate;

    private TreeSet<BankTransaction> latestTransactions = new TreeSet<>();

    public BankBalance(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public TreeSet<BankTransaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setLatestTransactions(TreeSet<BankTransaction> latestTransactions) {
        this.latestTransactions = latestTransactions;
    }

    public BankBalance process(BankTransaction bankTransaction) {
        this.id = bankTransaction.getBalanceId();
        var transactionBuilder = bankTransaction.toDefaultBuilder();
        if(this.amount.add(bankTransaction.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
            addLatestTransaction(transactionBuilder.state(BankTransaction.BankTransactionState.APPROVED).build());
            this.amount = this.amount.add(bankTransaction.getAmount());
        } else {
            addLatestTransaction(transactionBuilder.state(BankTransaction.BankTransactionState.REJECTED).build());
        }
        this.lastUpdate = bankTransaction.getTime();
        return this;
    }

    private void addLatestTransaction(BankTransaction transactionClone) {
        if (latestTransactions.size() > 10) latestTransactions.pollLast();
        latestTransactions.add(transactionClone);
    }


}
