package Exceptions;

public class NegativeValueException extends Exception{

	int prodqty;

	public NegativeValueException(int prodqty) {
		super();
		this.prodqty = prodqty;
	}

	@Override
	public String toString() {
		return "Product Quantity cannot be 0 or negative !!";
	}
	
	
}
