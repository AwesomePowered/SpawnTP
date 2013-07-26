package net.lazlecraft.spawntp;

import java.io.IOException;
import net.lazlecraft.spawntp.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
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
	public boolean jqM;
	public boolean eFW;
	public boolean sTP;
	public boolean cInv;
	public boolean oNJ;
	public String FW;
	public String sWorld;
	public String prefix = ChatColor.GOLD +""+ ChatColor.BOLD + "[" + ChatColor.RED + ChatColor.BOLD + "SpawnTP" + ChatColor.GOLD + ChatColor.BOLD + "] ";
	
	
	public void onEnable(){
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		confReload();
		getServer().getPluginManager().registerEvents(this, this); 
		try {
                    Metrics metrics = new Metrics(this);
                    metrics.start();
        }
                catch (IOException e) {}
	} 
	
	public void confReload() {
		sYaw = getConfig().getInt("SpawnYaw");
		sPitch = getConfig().getInt("SpawnPitch");
		sX = getConfig().getDouble("SpawnX");
		sY = getConfig().getDouble("SpawnY");
		sZ = getConfig().getDouble("SpawnZ");
		sWorld = getConfig().getString("SpawnWorld");
		FW = getConfig().getString("FireworkType");
		jqM = getConfig().getBoolean("EnableJoinQuitMessages");
		eFW = getConfig().getBoolean("FireworkOnJoin");
		sTP = getConfig().getBoolean("SpawnTP");
		cInv = getConfig().getBoolean("ClearInventory");
		oNJ = getConfig().getBoolean("SpawnOnlyNewJoin");
		this.saveConfig();
		this.reloadConfig();
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
        	confReload();
        }
        else if (commandLabel.equalsIgnoreCase("spawn") && (sender.hasPermission("spawntp.spawn"))) {
        	if(args.length == 0) {
            	sendSpawn(p);
        	} else if (args.length == 1 && sender.hasPermission("spawntp.spawn.others")) {
            		if (sender.getServer().getPlayer(args[0]) != null) {
            			Player pp = sender.getServer().getPlayer(args[0]);
            			sendSpawn(pp);
            		}
            	else sender.sendMessage(prefix + ChatColor.RED + "Player does not exist!");
        	}
     } 
        else if (commandLabel.equalsIgnoreCase("spawntp")) {
        	p.sendMessage(prefix + ChatColor.GOLD + "This plugin is made by the almighty LaxWasHere");
        	p.sendMessage(prefix + ChatColor.GOLD + "Running version " + ChatColor.RED + this.getDescription().getVersion());
        	if (sender.hasPermission("spawntp.reload")) {
        	confReload();
        	}
        }
        else if (commandLabel.equalsIgnoreCase("spawnloc") && (sender.hasPermission("spawntp.location"))) {
        	p.sendMessage(prefix + ChatColor.GREEN + "World: " + ChatColor.GOLD + sWorld);
        	p.sendMessage(prefix + ChatColor.GREEN + "X: " + ChatColor.GOLD + sX);
        	p.sendMessage(prefix + ChatColor.GREEN + "Y: " + ChatColor.GOLD + sY);
        	p.sendMessage(prefix + ChatColor.GREEN + "Z: " + ChatColor.GOLD + sZ);
        }
        else if (commandLabel.equalsIgnoreCase("setworldspawn") && (sender.hasPermission("spawntp.setworldspawn"))) {
        	Location l = p.getLocation();
        	int x = l.getBlockX();
        	int y = l.getBlockY();
        	int z = l.getBlockZ();
        	p.getWorld().setSpawnLocation(x, y, z);
        	p.sendMessage(prefix + " World spawn has been set!");
        }
        else if (commandLabel.equalsIgnoreCase("worldspawn") && (sender.hasPermission("spawntp.worldspawn"))) {
        	p.teleport(p.getWorld().getSpawnLocation().add(.5, .5, .5));
        	p.sendMessage(prefix + ChatColor.GREEN + " You teleported to the worlds spawn.");
        }
        return true;
	}
	
	@EventHandler
	public void onCustomLogin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.joinmessage")) {
			ev.setJoinMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("CustomJoinMessage").replace("%player%", p.getName())));
		} else if (!jqM) {
			ev.setJoinMessage("");
		}
	}
	
	@EventHandler
	public void onCustomQuit(PlayerQuitEvent ev){
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage")) {
			ev.setQuitMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("CustomQuitMessage").replace("%player%", p.getName())));
		} else if (!jqM) {
			ev.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onPKick(PlayerKickEvent ev) {
		Player p = ev.getPlayer();
		if (p.hasPermission("spawntp.quitmessage")) {
			ev.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("CustomQuitMessage").replace("%player%", p.getName())));
		} else if (!jqM) {
			ev.setLeaveMessage("");
		}
	}
	
	@EventHandler
	public void onpLogin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		if (getConfig().getBoolean("ClearChatOnLogin")) {
		if (!p.hasPermission("spawntp.noclearchat")) {
			p.sendMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			}
		}
	}
	
	@EventHandler
	public void onPJ(PlayerJoinEvent ev) {
		//Play sound
		Player p = ev.getPlayer(); 
		if (getConfig().getBoolean("SoundOnJoin")) {
			Location loc = p.getLocation();
			p.playSound(loc, Sound.valueOf(getConfig().getString("LoginSound")), getConfig().getInt("SoundVolume"), getConfig().getInt("SoundPitch"));
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
	                ftw.addEffects(FireworkEffect.builder().withFlicker().withTrail().withFade(Color.ORANGE).withColor(Color.GREEN).with(Type.STAR).with(Type.valueOf(FW)).with(Type.BALL_LARGE).build());
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
	}
	
	//SpawnTP
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) { 
	if (sTP && !oNJ) {
		Player p = ev.getPlayer();
		if (!p.hasPermission("spawntp.bypass")) {
		sendSpawn(p);
		}
	}
}
	
	@EventHandler
	public void onNewJoin(PlayerJoinEvent ev) {
		if (oNJ) {
			Player p = ev.getPlayer();
			if (!p.hasPlayedBefore()) {
				sendSpawn(p);
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.updateInventory();
	}
	
	public void sendSpawn(Player p) {
		if (sWorld == null)
			p.teleport(p.getWorld().getSpawnLocation().add(0.5,0.5,0.5));
		else {
    	Location SpawnLoc = new Location(Bukkit.getServer().getWorld(sWorld), sX, sY, sZ, sYaw, sPitch);
    	p.teleport(SpawnLoc);
    	if (getConfig().getBoolean("LogTeleport")) {
    		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "Sent " + p.getName() + " to spawn");
    	}
    }
  }
}
//LaxWasHere