package bankApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class createAccount extends accountSettings{
	
	Scanner input = new Scanner(System.in);
	DecimalFormat df = new DecimalFormat("##.00");
	accountSettings as = new accountSettings();
	
	public void create(){
		System.out.println("\n" + "Thankyou for creating an account with us.");
		System.out.println("Would you like to create a;");
		System.out.println("a) Current Account");
		System.out.println("b) Savings Account");
		System.out.println("Please enter 'a' or 'b'");
		
		String createOption = input.nextLine();
		if (createOption.equals("a") || createOption.equals("b")){
			System.out.println("Please enter your full name:");
			String newName = input.nextLine();
			customerName.add(newName);
			
			System.out.println("Please enter your street:");
			String newStreet = input.nextLine();
			customerStreet.add(newStreet);
			
			System.out.println("Please enter your town:");
			String newTown = input.nextLine();
			customerTown.add(newTown);
			
			System.out.println("Please enter your post code:");
			String newPostcode = input.nextLine();
			customerPostcode.add(newPostcode);
			
			System.out.println("Please enter your email:");
			String newEmail = input.nextLine();
			customerEmail.add(newEmail);
			
			System.out.println("Please enter your date of birth:");
			String newBirth = input.nextLine();
			customerBirth.add(newBirth);
			
			if (createOption.equals("a")){
				accountType.add("Current Account");
			}
			else if (createOption.equals("b")){
				accountType.add("Savings Account");
			}
			int max = 99999999;
			int min = 10000000;
			int newAccountNumber = (int) Math.round(Math.random() * (max - min + 1) + min);
			accountNumber.add(newAccountNumber);
			
			sortCode.add("11-03-92");
			
			System.out.println("Please enter a four digit pin number:");
			int newPinNumber = input.nextInt();
			pinNumber.add(newPinNumber);
			
			System.out.println("How much would you like to deposit?");
			Double deposit = input.nextDouble();
			accountBalance.add(deposit);
			
			if (accountNumber.contains(newAccountNumber)){
				int PD = accountNumber.indexOf(newAccountNumber);
				System.out.println(" ");
				System.out.println("Your details:");
				System.out.println(customerName.get(PD)+" - "+customerTown.get(PD)+" - "+accountNumber.get(PD)+" - "+sortCode.get(PD)+" - £ "+df.format(accountBalance.get(PD)));
				System.out.println(" ");
				
				try
				{
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");						/*To control the format of the date on the statement*/
					Date date = new Date();
					String filename= customerName.get(PD) + "'s Transactions.txt";
					FileWriter fw = new FileWriter(filename,true); 								/*the true will append the new data*/
					fw.write(dateFormat.format(date) + "     |  £ "+df.format(accountBalance.get(PD)) + "\r\n");					/*appends the string to the file*/
					fw.close();
				}
				catch(IOException ioe)
				{
				    System.out.println("Transaction was not recorded.");
				}
	        } else {
	               System.out.println("Customer not found");
	        }
		}
		else {
			System.out.println("Invalid option." + "\n");
		}
	}
}
