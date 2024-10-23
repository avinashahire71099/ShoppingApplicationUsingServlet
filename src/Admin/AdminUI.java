package Admin;


import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class AdminUI {
	Scanner sc = new Scanner(System.in);
	public void UserUI()
	{
		AdminDAO admindao = new AdminDAO();
		do
		{
			System.out.println("Select the option you want");
			System.out.println("1.Create an account\n2.Already have an acount");
			switch(sc.nextInt())
			{
			case 1:
				List<Admin> lst = new LinkedList<Admin>();
				System.out.println("\nEnter the Admin details...");
				System.out.println("Enter Admin name, Admin Username and Admin Password");
				lst.add(new Admin(sc.next(), sc.next(), sc.next()));
				admindao.createacc(lst);
				break;
			case 2:
				admindao.userlogin();
				break;
			default:
				System.out.println("Oooops wrong option");
			}
			System.out.println("Do you want to continue as Admin? yes/no");
		}while(sc.next().equalsIgnoreCase("yes"));
	}
}
