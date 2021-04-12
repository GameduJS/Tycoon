package de.tycoon.generators.generator;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class GeneratorBlock {

	private Location loc;
	private Material mat;
	private String displayName;
	private List<String> lores;
	
	public GeneratorBlock(Location loc, Material mat, String displayName, List<String> lores) {
		
		this.loc = loc;
		this.mat = mat;
		this.displayName = displayName;
		this.lores = lores;
		
	}

	public Location getLocation() {
		return loc;
	}

	public Material getMat() {
		return mat;
	}

	public String getDisplayName() {
		return displayName;
	}

	public List<String> getLores() {
		return lores;
	}
	
}
