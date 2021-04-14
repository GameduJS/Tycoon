package de.tycoon.economy;

import java.util.UUID;

public class IUser extends User{

	public IUser(UUID uuid) {
		super(uuid);
	}

	@Override
	public double getBalance() {
		return this.balance;
	}

	@Override
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public void addMoney(double money) {
		this.balance+=money;
	}

	@Override
	public void removeMoney(double money) {
		this.balance-=money;
	}

	@Override
	public int getTokens() {
		return this.tokens;
	}

	@Override
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	@Override
	public void addToken(int tokens) {
		this.tokens+=tokens;
	}

	@Override
	public void removeToken(int tokens) {
		this.tokens-=tokens;
	}

}
