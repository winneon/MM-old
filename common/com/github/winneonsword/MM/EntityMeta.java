package com.github.winneonsword.MM;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class EntityMeta {
	
	private LivingEntity entity;
	private EntityEquipment eq;
	
	public EntityMeta(LivingEntity entity){
		
		this.entity = entity;
		this.eq = this.entity.getEquipment();
		
	}
	
	public void setHelmet(ItemStack helmet){
		
		eq.setHelmet(helmet);
		
	}
	
	public void setChestplate(ItemStack chestplate){
		
		eq.setChestplate(chestplate);
		
	}
	
	public void setTrousers(ItemStack trousers){
		
		eq.setLeggings(trousers);
		
	}
	
	public void setBooties(ItemStack boots){
		
		eq.setBoots(boots);
		
	}
	
	public void setItemInHand(ItemStack item){
		
		eq.setItemInHand(item);
		
	}
	
	public void setArmour(ItemStack[] array){
		
		eq.setArmorContents(array);
		
	}
	
}
