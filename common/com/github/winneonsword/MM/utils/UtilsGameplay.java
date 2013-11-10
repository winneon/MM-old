package com.github.winneonsword.MM.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	private static Player p;
	private static PlayerInventory inven = p.getInventory();
	private static String clazz = getClass(p);
	
	private static ClassData data;
	
	public UtilsGameplay(MainMM pl, Player p){
		
		super(pl);
		this.p = p;
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void giveClassItems(Player p){
		
		inven.clear();
		inven.setArmorContents(data.getArmour());
		inven.setItem(0, data.getWeapon());
		
	}
	
}
