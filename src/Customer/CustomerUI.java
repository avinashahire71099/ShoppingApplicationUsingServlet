package Customer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class CustomerUI {
	Scanner sc = new Scanner(System.in);
	String str;

	public void UserUI()
	{
		do {
			CustomerDAO custdao = new CustomerDAO();
			System.out.println("Select the option you want");
			System.out.println("1.Create an account\n2.Already have an acount");
			switch(sc.nextInt())
			{
			case 1:
				List<Customer> lst = new LinkedList<Customer>();
				System.out.println("\nEnter the Customer details...");
				System.out.println("\nEnter Customer name, Customer Username and Customer Password");
				lst.add(new Customer(sc.next(), sc.next(), sc.next()));
				custdao.createacc(lst);
				break;
			case 2:
				int i=custdao.userlogin();
				if(i>0) {
					do {
						System.out.println("\nWhat operation would you like to perform ? ");
						System.out.println("1. Display all products \n2. View your cart \n3. Add products \n4. Remove from cart \n5. Generate a bill");
						int ch=sc.nextInt();
						switch(ch) {
						case 1:
							custdao.display();
							break;
						case 2:
							custdao.viewCart(i);
							break;
						case 3:
							custdao.addToCart(i);
							break;
						case 4:
							int j=custdao.deleteFromCart(i);
							break;
						case 5:
							custdao.generatebill(i);
							break;
						}
						System.out.println("Do you want to continue performing operations? yes/no");
						str=sc.next();
					}while(str.equalsIgnoreCase("yes"));
				}
				break;
			default:
				System.out.println("Oooops wrong option");
			}
			System.out.print("Do you want to continue from another customer account? yes/no");
			str=sc.next();
		}while(str.equalsIgnoreCase("yes"));
	}
}
