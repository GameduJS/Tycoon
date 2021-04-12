package de.tycoon.discord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.security.auth.login.LoginException;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
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
	
	
	public void start() throws LoginException {
		this.token = this.discordConfig.getString("Bot.Token");
		
		if(this.token == null)
			throw new LoginException("Invalid token for " + this.token);
		
		JDABuilder builder = JDABuilder.createDefault(token);
		builder.setActivity(Activity.of(Activity.ActivityType.valueOf((String) this.discordConfig.getString("Bot.Activity.ActivityType")), this.discordConfig.getString("Bot.Activity.Value")));
		builder.setStatus(OnlineStatus.valueOf(this.discordConfig.getString("Bot.OnlineStatus")));
		
		/** Enable Intents **/
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		
		/** Register Events **/
		this.events.stream().filter(Objects::nonNull).forEach(builder::addEventListeners);
		
		/** Build the bot **/
		this.jda = builder.build();
	}
	
	
	public void addEvent(ListenerAdapter adapter) {
		this.events.add(adapter);
	}
	
	
	
}
