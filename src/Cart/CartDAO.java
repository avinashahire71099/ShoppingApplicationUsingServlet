package Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Customer.CustomerDAO;
import Product.DBConnection;

public class CartDAO {

	static Scanner sc=new Scanner(System.in);
	static Connection con=DBConnection.getConnection();

	public static void display(int custid) {

		try {
			PreparedStatement p=con.prepareStatement("SELECT PRODUCT.PRODID,PRODUCT.PRODNAME,CART.PRODQ,PRODUCT.PRODPRICE FROM CART INNER JOIN PRODUCT ON CART.PRODID=PRODUCT.PRODID WHERE CID=?");
			p.setInt(1, custid);
			ResultSet rs=p.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			if(rs.next()) {
				System.out.println("\n*******************************************************************\n");
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					System.out.print("\t"+rsmd.getColumnName(i));
				}
				System.out.println("\n*******************************************************************");
				while(rs.next()) {
					System.out.println("\n\t"+rs.getString(1)+"\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t"+rs.getInt(4));
				}
			}
			else {
				System.out.println("You haven't added anything to your cart!! \n\n Would you like to see the range of products we offer? yes/no");
				if(sc.next().equalsIgnoreCase("yes")) {
					CustomerDAO.display();
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addToCart(int custid,int prodid,int qty) {
		try {
			PreparedStatement ps=con.prepareStatement("insert into cart values(?,?,?)");
			ps.setInt(1, custid);
			ps.setInt(2, prodid);
			ps.setInt(3, qty);
			int i=ps.executeUpdate();
			if(i>0) {
				System.out.println("Product added to cart!!");
			}
			PreparedStatement ps2=con.prepareStatement("select prodqty from product where prodid=?");
			ps2.setInt(1, prodid);
			ResultSet rs=ps2.executeQuery();
			int quantity=0;
			if(rs.next()) {
				quantity=rs.getInt(1)-qty;
			}
			PreparedStatement ps3=con.prepareStatement("update product set prodqty=? where prodid=?");
			ps3.setInt(1,quantity);
			ps3.setInt(2,prodid);
			ps3.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int deleteFromCart(int custid,int prodid,int qty) {
		int i=0;
		try {
			PreparedStatement ps=con.prepareStatement("delete from cart where cid=? and prodid=?");
			ps.setInt(1, custid);
			ps.setInt(2, prodid);
			i=ps.executeUpdate();
			if(i>0) {
				System.out.println("Removed from cart");
			}
			PreparedStatement ps2=con.prepareStatement("select prodqty from product where prodid=?");
			ps2.setInt(1, prodid);
			ResultSet rs=ps2.executeQuery();
			int quantity=0;
			if(rs.next()) {
				quantity=rs.getInt(1)+qty;
			}
			PreparedStatement ps3=con.prepareStatement("update product set prodqty=? where prodid=?");
			ps3.setInt(1,quantity);
			ps3.setInt(2,prodid);
			ps3.executeUpdate();	
	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static int generateBill(int custid) {
		int total=0;
		try {
			PreparedStatement ps=con.prepareStatement("select product.prodprice,cart.prodq from cart inner join product on product.prodid=cart.prodid where cid=?");
			ps.setInt(1, custid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				total+=rs.getInt(1)*rs.getInt(2);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public static void displayBill(int custid) {
		System.out.println("\n************************* BILL *************************************");
		try {
			PreparedStatement ps=con.prepareStatement("select cname from customer where cid=?");
			ps.setInt(1, custid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("Customer name : "+rs.getString(1));
				System.out.println("Customer id : "+custid);
			}
		}
		catch(SQLException e) {
			System.out.println("Exception occured!!");
		}
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    System.out.println("Date : " + formattedDate);
		display(custid);
		System.out.println("\n********************************************************************");
		int total=generateBill(custid);
		System.out.println("\n\t TOTAL AMOUNT : "+total);
		System.out.println("\t SGST : 5 %");		
		float tax=(float) (total*0.05);
		System.out.println("\t TAXABLE AMOUNT : "+tax);
		float ftotal=total+tax;
		System.out.println("\t TOTAL PAYABLE : "+ftotal);
		
		System.out.println("\nDo you want to confirm payment ? yes/no");
		if(sc.next().equalsIgnoreCase("yes")) {
			System.out.println("PAYMENT CONFIRMED !! YOU WILL RECEIVE YOUR ORDER WITHIN NEXT 3 WORKING DAYS. \n THANK YOU");
		}
		else {
			System.out.println("PAYMENT NOT CONFIRMED !! \nORDER STATUS : PENDING ");
		}
	}
}
