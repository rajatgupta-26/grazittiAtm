package com.controller;

import com.exception.InvalidAmountException;
import com.model.response.DispenseMoneyResponse;
import com.util.Constant;
import com.util.BankValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AtmController {

    private final Logger logger = LoggerFactory.getLogger(AtmController.class);

    public ResponseEntity<DispenseMoneyResponse> calculateBank(int amount){
        String responseCode;
        String responseDesc;
        String responseStatus;
        String responseBody = null;

        DispenseMoneyResponse responseWrapper = new DispenseMoneyResponse();
        try {
            BankValidator.validateAmount(amount);
            int[] notesCounter = getNumberOfNotes(amount, null);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < Constant.DEFAULT_CURRENCY_AVAILABLE.length; i++) {
                if (notesCounter[i] != 0) {
                    body.append(Constant.DEFAULT_CURRENCY_AVAILABLE[i]).append(" : ").append(notesCounter[i]).append(",");
                }
            }
            responseBody = String.valueOf(body);
            responseCode = Constant.SUCCESS_CODE;
            responseDesc = Constant.SUCCESS;
            responseStatus = Constant.SUCCESS;
        } catch (Exception e){
            logger.error("Got Exception while processing : {}", e.getMessage());
            responseCode = Constant.FAIL_CODE;
            responseDesc = e.getMessage();
            responseStatus = Constant.FAIL;
        }

        if(responseBody != null){
            responseWrapper.setCurrencyBreakdown(responseBody);
        }
        responseWrapper.setResponseCode(responseCode);
        responseWrapper.setResponseDesc(responseDesc);
        responseWrapper.setResponseStatus(responseStatus);

        ResponseEntity<DispenseMoneyResponse> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }

    private int[] getNumberOfNotes(int amount, int[] currencyToBeUsed) throws InvalidAmountException {
        int numOfCurrencyAvailable = Constant.DEFAULT_CURRENCY_AVAILABLE.length;
        int[] noteCounter = new int[numOfCurrencyAvailable];

        for (int i = 0; i < numOfCurrencyAvailable; i++) {
            int noteValue = Constant.DEFAULT_CURRENCY_AVAILABLE[i];
            if (amount >= noteValue) {
                noteCounter[i] = amount / noteValue;
                amount -= noteCounter[i] * noteValue;
            }
        }
        if(amount != 0){
            throw new InvalidAmountException("Amount entered can not be dispensed, " +
                    "Please select currency in multiple of 100");
        }
        return noteCounter;
    }
}
