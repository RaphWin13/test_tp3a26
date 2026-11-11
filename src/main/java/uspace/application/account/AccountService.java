package uspace.application.account;

import jakarta.inject.Inject;
import uspace.application.account.dtos.AccountDto;
import uspace.domain.account.Account;
import uspace.domain.account.AccountRepository;
import uspace.domain.account.AccountUsername;
import uspace.domain.account.exceptions.UsernameAlreadyExistsException;
import uspace.domain.account.exceptions.AccountNotFoundException;

public class AccountService {

	private final AccountRepository accountRepository;
	private final AccountAssembler accountAssembler;

	@Inject
	public AccountService(AccountRepository accountRepository, AccountAssembler accountAssembler) {
		this.accountRepository = accountRepository;
		this.accountAssembler = accountAssembler;
	}

	public void createAccount(AccountDto accountDto) {
		Account account = accountRepository.findByUsername(new AccountUsername(accountDto.username));
		if (account != null) {
			throw new UsernameAlreadyExistsException();
		}

		Account newAccount = accountAssembler.toAccount(accountDto);
		accountRepository.save(newAccount);
	}

	public AccountDto findAccount(String username) {
		Account account = accountRepository.findByUsername(new AccountUsername(username));
		if (account == null) {
			throw new AccountNotFoundException();
		}

		return accountAssembler.toDto(account);
	}
}
