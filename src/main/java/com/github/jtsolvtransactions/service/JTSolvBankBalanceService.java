package com.github.jtsolvtransactions.service;

import com.github.jtsolvtransactions.model.JTSolvBankBalance;
import com.github.jtsolvtransactions.repository.JTSolvBankBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class JTSolvBankBalanceService {

    private final JTSolvBankBalanceRepository bankBalanceRepository;

    public JTSolvBankBalanceService(JTSolvBankBalanceRepository bankBalanceRepository) {
        this.bankBalanceRepository = bankBalanceRepository;
    }

    public JTSolvBankBalance getBankBalance(Long bankBalanceId) {
        return bankBalanceRepository.find(bankBalanceId);
    }

}
