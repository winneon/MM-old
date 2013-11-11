package com.github.winneonsword.MM.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	private Player p;
	private PlayerInventory inven;
	private String clazz;
	private ClassData data = null;
	
	public UtilsGameplay(MainMM pl){
		
		super(pl);
		
	}
	
	public void giveGameItems(){
		
		this.inven.clear();
		this.giveClassItems();
		this.giveStandardItems();
		
	}
	
	public void giveClassItems(){
		
		this.inven.setArmorContents(data.getArmour());
		this.inven.setItem(0, data.getWeapon());
		
		if (this.clazz.equals("sniper")){
			
			this.inven.setItem(9, new ItemStack(Material.ARROW, 1));
			
		}
		
	}
	
	public void giveStandardItems(){
		
		this.inven.setItem(7, data.getAlphaDisc());
		this.inven.setItem(8, data.getOmegaDisc());
		
	}
	
	public void setVariables(Player p){
		
		this.p = p;
		this.inven = this.p.getInventory();
		this.clazz = this.getClass(p);
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
