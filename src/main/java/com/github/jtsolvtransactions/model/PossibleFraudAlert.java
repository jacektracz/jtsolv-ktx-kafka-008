package com.github.jtsolvtransactions.model;

public class PossibleFraudAlert {

    private Long balanceId;
    private Long rejectedTransactionsCount;
    private String message;

    public PossibleFraudAlert(Long balanceId, Long rejectedTransactionsCount, String message) {
        this.balanceId = balanceId;
        this.rejectedTransactionsCount = rejectedTransactionsCount;
        this.message = message;
    }

    public PossibleFraudAlert() {
    }

    public Long getBalanceId() {
        return this.balanceId;
    }

    public Long getRejectedTransactionsCount() {
        return this.rejectedTransactionsCount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public void setRejectedTransactionsCount(Long rejectedTransactionsCount) {
        this.rejectedTransactionsCount = rejectedTransactionsCount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PossibleFraudAlert)) return false;
        final PossibleFraudAlert other = (PossibleFraudAlert) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$balanceId = this.getBalanceId();
        final Object other$balanceId = other.getBalanceId();
        if (this$balanceId == null ? other$balanceId != null : !this$balanceId.equals(other$balanceId)) return false;
        final Object this$rejectedTransactionsCount = this.getRejectedTransactionsCount();
        final Object other$rejectedTransactionsCount = other.getRejectedTransactionsCount();
        if (this$rejectedTransactionsCount == null ? other$rejectedTransactionsCount != null : !this$rejectedTransactionsCount.equals(other$rejectedTransactionsCount))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PossibleFraudAlert;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $balanceId = this.getBalanceId();
        result = result * PRIME + ($balanceId == null ? 43 : $balanceId.hashCode());
        final Object $rejectedTransactionsCount = this.getRejectedTransactionsCount();
        result = result * PRIME + ($rejectedTransactionsCount == null ? 43 : $rejectedTransactionsCount.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "PossibleFraudAlert(balanceId=" + this.getBalanceId() + ", rejectedTransactionsCount=" + this.getRejectedTransactionsCount() + ", message=" + this.getMessage() + ")";
    }
}
