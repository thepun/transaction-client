package org.bigwpay.transaction.service;

import org.bigwpay.transaction.model.Credentials;
import org.bigwpay.transaction.model.TransactionSearchResult;
import org.bigwpay.transaction.model.TransactionSearchStatus;
import org.bigwpay.transaction.model.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void prepare() {
        transactionService = new TransactionService(Credentials.of("test_key", "test_secret"));
    }

    @Test
    void transaction1() {
        TransactionSearchResult transactionSearchResult = transactionService.findTransaction("7bd710477a2174bc2424030feec46542");
        assertEquals(TransactionSearchStatus.FOUND, transactionSearchResult.getStatus());
        assertEquals(TransactionStatus.COMPLETED, transactionSearchResult.getTransactionStatus());
    }

    @Test
    void transaction2() {
        TransactionSearchResult transactionSearchResult = transactionService.findTransaction("310a5dc34c393e2154e080809c2c21c6");
        assertEquals(TransactionSearchStatus.FOUND, transactionSearchResult.getStatus());
        assertEquals(TransactionStatus.CANCELLED, transactionSearchResult.getTransactionStatus());
    }

    @Test
    void transaction3() {
        TransactionSearchResult transactionSearchResult = transactionService.findTransaction("df4efeffe327a3ce66394ccf6a256626");
        assertEquals(TransactionSearchStatus.FOUND, transactionSearchResult.getStatus());
        assertEquals(TransactionStatus.DECLINED, transactionSearchResult.getTransactionStatus());
    }

    @Test
    void transaction4() {
        TransactionSearchResult transactionSearchResult = transactionService.findTransaction("df4efeffe327a3ce66394ccf6a251111");
        assertEquals(TransactionSearchStatus.NOT_FOUND, transactionSearchResult.getStatus());
    }
}
