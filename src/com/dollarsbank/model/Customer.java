package com.dollarsbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5268830798838347882L;
	
	private String name;
	private String address;
	private String phoneNumber;
	private String username;
	private String password;
	private CheckingAccount checkingAccount;
	private SavingsAccount savingsAccount;
	private ArrayList<String> transactions;
	
	public Customer(String name, String address, String phoneNumber, String username, String password,
			BigDecimal firstDeposit, BigDecimal initialSavings) {
		super();
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
		this.checkingAccount = new CheckingAccount(firstDeposit);
		this.savingsAccount = new SavingsAccount(initialSavings);
	}
	
	//A customer constructor with history of transactions created after each login
	public Customer(String name, String address, String phoneNumber, String username, String password,
			BigDecimal firstDeposit, BigDecimal initialSavings, ArrayList<String> transactions) {
		super();
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
		this.checkingAccount = new CheckingAccount(firstDeposit);
		this.savingsAccount = new SavingsAccount(initialSavings);
		this.transactions = transactions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}

	public void setCheckingAccount(CheckingAccount checkingAccount) {
		this.checkingAccount = checkingAccount;
	}

	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(SavingsAccount savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	public ArrayList<String> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<String> transactions) {
		this.transactions = transactions;
	}
	
	//Converts transaction into a file for display as an ArrayList
	public String toFileString() {
		String transactionsString = String.join(", ", transactions);
		return "" + name +", Adddress: "+ address +", Phone Number: "+ phoneNumber +", Username: "+ username +", Password: "+ password +", Checking Account Balance: "+ checkingAccount.getBalance() +", Savings Account Balance: "+ savingsAccount.getBalance() +" "+ transactionsString;
	}

}
