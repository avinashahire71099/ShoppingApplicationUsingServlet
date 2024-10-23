package Customer;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Cart.CartDAO;
import Exceptions.UserExists;
import Product.DBConnection;
import Product.ProductDAO;


public class CustomerDAO {
	Scanner sc = new Scanner(System.in);

	public void createacc(List<Customer> lst)
	{
		Connection con = DBConnection.getConnection();
		int done = 0;
		Boolean b;
		for(Customer ad:lst)
		{
			b=validate(ad.getUsername());
			if(b) {
				break;
			}
			else {
				try {
					PreparedStatement pstate = con.prepareStatement("INSERT INTO Customer VALUES(CUSTOMERSEQ.NEXTVAL,?,?,?)");
					//pstate.setInt(1, ad.getCustid());
					pstate.setString(1, ad.getCustname());
					pstate.setString(2, ad.getUsername());
					pstate.setString(3, ad.getPassword());
					done=pstate.executeUpdate();
					if(done>0)
						System.out.println("Data added in the DB");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean validate(String username) {
		Connection con=DBConnection.getConnection();
		Boolean b=false;
		PreparedStatement pstate;
		try {
			pstate = con.prepareStatement("SELECT * FROM customer WHERE cusername = ?");
			pstate.setString(1, username);
			ResultSet rs=pstate.executeQuery();
			if(rs.next()) {
				b=true;
				throw new UserExists(username);
			}
		}
		catch(UserExists e) {
			System.out.println(e);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int userlogin()
	{
		int b=0;

		for(int i=1;i<3;i++) {
			Connection con = DBConnection.getConnection();
			System.out.println("Enter username");
			String struser = sc.next();

			System.out.println("Please Enter the password");
			String strpass = sc.next();

			ResultSet rs=null;
			try {
				PreparedStatement pstate = con.prepareStatement("SELECT cid,cusername,cpassword FROM customer WHERE cusername = ? and cpassword=?");
				pstate.setString(1, struser);
				pstate.setString(2, strpass);
				ResultSet rs1 = pstate.executeQuery();
				if(rs1.next())
				{
					System.out.println("\n Welcome !! We are glad to have you here ......");
					b=rs1.getInt(1);
					break;
				}
				else
				{
					System.out.println("Invalid UserID or password");
					b=0;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Do to want to give it another try? yes/no");
			String str=sc.next();
			if(str.equalsIgnoreCase("yes"))
				continue;
			else {
				System.out.println("Hard Luck !!");
				break;
			}
		}
		return b;
	}

	public static void display()
	{
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement("select prodid,prodname,prodqty from Product");
			//String str1="select prodid,prodname,prodqty from Product";
			//Statement p=con.createStatement();
			ResultSet result=ps.executeQuery();
			ResultSetMetaData a=result.getMetaData();
			System.out.println("\t");
			for(int i=1;i<=a.getColumnCount();i++)
			{
				System.out.print(a.getColumnName(i)+"\t");

			}
			System.out.println("\n--------------------------------------------------");
			while(result.next())
			{
				System.out.println("\t"+result.getInt(1)+"\t"+result.getString(2)+"\t\t"+result.getInt(3));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void viewCart(int custid) {
		CartDAO c=new CartDAO();
		c.display(custid);
	}

	public void addToCart(int custid) {
		do {
			display();
			System.out.println("\n Enter the product id of the product you want to enter into your cart : ");
			int prodid=sc.nextInt();
			boolean b=ProductDAO.validateProd(prodid);
			if(b) {
				break;
			}
			else {
				System.out.println("Enter the number of items you want : ");
				int qty=sc.nextInt();
				CartDAO.addToCart(custid,prodid,qty);
				System.out.println("Do you want to add more items ? yes/no");
			}
		}while(sc.next().equalsIgnoreCase("yes"));
	}

	public int deleteFromCart(int custid) {
		CartDAO.display(custid);
		int i=0;
		boolean b=checkCart(custid);
		if(b) {
			i=0;
		}
		else {
			System.out.println("Enter the product id of the product that you want to remove from your cart : ");
			int prodid=sc.nextInt();
			boolean b2=ProductDAO.validateProd(prodid);
			if(b2) {
				i=0;
			}
			else {
				System.out.println("Enter the number of items that you want to delete : ");
				int quantity=sc.nextInt();
				i=CartDAO.deleteFromCart(custid, prodid,quantity);
			}
		}
		return i;
	}

	public boolean checkCart(int custid) {
		Connection con = DBConnection.getConnection();
		boolean b=true;
		try {
			PreparedStatement ps=con.prepareStatement("select * from cart where cid=?");
			ps.setInt(1,custid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				b=false;
			}
			else {
				System.out.println("No items are present in you cart !!");
				b=true;
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return b;
	}

	public void generatebill(int custid) {
		boolean b=checkCart(custid);
		if(b) {
			System.out.println("....");
		}
		else {
			CartDAO.displayBill(custid);
		}
		
	}
}
