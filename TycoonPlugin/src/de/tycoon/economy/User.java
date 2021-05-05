package de.tycoon.economy;

import java.util.UUID;

import de.tycoon.economy.levels.LevelManager;
import de.tycoon.economy.levels.Levels;

public abstract class User {

	private UUID uuid;
	
	public User(UUID uuid) {
		this.uuid = uuid;
		this.balance = 0;
		this.tokens = 0;
		this.levels = new Levels(1, 0, LevelManager.BASE_XP);
	}
	
	protected double balance;
	protected int tokens;
	protected Levels levels;
	
	
	public abstract double getBalance();
	public abstract void setBalance(double balance);
	public abstract void addMoney(double money);
	public abstract void removeMoney(double money);
	
	public abstract int getTokens();
	public abstract void setTokens(int tokens);
	public abstract void addTokens(int tokens);
	public abstract void removeToken(int tokens);
	
	public abstract Levels getUserLevel();
	public abstract void setUserLevel(Levels levels);
	public abstract void addXP(double xp);
	
	public abstract void nextLevel();
	
	public UUID getUUID() {
		return this.uuid;
	}
	
}
