package de.tycoon.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Config {

	public static final String GEN_SETTING_PATH = "./plugins/Tycoon/GeneratorSettings";
	public static final String SETTING_PATH = "./plugins/Tycoon/Settings";
	public static final String ECONOMY_PATH = "./plugins/Tycoon/Economy";
	public static final String ECONOMY_USER_CHACHE_PATH = "./plugins/Tycoon/Economy";
	public static final String DISCORD_PATH = "./plugins/Tycoon/Discord";
	public static final String GENERATOR_CHACHE_PATH = "./plugins/Tycoon/GeneratorChache";
	public static final String MESSAGES_PATH = "./plugins/Tycoon/Messages";
	public static final String SHOP_PATH = "./plugins/Tycoon/Shop";
	public static final String TOKENMINE_PATH = "./plugins/Tycoon/TokenMine";
	
	private File file;
	private YamlConfiguration configuration;
	
	public Config(String folderPath, String fileName) {

		//Check if given filename contains yml ending
		if(!fileName.contains(".yml")) {
			 fileName = String.join("", fileName, ".yml");
		}
		
		File basePath = new File(folderPath);
		
		/* Create folder if not exists */
		if(!basePath.exists()) {
			basePath.mkdirs();
		}
		
		File configFile = new File(basePath, fileName);

		/* Create file if not exists */
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/* Load YamlConfiguartion */
		this.file = configFile;
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	
	}
	
	public boolean contains(String path) {
		return this.configuration.contains(path);
	}
	
	public Object get(String path) {
		if(!contains(path))
			return null;
		return this.configuration.get(path);
	}
	
	public String getString(String path) {
		if(!contains(path)) 
			return null;
		return this.configuration.getString(path);
	}
	
	@SuppressWarnings("null")
	public int getInt(String path) {
		if(!contains(path))
			return (Integer) null;
		return this.configuration.getInt(path);
	}
	
	@SuppressWarnings("null")
	public  boolean getBoolean(String path) {
		if(!contains(path))
			return (Boolean) null;
		return this.configuration.getBoolean(path);
	}
	
	@SuppressWarnings("null")
	public double getDouble(String path) {
		if(!contains(path))
			return (Double) null;
		return this.configuration.getDouble(path);
	}
	
	public ItemStack getItemStack(String path) {
		if(!contains(path))
			return null;
		return this.configuration.getItemStack(path);
	}
	
	public List<String> getStringList(String path) {
		if(!contains(path))
			return null;
		return this.configuration.getStringList(path);
	}
	
	public ConfigurationSection getConfigurationSection(String path) {
		if(!contains(path))
			return null;
		return this.configuration.getConfigurationSection(path);
	}
	
	public Object getOrDefault(String path, Object defaultValue) {
		if(!contains(path)) 
			return defaultValue;
		return this.configuration.get(path);
	}
	
	public void set(String path, Object value) {
		this.configuration.set(path, value);
		this.save();
	}
	
	public String getFileName() {
		return this.file.getName();
	}
	
	
	
	private void save() {
		try {
			this.configuration.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
