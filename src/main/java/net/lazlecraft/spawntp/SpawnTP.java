package net.lazlecraft.spawntp;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTP extends JavaPlugin implements Listener {
	
	public float sYaw;
	public float sPitch;
	public double sX;
	public double sY;
	public double sZ;
	public float FsYaw;
	public float FsPitch;
	public double FsX;
	public double FsY;
	public double FsZ;
	public boolean jqM;
	public boolean eFW;
	public boolean sTP;
	public boolean cInv;
	public boolean oNJ;
	public boolean cCht;
	public boolean aFJ;
	public String nJM;
	public String Ft1;
	public String Ft2;
	public String Ft3;
	public String sWorld;
	public String FsWorld;
	public String cJN;
	public String cQT;
	public String clearChat = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";//lol
	public String prefix;
	public String realPrefix = ChatColor.GOLD +""+ ChatColor.BOLD + "[" + ChatColor.RED + ChatColor.BOLD + "SpawnTP" + ChatColor.GOLD + ChatColor.BOLD + "] ";
	public String debug = "[STPDebug]";
	
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		confReload();
		getServer().getPluginManager().registerEvents(this, this);
		checkConfig();
		metrics();
	} 
	
	public void confReload() {
		sYaw = getConfig().getInt("Spawn.Yaw");
		sPitch = getConfig().getInt("Spawn.Pitch");
		sX = getConfig().getDouble("Spawn.X");
		sY = getConfig().getDouble("Spawn.Y");
		sZ = getConfig().getDouble("Spawn.Z");
		sWorld = getConfig().getString("Spawn.World");
		FsYaw = getConfig().getInt("FirstSpawn.Yaw");
		FsPitch = getConfig().getInt("FirstSpawn.Pitch");
		FsX = getConfig().getDouble("FirstSpawn.X");
		FsY = getConfig().getDouble("FirstSpawn.Y");
		FsZ = getConfig().getDouble("FirstSpawn.Z");
		nJM = getConfig().getString("NewPlayers.AnnounceMessage");
		FsWorld = getConfig().getString("FirstSpawn.World");
		Ft1 = getConfig().getString("Firework.Type1");
		Ft2 = getConfig().getString("Firework.Type2");
		Ft3 = getConfig().getString("Firework.Type3");
		cJN = getConfig().getString("LoginMessages.Join");
		cQT = getConfig().getString("LoginMessages.Quit");
		prefix = getConfig().getString("prefix");
		jqM = getConfig().getBoolean("LoginMessages.Enabled");
		eFW = getConfig().getBoolean("Firework.Enabled");
		sTP = getConfig().getBoolean("SpawnTP");
		cInv = getConfig().getBoolean("Clear.Inventory");
		cCht = getConfig().getBoolean("Clear.Chat");
		oNJ = getConfig().getBoolean("SpawnOnlyNewJoin");
		aFJ = getConfig().getBoolean("NewPlayers.Announce");
		this.saveConfig();
		this.reloadConfig();
	}
	
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
        	p.sendMessage(realPrefix + ChatColor.GREEN + "Spawn set!");
        	confReload();
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
                	confReload();
                	p.sendMessage(realPrefix + " World spawn has been set!");
        		} if (args[0].equalsIgnoreCase("firstjoin")) {
                	Location l = p.getLocation();
                	getConfig().set("FirstSpawn.X", Double.valueOf(l.getBlockX() + 0.5));
                	getConfig().set("FirstSpawn.Y", Double.valueOf(l.getBlockY() + 0.5));
                	getConfig().set("FirstSpawn.Z", Double.valueOf(l.getBlockZ() + 0.5));
                	getConfig().set("FirstSpawn.Yaw", Float.valueOf(l.getYaw()));
                	getConfig().set("FirstSpawn.Pitch", Float.valueOf(l.getPitch()));
                	getConfig().set("FirstSpawn.World", String.valueOf(l.getWorld().getName()));
                	p.sendMessage(realPrefix + ChatColor.GREEN + "First join spawn set!");
                	confReload();
        		} else {
        			sender.sendMessage(realPrefix + "You may not set the spawn!");
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
            	else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.RED + "Player does not exist!");
        	}
     } 
        else if (commandLabel.equalsIgnoreCase("spawntp")) {
        	p.sendMessage(realPrefix + ChatColor.GOLD + "This plugin is made by the almighty LaxWasHere");
        	p.sendMessage(realPrefix + ChatColor.GOLD + "Running version " + ChatColor.RED + this.getDescription().getVersion());
        	if (sender.hasPermission("spawntp.reload")) {
        	confReload();
        	}
        }
        else if (commandLabel.equalsIgnoreCase("worldspawn") && (sender.hasPermission("spawntp.worldspawn"))) {
        	World WorldName = p.getLocation().getWorld();
        	if (getConfig().getBoolean("WorldSpawn." + WorldName.toString())) {
        	Location SpawnLoc = new Location(WorldName, getConfig().getDouble("WorldSpawn.X"+WorldName.getName()), getConfig().getDouble("WorldSpawn.Y"+WorldName.getName()), getConfig().getDouble("WorldSpawn.Z"+WorldName.getName()), getConfig().getInt("WorldSpawn.Yaw"+WorldName.getName()), getConfig().getInt("WorldSpawn.Pitch"+WorldName.getName()));//Fuck the line police!
        	p.teleport(SpawnLoc);
        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GREEN + " You teleported to the world spawn.");
        	} else {
        		p.teleport(p.getWorld().getSpawnLocation().add(.5, .5, .5));
        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.RED + "No world spawn set, you have been sent to default world spawn");
        	}
        }
        return true;
	}
	
	@EventHandler
	public void onCustomLogin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.joinmessage") && jqM) {
			ev.setJoinMessage(ChatColor.translateAlternateColorCodes('&', cJN.replace("%player%", p.getName())));
		} else {
			ev.setJoinMessage("");
		}
	}
	
	@EventHandler
	public void onCustomQuit(PlayerQuitEvent ev){
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage") && jqM) {
			ev.setQuitMessage(ChatColor.translateAlternateColorCodes('&', cQT.replace("%player%", p.getName())));
		} else {
			ev.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onPKick(PlayerKickEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage") && jqM) {
			ev.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', cQT.replace("%player%", p.getName())));
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
	    if(eFW) {
	    	if (p.hasPermission("spawntp.firework")) {
	        new BukkitRunnable() {
	            public void run() {
	                Location loc = p.getLocation();
	                Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	                FireworkMeta ftw = (FireworkMeta) firework.getFireworkMeta();
	                ftw.addEffects(FireworkEffect.builder().withFlicker().withTrail().withFade(Color.ORANGE).withColor(Color.GREEN).with(Type.valueOf(Ft1)).with(Type.valueOf(Ft2)).with(Type.valueOf(Ft3)).build());
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
		if (cInv) {
			if (!ev.getPlayer().hasPermission("spawntp.noinvclear")) {
				clearInv(p);
			}
		}
		if (cCht) {
			if (!p.hasPermission("spawntp.noclearchat")) {
				p.sendMessage(clearChat);
			}
		}
	}
	
	//SpawnTP
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) { 
	if (sTP && !oNJ) {
		Player p = ev.getPlayer();
		if (!p.hasPermission("spawntp.bypass")) {
			if (!getConfig().getStringList("DisabledInWorld").contains(p.getLocation().getWorld().getName())) {
		sendSpawn(p);
			}
		}
	} if (oNJ) {
		if (!ev.getPlayer().hasPlayedBefore()) {
			sendNewJoin(ev.getPlayer());
			if (aFJ) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', nJM));
			}
		}
	}
}	
	
	public void checkConfig() {
		if (getConfig().getInt("ConfigVersion") != 2) {
			Bukkit.getConsoleSender().sendMessage(prefix + " Outdated Config!");
			Bukkit.getConsoleSender().sendMessage(prefix + " Please reset your config!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.updateInventory();
	}
	
	public void sendNewJoin(Player p) {
		if (FsWorld == null) 
			sendSpawn(p);
		else {
	    	Location FirstSpawnLoc = new Location(Bukkit.getServer().getWorld(FsWorld), FsX, FsY, FsZ, FsYaw, FsPitch);
	    	p.teleport(FirstSpawnLoc);
	    	if (getConfig().getBoolean("LogTeleport")) {
	    		Bukkit.getConsoleSender().sendMessage(realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to First Join Spawn");
	    	}
		}
	}
	
	public void setSpawn(String configLoc, String location) {
		getConfig().set(configLoc, location);
	}
	
	public void sendSpawn(Player p) {
		if (sWorld == null)
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		else {
    	Location SpawnLoc = new Location(Bukkit.getServer().getWorld(sWorld), sX, sY, sZ, sYaw, sPitch);
    	p.teleport(SpawnLoc);
    	if (getConfig().getBoolean("LogTeleport")) {
    		Bukkit.getConsoleSender().sendMessage(realPrefix + ChatColor.GOLD + "Sent " + p.getName() + " to spawn");
    	}
    }
  }
}
//LaxWasHere
