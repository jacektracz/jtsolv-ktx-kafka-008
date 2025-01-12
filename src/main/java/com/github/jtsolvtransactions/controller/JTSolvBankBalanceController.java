package com.github.jtsolvtransactions.controller;

import com.github.jtsolvtransactions.model.JTSolvBankBalance;
import com.github.jtsolvtransactions.service.JTSolvBankBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-balance")
public class JTSolvBankBalanceController {

    private final JTSolvBankBalanceService bankBalanceService;

    @Autowired
    public JTSolvBankBalanceController(JTSolvBankBalanceService bankBalanceService) {
        this.bankBalanceService = bankBalanceService;
    }

    @GetMapping(value = "/{bankBalanceId}", produces = "application/json")
    public ResponseEntity<JTSolvBankBalance> getBankBalance(@PathVariable("bankBalanceId") Long bankBalanceId) {
        var bankBalance = bankBalanceService.getBankBalance(bankBalanceId);
        return ResponseEntity.ok(bankBalance);
    }
}
