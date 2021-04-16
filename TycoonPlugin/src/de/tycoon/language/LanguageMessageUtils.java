package de.tycoon.language;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tycoon.config.Config;

public class LanguageMessageUtils {

	private Config config;
	private String prefix;

	public LanguageMessageUtils(String prefix) {
		this.config = new Config(Config.MESSAGES_PATH, "lang.yml");
		this.prefix = prefix;
	}

	public String getPlayerLocale(Player player) {
		return player.getLocale().substring(0, 2);
	}

	public boolean languageExists(Player player, Messages configEnum) {
		return config.contains(configEnum.getPath() + "." + getPlayerLocale(player));
	}

	public String getLanguage(Messages configEnum, Player player) {
		if (languageExists(player, configEnum)) {
			return getPlayerLocale(player);
		}
		return "en";
	}

	public String get(Player player, Messages configEnum) {

		String path = configEnum.getPath() + "." + getPlayerLocale(player);

		if (languageExists(player, configEnum)) {

			return translate(this.config.getString(path).replace("%prefix%", Messages.PREFIX.getMessage()));

		}

		return translate(this.config.getString(configEnum.getPath() + ".en").replace("%prefix%", Messages.PREFIX.getMessage()));

	}

	public String translate(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
}
