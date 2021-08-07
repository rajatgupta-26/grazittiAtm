package com.controller;

import com.exception.InvalidAmountException;
import com.model.response.DispenseMoneyResponse;
import com.util.Constant;
import com.util.AtmValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
public class AtmController {

    private final Logger logger = LoggerFactory.getLogger(AtmController.class);

    public ResponseEntity<DispenseMoneyResponse> calculateBank(int amount, int[] requiredCurrency){
        String responseCode;
        String responseDesc;
        String responseStatus;
        String responseBody = null;

        DispenseMoneyResponse responseWrapper = new DispenseMoneyResponse();
        try {
            boolean isCurrencySpecified = false;
            if(requiredCurrency.length > 0){
                AtmValidator.validateRequiredCurrency(requiredCurrency);
                isCurrencySpecified = true;
            }

            sortCurrency(requiredCurrency);
            AtmValidator.validateAmount(amount);
            int[] notesCounter = getNumberOfNotes(amount, requiredCurrency);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < notesCounter.length; i++) {
                if(isCurrencySpecified){
                    if (notesCounter[i] != 0) {
                        body.append(requiredCurrency[i]).append(" : ").append(notesCounter[i]).append(",");
                    }
                } else{
                    if (notesCounter[i] != 0) {
                        body.append(Constant.DEFAULT_CURRENCY_AVAILABLE[i]).append(" : ").append(notesCounter[i]).append(",");
                    }
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
        int[] currencyValue = Constant.DEFAULT_CURRENCY_AVAILABLE;
        if(currencyToBeUsed.length != 0){
            numOfCurrencyAvailable = currencyToBeUsed.length;
            currencyValue = currencyToBeUsed;
        }
        int[] noteCounter = new int[numOfCurrencyAvailable];
        int leftAmount = compute(numOfCurrencyAvailable, amount, currencyValue, noteCounter);

        if(leftAmount != 0){
            System.out.println(amount);
            throw new InvalidAmountException("Amount entered can not be dispensed, " +
                    "Please select currency in multiple of 100");
        }
        return noteCounter;
    }

    private int compute(int length, int amount, int[] currencyValue, int[] noteCounter){
        for (int i = 0; i < length; i++) {
            int noteValue = currencyValue[i];
            if (amount >= noteValue) {
                noteCounter[i] = amount / noteValue;
                amount -= noteCounter[i] * noteValue;
            }
        }
        return amount;
    }

    private void sortCurrency(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
