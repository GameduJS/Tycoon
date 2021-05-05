package de.tycoon.discord.commands.basecommand;

import java.util.concurrent.ConcurrentHashMap;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordCommandManager {

	public ConcurrentHashMap<String, DiscordCommand> commands;
	public String thisCmd;
	public DiscordCommand thisServerCmd;
	
	public DiscordCommandManager() {
		this.commands = new ConcurrentHashMap<String, DiscordCommand>();
	}
	
	public boolean perform(String command, Member member, TextChannel channel, Message message, Guild guild) {
		
		DiscordCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.onCommand(guild, member, message, channel);
			return true;
		}
		return false;
	}
	
	public DiscordCommandManager getCommand(String cmd) {
		this.thisCmd = cmd;
		return this;
	}
	
	public void setExecuter(DiscordCommand serverCommand) {
		this.thisServerCmd = serverCommand;
		this.commands.put(thisCmd, thisServerCmd);
	}
	
}

