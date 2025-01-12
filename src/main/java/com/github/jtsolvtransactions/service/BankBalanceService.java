package com.github.jtsolvtransactions.service;

import com.github.jtsolvtransactions.model.BankBalance;
import com.github.jtsolvtransactions.repository.BankBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class BankBalanceService {

    private final BankBalanceRepository bankBalanceRepository;

    public BankBalanceService(BankBalanceRepository bankBalanceRepository) {
        this.bankBalanceRepository = bankBalanceRepository;
    }

    public BankBalance getBankBalance(Long bankBalanceId) {
        return bankBalanceRepository.find(bankBalanceId);
    }

}
