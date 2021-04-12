package de.tycoon.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static void createFolderIfNotExists(File file) {
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public static void createFileIfNotExists(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
