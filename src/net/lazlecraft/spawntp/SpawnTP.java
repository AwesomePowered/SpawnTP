package net.lazlecraft.spawntp;

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
	
	public String prefix = "§6§l[§4§lSpawnTP§6§l] ";
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't set the spawn, console!");
            return false;
        }
    	Player p = (Player)sender;
        if (((commandLabel.equalsIgnoreCase("ssetspawn")) || commandLabel.equalsIgnoreCase("sss")) && (sender.hasPermission("spawntp.setspawn"))) {
        	Location l = p.getLocation();
        	int x = l.getBlockX();
        	int y = l.getBlockY();
        	int z = l.getBlockZ();
        	p.getWorld().setSpawnLocation(x, y, z);
        	p.sendMessage(prefix + ChatColor.GREEN + "Spawn set!");
        }
        else if (commandLabel.equalsIgnoreCase("spawn") && (sender.hasPermission("spawntp.spawn"))) {
        	p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
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
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		}			
	}
}
//LaxWasHere