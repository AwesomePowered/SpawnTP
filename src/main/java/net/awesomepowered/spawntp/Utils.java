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
	
	public static void setSpawn(Player p, String whichSpawn) {
    	Location l = p.getLocation();
	if (whichSpawn.equalsIgnoreCase("main")) {
    	Config.setVars("Spawn.X", Double.valueOf(l.getBlockX() + 0.5).toString());
    	Config.setVars("Spawn.Y", Double.valueOf(l.getBlockY() + 0.5).toString());
    	Config.setVars("Spawn.Z", Double.valueOf(l.getBlockZ() + 0.5).toString());
    	Config.setVars("Spawn.Yaw", Float.valueOf(l.getYaw()).toString());
    	Config.setVars("Spawn.Pitch", Float.valueOf(l.getPitch()).toString());
    	Config.setVars("Spawn.World", String.valueOf(l.getWorld().getName()));
    	Config.confReload();
	} else if (whichSpawn.equalsIgnoreCase("world")) {
		String WorldName = l.getWorld().getName();
    	Config.setVars("WorldSpawns.X" + WorldName, Double.valueOf(l.getBlockX() + 0.5).toString());
    	Config.setVars("WorldSpawns.Y" + WorldName, Double.valueOf(l.getBlockY() + 0.5).toString());
    	Config.setVars("WorldSpawns.Z" + WorldName, Double.valueOf(l.getBlockZ() + 0.5).toString());
    	Config.setVars("WorldSpawns.Pitch" + WorldName, Float.valueOf(l.getPitch()).toString());
    	Config.setVars("WorldSpawns.Yaw" + WorldName, Float.valueOf(l.getYaw()).toString());
    	Config.confReload();
	} else if (whichSpawn.equalsIgnoreCase("firstjoin")) {
		Config.setVars("FirstSpawn.X", Double.valueOf(l.getBlockX() + 0.5).toString());
		Config.setVars("FirstSpawn.Y", Double.valueOf(l.getBlockY() + 0.5).toString());
		Config.setVars("FirstSpawn.Z", Double.valueOf(l.getBlockZ() + 0.5).toString());
		Config.setVars("FirstSpawn.Yaw", Float.valueOf(l.getYaw()).toString());
		Config.setVars("FirstSpawn.Pitch", Float.valueOf(l.getPitch()).toString());
		Config.setVars("FirstSpawn.World", String.valueOf(l.getWorld().getName()));
		Config.confReload();
		}
	}
}
