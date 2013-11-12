package com.github.winneonsword.MM.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	public int totalKilled;
	
	private PlayerInventory inven;
	private String clazz;
	private ClassData data = null;
	
	private int round;
	
	public UtilsGameplay(MainMM pl){
		
		super(pl);
		
	}
	
	public int getRound(){
		
		return this.round;
		
	}
	
	public int setRound(int round){
		
		this.round = round;
		
		return this.round;
		
	}
	
	public int getTotalRounds(){
		
		return 1;
		
	}
	
	public void giveGameItems(){
		
		this.clearInven();
		this.giveClassItems();
		this.giveStandardItems();
		
	}
	
	public void clearInven(){
		
		this.inven.clear();
		
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
	
	public void setTime(World world, long time){
		
		world.setTime(time);
		
	}
	
	public void setGameRule(World world, String gamerule, boolean b){
		
		String bool = String.valueOf(b);
		
		world.setGameRuleValue(gamerule, bool);
		
	}
	
	public void spawnMob(World world, int x, int y, int z, EntityType entity, int times){
		
		for (int i = 0; i < times; i++){
			
			Location loc = new Location(world, x, y, z);
			
			world.spawnEntity(loc, entity);
			
		}
		
	}
	
	public boolean checkMobType(int round, LivingEntity e){
		
		if (e.getKiller() instanceof Player){
			
			switch (round){
			
			case 1:
				
				if (e.getType() == EntityType.ZOMBIE){
					
					return true;
					
				}
				
				break;
				
			}
			
			return false;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public ItemStack getShard(){
		
		ItemStack shard = this.pl.api.invManager.makeItem(UtilsMM.AS("&d&lElemental Shard"), UtilsMM.AS("&6Right click me!"), true, Enchantment.DURABILITY, 10, 0, Material.QUARTZ, 1);
		
		return shard;
		
	}
	
	public void setVariables(Player p){
		
		this.inven = p.getInventory();
		this.clazz = this.getClass(p);
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
