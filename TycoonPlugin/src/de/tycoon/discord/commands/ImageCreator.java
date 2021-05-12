package de.tycoon.discord.commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import org.bukkit.OfflinePlayer;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.discord.commands.basecommand.DiscordCommand;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.generators.GeneratorManager;
import de.tycoon.util.FileUtils;
import de.tycoon.util.BukkitUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ImageCreator implements DiscordCommand {

	private File file;

	private TycoonPlugin plugin;
	private GeneratorManager generatorManager;
	private UserManager userManager;

	public ImageCreator() {
		this.plugin = TycoonPlugin.get();
		this.generatorManager = this.plugin.getGeneratorManager();
		this.userManager = this.plugin.getUserManager();

		this.file = new File(Config.DISCORD_PATH + "/PictureChache");
		FileUtils.createFolderIfNotExists(file);
	}

	public File create(OfflinePlayer player) throws MalformedURLException, IOException {

		int width = 1080;
		int height = 540;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();

		// Background
		g2d.drawImage(ImageIO.read(new File(this.file, "BG.png")), 0, 0, width, height, null);

		// Draw Strings
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Minecraft", 0, 48));

		g2d.drawString(player.getName(), 350, 110);

		User user = this.userManager.loadUser(player.getUniqueId());

		String placedGens = "Placed Generators: " + this.generatorManager.getQuantityofGenerators(player.getUniqueId())
				+ "/" + this.generatorManager.getMaxGens();
		String money = "Money: " + new DecimalFormat("#,###.#").format(user.getBalance()) + "€";
		String tokens = "Tokens: " + new DecimalFormat("#,###.#").format(user.getTokens());
		String levels = "Level: " + this.userManager.loadUser(player.getUniqueId()).getUserLevel().getLevel();

		String credits = "Skins by Crafatar";

		g2d.setColor(Color.yellow);
		g2d.drawString(placedGens, 350, 230);
		g2d.drawString(money, 350, 230 + g2d.getFont().getSize() + 10);
		g2d.drawString(tokens, 350, 230 + g2d.getFont().getSize() * 2 + 20);
		g2d.drawString(levels, 350, 230 + (g2d.getFont().getSize() + 10) * 3);

		g2d.setFont(new Font("Minecraft", 0, 14));
		g2d.setColor(Color.WHITE);
		g2d.drawString(credits, width - (credits.length() * g2d.getFont().getSize() / 2) - 10, height - 20);

		// Output -> Calculate on load / add / remove
		// e.g.: 40$/h

		// Skin
		String uuid = player.getUniqueId().toString();

		g2d.drawImage(ImageIO.read(new URL("https://crafatar.com/renders/body/" + uuid + "?scale=10")), 100, 50, null);

		g2d.dispose();

		// Save as PNG
		File file = new File(this.file, uuid + ".png");
		ImageIO.write(bufferedImage, "png", file);

		return file;

	}

	@Override
	public boolean onCommand(Guild guild, Member member, Message message, TextChannel textChannel) {

		String[] args = message.getContentDisplay().split(" ");
		message.delete().queue();

		if (args.length < 2) {
			textChannel.sendMessage(new EmbedBuilder()
					.setDescription("Hey" + member.getAsMention() + "\nYou have to provide an player name!")
					.setColor(new Color(252, 255, 87)).build()).queue();
			return false;
		}

		OfflinePlayer player = BukkitUtils.getOfflinePlayer(args[1]);
		if (player == null) {
			textChannel.sendMessage(new EmbedBuilder()
					.setDescription("Hey" + member.getAsMention() + "\nThis player never played before!")
					.setColor(new Color(255, 26, 26)).build()).queue();
			return false;
		}

		try {
			File picture = create(player);
			textChannel.sendFile(picture).queue();
			org.apache.cassandra.io.util.FileUtils.delete(new File[] { picture });
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
