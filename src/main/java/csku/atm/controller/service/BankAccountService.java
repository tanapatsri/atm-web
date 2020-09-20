package csku.atm.controller.service;


import csku.atm.model.BankAccount;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    private ArrayList<BankAccount> bankAccountsList;

    @PostConstruct
    public void postConstruct() { this.bankAccountsList = new ArrayList<>();}

    public void CreateBankAccount (BankAccount bankAccount){
        bankAccountsList.add(bankAccount);

    }

    public List<BankAccount> getBankAccount (){
        return new ArrayList<>(this.bankAccountsList) ;
    }
}
