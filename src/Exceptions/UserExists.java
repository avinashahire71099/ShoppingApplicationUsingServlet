package Exceptions;

public class UserExists extends Exception{

	private String username;
	public UserExists(String username) {
		this.username=username;
	}
	@Override
	public String toString() {
		return "User with username " + username + " already exists !!";
	}
	
}
