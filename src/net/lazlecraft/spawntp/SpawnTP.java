package net.lazlecraft.spawntp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnTP extends JavaPlugin implements Listener {
	
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onJoin(PlayerLoginEvent ev) {
		Player p = ev.getPlayer();
		//Dont TP new players.
		if (p.hasPlayedBefore()) {
			p.teleport(p.getWorld().getSpawnLocation());
		}			
	}
}
//LaxWasHere