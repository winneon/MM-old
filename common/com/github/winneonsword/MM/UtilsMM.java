package com.github.winneonsword.MM;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class UtilsMM {
	
	public static MainMM pl;
	
	public static HashMap<String, String> changingClass = new HashMap<String, String>();
	public static String WC = "&dWC &5// &d";
	
	public UtilsMM(MainMM plugin){
		
		this.pl = plugin;
		
	}
	
	public static List<String> playerList = pl.datacore.getStringList("playerList");
	
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
	
}
