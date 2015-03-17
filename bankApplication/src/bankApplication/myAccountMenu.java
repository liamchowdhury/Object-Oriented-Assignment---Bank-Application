package bankApplication;

import java.util.Scanner;

public class myAccountMenu {
	
	public void accountMenu() {
		
		Scanner input = new Scanner (System.in);
		
		accountSettings as = new accountSettings();
		
		int option;
		
		do {
			System.out.println("\r\n1) View Account Details");
			System.out.println("2) View Balance");
			System.out.println("3) Deposit");
			System.out.println("4) Withdraw");
			System.out.println("5) View Statement");
			System.out.println("6) Edit Account");
			System.out.println("7) Delete Account");
			System.out.println("8) Log Out");
			option = input.nextInt();
			switch(option){
			case 1: as.viewDetails();
					break;
			case 2: as.viewBalance();
					break;
			case 3: as.depositMoney();
					break;
			case 4: as.withdrawMoney();
					break;
			case 5: as.viewStatement();
					break;
			case 6: as.editAccount();
					break;
			case 7: as.deleteAccount();
					break;
			}
		}while (option != 8);
	}
}	
