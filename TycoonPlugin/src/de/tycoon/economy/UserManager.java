package de.tycoon.economy;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.tycoon.config.Config;
import de.tycoon.util.FileUtils;

public class UserManager {

	
	private Map<UUID, IUser> userData;
	
	
	public UserManager() {
		this.userData = new HashMap<>();
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
			
			String uuid = userFilesName[i].replace(".yml", "");
			
			Config config = new Config(Config.ECONOMY_USER_CHACHE_PATH, userFilesName[i]);
			User user = new IUser(UUID.fromString(uuid));
			user.setBalance(config.getDouble(uuid + ".Money"));
			user.setTokens(config.getInt(uuid + ".Tokens"));
			
			this.userData.put(UUID.fromString(uuid), (IUser) user);
			
		}
		
	}
	
	public void savePlayerBalance() {
		
		File userChacheFolder = new File(Config.ECONOMY_USER_CHACHE_PATH);
		FileUtils.createFolderIfNotExists(userChacheFolder);

		
		if(this.userData.isEmpty()) return;
		if(this.userData.keySet().isEmpty()) return;
		
		this.userData.keySet().forEach(uuid -> {
			
			Config config = new Config(Config.ECONOMY_USER_CHACHE_PATH, uuid.toString());
			
			config.set(uuid.toString() + ".Money", Math.round(this.userData.get(uuid).getBalance() * 100d) / 100d);
			config.set(uuid.toString() + ".Tokens", this.userData.get(uuid).getTokens());
			
		});
		
	}
	
	
	public User loadUser(UUID uuid) {
		if(!this.userData.containsKey(uuid))
			this.userData.put(uuid, new IUser(uuid));	
		return this.userData.get(uuid);
	}
	
}
