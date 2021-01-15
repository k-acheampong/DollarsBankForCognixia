package com.dollarsbank.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.dollarsbank.model.Customer;
import com.dollarsbank.utility.ColorsUtility;
import com.dollarsbank.utility.ConsoleMenuUtility;
import com.dollarsbank.utility.DataGeneratorUtility;
import com.dollarsbank.utility.FileStorageUtility;

public class DollarsBankController implements ColorsUtility{
	

	
	public static void welcome() {
		Scanner sc = new Scanner(System.in);
		LocalDateTime today = LocalDateTime.now();
		
		String formattedDate = today.getDayOfWeek() + " " + today.getMonthValue() + "/" + 
								today.getDayOfMonth() + "/" + today.getYear();
		
		
		
		
		String[] custArr;
		Customer customer = null;
		boolean finished = false;
		
		while(!finished) {
			try {
				ConsoleMenuUtility.welcome();
				System.out.println(YELLOW + "\nEnter Selection (1, 2, 3)" + RESET);
				String selection = sc.nextLine();
				

				if (selection.equals("1")) {
					
					custArr = ConsoleMenuUtility.createAccount(sc);
					BigDecimal dep = ConsoleMenuUtility.initialDeposit(sc);
					
					customer = new Customer(custArr[0], custArr[1], custArr[2], custArr[3], custArr[4], dep, new BigDecimal(0));
					
					
					String balance = DataGeneratorUtility.formatDollars(customer.getCheckingAccount().getBalance());
					String deposit = DataGeneratorUtility.formatDollars(dep);

					
					ArrayList<String> transactions = new ArrayList<String>();
					transactions.add("Initial Deposit - Deposit Amount: " + deposit + " into Checking - \nChecking Balance: " + balance + 
									" - as on " + formattedDate + " " + LocalTime.now());
					
					customer.setTransactions(transactions);
					
					FileStorageUtility.saveCustomer(customer);
					
					customerPage(customer, formattedDate);
					
				//Login	
				} else if (selection.equals("2")) {
					boolean validated = false;
					
					
					while(!validated) {
						
						String[] login = ConsoleMenuUtility.login(sc);
						
						//Validate login here
						String[] valid = FileStorageUtility.validateLogin(login);
						
						if (valid.length == 8) {
							
							String[] trans = valid[7].split(", ");
							ArrayList<String> transactions = new ArrayList<>(Arrays.asList(trans));
							
							
							customer = new Customer(valid[0], valid[1], valid[2], valid[3], 
													valid[4], new BigDecimal(valid[5]), 
													new BigDecimal(valid[6]), transactions);
							validated = true; 
							
							customerPage(customer, formattedDate);
							
						} else {
							System.out.println(RED + "Invalid Login Credentials, Please Try again");
						}
					}
					
					
				
				} else if (selection.equals("3")) {
					System.out.println("\nClosing Bank App, Good Bye!");
					System.exit(0);

				} else {
					System.out.println(RED + "Invalid input, try again." + RESET);
					welcome();
				}
				
			} catch (InputMismatchException e){
				System.out.println(RED + "Invalid input, expecting number");
			}
			
		}	
		sc.close();
	}
	
	public static void customerPage(Customer customer, String formattedDate) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> transactions = customer.getTransactions();
		boolean finished = false;
		
		while(!finished) {
			try {
				ConsoleMenuUtility.customerPage();
				System.out.println(YELLOW + "Enter Selection (1, 2, 3, 4, 5, 6)" + RESET);
				String selection = sc.nextLine();

					
				//Deposit
				if (selection.equals("1")) {
					
					boolean accountValid = false;
					String account= "";
					while(!accountValid) {
						
						
						account = ConsoleMenuUtility.depositAccount(sc);
						if (account.equals("1") || account.equals("2")) {
							accountValid = true;
						} else {
							System.out.println(RED + "Invalid Selection. Expecting 1 or 2" + RESET);
						}
					}
					
					
					BigDecimal dep = ConsoleMenuUtility.depositAmount(sc);
					String deposit = DataGeneratorUtility.formatDollars(dep);
					
					//Deposit to checking
					if (account.equals("1")) {
						BigDecimal checking = customer.getCheckingAccount().getBalance();
						customer.getCheckingAccount().setBalance(checking.add(dep));
						
						String balance = DataGeneratorUtility.formatDollars(customer.getCheckingAccount().getBalance());
						
						transactions.add("Deposit into Checking - Deposit Amount: " + deposit + " - \nChecking Balance: " + balance + 
									" - as on " + formattedDate + " " + LocalTime.now());
				
						
					//Deposit to savings	
					} else if (account.equals("2")) {
						BigDecimal savings = customer.getSavingsAccount().getBalance();
						customer.getSavingsAccount().setBalance(savings.add(dep));
						
						String balance = DataGeneratorUtility.formatDollars(customer.getSavingsAccount().getBalance());
						transactions.add("Deposit into Savings - Deposit Amount: " + deposit + " - \nSavings Balance: " + balance + 
									" - as on " + formattedDate + " " + LocalTime.now());

					} else {
						
					}
					
					
					
				//Withdraw
				} else if (selection.equals("2")) {
					
					boolean accountValid = false;
					String account= "";
					while(!accountValid) {
						
						account = ConsoleMenuUtility.withdrawAccount(sc);
						if (account.equals("1") || account.equals("2")) {
							accountValid = true;
						} else {
							System.out.println(RED + "Invalid Selection. Expecting 1 or 2" + RESET);
						}
					}
					
					BigDecimal withd = ConsoleMenuUtility.withdrawAmount(sc);
					String withdraw = DataGeneratorUtility.formatDollars(withd);
					
					
					
					//Withdraw from checking
					if (account.equals("1")) {
						
						BigDecimal checkingBalance = customer.getCheckingAccount().getBalance();
						
						if (withd.compareTo(checkingBalance) == 1) {
							System.out.println(RED + "Insufficient funds for withdrawal amount");
						} else {
							
							customer.getCheckingAccount().setBalance(checkingBalance.subtract(withd));
							
							String balance = DataGeneratorUtility.formatDollars(customer.getCheckingAccount().getBalance());
							transactions.add("Withdraw from Checking - Withdraw Amount: " + withdraw + " - \nChecking Balance: " + 
												balance + " - as on " + formattedDate + " " + LocalTime.now());
							
						}
						
					//Withdraw from savings
					} else if (account.equals("2")) {
						
						BigDecimal savingsBalance = customer.getSavingsAccount().getBalance();
						
						if (withd.compareTo(savingsBalance) == 1) {
							System.out.println(RED + "Insufficient funds for withdrawal amount");
						
						} else {
							customer.getSavingsAccount().setBalance(savingsBalance.subtract(withd));;
							
							String balance = DataGeneratorUtility.formatDollars(customer.getSavingsAccount().getBalance());
							transactions.add("Withdraw from Savings - Withdraw Amount: " + withdraw + 
												" - \nSavings Balance: " + balance + " - as on " 
												+ formattedDate + " " + LocalTime.now());


					}
						customer.setTransactions(transactions);	
						
					}
					
				//Transfer funds
				} else if (selection.equals("3")) {
					boolean accountValid = false;
					String account= "";
					while(!accountValid) {
						
						account = ConsoleMenuUtility.transferAccount(sc);
						if (account.equals("1") || account.equals("2")) {
							accountValid = true;
						} else {
							System.out.println(RED + "Invalid Selection. Expecting 1 or 2" + RESET);
						}
					}
					
					BigDecimal transfer =  new BigDecimal(ConsoleMenuUtility.transferAmount(sc));
					String transferAmt = DataGeneratorUtility.formatDollars(transfer);
					
					
					//1 = transfer from checking to savings
					if (account.equals("1")) {
						
						if (transfer.compareTo(customer.getCheckingAccount().getBalance()) == 1) {
							System.out.println(RED + "Insufficient funds for withdrawal amount");
						} else {
							BigDecimal checkingsBalance = customer.getCheckingAccount().getBalance();
							BigDecimal savingsBalance = customer.getSavingsAccount().getBalance();
							
							customer.getCheckingAccount().setBalance(checkingsBalance.subtract(transfer));
							customer.getSavingsAccount().setBalance(savingsBalance.add(transfer));
							
							String checking = DataGeneratorUtility.formatDollars(customer.getCheckingAccount().getBalance());
							String savings = DataGeneratorUtility.formatDollars(customer.getSavingsAccount().getBalance());
							transactions.add("Transfer funds from Checking->Savings - Transfer Amount: " + 
												transferAmt + " - \nChecking Acct Balance: " + GREEN + checking + RESET + 
												" - Savings Acct Balance: " + GREEN + savings + RESET + 
												" - as on " + formattedDate + " " + LocalTime.now());

							
							customer.setTransactions(transactions);
						}
						
					} else if (account.equals("2")) {
						
						if (transfer.compareTo(customer.getSavingsAccount().getBalance()) == 1) {
							System.out.println(RED + "Insufficient funds for withdrawal amount");
						} else {
							
							BigDecimal savingsBalance = customer.getSavingsAccount().getBalance();
							BigDecimal checkingsBalance = customer.getCheckingAccount().getBalance();
							
							customer.getSavingsAccount().setBalance(savingsBalance.subtract(transfer));
							customer.getCheckingAccount().setBalance(checkingsBalance.add(transfer));
							
							String checking = DataGeneratorUtility.formatDollars(customer.getCheckingAccount().getBalance());
							String savings = DataGeneratorUtility.formatDollars(customer.getSavingsAccount().getBalance());
							transactions.add("Transfer funds from Savings->Checking - Transfer Amount: " + 
												transferAmt + " - \nSavings Acct Balance: " + savings + 
												" - Checking Acct Balance: " + checking + 
												" - as on " + formattedDate + " " + LocalTime.now());
		
							
							customer.setTransactions(transactions);
						}
					}
					
					
				//Last 5 transactions
				} else if (selection.equals("4")) {
					ConsoleMenuUtility.lastFiveTrans(transactions);									
				
				//Customer Information
				} else if (selection.equals("5")) {
					String formattedUserInfo = DataGeneratorUtility.formatUserInfo(customer);
					
					ConsoleMenuUtility.customerInformation(formattedUserInfo);
				
					
				//Log off	
				} else if (selection.equals("6")) {
					
					FileStorageUtility.saveCustomer(customer);
					
					System.out.println("Logging off...");
					finished = true;
				} 
				
				
			} catch (InputMismatchException e){
				System.out.println(RED + "Invalid input, expecting number");
			}
		}
		
	}
	

}
