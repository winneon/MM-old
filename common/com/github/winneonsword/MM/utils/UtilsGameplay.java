package com.github.winneonsword.MM.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	public HashMap<Projectile, Boolean> fireball = new HashMap<Projectile, Boolean>();
	
	private Player p;
	private PlayerInventory inven;
	private String clazz;
	private ClassData data = null;
	
	private boolean scoreboard;
	private int round;
	
	private int totalKilled;
	private int mobKills;
	private int shards;
	
	private boolean oneHitKO;
	private int knockback;
	
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
	
	public int getTotalKilled(){
		
		return this.totalKilled;
		
	}
	
	public void setTotalKilled(int killed){
		
		this.totalKilled = killed;
		
	}
	
	public void incrementTotalKilled(int times){
		
		this.totalKilled += times;
		
	}
	
	public int getShards(){
		
		return this.shards;
		
	}
	
	public void setShards(int shards){
		
		this.shards = shards;
		this.pl.datacore.set("Users." + this.p.getName() + ".shards", this.shards);
		this.pl.saveYMLs();
		
	}
	
	public void incrementShards(int times){
		
		this.shards += times;
		this.pl.datacore.set("Users." + this.p.getName() + ".shards", this.shards);
		this.pl.saveYMLs();
		
	}
	
	public boolean getScoreboard(){
		
		return this.scoreboard;
		
	}
	
	public void setScoreboard(boolean bool){
		
		this.scoreboard = bool;
		
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
	
	public boolean getOneHitKO(){
		
		return this.oneHitKO;
		
	}
	
	public void setOneHitKO(boolean bool){
		
		this.oneHitKO = bool;
		this.pl.datacore.set("Users." + p.getName() + ".oneHitKO", bool);
		this.pl.saveYMLs();
		
	}
	
	public int getKnockbackCounter(){
		
		return this.knockback;
		
	}
	
	public void setKnockbackCounter(int counter){
		
		this.knockback = counter;
		this.pl.datacore.set("Users." + p.getName() + ".knockback", this.knockback);
		this.pl.saveYMLs();
		
	}
	
	public void incrementKnockbackCounter(int times){
		
		this.knockback += times;
		this.pl.datacore.set("Users." + p.getName() + ".knockback", this.knockback);
		this.pl.saveYMLs();
		
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
	
	// This method goes to Rprrr on the Bukkit forums!
	
	public static List<Location> makeCircle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
		
		List<Location> circleblocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		
		for (int x = cx - r; x <= cx + r; x++)
			
			for (int z = cz - r; z <= cz + r; z++)
				
				for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
					
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					
					if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
						
						Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						circleblocks.add(l);
						
					}
					
				}
		
		return circleblocks;
		
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
	
	public ItemStack getShardItem(){
		
		ItemStack shard = this.pl.invManager.makeItem(UtilsMM.AS("&d&lElemental Shard"), UtilsMM.AS("&6Right click me!"), true, Enchantment.DURABILITY, 10, 0, Material.QUARTZ, 1);
		
		return shard;
		
	}
	
	public ItemStack getWarriorAxe(){
		
		ItemStack axe = this.pl.invManager.makeItem(this.AS("&e&lWarrior Axe"), this.AS("&6Use this to knockback mobs!"), true, Enchantment.DURABILITY, 10, 0, Material.DIAMOND_AXE, 1);
		
		return axe;
		
	}
	
	public ItemStack getInfernoWand(String type){
		
		ItemStack wand = null;
		
		if (type.equals("alpha")){
			
			wand = this.pl.invManager.makeItem(this.AS("&e&lInferno Wand"), this.AS("&6Use this to shoot fireballs!"), true, Enchantment.DURABILITY, 10, 0, Material.STICK, 1);
			
		}
		
		if (type.equals("omega")){
			
			wand = this.pl.invManager.makeItem(this.AS("&e&lInferno Wand"), this.AS("&6Use this to forge a fire ring around you!"), true, Enchantment.DURABILITY, 10, 0, Material.STICK, 1);
			
		}
		
		return wand;
		
	}
	
	public void setVariables(Player p){
		
		this.p = p;
		this.inven = p.getInventory();
		this.clazz = this.getClass(p);
		this.mobKills = this.pl.datacore.getInt("Users." + p.getName() + ".mobKills");
		this.shards = this.pl.datacore.getInt("Users." + p.getName() + ".shards");
		this.oneHitKO = this.pl.datacore.getBoolean("Users." + p.getName() + ".oneHitKO");
		this.knockback = this.pl.datacore.getInt("Users." + p.getName() + ".knockback");
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	private void setVariables(){
		
		this.scoreboard = false;
		
	}
	
}
