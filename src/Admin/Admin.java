package Admin;

public class Admin {
	private int adminid;
	private String adminname;
	private String username;
	private String password;
	public Admin(int adminid, String adminname, String username, String password) {
		super(); 
		this.adminid = adminid;
		this.adminname = adminname;
		this.username = username;
		this.password = password;
	}
	public int getAdminid() {
		return adminid;
	}
	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}
	public Admin(String adminname, String username, String password) {
		super();
		this.adminname = adminname;
		this.username = username;
		this.password = password;
	}
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
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
