package de.tycoon.discord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {

	private String token = "";
	private JDA jda;
	private List<ListenerAdapter> events;
	
	private TycoonPlugin plugin;
	private ConfigManager configManager;
	private Config discordConfig;
	
	public DiscordBot() {
		
		this.events = new ArrayList<>();
		
		this.plugin = TycoonPlugin.get();
		this.configManager = this.plugin.getConfigManager();
		this.discordConfig = this.configManager.getDiscordConfig();
		
		this.setDefaults();
	}
	
	
	private void setDefaults() {
		if(!this.discordConfig.contains("Bot.Token"))
		this.discordConfig.set("Bot.Token", "<Your Token>");
		
		if(!this.discordConfig.contains("Bot.Activity.ActivityType"))
		this.discordConfig.set("Bot.Activity.ActivityType", Activity.ActivityType.DEFAULT.toString());
		
		if(!this.discordConfig.contains("Bot.Activity.Value"))
		this.discordConfig.set("Bot.Activity.Value", " on localhost");
		
		if(!this.discordConfig.contains("Bot.OnlineStatus"))
		this.discordConfig.set("Bot.OnlineStatus", OnlineStatus.ONLINE.toString());
	}
	
	
	public void start() {
		
		if(!this.configManager.getSettingConfig().getBoolean("Discord.Bot-Enabled")) {
			Bukkit.getLogger().info("Bot is disabled by config!");
			return;
		}
		
		this.token = this.discordConfig.getString("Bot.Token");
		
		if(this.token == null) {
			Bukkit.getLogger().warning("Shutdown Discordbot - Null token");
			Bukkit.getLogger().warning("Shutdown Discordbot - Null token");
			Bukkit.getLogger().warning("Shutdown Discordbot - Null token");
		}
			
		JDABuilder builder = JDABuilder.createDefault(token);
		builder.setActivity(Activity.of(Activity.ActivityType.valueOf(this.discordConfig.getString("Bot.Activity.ActivityType")), this.discordConfig.getString("Bot.Activity.Value")));
		builder.setStatus(OnlineStatus.valueOf(this.discordConfig.getString("Bot.OnlineStatus")));
		
		/** Enable Intents **/
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		
		/** Register Events **/
		this.events.stream().filter(Objects::nonNull).forEach(builder::addEventListeners);
		Bukkit.getLogger().info("[DISCORD] Initialize Events!");
		
		/** Build the bot **/
		try {
			this.jda = builder.build();
			Bukkit.getLogger().info("[DISCORD] Building bot...");
		} catch (LoginException e) {
			Bukkit.getLogger().warning("Error this is not a valid Discordbot Token!");
			Bukkit.getLogger().warning("Error this is not a valid Discordbot Token!");
			Bukkit.getLogger().warning("Error this is not a valid Discordbot Token!");
		}
	}
	
	public void stop() {
		if(this.jda == null)
			return;
		
		Bukkit.getLogger().info("[DISCORD] Shutdown bot...");
		
		try {
			
			this.jda.getPresence().setStatus(OnlineStatus.OFFLINE);
			this.jda.shutdown();
			this.jda = null;
			
		}catch (Exception e) {
			return;
		}
		
	}
	
	
	public void addEvent(ListenerAdapter adapter) {
		this.events.add(adapter);
	}
}
