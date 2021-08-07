package com.service;

import com.controller.AtmController;
import com.exception.InvalidAmountException;
import com.exception.InvalidCurrencyException;
import com.model.response.DispenseMoneyResponse;
import com.util.AtmValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
public class AtmService {

    @Autowired AtmController atmController;

    private final Logger logger = LoggerFactory.getLogger(AtmService.class);

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/dispense")
    public ResponseEntity<DispenseMoneyResponse> deposit(
            @RequestParam Map<String,String> requestParams) throws  InvalidAmountException {
        int amount = Integer.parseInt(requestParams.get("amount"));
        int[] requiredNotes = new int[0];

        if(requestParams.containsKey("requiredNotes")){
            requiredNotes = Arrays.asList(requestParams.get("requiredNotes")
                    .split(","))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
            logger.info("required notes are {}", Arrays.toString(requiredNotes));
        }

        return atmController.calculateBank(amount, requiredNotes);
    }



}
