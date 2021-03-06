package com;

import com.exception.InvalidAmountException;
import com.util.AtmValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmValidatorTest {

    @Test
    public void validateMinAmountSuccess() throws InvalidAmountException {
        boolean result = AtmValidator.validateAmount(50);
        Assert.assertTrue(result);
    }

    @Test
    public void validateMinAmountFailWithInvalidAmountException() {
        String expectedErr = "Min Amount:10,  Amount less than min amount";
        String errMsg = null;
        try {
            AtmValidator.validateAmount(1);
        } catch (InvalidAmountException e){
            errMsg = e.getMessage();
        }

        Assert.assertEquals(expectedErr, errMsg);
    }

}
