package net.awesomepowered.spawntp;

import org.bukkit.ChatColor;

public class Config {
	
	public static float sYaw;
	public static float sPitch;
	public static double sX;
	public static double sY;
	public static double sZ;
	public static float FsYaw;
	public static float FsPitch;
	public static double FsX;
	public static double FsY;
	public static double FsZ;
	public static int sSV;
	public static int sSP;
	public static boolean jqM;
	public static boolean eFW;
	public static boolean sTP;
	public static boolean cInv;
	public static boolean oNJ;
	public static boolean cCht;
	public static boolean aFJ;
	public static boolean lTP;
	public static boolean sEN;
	public static String sSS;
	public static String nJM;
	public static String Ft1;
	public static String Ft2;
	public static String Ft3;
	public static String sWorld;
	public static String FsWorld;
	public static String cJN;
	public static String cQT;
	public static String clearChat = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";//lol
	public static String prefix;
	public static String realPrefix = ChatColor.GOLD +""+ ChatColor.BOLD + "[" + ChatColor.RED + ChatColor.BOLD + "SpawnTP" + ChatColor.GOLD + ChatColor.BOLD + "] ";
	public static String debug = "[STPDebug]";
	
	public static void confReload() {
		sYaw = SpawnTP.MainClass().getConfig().getInt("Spawn.Yaw");
		sPitch = SpawnTP.MainClass().getConfig().getInt("Spawn.Pitch");
		sX = SpawnTP.MainClass().getConfig().getDouble("Spawn.X");
		sY = SpawnTP.MainClass().getConfig().getDouble("Spawn.Y");
		sZ = SpawnTP.MainClass().getConfig().getDouble("Spawn.Z");
		sWorld = SpawnTP.MainClass().getConfig().getString("Spawn.World");
		FsYaw = SpawnTP.MainClass().getConfig().getInt("FirstSpawn.Yaw");
		FsPitch = SpawnTP.MainClass().getConfig().getInt("FirstSpawn.Pitch");
		sSV = SpawnTP.MainClass().getConfig().getInt("Sound.Volume");
		sSP = SpawnTP.MainClass().getConfig().getInt("Sound.Pitch");
		FsX = SpawnTP.MainClass().getConfig().getDouble("FirstSpawn.X");
		FsY = SpawnTP.MainClass().getConfig().getDouble("FirstSpawn.Y");
		FsZ = SpawnTP.MainClass().getConfig().getDouble("FirstSpawn.Z");
		nJM = SpawnTP.MainClass().getConfig().getString("NewPlayers.AnnounceMessage");
		FsWorld = SpawnTP.MainClass().getConfig().getString("FirstSpawn.World");
		sSS = SpawnTP.MainClass().getConfig().getString("Sound.Sound");
		Ft1 = SpawnTP.MainClass().getConfig().getString("Firework.Type1");
		Ft2 = SpawnTP.MainClass().getConfig().getString("Firework.Type2");
		Ft3 = SpawnTP.MainClass().getConfig().getString("Firework.Type3");
		cJN = SpawnTP.MainClass().getConfig().getString("LoginMessages.Join");
		cQT = SpawnTP.MainClass().getConfig().getString("LoginMessages.Quit");
		prefix = SpawnTP.MainClass().getConfig().getString("prefix");
		lTP = SpawnTP.MainClass().getConfig().getBoolean("LogTeleport");
		sEN = SpawnTP.MainClass().getConfig().getBoolean("Sound.Enabled");
		jqM = SpawnTP.MainClass().getConfig().getBoolean("LoginMessages.Enabled");
		eFW = SpawnTP.MainClass().getConfig().getBoolean("Firework.Enabled");
		sTP = SpawnTP.MainClass().getConfig().getBoolean("SpawnTP");
		cInv = SpawnTP.MainClass().getConfig().getBoolean("Clear.Inventory");
		cCht = SpawnTP.MainClass().getConfig().getBoolean("Clear.Chat");
		oNJ = SpawnTP.MainClass().getConfig().getBoolean("NewPlayers.SendToSpawn");
		aFJ = SpawnTP.MainClass().getConfig().getBoolean("NewPlayers.Announce");
		SpawnTP.MainClass().saveConfig();
		SpawnTP.MainClass().reloadConfig();
	}
	
	
	public static void setVars(String configLoc, String var) {
		SpawnTP.MainClass().getConfig().set(configLoc, var);
	}

}
