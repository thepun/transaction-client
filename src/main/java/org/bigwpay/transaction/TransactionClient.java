package org.bigwpay.transaction;

import org.bigwpay.transaction.model.Credentials;
import org.bigwpay.transaction.model.TransactionSearchResult;
import org.bigwpay.transaction.model.TransactionSearchStatus;
import org.bigwpay.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(name = "transaction", description = "Utility to search transactions on bigwallet")
public class TransactionClient implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionClient.class);


    @CommandLine.Parameters(index = "0", description = "Transaction ID")
    private String transactionId;

    @CommandLine.Option(names = {"-u", "--user"}, description = "Username to use")
    private String userName = "test_key";

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password to use")
    private String password = "test_secret";

    @Override
    public void run() {
        LOGGER.info("Search transaction");
        Credentials credentials = Credentials.of(userName, password);
        TransactionService transactionService = new TransactionService(credentials);
        TransactionSearchResult transactionSearchResult = transactionService.findTransaction(transactionId);
        if (transactionSearchResult.getStatus() == TransactionSearchStatus.FOUND) {
            LOGGER.info("Transaction is found: {}", transactionSearchResult.getTransactionStatus());
        } else if (transactionSearchResult.getStatus() == TransactionSearchStatus.NOT_FOUND) {
            LOGGER.info("Transaction is not found");
        } else if (transactionSearchResult.getStatus() == TransactionSearchStatus.AUTH_FAILED) {
            LOGGER.info("Authentication failed");
        } else {
            LOGGER.info("Unexpected error accured");
        }
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new TransactionClient()).execute(args);
        System.exit(exitCode);
    }
}
