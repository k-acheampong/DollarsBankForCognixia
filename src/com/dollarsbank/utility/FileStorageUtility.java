package com.dollarsbank.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.dollarsbank.model.Customer;



public class FileStorageUtility {
	
	public static File createCustomerFile() {
		File file = new File("database/customer.data");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				System.out.println("File could not be created");
				e.printStackTrace();
			}	
		}
		
		return file;
	}	
	
	
	
	public static void saveCustomer(Customer customer) {
		File file = createCustomerFile();
		
		try (FileWriter fr = new FileWriter(file);
				BufferedWriter br = new BufferedWriter(fr);
				PrintWriter pr = new PrintWriter(br); 
				){
			
			
			pr.println(customer.toFileString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTransactions() {
		
	}
	
	public static String[] validateLogin(String[] login) {
		File file = createCustomerFile();
		String[] details = null;
	
		try (FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
			){
			
			String sb = new String("");
			
			while((sb = br.readLine()) != null){
				details = sb.split(": ");
				
				if (login[0].equals(details[3])) {
					if (login[1].equals(details[4])) {
						return details;
					}
				} else {
					String[] fail = {"fail"};
					return fail;
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e7) {
			e7.printStackTrace();
		}
		
		return details;
	}
	

}
