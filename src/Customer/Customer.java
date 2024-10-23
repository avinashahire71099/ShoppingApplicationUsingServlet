package Customer;

public class Customer {
	private int custid;
	private String custname;
	private String username;
	private String password;
	public Customer(int custid, String custname, String username, String password) {
		this.custid = custid;
		this.custname = custname;
		this.username = username;
		this.password = password;
	}
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public Customer(String custname, String username, String password) {
		super();
		this.custname = custname;
		this.username = username;
		this.password = password;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
