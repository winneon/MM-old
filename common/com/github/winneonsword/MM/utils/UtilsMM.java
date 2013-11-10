package com.github.winneonsword.MM.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.winneonsword.MM.ClassInfo;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsMM {
	
	public static MainMM pl;
	
	public static HashMap<String, String> changingClass = new HashMap<String, String>();
	public static String WC = "&dWC &5// &d";
	
	public static boolean toggle;
	public static List<String> playerList;
	
	private static int arenaX;
	private static int arenaY;
	private static int arenaZ;
	private static World arenaW;
	
	private static Location arenaLoc;
	
	public UtilsMM(MainMM plugin){
		
		this.pl = plugin;
		this.playerList = pl.datacore.getStringList("playerList");
		
		this.setArenaLocs();
		
	}
	
	public static String AS(String message){
		
		message = ChatColor.translateAlternateColorCodes('&', message);
		
		return message;
		
	}
	
	public static void s(Player p, String message){
		
		p.sendMessage(AS(WC + message));
		
	}
	
	public static void s(Player p, String[] message){
		
		for (int i = 0; i < message.length; i++){
			
			p.sendMessage(AS(message[i]));
			
		}
		
	}
	
	public static void blankS(Player p, String message){
		
		p.sendMessage(AS(message));
		
	}
	
	public static void delay(Plugin pl, Runnable runnable, long ticks){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, runnable, ticks);
		
	}
	
	public static String getClass(Player p){
		
		return pl.datacore.getString("Users." + p.getName() + ".class");
		
	}
	
	public static String[] getClassArray(){
		
		String[] c = {
				
				"medic",
				"spirit",
				"warrior",
				"inferno",
				"roadrunner",
				"sniper"
				
		};
		
		return c;
		
	}
	
	public static List<String> getClassList(){
		
		String[] c = getClassArray();
		List<String> classes = Arrays.asList(c);
		
		return classes;
		
	}
	
	public static ClassInfo getClassInfo(String name) throws InvalidClassException {
		
		return new ClassInfo(name);
		
	}
	
	public static List<String> getPlayerList(){
		
		return playerList;
		
	}
	
	@Deprecated
	public static boolean checkClass(String name){
		
		List<String> classes = getClassList();
		
		if (classes.contains(name)){
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean isInClass(Player p){
		
		String clazz = getClass(p);
		
		if (clazz == null){
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}
	
	public static boolean toggleJoin(){
		
		toggle = !(toggle);
		
		if (toggle){
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean addPlayer(Player p, String name){
		
		if (isInClass(p)){
			
			return false;
			
		} else {
				
			playerList.add(p.getName());			
			pl.datacore.set("Users." + p.getName() + ".class", name);
			
			return true;
			
		}
		
	}
	
	public static boolean removePlayer(Player p){
		
		if (!(isInClass(p))){
			
			return false;
			
		} else {
			
			playerList.remove(p.getName());
			pl.datacore.set("Users." + p.getName() + ".class", null);
			
			return true;
			
		}
		
	}
	
	public static void changeClass(Player p, String name){
		
		pl.datacore.set("Users." + p.getName() + ".class", name);
		changingClass.remove(p.getName());
		
	}
	
	public static Location getArena(){
		
		return arenaLoc;
		
	}
	
	public static int getArenaX(){
		
		return arenaX;
		
	}
	
	public static int getArenaY(){
		
		return arenaY;
		
	}
	
	public static int getArenaZ(){
		
		return arenaZ;
		
	}
	
	public static World getArenaW(){
		
		return arenaW;
		
	}
	
	public static void setArena(Player p){
		
		arenaLoc = p.getLocation();
		arenaX = arenaLoc.getBlockX();
		arenaY = arenaLoc.getBlockY();
		arenaZ = arenaLoc.getBlockZ();
		arenaW = arenaLoc.getWorld();
		
	}
	
	public static boolean checkArena(){
		
		if (arenaLoc == null){
			
			return false;
			
		} else {
			
			return true;
		}
		
	}
	
	private void setArenaLocs(){
		
		try {
			
			this.arenaX = pl.datacore.getInt("arenaLoc.arenaX");
			this.arenaY = pl.datacore.getInt("arenaLoc.arenaY");
			this.arenaZ = pl.datacore.getInt("arenaLoc.arenaZ");
			this.arenaW = Bukkit.getWorld(pl.datacore.getString("arenaLoc.arenaW"));
			this.arenaLoc = new Location(arenaW, arenaX, arenaY, arenaZ);
			
		} catch (IllegalArgumentException e){
			
			this.arenaX = 0;
			this.arenaY = 0;
			this.arenaZ = 0;
			this.arenaW = Bukkit.getWorlds().get(0);
			this.arenaLoc = null;
			
			Bukkit.getLogger().log(Level.SEVERE, "Failed to set the arena location! Set the arena with /mm set ASAP!");
			e.printStackTrace();
			
		}
		
	}
	
}
