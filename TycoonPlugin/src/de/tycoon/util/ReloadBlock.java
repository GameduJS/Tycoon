package de.tycoon.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class ReloadBlock {

	private Block reloadBlock;
	private Material materialToReload;
	
	private int time;
	
	public ReloadBlock(Block block, Material material, int time) {
		this.reloadBlock = block;
		this.materialToReload = material;
		this.time = time;
	}
	
	public Block getReloadBlock() {
		return reloadBlock;
	}
	
	public Material getMaterialToReload() {
		return materialToReload;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
}
