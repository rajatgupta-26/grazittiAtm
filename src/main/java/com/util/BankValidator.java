package com.util;

import com.exception.InvalidAmountException;

public class BankValidator {

    public static boolean validateAmount(int amount) throws InvalidAmountException {
        if (amount < 10){
            throw new InvalidAmountException("Min Amount:10,  Amount less than min amount");
        }
        return true;
    }
}
