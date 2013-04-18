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
	
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't set the spawn, console!");
            return true;
        }
    	Player p = (Player)sender;
        if (commandLabel.equalsIgnoreCase("ssetspawn") || commandLabel.equalsIgnoreCase("sss")) {
        	Location l = p.getLocation();
        	int x = l.getBlockX();
        	int y = l.getBlockY();
        	int z = l.getBlockZ();
        	p.getWorld().setSpawnLocation(x, y, z);
        	//debug
        	p.sendMessage(ChatColor.GREEN + "Spawn set!");
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