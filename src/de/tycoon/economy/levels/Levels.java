package de.tycoon.economy.levels;

public class Levels {

	private int level;
	private double xpHave;
	private double xpNeeded;
	
	public Levels(int level, double xpHave, double xpNeeded) {
		this.level = level;
		this.xpHave = xpHave;
		this.xpNeeded = xpNeeded;
	}
	
	public int getLevel() {
		return level;
	}
	public double getCurrentLevelXP() {
		return xpHave;
	}
	public double getXPNeeded() {
		return xpNeeded;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public void setCurrentXP(double xpHave) {
		this.xpHave = xpHave;
	}

	public void setXpNeeded(double xpNeeded) {
		this.xpNeeded = xpNeeded;
	}
	
	public void addLevel(int level) {
		this.level+=level;
	}
	
}
