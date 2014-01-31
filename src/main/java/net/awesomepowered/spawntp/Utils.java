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
			Bukkit.getPluginManager().disablePlugin(SpawnTP.MainClass());
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.updateInventory();
	}
	
	public static void sendSpawn(Player p) {
		if (Config.sWorld == null)
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		else {
    	Location SpawnLoc = new Location(Bukkit.getServer().getWorld(Config.sWorld), Config.sX, Config.sY, Config.sZ, Config.sYaw, Config.sPitch);
    	p.teleport(SpawnLoc);
    	if (Config.lTP) {
    		Bukkit.getConsoleSender().sendMessage(Config.realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to spawn");
    	}
    }
  }
	
	public static void sendNewJoin(Player p) {
		if (Config.FsWorld == null) 
			sendSpawn(p);
		else {
	    	Location FirstSpawnLoc = new Location(Bukkit.getServer().getWorld(Config.FsWorld), Config.FsX, Config.FsY, Config.FsZ, Config.FsYaw, Config.FsPitch);
	    	p.teleport(FirstSpawnLoc);
	    	if (Config.lTP) {
	    		Bukkit.getConsoleSender().sendMessage(Config.realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to First Join Spawn");
	    	}
		}
	}
	
	public static void lunchFirework(Location loc) {
		
	}

}
