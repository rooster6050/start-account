package com.qa.service;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class RepositoryLayer {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em; 
	private JSONUtil json;
	
		
	public String getAllAccounts() {
		Query query = em.createQuery("Select a FROM account");
		Collection<Account> accounts = (Collection<Account>) query.getResultList();
		return json.getJSONForObject(accounts);
	}
	
	private Account findAccount(int id) {
		return em.find(Account.class, id);
	}
	
	@Transactional(REQUIRED)
	public void createAccount(String account) {
		Account anAccount = json.getObjectForJSON(account, Account.class);
		em.persist(anAccount);
	}
	
	
	@Transactional(REQUIRED)
	public String deleteAccount(int id) 
	{
		Account account = findAccount(id);
		if (account != null) 
		{
			em.remove(account);
			return "Account deleted";
		}
		return "Account not found";

	}
	
	public void setManager(EntityManager newManager)
	{
		em = newManager;
	}
	
}