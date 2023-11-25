package com.parsla.PeopleHub.repo;

import com.parsla.PeopleHub.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAcctNo(long acctNo);
}
