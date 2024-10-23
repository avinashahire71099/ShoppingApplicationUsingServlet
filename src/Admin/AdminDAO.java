package Admin;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Exceptions.UserExists;
import Product.DBConnection;
import Product.Product;
import Product.ProductDAO;

public class AdminDAO {
	Scanner sc = new Scanner(System.in);

	public void createacc(List<Admin> lst)
	{
		Connection con=DBConnection.getConnection();
		int done = 0;
		Boolean b;
		for(Admin ad:lst)
		{
			b=validate(ad.getUsername());
			if(b) {
				break;
			}
			else {
				try {
					PreparedStatement pstate = con.prepareStatement("INSERT INTO admin VALUES(ADMINSEQ.NEXTVAL,?,?,?)");
					//pstate.setInt(1, ad.getAdminid());
					pstate.setString(1, ad.getAdminname());
					pstate.setString(2, ad.getUsername());
					pstate.setString(3, ad.getPassword());
					done=pstate.executeUpdate();
					if(done>0)
						System.out.println("Data added in the DB");
				} catch (SQLException e) {
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
			pstate = con.prepareStatement("SELECT * FROM admin WHERE ADMINUSER = ?");
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
	
	public void userlogin()
	{
		Connection con=DBConnection.getConnection();
		System.out.println("Enter username");
		String struser = sc.next();
		System.out.println("Please Enter the password");
		String strpass = sc.next();
		ResultSet rs=null;
		try {
			PreparedStatement pstate = con.prepareStatement("SELECT * FROM admin WHERE ADMINUSER = ? AND ADMINPASS=?");
			pstate.setString(1, struser);
			pstate.setString(2,strpass);
			ResultSet rs1 = pstate.executeQuery();
			if(rs1.next())
			{
				System.out.println("\nWelcome back "+rs1.getString(2));
				int a=rs1.getInt(1);
				System.out.println(a);
				Operation(a);
			}
			else
			{
				System.out.println("Invalid UserID or password");
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
	}

	public void Operation(int a)
	{
		Connection con=DBConnection.getConnection();
		ProductDAO proddao=new ProductDAO();
		int op;
		do
		{
			System.out.println("\n\nEnter the Operation you want");
			System.out.println("1.Enter product in list\n2.Display product from list\n3.Delete product from list\n4.Update Data in list\n5.Search product");
			switch(sc.nextInt())
			{
			case 1:
				op = proddao.create(a);
				if(op>0)
					System.out.println("Data Added to Database");
				break;
			case 2:
				proddao.Display(a);
				break;
			case 3:
				System.out.println("Remove");
				op= proddao.Remove();
				if(op>0)
					System.out.println("Data Removed from database");
				break;
			case 4:
				op = proddao.Update();
				if(op>0)
					System.out.println("Data Updated in the list");
				break;
			case 5:
				List<Product> c=proddao.search();
				for(Product i:c)
				{
					System.out.println(i.getProdid()+i.getProdname()+i.getProdprice()+i.getProdqty());
				}

			}
			System.out.println("Do you want to continue to ADMIN LIST");
		}while(sc.next().equalsIgnoreCase("yes"));
	}
}
