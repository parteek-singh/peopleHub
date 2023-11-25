package com.parsla.PeopleHub.service;

import com.parsla.PeopleHub.common.NumberUtil;
import com.parsla.PeopleHub.constant.enums.ObjectType;
import com.parsla.PeopleHub.entity.Account;
import com.parsla.PeopleHub.entity.Department;
import com.parsla.PeopleHub.repo.AccountRepository;
import com.parsla.PeopleHub.view.request.AccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepartmentService departmentService;
    private final NumberUtil numberUtil;

    public AccountService(AccountRepository accountRepository,
                          DepartmentService departmentService,
                          NumberUtil numberUtil){
        this.accountRepository = accountRepository;
        this.departmentService = departmentService;
        this.numberUtil = numberUtil;
    }


    public Account add(AccountRequest request) {

        System.out.println("create  Account");
        Department department = departmentService.getByNo(request.getDeptNo());

        long count = accountRepository.count();
        long acctNo = numberUtil.generateIndexNo(ObjectType.ACCOUNT, count);

        Account project = Account.builder()
                .name(request.getName())
                .acctNo(acctNo)
                .department(department)
                .description(request.getDescription())
                .startDt(request.getStartDt())
                .build();

        return accountRepository.save(project);

    }

    public Account update(AccountRequest request, long acctNo){
        Account account = getByNo(acctNo);
        account.setName(request.getName());
        account.setDescription(request.getDescription());
        account.setStartDt(request.getStartDt());
        return accountRepository.save(account);
    }

    public Account getByNo(long acctNo) {
        Optional<Account> account = accountRepository.findByAcctNo(acctNo);
        if (account.isPresent()) {
            return account.get();
        }

        throw new RuntimeException("Account not found");
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public boolean updateManager(long deptNo, long empNo) {
        return true;
    }

    public long countAll() {
        return accountRepository.count();
    }

    public List<Account> getAll(Pageable pageable) {
        Page<Account> page =  this.accountRepository.findAll(pageable);
        return page.getContent();
    }
}
