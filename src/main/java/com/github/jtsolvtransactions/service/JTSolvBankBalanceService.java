package com.github.jtsolvtransactions.service;

import com.github.jtsolvtransactions.model.JTSolvBankBalance;
import com.github.jtsolvtransactions.repository.BankBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class JTSolvBankBalanceService {

    private final BankBalanceRepository bankBalanceRepository;

    public JTSolvBankBalanceService(BankBalanceRepository bankBalanceRepository) {
        this.bankBalanceRepository = bankBalanceRepository;
    }

    public JTSolvBankBalance getBankBalance(Long bankBalanceId) {
        return bankBalanceRepository.find(bankBalanceId);
    }

}
