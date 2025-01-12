package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeSet;




public class JTSolvBankBalance {

    private Long id;

    private BigDecimal amount = BigDecimal.ZERO;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")

    private Date lastUpdate;

    private TreeSet<JTSolvBankTransaction> latestTransactions = new TreeSet<>();

    public JTSolvBankBalance(){

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

    public TreeSet<JTSolvBankTransaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setLatestTransactions(TreeSet<JTSolvBankTransaction> latestTransactions) {
        this.latestTransactions = latestTransactions;
    }

    public JTSolvBankBalance process(JTSolvBankTransaction bankTransaction) {
        this.id = bankTransaction.getBalanceId();
        var transactionBuilder = JTSolvBankTransactionBuilder.toDefaultBuilder();
        if(this.amount.add(bankTransaction.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
            addLatestTransaction(transactionBuilder.state(JTSolvBankTransaction.BankTransactionState.APPROVED).build());
            this.amount = this.amount.add(bankTransaction.getAmount());
        } else {
            addLatestTransaction(transactionBuilder.state(JTSolvBankTransaction.BankTransactionState.REJECTED).build());
        }
        this.lastUpdate = bankTransaction.getBankTransactionTime();
        return this;
    }

    private void addLatestTransaction(JTSolvBankTransaction transactionClone) {
        if (latestTransactions.size() > 10) latestTransactions.pollLast();
        latestTransactions.add(transactionClone);
    }


}
