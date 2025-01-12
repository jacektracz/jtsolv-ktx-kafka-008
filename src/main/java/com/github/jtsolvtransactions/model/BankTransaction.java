package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;


public class BankTransaction implements Comparable<BankTransaction> {

    private String id;
    private Long balanceId;
    private String concept;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    public Date time;

    public BankTransactionState state = BankTransactionState.CREATED;

    public BankTransaction(String id, Long balanceId, String concept, BigDecimal amount, Date time, BankTransactionState state) {
        this.id = id;
        this.balanceId = balanceId;
        this.concept = concept;
        this.amount = amount;
        this.time = time;
        this.state = state;
    }

    public BankTransaction() {
    }

    private static BankTransactionState getDfaultSstate() {
        return BankTransactionState.CREATED;
    }

    public static BankTransactionBuilder toDefaultBuilder() {
        return new BankTransactionBuilder();
    }

    @Override
    public int compareTo(BankTransaction o) {
        var r = o.time.compareTo(this.time);
        if (r == 0) return o.id.compareTo(this.id);
        return r;
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

    public Date getTime() {
        return this.time;
    }

    public BankTransactionState getState() {
        return this.state;
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
    public void setTime(Date time) {
        this.time = time;
    }

    public void setState(BankTransactionState state) {
        this.state = state;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BankTransaction)) return false;
        final BankTransaction other = (BankTransaction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$balanceId = this.getBalanceId();
        final Object other$balanceId = other.getBalanceId();
        if (this$balanceId == null ? other$balanceId != null : !this$balanceId.equals(other$balanceId)) return false;
        final Object this$concept = this.getConcept();
        final Object other$concept = other.getConcept();
        if (this$concept == null ? other$concept != null : !this$concept.equals(other$concept)) return false;
        final Object this$amount = this.getAmount();
        final Object other$amount = other.getAmount();
        if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
        final Object this$time = this.getTime();
        final Object other$time = other.getTime();
        if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        final Object this$state = this.getState();
        final Object other$state = other.getState();
        if (this$state == null ? other$state != null : !this$state.equals(other$state)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BankTransaction;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $balanceId = this.getBalanceId();
        result = result * PRIME + ($balanceId == null ? 43 : $balanceId.hashCode());
        final Object $concept = this.getConcept();
        result = result * PRIME + ($concept == null ? 43 : $concept.hashCode());
        final Object $amount = this.getAmount();
        result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
        final Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        final Object $state = this.getState();
        result = result * PRIME + ($state == null ? 43 : $state.hashCode());
        return result;
    }

    public String toString() {
        return "BankTransaction(id=" + this.getId() + ", balanceId=" + this.getBalanceId() + ", concept=" + this.getConcept() + ", amount=" + this.getAmount() + ", time=" + this.getTime() + ", state=" + this.getState() + ")";
    }

    public BankTransactionBuilder getToBuilder() {
        return new BankTransactionBuilder().id(this.id).balanceId(this.balanceId).concept(this.concept).amount(this.amount).time(this.time).state(this.state);
    }

    public static enum BankTransactionState {
        CREATED, APPROVED, REJECTED
    }

    public static class BankTransactionBuilder {
        private String id;
        private Long balanceId;
        private String concept;
        private BigDecimal amount;
        private Date time;
        private BankTransactionState stateValue;
        private boolean stateSet;

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

        public BankTransactionBuilder state(BankTransactionState state) {
            this.stateValue = state;
            this.stateSet = true;
            return this;
        }

        public BankTransaction build() {
            BankTransactionState state$value = this.stateValue;
            if (!this.stateSet) {
                state$value = BankTransaction.getDfaultSstate();
            }
            return new BankTransaction(this.id, this.balanceId, this.concept, this.amount, this.time, state$value);
        }

        public String toString() {
            return "BankTransaction.BankTransactionBuilder(id=" + this.id + ", balanceId=" + this.balanceId + ", concept=" + this.concept + ", amount=" + this.amount + ", time=" + this.time + ", state$value=" + this.stateValue + ")";
        }
    }
}
