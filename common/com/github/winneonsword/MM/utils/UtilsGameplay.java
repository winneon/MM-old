package com.github.winneonsword.MM.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	public int totalKilled;
	public int mobKills;
	
	private Player p;
	private PlayerInventory inven;
	private String clazz;
	private ClassData data = null;
	
	private int round;
	
	public UtilsGameplay(MainMM pl){
		
		super(pl);
		
		this.setVariables();
		
	}
	
	public int getRound(){
		
		return this.round;
		
	}
	
	public int setRound(int round){
		
		this.round = round;
		
		return this.round;
		
	}
	
	public int getTotalRounds(){
		
		return 4;
		
	}
	
	public void giveGameItems(){
		
		this.clearInven();
		this.clearArmour();
		this.giveClassItems();
		this.giveStandardItems();
		
	}
	
	public void clearInven(){
		
		this.inven.clear();
		
	}
	
	public void clearArmour(){
		
		this.inven.setArmorContents(new ItemStack[] {
				
				new ItemStack(Material.AIR, 1),
				new ItemStack(Material.AIR, 1),
				new ItemStack(Material.AIR, 1),
				new ItemStack(Material.AIR, 1)
				
		});
		
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
	
	public int getMobKills(){
		
		return this.mobKills;
		
	}
	
	public void setMobKills(int kills){
		
		this.mobKills = kills;
		this.pl.datacore.set("Users." + this.p.getName() + ".mobKills", this.mobKills);
		this.pl.saveYMLs();
		
	}
	
	public void incrementMobKills(int times){
		
		this.mobKills += times;
		this.pl.datacore.set("Users." + this.p.getName() + ".mobKills", this.mobKills);
		this.pl.saveYMLs();
		
	}
	
	public boolean getScoreboard(){
		
		return UtilsMisc.scoreboard;
		
	}
	
	public void setScoreboard(boolean bool){
		
		UtilsMisc.scoreboard = bool;
		
		for (int i = 0; i < getPlayerList().size(); i++){
			
			String pl = getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
			
		}
		
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
	
	public void clearMobs(World world, int x, int y, int z, double radius){
		
		Location loc = new Location(world, x, y, z);
		
		for (Entity e : world.getEntities()){
			
			if (e.getLocation().distance(loc) <= radius && !(e instanceof Player)){
				
				e.remove();
				
			}
			
		}
		
	}
	
	public boolean checkMobType(int round, LivingEntity e){
		
		if (e.getLocation().distance(this.getArena()) <= this.getArenaR()){
			
			EntityType ent = e.getType();
			
			switch (round){
			
			case 1:
				
				if (ent == EntityType.ZOMBIE){
					
					return true;
					
				}
				
				break;
				
			case 2:
				
				if (ent == EntityType.SKELETON){
					
					return true;
					
				}
				
				break;
				
			case 3:
				
				if (ent == EntityType.SPIDER){
					
					return true;
					
				}
				
				break;
				
			case 4:
				
				if (ent == EntityType.ZOMBIE || ent == EntityType.SKELETON){
					
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
		
		this.p = p;
		this.inven = p.getInventory();
		this.clazz = this.getClass(p);
		this.mobKills = this.pl.datacore.getInt("Users." + p.getName() + ".mobKills");
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	private void setVariables(){
		
		UtilsMisc.scoreboard = false;
		
	}
	
}
