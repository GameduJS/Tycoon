package de.tycoon.language;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tycoon.config.Config;


public class LanguageHandler {

	public Map<String, Map<String, String>> languages;
	private Config config;
	
	public LanguageHandler() {
		this.languages = new HashMap<>();
		this.config = new Config(Config.MESSAGES_PATH, "lang.yml");
	}
	
	public void registerLanguage(String langueageToken, Messages message, String sentence) {
		Map<String, String> pathLanguage = new HashMap<>();
		pathLanguage.put(message.getPath(), sentence);
		this.languages.put(langueageToken, pathLanguage);
	}
	
	protected void registerLanguage(String languageToken, String enumPath, String sentence) {
		Map<String, String> pathLanguage = new HashMap<>();
		pathLanguage.put(enumPath, sentence);
		this.languages.put(languageToken, pathLanguage);
	}
	
	public void saveNewLanguageSentence() {
		
		System.out.println("Saving new languages");
		
		getLanguagesToken().forEach(langToken -> {
			
			this.languages.get(langToken).keySet().forEach(langConfigPath -> {
				
				String section = langConfigPath + "." + langToken;
				
				if(!this.config.contains(section)) {
					
					this.config.set(section, getLanguageSentence(langToken, langConfigPath));
				}
				
			});
			
		});
		this.languages.clear();
	}
	
	public void saveLanguageChanges() {
		
		System.out.println("Saving new languages");
		
		getLanguagesToken().forEach(langToken -> {
					
			this.languages.get(langToken).keySet().forEach(langConfigPath -> {
				
				String section = langConfigPath + "." + langToken;
				
				this.config.set(section, getLanguageSentence(langToken, langConfigPath));
				
			});
			
		});
		this.languages.clear();
	}
	
	public Set<String> getLanguagesToken(){
		return this.languages.keySet();
	}
	
	public String getLanguageSentence(String languageToken, String path) {
		return this.languages.get(languageToken).get(path);
	}
	
	
	public void setDefaultValues() {
		
		for(Messages lang: Messages.values()) {
			
			String section = lang.getPath();
			
			if(!this.config.contains(section)) {
				
				this.config.set(section + ".en", lang.getMessage());
				
			}
		}
	}
	
	
	
}
