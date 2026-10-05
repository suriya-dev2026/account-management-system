package com.accountmanagement.model.listeners;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingBankAccountType;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class AccountingBankAccountTypeListener {

    @PrePersist
    public void onCreateAccountingBankAccountType(AccountingBankAccountType accountingBankAccountType) {
        accountingBankAccountType.setStatus(AppConstants.ACTIVE);
    }

}
