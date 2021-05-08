package de.tycoon.economy;

import java.util.UUID;

import de.tycoon.economy.levels.LevelManager;
import de.tycoon.economy.levels.Levels;
import de.tycoon.util.BukkitUtils;

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
	public void addTokens(int tokens) {
		this.tokens+=tokens;
	}

	@Override
	public void removeToken(int tokens) {
		this.tokens-=tokens;
	}

	@Override
	public Levels getUserLevel() {
		return this.levels;
	}
	
	@Override
	public void setUserLevel(Levels levels) {
		this.levels = levels;	
	}
	
	@Override
	public void addXP(double xp) {
		this.levels.setCurrentXP(this.levels.getCurrentLevelXP()+xp);
		this.nextLevel();
	}

	@Override
	public void nextLevel() {
		
		double currentXP = this.levels.getCurrentLevelXP();
		int level = this.levels.getLevel();
		double needXP = this.levels.getXPNeeded();
		
		if(calcLevel(level, currentXP, needXP, level)) {
			BukkitUtils.fromUUID(getUUID()).sendMessage("You leveld up!");
		}
		
	}
	
	public boolean calcLevel(int level, double currentXP, double xpNeeded, int tmpLevel) {
		if(currentXP >= xpNeeded) {
			currentXP -=xpNeeded;
			level++;
			calcLevel(level, currentXP, xpNeeded, tmpLevel);
		} else {
				this.levels.setLevel(level);
				this.levels.setXpNeeded(LevelManager.BASE_XP * Math.pow(LevelManager.XP_MULTIPLIER, level));
				this.levels.setCurrentXP(currentXP);
		}
		
		return level != tmpLevel;
		
	}
	
	
}
