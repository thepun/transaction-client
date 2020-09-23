package org.bigwpay.transaction.model;

public final class TransactionSearchResult {

    public static TransactionSearchResult found(String transactionId, TransactionStatus status) {
        return new TransactionSearchResult(transactionId, TransactionSearchStatus.FOUND, status);
    }

    public static TransactionSearchResult notFound(String transactionId) {
        return new TransactionSearchResult(transactionId, TransactionSearchStatus.NOT_FOUND, null);
    }

    public static TransactionSearchResult error(String transactionId) {
        return new TransactionSearchResult(transactionId, TransactionSearchStatus.ERROR, null);
    }

    public static TransactionSearchResult authFailed(String transactionId) {
        return new TransactionSearchResult(transactionId, TransactionSearchStatus.AUTH_FAILED, null);
    }

    private final String transactionId;
    private final TransactionSearchStatus status;
    private final TransactionStatus transactionStatus;

    private TransactionSearchResult(String transactionId, TransactionSearchStatus status, TransactionStatus transactionStatus) {
        this.transactionId = transactionId;
        this.status = status;
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionSearchStatus getStatus() {
        return status;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public String toString() {
        return "TransactionSearchResult{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", status=" + transactionStatus +
                '}';
    }
}
