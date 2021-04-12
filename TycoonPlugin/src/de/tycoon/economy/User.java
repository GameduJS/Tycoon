package de.tycoon.economy;

import java.util.UUID;

public class User {

	private UUID uuid;
	
	private UserManager userManager = new UserManager();
	
	public User(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Balance getBalance() {
		return this.userManager.getBalance(getUuid());
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
}
