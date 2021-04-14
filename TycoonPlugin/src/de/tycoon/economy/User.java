package de.tycoon.economy;

import java.util.UUID;

public abstract class User {

	private UUID uuid;
	
	public User(UUID uuid) {
		this.uuid = uuid;
		this.balance = 0;
		this.tokens = 0;
	}
	
	protected double balance;
	protected int tokens;
	
	
	public abstract double getBalance();
	public abstract void setBalance(double balance);
	public abstract void addMoney(double money);
	public abstract void removeMoney(double money);
	
	public abstract int getTokens();
	public abstract void setTokens(int tokens);
	public abstract void addToken(int tokens);
	public abstract void removeToken(int tokens);
	
	public UUID getUUID() {
		return this.uuid;
	}
	
}
