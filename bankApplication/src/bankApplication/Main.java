package bankApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
		
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		int option;
		accountSettings as = new accountSettings();
		createAccount ca = new createAccount();
		System.out.println("Liam's Bank Application");
		
		try {
			do {
				System.out.println("\r\n1) Create Account");
				System.out.println("2) My Account");
				System.out.println("3) Exit Application");
				option = input.nextInt();
				switch (option) {
				case 1: ca.create();
						break;
				case 2: as.accountLogin();
						break;
				}
			}while (option != 3);
			System.out.println("See you soon!");
			input.close();
		} catch (InputMismatchException e) {
	        System.out.print("Please enter a valid option from the menu." + "\n");
	        Main.main(null);
	    }
		
	}

}
