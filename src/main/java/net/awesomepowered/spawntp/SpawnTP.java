package net.awesomepowered.spawntp;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTP extends JavaPlugin implements Listener {
	
	public void onEnable() {
		thePlugin = this;
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		Utils.checkConfig();
		Config.confReload();
		getServer().getPluginManager().registerEvents(this, this);
	} 
	
	public static SpawnTP MainClass() {
		return thePlugin;
	}
	
	public static SpawnTP thePlugin;
	
	
	public void metrics() {
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {   
    	Player p = (Player)sender;
        if (((commandLabel.equalsIgnoreCase("setspawn")) || commandLabel.equalsIgnoreCase("sss")) && (sender.hasPermission("spawntp.setspawn")) && (sender instanceof Player)) {
        	if (args.length == 0) {
        	Location l = p.getLocation();
        	getConfig().set("Spawn.X", Double.valueOf(l.getBlockX() + 0.5));
        	getConfig().set("Spawn.Y", Double.valueOf(l.getBlockY() + 0.5));
        	getConfig().set("Spawn.Z", Double.valueOf(l.getBlockZ() + 0.5));
        	getConfig().set("Spawn.Yaw", Float.valueOf(l.getYaw()));
        	getConfig().set("Spawn.Pitch", Float.valueOf(l.getPitch()));
        	getConfig().set("Spawn.World", String.valueOf(l.getWorld().getName()));
        	p.sendMessage(Config.realPrefix + ChatColor.GREEN + "Spawn set!");
        	Config.confReload();
        	} else if (args.length == 1) {
        		if (args[0].equalsIgnoreCase("world")) {
                	Location l = p.getLocation();
                	String WorldName = p.getLocation().getWorld().getName();
                	int x = l.getBlockX();
                	int y = l.getBlockY();
                	int z = l.getBlockZ();
                	p.getWorld().setSpawnLocation(x, y, z);
                	getConfig().set("WorldSpawns." + WorldName, true);
                	setSpawn("WorldSpawns.X" + WorldName, Double.valueOf(l.getBlockX() + 0.5).toString());
                	setSpawn("WorldSpawns.Y" + WorldName, Double.valueOf(l.getBlockY() + 0.5).toString());
                	setSpawn("WorldSpawns.Z" + WorldName, Double.valueOf(l.getBlockZ() + 0.5).toString());
                	setSpawn("WorldSpawns.Pitch" + WorldName, Float.valueOf(l.getPitch()).toString());
                	setSpawn("WorldSpawns.Yaw" + WorldName, Float.valueOf(l.getYaw()).toString());
                	Config.confReload();
                	p.sendMessage(Config.realPrefix + " World spawn has been set!");
        		} if (args[0].equalsIgnoreCase("firstjoin")) {
                	Location l = p.getLocation();
                	getConfig().set("FirstSpawn.X", Double.valueOf(l.getBlockX() + 0.5));
                	getConfig().set("FirstSpawn.Y", Double.valueOf(l.getBlockY() + 0.5));
                	getConfig().set("FirstSpawn.Z", Double.valueOf(l.getBlockZ() + 0.5));
                	getConfig().set("FirstSpawn.Yaw", Float.valueOf(l.getYaw()));
                	getConfig().set("FirstSpawn.Pitch", Float.valueOf(l.getPitch()));
                	getConfig().set("FirstSpawn.World", String.valueOf(l.getWorld().getName()));
                	p.sendMessage(Config.realPrefix + ChatColor.GREEN + "First join spawn set!");
                	Config.confReload();
        		} else {
        			sender.sendMessage(Config.realPrefix + "You may not set the spawn!");
        		}
        	}
        }
        
        else if (commandLabel.equalsIgnoreCase("spawn") && (sender.hasPermission("spawntp.spawn"))) {
        	if(args.length == 0) {
            	sendSpawn(p);
        	} else if (args.length == 1 && sender.hasPermission("spawntp.spawn.others")) {
            		if (sender.getServer().getPlayer(args[0]) != null) {
            			Player pp = sender.getServer().getPlayer(args[0]);
            			sendSpawn(pp);
            		}
            	else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix) + ChatColor.RED + "Player does not exist!");
        	}
     } 
        else if (commandLabel.equalsIgnoreCase("spawntp")) {
        	p.sendMessage(Config.realPrefix + ChatColor.GOLD + "This plugin is made by the almighty LaxWasHere");
        	p.sendMessage(Config.realPrefix + ChatColor.GOLD + "Running version " + ChatColor.RED + this.getDescription().getVersion());
        	if (sender.hasPermission("spawntp.reload")) {
        		Config.confReload();
        	}
        }
        else if (commandLabel.equalsIgnoreCase("worldspawn") && (sender.hasPermission("spawntp.worldspawn"))) {
        	World WorldName = p.getLocation().getWorld();
        	if (getConfig().getBoolean("WorldSpawn." + WorldName.toString())) {
        	Location SpawnLoc = new Location(WorldName, getConfig().getDouble("WorldSpawn.X"+WorldName.getName()), getConfig().getDouble("WorldSpawn.Y"+WorldName.getName()), getConfig().getDouble("WorldSpawn.Z"+WorldName.getName()), getConfig().getInt("WorldSpawn.Yaw"+WorldName.getName()), getConfig().getInt("WorldSpawn.Pitch"+WorldName.getName()));//Fuck the line police!
        	p.teleport(SpawnLoc);
        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix) + ChatColor.GREEN + " You teleported to the world spawn.");
        	} else {
        		p.teleport(p.getWorld().getSpawnLocation().add(.5, .5, .5));
        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix) + ChatColor.RED + "No world spawn set, you have been sent to default world spawn");
        	}
        }
        return true;
	}
	
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
		if (getConfig().getBoolean("Sound.Enabled")) {
			Location loc = p.getLocation();
			p.playSound(loc, Sound.valueOf(getConfig().getString("Sound.Sound")), getConfig().getInt("Sound.Volume"), getConfig().getInt("Sound.Pitch"));
		}
	}
	
	//Fireworks
	//Thanks to http://lazle.us/11VGS5v
	@EventHandler
	public void onJoinPlayer(PlayerJoinEvent ev) {
	    final Player p = ev.getPlayer();
	    if(Config.eFW) {
	    	if (p.hasPermission("spawntp.firework")) {
	        new BukkitRunnable() {
	            public void run() {
	                Location loc = p.getLocation();
	                Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	                FireworkMeta ftw = (FireworkMeta) firework.getFireworkMeta();
	                ftw.addEffects(FireworkEffect.builder().withFlicker().withTrail().withFade(Color.ORANGE).withColor(Color.GREEN).with(Type.valueOf(Config.Ft1)).with(Type.valueOf(Config.Ft2)).with(Type.valueOf(Config.Ft3)).build());
	                ftw.setPower(2);
	                firework.setFireworkMeta(ftw);
	            }
	        }.runTaskLater(this, 20L);
	    }
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
	
	//SpawnTP
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) { 
	if (Config.sTP && !Config.oNJ) {
		Player p = ev.getPlayer();
		if (!p.hasPermission("spawntp.bypass")) {
			if (!getConfig().getStringList("DisabledInWorld").contains(p.getLocation().getWorld().getName())) {
		sendSpawn(p);
			}
		}
	} if (Config.oNJ) {
		if (!ev.getPlayer().hasPlayedBefore()) {
			sendNewJoin(ev.getPlayer());
			if (Config.aFJ) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Config.nJM));
			}
		}
	}
}	

	
	public void sendNewJoin(Player p) {
		if (Config.FsWorld == null) 
			sendSpawn(p);
		else {
	    	Location FirstSpawnLoc = new Location(Bukkit.getServer().getWorld(Config.FsWorld), Config.FsX, Config.FsY, Config.FsZ, Config.FsYaw, Config.FsPitch);
	    	p.teleport(FirstSpawnLoc);
	    	if (getConfig().getBoolean("LogTeleport")) {
	    		Bukkit.getConsoleSender().sendMessage(Config.realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to First Join Spawn");
	    	}
		}
	}
	
	public void setSpawn(String configLoc, String location) {
		getConfig().set(configLoc, location);
	}
	
	public void sendSpawn(Player p) {
		if (Config.sWorld == null)
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		else {
    	Location SpawnLoc = new Location(Bukkit.getServer().getWorld(Config.sWorld), Config.sX, Config.sY, Config.sZ, Config.sYaw, Config.sPitch);
    	p.teleport(SpawnLoc);
    	if (getConfig().getBoolean("LogTeleport")) {
    		Bukkit.getConsoleSender().sendMessage(Config.realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to spawn");
    	}
    }
  }
}
//LaxWasHere
