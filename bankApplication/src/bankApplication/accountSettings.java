package bankApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class accountSettings {
	public static List<String> customerName = new ArrayList<>();						/*All arrayLists are assigned here*/
	public static List<String> customerStreet = new ArrayList<>();						/*These arrayLists are assigned as 'public static', so they*/
	public static List<String> customerTown = new ArrayList<>();						/* can be accessed and altered in different classes.*/
	public static List<String> customerPostcode = new ArrayList<>();	
	public static List<String> customerEmail = new ArrayList<>();
	public static List<String> customerBirth = new ArrayList<>();
	public static List<String> accountType = new ArrayList<>();
	public static List<Integer> accountNumber = new ArrayList<>();
	public static List<String> sortCode = new ArrayList<>();
	public static List<Integer> pinNumber = new ArrayList<>();
	public static List<Double> accountBalance = new ArrayList<>();
	public static List<String> login = new ArrayList<>();								/*This arrayList is purely for temporary reasons whilst logged into "My Account".*/
	public static List<Double> balanceDifference = new ArrayList<>();
	
	myAccountMenu mam = new myAccountMenu();											/*So the myAccountMenu can be accessed.*/
	Scanner input = new Scanner(System.in);
	DecimalFormat df = new DecimalFormat("##.00");										/*So that all money can be shown as a two decimal point.*/
	
	public void accountLogin(){															/*To access personal details, and deposit/withdraw from their account.*/
		
		System.out.println("Please enter your email address:");							/*The user will have to insert there email address, then their PIN to access their account.*/
		String emailLogin = input.nextLine();
		if (customerEmail.contains(emailLogin)){
			int LI = customerEmail.indexOf(emailLogin);
			System.out.println("Please enter your PIN:");
			int pinLogin = input.nextInt();
			if (pinNumber.get(LI).equals(pinLogin)){
				login.add(emailLogin);													/*The email of the entered account is temporarily saved into the login arrayList*/
				mam.accountMenu();														/*This is so the user won't have to keep logging in to view balance, edit account, deposit etc.*/
				printTransactions();
			}else{
				System.out.println("PIN entered does not exist.");
			}
		}else{
			System.out.println("Your details are not found.");
		}
	}
	
	public void viewDetails() {															/*For the user to view their personal details.*/
		
		for (int i = 0; i < login.size(); i++)											/*This 'for loop' is used in the majority of the methods so the user*/
		{																				/*won't be prompted to enter email and PIN every time to access a function.*/
			int PD = customerEmail.indexOf(login.get(i));								/*The for loop finds all available strings in the arrayList for login.*/
			System.out.println(" ");													/*But as there is only one string stored in the arrayList, the customerEmail will be compared to the login arrayList*/
			System.out.println("Your details:");										/*The login array is compared to the users' email, so that the index will be found to show the relevant information*/
			System.out.println(customerName.get(PD)+" - "+customerTown.get(PD)+" - "+accountNumber.get(PD)+" - "+sortCode.get(PD)+" - £ "+df.format(accountBalance.get(PD)));
		}
		try {																			/*This delays the menu to appear*/
		    Thread.sleep(3000);                 										/*3000 milliseconds is three seconds.*/
		} catch(InterruptedException ex) {
			 Thread.currentThread().interrupt();
		}  
	}
	
	public void viewBalance() {															/*For the user to view their current balance.*/
		
		double currentAmount, interestAmount, newBalance;								/*Variables are created to allow calculations for the interest rate to take place.*/
		for (int i = 0; i < login.size(); i++)
		{
			
			int PD = customerEmail.indexOf(login.get(i));								/*The int PD stands for Personal Details*/
			if (accountType.get(PD).equals("Savings Account")){							/*This checks if the customer has a Savings Account*/
				currentAmount = accountBalance.get(PD);									/*If so, then an interest rate of 1% is added every time the customer views their balance*/
				interestAmount = (currentAmount * 0.01);
				newBalance = interestAmount + currentAmount;
				accountBalance.set(PD, newBalance);										/*The new balance that includes the interest rate is assigned to the array*/
			}
			System.out.println("\n" + "Your details:");
			System.out.println("Current balance: £ "+df.format(accountBalance.get(PD))); 	/*df.format allows the account balance to be shown as a currency.*/
		}
		try {																			/*This delays the menu to appear*/
		    Thread.sleep(3000);                 										/*3000 milliseconds is three seconds.*/
		} catch(InterruptedException ex) {
			 Thread.currentThread().interrupt();
		}
	}

	public void depositMoney() {														/*For the user to deposit money into their account.*/
		
		for (int i = 0; i < login.size(); i++)
		{
			int PD = customerEmail.indexOf(login.get(i));
			System.out.println(" ");
			System.out.println("Please enter the amount you would like to deposit:");
			Double balance = accountBalance.get(PD);
			Double deposit = input.nextDouble();
			if (deposit < 0){															/*If the user inputs a number less than zero, the deposit is cancelled.*/
				System.out.println("Invalid amount.");
			}
			Double total = new Double(balance += deposit.doubleValue());
			accountBalance.set(PD, total);
			System.out.println("\n" + "Your new balance:");
			System.out.println("£ "+df.format(accountBalance.get(PD))); 				/*The DecimalFormat function is called to present the balance in 2 decimal points.*/
			printTransactions();
			try {																		/*This delays the menu to appear*/
			    Thread.sleep(3000);                 									/*3000 milliseconds is three seconds.*/
			} catch(InterruptedException ex) {
				 Thread.currentThread().interrupt();
			}  
        }
	}
	
	public void withdrawMoney() {														/*Allows the user to withdraw from their account.*/
		
		for (int i = 0; i < login.size(); i++)
		{
			int PD = customerEmail.indexOf(login.get(i));								/*Asks the user how much they would like to withdraw from their account.*/
			System.out.println("\n" + "Please enter the amount you would like to withdraw:");
			Double balance = accountBalance.get(PD);
			Double withdraw = input.nextDouble();
			
			if (accountType.get(i).equals("Current Account")){							/*Performs a query of what type of account the user has, Current Account or Savings Account.*/
				if (accountBalance.get(i) >= withdraw){									/*If the user has enough money in their account, then proceed.*/
					Double total = new Double(balance - withdraw.doubleValue());
					accountBalance.set(PD, total);
					System.out.println("\n" + "You have withdrawn:" + "£ "+df.format(withdraw));
					System.out.println("\n" + "Your new balance:" + "£ "+df.format(accountBalance.get(PD)));
					printTransactions();
				}
				else if (accountBalance.get(PD) < withdraw){							/*If the user does not have enough in their account, then a message will appear*/
					System.out.println("You do not have enough money in your account. You will now be going into overdraft with a limit of £250.");
					System.out.println("If you exceed the limit, you will be automatically charged £100.");
					System.out.println("Would you like to proceed?");
					System.out.println("1) Yes");
					System.out.println("2) No");
					int proceedWithdraw = input.nextInt();
					switch(proceedWithdraw) {
					case 1 : Double total = new Double(balance - withdraw.doubleValue());
							 accountBalance.set(PD, total);
							 System.out.println("\n" + "You have withdrawn:" + "£ "+df.format(withdraw));
							 System.out.println("\n" + "Your new balance:" + "£ "+df.format(accountBalance.get(PD)));
							 break;

					case 2: try {														/*This delays the menu to appear*/
								Thread.sleep(3000);                 					/*3000 milliseconds is three seconds.*/
							} catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							break;						
						}
					}
				}
			if (accountBalance.get(PD) < -250) {
				Double ODLimit = new Double(accountBalance.get(PD) - 100);
				accountBalance.set(PD,  ODLimit);
				System.out.println("You have exceeded your maximum overdraft. You will be charged with a £100 fine.");
				printTransactions();
			}
			try {																		/*This delays the menu to appear*/
				Thread.sleep(3000);                 									/*3000 milliseconds is three seconds.*/
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			}
		}
	
	public void editAccount() {															/*Allows the user to edit any personal details.*/
		
		String editOp;
		for (int i = 0; i < login.size(); i++)
		{
			int PD = customerEmail.indexOf(login.get(i));
			System.out.println("\n" + "What would you like to edit?");
			System.out.println("\n" + "Please select one of the following;");
			System.out.println("a) Name");
			System.out.println("b) Date of Birth");
			System.out.println("c) Address");
			System.out.println("d) Email");
			System.out.println("e) Return" + "\n");
			editOp = input.nextLine();
			if (editOp.equals("a")) {													/*User can edit their name assigned to their account.*/
				File oldFile =  new File (customerName.get(PD) + "'s Transactions.txt");
				System.out.println("\n" + "Please enter a new name:");
				String newName = input.nextLine();
				File newFile = new File (newName + "'s Transactions.txt");
				oldFile.renameTo(newFile);
				customerName.set(PD, newName); 											/*The user input has been assigned to the arrayList.*/
				editAccount();				   											/*Instead of the My Account menu appearing, the user will be referred back to the edit account menu.*/
			}
			else if (editOp.equals("b")){												/*User can edit their date of birth.*/
				System.out.println("\n" + "Please enter your new Date Of Birth:");
				String newDOB = input.nextLine();
				customerBirth.set(PD, newDOB);
				editAccount();
			}
			else if (editOp.equals("c")) {												/*User can edit their address, in three process', street, town, then psot code.*/
				System.out.println("\n" + "Please enter your street:");
				String newStreet = input.nextLine();
				customerStreet.set(PD, newStreet);
				System.out.println("\n" + "Please enter your town:");
				String newTown = input.nextLine();
				customerTown.set(PD, newTown);
				System.out.println("\n" + "Please enter Post Code:");
				String newPostCode = input.nextLine();
				customerPostcode.set(PD, newPostCode);
				editAccount();
			}
			else if (editOp.equals("d")) {
				System.out.println("\n" + "Please ener your email:");
				String newEmail = input.nextLine();
				customerEmail.set(PD, newEmail);
				login.set(i, newEmail);
				editAccount();
			}
			else if (editOp.equals("e")) {
				mam.accountMenu();
			}
			else {
				System.out.println("Invalid input");
			}
			try {																			
			    Thread.sleep(3000);                 						
			} catch(InterruptedException ex) {
				 Thread.currentThread().interrupt();
			}  
		}	
	}
	
	public void deleteAccount() {														/*User can also delete their account by typing Yes or No.*/
		
		System.out.println("\n" + "Are you sure you would like to delete your account?");
		System.out.println("\n" + "Please type 'Yes' or 'No'");
		String deleteOp = input.nextLine();
		if (deleteOp.equals("Yes")){
			for (int i = 0; i < login.size(); i++)
			{
				int PD = customerEmail.indexOf(login.get(i));
				customerName.remove(PD);												/*All details of the user are removed from the arrayLists.*/
				customerStreet.remove(PD);
				customerTown.remove(PD);
				customerPostcode.remove(PD);
				customerEmail.remove(PD);
				customerBirth.remove(PD);
				accountType.remove(PD);
				accountNumber.remove(PD);
				sortCode.remove(PD);
				pinNumber.remove(PD);
				accountBalance.remove(PD);
				System.out.println("\n" + "Thankyou for your time with us." + "\n");
				login.clear();															/*The temporarily arrayList is now also empty, as the user has also logged out.*/
				Main.main(null);														/*Instead of returning to the My Account menu, the user is referred to the main menu.*/
				try {																	/*This delays the menu to appear*/
				    Thread.sleep(3000);                 								/*3000 milliseconds is three seconds.*/
				} catch(InterruptedException ex) {
					 Thread.currentThread().interrupt();
				}  
			}
		}
	}
	
	public void viewStatement() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");						/*To control the format of the date on the statement*/
		Date date = new Date();															/*To import and display the current date for the user*/
		for (int i = 0; i < login.size(); i++)
		{
			int PD = customerEmail.indexOf(login.get(i));
			System.out.println("Below is your statement:");
			System.out.println("\r\n" + dateFormat.format(date) + "\r\n");
			System.out.println("\r\n" + "Name: " + customerName.get(PD).toString());
			System.out.println("\r\n" + "Address: " + customerStreet.get(PD).toString() + ",");
			System.out.println("         " + customerTown.get(PD).toString() + ",");
			System.out.println("         " + customerPostcode.get(PD).toString());
			System.out.println("\r\n" + "Email: " + customerEmail.get(PD).toString() + "\r\n");
			System.out.println("Bank Details:");
			System.out.println("Account Number - Sort Code - Account Type");
			System.out.println(accountNumber.get(PD).toString() + "       - " + sortCode.get(PD).toString() + "  - " + accountType.get(PD).toString() + "\r\n");
			System.out.println("Date           |  Balance" + "\r\n");
			
			/*Below find's the text file that stores the user's transactions and then prints them to the console.*/
			
			int AT = customerEmail.indexOf(login.get(i));
			String fileName = customerName.get(AT) + "'s Transactions.txt";
			String line = null;
		    try {
		        FileReader fileReader = new FileReader(fileName);
		        BufferedReader bufferedReader = new BufferedReader(fileReader);

		         while((line = bufferedReader.readLine()) != null) {
		        	 System.out.println(line);
		         }    
		         bufferedReader.close();            
		    } catch(FileNotFoundException ex) {
		    	System.out.println("Unable to open file '" + fileName + "'");                
		    } catch(IOException ex) {
		    	System.out.println("Error reading file '" + fileName + "'");                   
		    }
			
		    /*Asks if the user would like to save the Statement to a text file.*/
		    System.out.println("\r\n" + "Would you like to export this statement?");
		    System.out.println("1) Yes");
		    System.out.println("2) No");
			int printOp = input.nextInt();
			switch(printOp) {
			case 1: System.out.println("\r\n" + "Your statement is being exported...");
					exportStatement();
					break;
			case 2: System.out.println("Thankyou.");
					break;
			}  
		}
		try {															
		    Thread.sleep(3000);                 											
		} catch(InterruptedException ex) {
			 Thread.currentThread().interrupt();
		}
	}
	
	public void exportStatement() {							
		for (int i = 0; i < login.size(); i++)
		{
			int PD = customerEmail.indexOf(login.get(i));
			try {
				String fileName = customerName.get(PD) + "'s Statement.txt";				/*This names and creates the text file for the statement to be saved to*/
				FileWriter fw = new FileWriter(fileName);									/*This imports the utility to write a file name*/
				Writer output = new BufferedWriter(fw);										/*This utility is imported to write to the file*/
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 				/*To control the format of the date on the statement*/
				Date date = new Date();														/*Imports the date function to display the current date*/
				{
					output.write(dateFormat.format(date));									/*These are the details that will be written to the text file*/
					output.write("\r\n" + "Name: " + customerName.get(i).toString() + "\r\n");
					output.write("\r\n" + "Address: " + customerStreet.get(i).toString() + ",");
					output.write("\r\n" + "         " + customerTown.get(i).toString() + ",");
					output.write("\r\n" + "         " + customerPostcode.get(i).toString());
					output.write("\r\n" + "Email:   " + customerEmail.get(i).toString() + "\r\n");
					output.write("\r\n" + "Bank Details:");
					output.write("\r\n" + "Account Number - Sort Code - Account Type");
					output.write("\r\n" + accountNumber.get(i).toString() + "       - " + sortCode.get(i).toString() + "  - " + accountType.get(i).toString() + "\r\n");
					output.write("\r\n");
					output.write("Date           |  Balance" + "\r\n");
					FileReader fileReader = new FileReader(customerName.get(i) + "'s Transactions.txt");
			        BufferedReader bufferedReader = new BufferedReader(fileReader);
			        String line = null;
			        while((line = bufferedReader.readLine()) != null) {
			        	 output.write("\r\n" + line);
			         }    
			         bufferedReader.close();
				}
				output.close();
			} catch (IOException e) {
				
				System.out.println("There was an error. Statement cannot be exported.");
			}
			try {															
			    Thread.sleep(3000);                 						
			} catch(InterruptedException ex) {
				 Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public void printTransactions() {
		try
		{
			for (int i = 0; i < login.size(); i++)
			{
				int AT = customerEmail.indexOf(login.get(i));
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");						/*To control the format of the date on the statement*/
				Date date = new Date();
				String filename= customerName.get(AT) + "'s Transactions.txt";
			    FileWriter fw = new FileWriter(filename,true); 								/*the true will append the new data*/
			    fw.write(dateFormat.format(date) + "     |  £ "+df.format(accountBalance.get(AT)) + "\r\n");					/*appends the string to the file*/
			    fw.close();
			}
		}
		catch(IOException ioe)
		{
		    System.out.println("Transaction was not recorded.");
		}
	}
	
	
}
