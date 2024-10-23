package Product;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Exceptions.NegativeValueException;
import Exceptions.ProductDoesNotExist;
import Exceptions.ProductExists;

public class ProductDAO {
	Scanner sc = new Scanner(System.in);

	public int create(int a)
	{
		Connection con = DBConnection.getConnection();
		int done = 0;
		Boolean b;
		do
		{
			System.out.println("Enter the Product Details.");
			System.out.println("Enter the prod id");
			int prodid = sc.nextInt();
			b=validate(prodid);
			if(b) {
				break;
			}
			else {
				System.out.println("Enter the prod name");
				String prodnm = sc.next();
				System.out.println("Enter the prod price");
				double prodprice = sc.nextDouble();
				System.out.println("Enter the prod quantity");
				int prodqty = sc.nextInt();
				b=negativeCheck(prodqty);
				if(b) {
					break;
				}
				else {
					Product prod = new Product(prodid, prodnm, prodprice, prodqty);
					try {
						PreparedStatement pstate = con.prepareStatement("INSERT INTO PRODUCT VALUES(?,?,?,?,?)");
						pstate.setInt(1, prod.getProdid());
						pstate.setString(2, prod.getProdname());
						pstate.setDouble(3, prod.getProdprice());
						pstate.setInt(4, prod.getProdqty());
						pstate.setInt(5, a);
						done = pstate.executeUpdate();
					} 

					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Do you want to add more items");
		}while(sc.next().equalsIgnoreCase("yes"));

		return done;
	}

	public boolean negativeCheck(int prodqty) {
		boolean b=false;
		try {
			if(prodqty<1) {
				b=true;
				throw new NegativeValueException(prodqty);
			}
		}
		catch(NegativeValueException e){
			System.out.println(e);
		}
		return b;
	}

	public boolean validate(int prodid) {
		Boolean b=false;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstate = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODID = ?");
			pstate.setInt(1, prodid);
			ResultSet rs = pstate.executeQuery();
			if(rs.next()) {
				b=true;
				throw new ProductExists(prodid);
			}
		}
		catch(ProductExists e) {
			System.out.println(e);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int Remove()
	{
		Connection con = DBConnection.getConnection();
		System.out.println("Enter the Product ID to delete");
		int prodid = sc.nextInt();
		int done = 0;
		boolean b=validateProd(prodid);
		if(b) {
			done=0;
		}
		else {
			try {
				PreparedStatement pstate = con.prepareStatement("DELETE FROM PRODUCT WHERE PRODID = ?");
				pstate.setInt(1, prodid);
				done = pstate.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public static boolean validateProd(int prodid) {
		Boolean b=false;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstate = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODID = ?");
			pstate.setInt(1, prodid);
			ResultSet rs = pstate.executeQuery();
			if(rs.next()) {
				b=false;
			}
			else {
				b=true;
				throw new ProductDoesNotExist(prodid);
			}
		}
		catch(ProductDoesNotExist e) {
			System.out.println(e);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int Update()
	{
		Connection con = DBConnection.getConnection();
		int done = 0;
		System.out.println("Enter the Product ID to update");
		int prodid = sc.nextInt();
		boolean b=validateProd(prodid);
		if(b) {
			done=0;
		}
		else {
			System.out.println("Enter the Product Price");
			double prodprice = sc.nextDouble();
			try {
				PreparedStatement pstate = con.prepareStatement("UPDATE PRODUCT SET PRODPRICE = ? WHERE PRODID = ?");
				pstate.setDouble(1, prodprice);
				pstate.setInt(2, prodid);
				done = pstate.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	public void Display(int a)
	{
		List<Product> prodlist = new LinkedList<Product>();
		String str = "SELECT * FROM PRODUCT";
		try {
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(str);
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println("\n==========================================================================\n\t");
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				System.out.print(rsmd.getColumnName(i)+"\t");
			}
			System.out.println("\n===========================================================================");
			while(rs.next())
			{
				//prodlist.add(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)));
				System.out.println("\n\t"+rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getInt(4)+"\t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> search()
	{
		Connection con = DBConnection.getConnection();
		List<Product> prodlist = new ArrayList<Product>();
		System.out.println("Enter the Product ID to search");
		int prodid = sc.nextInt();
		try {
			PreparedStatement pstate = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODID = ?");
			pstate.setInt(1, prodid);
			ResultSet rs = pstate.executeQuery();
			if(rs.next()) {
				System.out.println("Product found!!");
				prodlist.add(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)));
			}
			else
				System.out.println("Product Not Found!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prodlist;
	}
}
