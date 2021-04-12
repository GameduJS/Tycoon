package de.tycoon.economy;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.utils.FileUtils;

public class UserManager {

	private Map<User, Balance> userBalances;
	
	private TycoonPlugin plugin;
	private Config config;
	
	public UserManager() {
		this.plugin = TycoonPlugin.get();
		this.userBalances = new HashedMap<>();
	}
	
	
	public void loadPlayersBalances() {
		
		File userChacheFolder = new File(Config.ECONOMY_USER_CHACHE_PATH);
		FileUtils.createFolderIfNotExists(userChacheFolder);
		
		/* Get all files from folder that ends with .yml */
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".yml");
			}
			
		};
		String[] userFilesName = userChacheFolder.list(filter);
		
		/* Load user balance */
		for(int i = 0; i < userFilesName.length; i++) {
			
			Config config = new Config(Config.ECONOMY_USER_CHACHE_PATH, userFilesName[i]);
			User user = new User(UUID.fromString(userFilesName[i].replace(".yml", "")));
			int bal = (int) config.getOrDefault(user.getUuid().toString() + ".Money", 0);
			Balance balance = new Balance(user, bal);
			
			userBalances.put(user, balance);
			
		}
		
	}
	
	public void savePlayerBalance() {
		File userChacheFolder = new File(Config.ECONOMY_USER_CHACHE_PATH);
		FileUtils.createFolderIfNotExists(userChacheFolder);

		if(this.userBalances.isEmpty()) return;
		
		this.userBalances.keySet().forEach(user -> {
			
			UUID playersUUID = user.getUuid();
			
			Config config = new Config(Config.ECONOMY_USER_CHACHE_PATH, playersUUID.toString());
			config.set(playersUUID.toString() + ".Money", this.userBalances.get(user));
			
		});
		
	}
	
	public User loadUser(UUID uuid) {
		for(User user : this.userBalances.keySet()) {
			if(user.getUuid() == uuid) {
				return user;
			}
		}
		return new User(uuid);
	}
	
	public Balance getBalance(UUID uuid) {
		return this.userBalances.getOrDefault(uuid, new Balance(new User(uuid), 0));
	}

	
}
