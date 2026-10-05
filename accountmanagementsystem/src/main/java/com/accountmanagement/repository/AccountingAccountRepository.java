package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.AccountingAccount;

public interface AccountingAccountRepository extends JpaRepository<AccountingAccount, UUID> {

    Optional<AccountingAccount> findTopByOrganizationIdOrderByAccountCodeDesc(UUID organizationId);

    boolean existsByOrganizationIdAndAccountName(UUID organizationId, String accountName);

    Optional<AccountingAccount> findByIdAndOrganizationId(UUID accountId, UUID organizationId);

    Optional<AccountingAccount> findTopByOrganizationIdAndAccountTypeIdOrderByAccountCodeDesc(UUID organizationId,
            Integer accountTypeId);

}
