package Bill;

public class Bill {
    private int id;
    private String uname;
    private String date;
    private int total;
    private int interest;
    private float ftotal;

    public Bill(int id, String uname, String date, int total, int interest, float ftotal) {
		super();
		this.id = id;
		this.uname = uname;
		this.date = date;
		this.total = total;
		this.interest = interest;
		this.ftotal = ftotal;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public float getFtotal() {
		return ftotal;
	}

	public void setFtotal(float ftotal) {
		this.ftotal = ftotal;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
