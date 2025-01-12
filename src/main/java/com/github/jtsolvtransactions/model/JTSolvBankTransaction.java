package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class JTSolvBankTransaction implements Comparable<JTSolvBankTransaction> {

    private String id;

    private Long balanceId;

    private String concept;

    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")

    public Date bankTransactionTime;

    public BankTransactionState bankTransactionState = BankTransactionState.CREATED;

    public JTSolvBankTransaction(String id, Long balanceId, String concept, BigDecimal amount, Date time, BankTransactionState state) {
        this.id = id;
        this.balanceId = balanceId;
        this.concept = concept;
        this.amount = amount;
        this.bankTransactionTime = time;
        this.bankTransactionState = state;
    }

    public JTSolvBankTransaction() {
    }

    public static BankTransactionState getDfaultSstate() {
        return BankTransactionState.CREATED;
    }

    public String getId() {
        return this.id;
    }

    public Long getBalanceId() {
        return this.balanceId;
    }

    public String getConcept() {
        return this.concept;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Date getBankTransactionTime() {
        return this.bankTransactionTime;
    }

    public BankTransactionState getBankTransactionState() {
        return this.bankTransactionState;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public void setBankTransactionTime(Date bankTransactionTime) {
        this.bankTransactionTime = bankTransactionTime;
    }

    public void setBankTransactionState(BankTransactionState bankTransactionState) {
        this.bankTransactionState = bankTransactionState;
    }

    @Override
    public int compareTo(JTSolvBankTransaction o) {
        if(o == null) {
            return -1;
        }
        if(o.bankTransactionTime == null
                && this.bankTransactionTime == null){
            return 0;
        }

        if(o.bankTransactionTime == null ){
            return -1;
        }

        var r = o.bankTransactionTime.compareTo(
                this.bankTransactionTime);

        if (r == 0){
            return o.id.compareTo(this.id);
        }
        return r;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JTSolvBankTransaction)) return false;
        final JTSolvBankTransaction other = (JTSolvBankTransaction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JTSolvBankTransaction;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public String toString() {
        return "BankTransaction(id=" + this.getId() + ", balanceId=" + this.getBalanceId() + ", concept=" + this.getConcept() + ", amount=" + this.getAmount() + ", time=" + this.getBankTransactionTime() + ", state=" + this.getBankTransactionState() + ")";
    }

    public static enum BankTransactionState {
        CREATED, APPROVED, REJECTED
    }

}
