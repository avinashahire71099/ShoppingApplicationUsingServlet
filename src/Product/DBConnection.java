package Product;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection()
	{


		Connection con=null;
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","aditi0408");
			//System.out.println("Connection to DB\n"+con);

		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return con;
	}
}
