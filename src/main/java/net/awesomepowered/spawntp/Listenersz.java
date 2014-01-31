package net.awesomepowered.spawntp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Listenersz implements Listener {
	public static SpawnTP plugin;
	
	@EventHandler
	public void onCustomLogin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.joinmessage") && Config.jqM) {
			ev.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Config.cJN.replace("%player%", p.getName())));
		} else {
			ev.setJoinMessage("");
		}
	}
	
	@EventHandler
	public void onCustomQuit(PlayerQuitEvent ev){
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage") && Config.jqM) {
			ev.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Config.cQT.replace("%player%", p.getName())));
		} else {
			ev.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onPKick(PlayerKickEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage") && Config.jqM) {
			ev.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', Config.cQT.replace("%player%", p.getName())));
		} else {
			ev.setLeaveMessage("");
		}
	}
	
	@EventHandler
	public void onPJ(PlayerJoinEvent ev) {
		//Play sound
		Player p = ev.getPlayer(); 
		if (Config.sEN) {
			Location loc = p.getLocation();
			p.playSound(loc, Sound.valueOf(Config.sSS), Config.sSV, Config.sSP);
		}
	}
	
	@EventHandler
	public void onpJoin(PlayerJoinEvent ev)	{
		Player p = ev.getPlayer();
		if (Config.cInv) {
			if (!ev.getPlayer().hasPermission("spawntp.noinvclear")) {
				Utils.clearInv(p);
			}
		}
		if (Config.cCht) {
			if (!p.hasPermission("spawntp.noclearchat")) {
				p.sendMessage(Config.clearChat);
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (Config.sTP) {
		if (p.hasPermission("spawntp.joinspawn")) {
		if (Config.oNJ && !p.hasPlayedBefore()) {
			Utils.sendNewJoin(p);
			if (Config.aFJ) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Config.nJM));
				}
			}
		} else {
			if (!SpawnTP.MainClass().getConfig().getStringList("DisabledInWorld").contains(p.getLocation().getWorld().getName())) {
				Utils.sendSpawn(p);
				}
			}
		}
	}
}
