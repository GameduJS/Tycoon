package de.tycoon.economy;

public class Balance {

	private User user;
	private int bal;
	
	public Balance(User user, int bal) {
		this.user = user;
		this.bal = bal;
	}

	public int getBal() {
		return bal;
	}

	public void setBal(int bal) {
		this.bal = bal;
	}

	public User getUser() {
		return user;
	}
	
	
	
}
