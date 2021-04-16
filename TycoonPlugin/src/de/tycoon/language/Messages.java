package de.tycoon.language;

public enum Messages {

	/** 
	 * %prefix%
	 * %money%
	 * %more_money%
	**/

	/** Prefix **/
	PREFIX("&e&lTYCOON"),
	
	/** Generator  **/
	GENERATOR_UPGRADE("%prefix% &7You have upgraded your generator!"),
	GENERATOR_NOT_ENOUGH_MONEY("%prefix% &7You need &a%more_money% &7more to upgrade!"),
	GENERATOR_REMOVE("%prefix% &7You have removed a generator &b(x / y)"),
	
	/** Economy **/
	ECONOMY_SELL("%prefix% &7You sold all items for &a%money%€&7!")
	
	/** Economy  **/
	
	
	
	
	;
	
	private String message;
	
	private Messages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getPath() {
		return this.name().toLowerCase();
	}
	
}
