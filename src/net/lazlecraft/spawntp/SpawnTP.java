package net.lazlecraft.spawntp;

import net.lazlecraft.spawntp.Metrics;
import java.io.IOException;
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
	
	public String prefix = "§6§l[§4§lSpawnTP§6§l] ";
	
	public void onEnable(){
		//Config
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		//Get config values.
		sYaw = getConfig().getInt("SpawnYaw");
		sPitch = getConfig().getInt("SpawnPitch");
		//Metrics
		getServer().getPluginManager().registerEvents(this, this);
		try {
                    Metrics metrics = new Metrics(this);
                    metrics.start();
                }
                catch (IOException e) {}
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
        	getConfig().set("SpawnYaw", Float.valueOf(l.getYaw()));
        	getConfig().set("SpawnPitch", Float.valueOf(l.getPitch()));
        	p.getWorld().setSpawnLocation(x, y, z);
        	p.sendMessage(prefix + ChatColor.GREEN + "Spawn set!");
        }
        else if (commandLabel.equalsIgnoreCase("spawn") && (sender.hasPermission("spawntp.spawn"))) {
        	p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5), sYaw, sPitch);
        }
        else if (commandLabel.equalsIgnoreCase("spawntp")) {
        	p.sendMessage(prefix + ChatColor.GOLD + "This plugin is made by the almighty LaxWasHere");
        	p.sendMessage(prefix + "Running version " + this.getDescription().getVersion());
        }
        return false;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		//Dont TP new players.
		if (p.hasPlayedBefore()) {
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5), sYaw, sPitch);
		}			
	}
}
//LaxWasHere
