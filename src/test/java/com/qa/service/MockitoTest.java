package com.qa.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {
	
	@InjectMocks
	private RepositoryLayer repoLayer;
	
	@Mock
	private EntityManager em;
	
	@Mock
	private Query query;
	
	private JSONUtil util;
	
	@Before
	public void setup() 
	{
		repoLayer.setManager(em);
	}

	@Test
	public void testGetAllAccounts() 
	{
		Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(query);
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("Ed", "Hawksworth", "1234"));
		Mockito.when(query.getResultList()).thenReturn(accounts);
		Assert.assertEquals("[{\"firstName\":\"Ed\",\"secondName\":\"Hawksworth\",\"accountNumber\":\"1234\"}]", repoLayer.getAllAccounts());
	}

}