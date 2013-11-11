package com.github.winneonsword.MM;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsMM;

public class ClassData {
	
	private String name;
	
	private ItemStack weapon;
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack trousers;
	private ItemStack booties;
	
	private ItemStack[] armour;
	
	private ItemStack alpha;
	private ItemStack omega;
	
	public ClassData(String name) throws InvalidClassException {
		
		List<String> c = UtilsMM.getClassList();
		
		if (c.contains(name)){
			
			this.name = name;
			this.weapon = UtilsMM.pl.datacore.getItemStack("Classes." + this.name + ".data.weapon");
			this.helmet = UtilsMM.pl.datacore.getItemStack("Classes." + this.name + ".data.helmet");
			this.chestplate = UtilsMM.pl.datacore.getItemStack("Classes." + this.name + ".data.chestplate");
			this.trousers = UtilsMM.pl.datacore.getItemStack("Classes." + this.name + ".data.trousers");
			this.booties = UtilsMM.pl.datacore.getItemStack("Classes." + this.name + ".data.booties");
			
			this.armour = new ItemStack[] {
					
					helmet, chestplate, trousers, booties
					
			};
			
			ArrayUtils.reverse(armour);
			
			this.alpha = UtilsMM.pl.api.invManager.makeItem(UtilsMM.AS("&2&lAlpha Ability"), UtilsMM.AS("&6Right click me!"), false, Enchantment.DURABILITY, 10, 0, Material.GREEN_RECORD, 1);
			this.omega = UtilsMM.pl.api.invManager.makeItem(UtilsMM.AS("&6&lOmega Ability"), UtilsMM.AS("&6Right click me!"), false, Enchantment.DURABILITY, 10, 0, Material.GOLD_RECORD, 1);
			
		} else {
			
			throw new InvalidClassException(name);
			
		}
		
	}
	
	public ItemStack getWeapon(){
		
		return this.weapon;
		
	}
	
	public ItemStack getHelmet(){
		
		return this.helmet;
	}
	
	public ItemStack getChestplate(){
		
		return this.chestplate;
		
	}
	
	public ItemStack getTrousers(){
		
		return this.trousers;
		
	}
	
	public ItemStack getBooties(){
		
		return this.booties;
		
	}
	
	public ItemStack[] getArmour(){
		
		return this.armour;
		
	}
	
	public ItemStack getAlphaDisc(){
		
		return this.alpha;
		
	}
	
	public ItemStack getOmegaDisc(){
		
		return this.omega;
		
	}
	
}
