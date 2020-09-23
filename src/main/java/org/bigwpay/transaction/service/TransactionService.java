package org.bigwpay.transaction.service;

import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;
import org.bigwpay.transaction.model.Credentials;
import org.bigwpay.transaction.model.TokenResponse;
import org.bigwpay.transaction.model.TransactionResponse;
import org.bigwpay.transaction.model.TransactionSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private final Credentials credentials;

    public TransactionService(Credentials credentials) {
        this.credentials = credentials;
    }

    public TransactionSearchResult findTransaction(String transactionId) {
        // do auth request first
        LOGGER.debug("Authentication on bigwallet with user {}...", credentials.getUserName());
        HttpResponse<TokenResponse> tokenResponse = Unirest.get("https://app.bigwallet.online/auth/token")
                .connectTimeout(10000)
                .socketTimeout(10000)
                .basicAuth(credentials.getUserName(), credentials.getPassword())
                .asObject(TokenResponse.class);
        if (!tokenResponse.isSuccess()) {
            LOGGER.error("Failed to authenticate with user {}: http error code {}",
                    credentials.getUserName(), tokenResponse.getStatus());
            return TransactionSearchResult.authFailed(transactionId);
        }

        // check token not empty
        if (tokenResponse.getBody().getAccessToken() == null) {
            LOGGER.error("Failed to authenticate with user {}: empty auth token", credentials.getUserName());
            return TransactionSearchResult.authFailed(transactionId);
        }

        // do transaction request
        LOGGER.debug("Requesting transaction '{}' from bigwallet...", transactionId);
        HttpResponse<TransactionResponse> transactionResponse = Unirest.get("https://app.bigwallet.online/api/v1/transactions/{transactionId}")
                .routeParam("transactionId", transactionId)
                .connectTimeout(10000)
                .socketTimeout(10000)
                .header("Authorization", "Bearer " + tokenResponse.getBody().getAccessToken())
                .asObject(TransactionResponse.class);
        if (transactionResponse.isSuccess()) {
            LOGGER.info("Transaction {} found", transactionId);
            return TransactionSearchResult.found(transactionId, transactionResponse.getBody().getState());
        } else {
            if (transactionResponse.getStatus() == HttpStatus.NOT_FOUND) {
                LOGGER.error("Transaction '{}' not found", transactionId);
                return TransactionSearchResult.notFound(transactionId);
            } else if (transactionResponse.getStatus() == HttpStatus.UNAUTHORIZED) {
                LOGGER.error("Failed to find transaction '{}': token is not valid", transactionId);
                return TransactionSearchResult.authFailed(transactionId);
            } else {
                LOGGER.error("Failed to find transaction '{}': http error code {}", transactionId, transactionResponse.getStatus());
                return TransactionSearchResult.error(transactionId);
            }
        }
    }

}
