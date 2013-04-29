package net.lazlecraft.spawntp;

import net.lazlecraft.spawntp.Metrics;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnTP extends JavaPlugin implements Listener {
	
	public float sYaw;
	public float sPitch;
	public String sWorld;
	public double sX;
	public double sY;
	public double sZ;
	public String prefix = "§6§l[§4§lSpawnTP§6§l] ";
	
	public void onEnable(){
		//Config
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		//Get config values.
		confreload();
		//Register events
		getServer().getPluginManager().registerEvents(this, this); 
		//Metrics
		try {
                    Metrics metrics = new Metrics(this);
                    metrics.start();
        }
                catch (IOException e) {}
	} 
	
	public void confreload() {
		sYaw = getConfig().getInt("SpawnYaw");
		sPitch = getConfig().getInt("SpawnPitch");
		sX = getConfig().getDouble("SpawnX");
		sY = getConfig().getDouble("SpawnY");
		sZ = getConfig().getDouble("SpawnZ");
		sWorld = getConfig().getString("SpawnWorld");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You may not use SpawnTP commands, console!");
            return false;
        }
             
    	Player p = (Player)sender;
        if (((commandLabel.equalsIgnoreCase("setspawn")) || commandLabel.equalsIgnoreCase("sss")) && (sender.hasPermission("spawntp.setspawn"))) {
        	Location l = p.getLocation();
        	int x = l.getBlockX();
        	int y = l.getBlockY();
        	int z = l.getBlockZ();
        	getConfig().set("SpawnX", Double.valueOf(l.getBlockX() + 0.5));
        	getConfig().set("SpawnY", Double.valueOf(l.getBlockY() + 0.5));
        	getConfig().set("SpawnZ", Double.valueOf(l.getBlockZ() + 0.5));
        	getConfig().set("SpawnYaw", Float.valueOf(l.getYaw()));
        	getConfig().set("SpawnPitch", Float.valueOf(l.getPitch()));
        	getConfig().set("SpawnWorld", String.valueOf(l.getWorld().getName()));
        	p.getWorld().setSpawnLocation(x, y, z);
        	p.sendMessage(prefix + ChatColor.GREEN + "Spawn set!");
        	confreload();
        }
        else if (commandLabel.equalsIgnoreCase("spawn") && (sender.hasPermission("spawntp.spawn"))) {
        	Location SpawnLoc = new Location(Bukkit.getServer().getWorld(sWorld), sX, sY, sZ, sYaw, sPitch);
        	p.teleport(SpawnLoc);
        }
        else if (commandLabel.equalsIgnoreCase("spawntp")) {
        	p.sendMessage(prefix + ChatColor.GOLD + "This plugin is made by the almighty LaxWasHere");
        	p.sendMessage(prefix + "Running version " + ChatColor.RED + this.getDescription().getVersion());
        	confreload();
        }
        return true;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (sWorld == null)
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		else {
			Location SpawnLoc = new Location(Bukkit.getServer().getWorld(sWorld), sX, sY, sZ, sYaw, sPitch);
			p.teleport(SpawnLoc);		
		}
	}
}
//LaxWasHere