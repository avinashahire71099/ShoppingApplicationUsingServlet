package main;

import java.util.Scanner;

import Admin.AdminUI;
import Customer.CustomerUI;

public class ShoppingMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AdminUI adui = new AdminUI();
		CustomerUI custui = new CustomerUI();
		System.out.println("______Welcome to Shopping Application_____");
		do
		{
			System.out.println("Please select the option for login");
			System.out.println("1.Admin\n2.Customer");
			switch(sc.nextInt())
			{
			case 1:
				adui.UserUI();
				break;
			case 2:
				custui.UserUI();
				break;
			default:

				System.err.print("Ooops..! Wrong option");					
			}
		}while(sc.next().equalsIgnoreCase("yes"));
	}
}





