package Exceptions;

public class ProductExists extends Exception{

	int prodid;

	public ProductExists(int prodid) {
		super();
		this.prodid = prodid;
	}

	@Override
	public String toString() {
		return "Product with product id " + prodid + " already exists !! \nYou cannot create a product with the same id !!";
	}
	
	
}
