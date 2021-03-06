package de.tycoon.discord.commands.basecommand;

import de.tycoon.TycoonPlugin;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(!e.isFromType(ChannelType.TEXT)) return;
		
		TextChannel channel = e.getTextChannel();
		String message = e.getMessage().getContentDisplay();
			
			/**
			 * Command
			 */
			if (message.startsWith("!")) {
				String[] args = message.substring(1).split(" ");
				if (args.length > 0) {
					
					String cmd;
					StringBuilder sb = new StringBuilder("!");
					
						for (int i = 0; i < args.length; i++) {
							sb.append(args[i] + " ");
						}
					
					cmd = sb.toString();
					sb.setLength(0);
					
					if (!TycoonPlugin.get().getDiscordCommandManager().perform(args[0], e.getMember(), channel, e.getMessage(), e.getGuild())) {
						return;
					} else {
						System.out.println(e.getMember().getEffectiveName() + " entered command: " + cmd);
					}
				}
			}
	}
}
