package com.util;

import com.exception.InvalidAmountException;
import com.exception.InvalidCurrencyException;

public class AtmValidator {

    public static boolean validateAmount(int amount) throws InvalidAmountException {
        if (amount < 10){
            throw new InvalidAmountException("Min Amount:10,  Amount less than min amount");
        }
        return true;
    }

    public static boolean validateRequiredCurrency(int[] requiredNotes) throws InvalidCurrencyException {
        boolean isSupported = true;
        StringBuilder unSupportedCurrencies = new StringBuilder("[");
        for (int requiredNote : requiredNotes) {
            int j;
            for ( j = 0; j < Constant.DEFAULT_CURRENCY_AVAILABLE.length; j++) {
                if (requiredNote == Constant.DEFAULT_CURRENCY_AVAILABLE[j])
                    break;
            }
            if(j == Constant.DEFAULT_CURRENCY_AVAILABLE.length){
                isSupported = false;
                unSupportedCurrencies.append(requiredNote).append(",");
            }

        }
        unSupportedCurrencies.append(']');

        if(!isSupported){
            throw new InvalidCurrencyException(unSupportedCurrencies + "are not supported");
        }

        return true;
    }
}
