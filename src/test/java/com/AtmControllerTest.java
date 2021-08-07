package com;

import com.controller.AtmController;
import com.model.response.DispenseMoneyResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmControllerTest {


    @InjectMocks
    AtmController atmController;

    @Before
    public void setUp(){
    }


    @Test
    public void calculateBankWorkCorrectlyWhenSuccess(){

        String expectedResultCode = "0";
        String expectedResultDesc = "SUCCESS";

        ResponseEntity<DispenseMoneyResponse> response = atmController.calculateBank(180);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
    }

    @Test
    public void calculateBankThrowsExceptionWhenAmountIsValid(){
        String expectedResultCode = "0";
        String expectedResultDesc = "SUCCESS";
        String expectedResultBody = "500 : 1,200 : 1,100 : 1,10 : 1,";

        ResponseEntity<DispenseMoneyResponse> response =  atmController.calculateBank(810);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
        Assert.assertEquals(expectedResultBody, response.getBody().getCurrencyBreakdown());
    }

    @Test
    public void calculateBankThrowsExceptionWhenAmountIsInvalid(){
        String expectedResultCode = "1";
        String expectedResultDesc = "Amount entered can not be dispensed, Please select currency in multiple of 100";

        ResponseEntity<DispenseMoneyResponse> response =  atmController.calculateBank(811);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
    }

    @Test
    public void calculateBankThrowsExceptionWhenAmountIsZero(){
        String expectedResultCode = "1";
        String expectedResultDesc = "Min Amount:10,  Amount less than min amount";

        ResponseEntity<DispenseMoneyResponse> response =  atmController.calculateBank(0);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
    }
}
