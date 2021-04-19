package de.tycoon.discord.commands.basecommand;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface DiscordCommand {

	public boolean onCommand(Guild guild, Member member, Message message, TextChannel textChannel);
	
}
