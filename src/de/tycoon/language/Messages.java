package de.tycoon.language;

public enum Messages {

	/** 
	 * %player%
	 * %prefix%
	 * %money%
	 * %current_gens%
	 * %max_gens%
	**/

	/** Prefix **/
	PREFIX("&e&lTYCOON"),
	
	/** Generator  **/
	GENERATOR_NOT_ENOUGH_MONEY("%prefix% &7You need &a%money% &7more to upgrade!"),
	GENERATOR_UPGRADE("%prefix% &7You have upgraded your generator!"),
	GENERATOR_REACHED_LIMIT("%prefix% &7You cannot place anymore gens!"),
	GENERATOR_ADD("%prefix% &7You placed a generator &b(%current_gens% / %max_gens%)"),
	GENERATOR_REMOVE("%prefix% &7You have removed a generator &b(%current_gens% / %max_gens%)"),
	
	
	/** Economy **/
	ECONOMY_SELL("%prefix% &7You sold all items for &a%money%€&7!"),
	ECONOMY_ADD_MONEY("%prefix% &7Added &a%money%€ &7to %player%")
	
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
