package de.tycoon.discord.commands.basecommand;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface DiscordCommand {

	/**
	 * Method calls when command is executed
	 * @param guild
	 * @param member
	 * @param message
	 * @param textChannel
	 * @return
	 */
	public boolean onCommand(Guild guild, Member member, Message message, TextChannel textChannel);
	
}
