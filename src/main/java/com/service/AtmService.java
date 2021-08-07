package com.service;

import com.controller.AtmController;
import com.exception.InvalidAmountException;
import com.model.response.DispenseMoneyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AtmService {

    @Autowired AtmController atmController;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/dispense")
    public ResponseEntity<DispenseMoneyResponse> deposit(
            @RequestParam("amount") int amount) throws  InvalidAmountException {
        return atmController.calculateBank(amount);
    }

}
