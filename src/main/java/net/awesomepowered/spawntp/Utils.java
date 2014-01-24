package net.awesomepowered.spawntp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
	
	public Utils(SpawnTP plugin) {
		this.plugin = plugin;
	}
	SpawnTP plugin;
	
	
	
	public static void checkConfig() {
		if (SpawnTP.MainClass().getConfig().getInt("ConfigVersion") != 2) {
			Bukkit.getConsoleSender().sendMessage(Config.realPrefix + " Outdated Config!");
			Bukkit.getConsoleSender().sendMessage(Config.realPrefix + " Please reset your config!");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.updateInventory();
	}
	
	public static void sendSpawn() {
		
	}
	
	public static void lunchFirework(Location loc) {
		
	}

}
