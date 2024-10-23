package Exceptions;

public class ProductDoesNotExist extends Exception{

	int prodid;

	public ProductDoesNotExist(int prodid) {
		super();
		this.prodid = prodid;
	}

	@Override
	public String toString() {
		return "Product with id " + prodid + " does not exist !!";
	}
	
	
}
