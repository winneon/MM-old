package com.github.winneonsword.MM.events;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.github.winneonsword.MM.EntityMeta;
import com.github.winneonsword.MM.Gameplay;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsMM;

public class EntityDeath extends UtilsMM implements Listener {
	
	private Gameplay game;
	
	public EntityDeath(MainMM pl){
		
		super(pl);
		
		this.game = new Gameplay(this.pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMobKill(EntityDeathEvent e){
		
		int random = this.randomize(3);
		LivingEntity ent = e.getEntity();
		
		if (ent.getKiller() instanceof Player){
			
			Player p = ent.getKiller();
			int round = this.pl.utils.getRound();
			
			switch (round){
			
			case 1:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(50, 2);
					
				}
				
				break;
				
			case 2:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(70, 3);
					
				}
				
				break;
				
			case 3:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(90, 4);
					
				}
				
				break;
				
			case 4:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(120, 5);
					
				}
				
				break;
				
			case 5:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(3, 6);
					
				}
				
				break;
				
			case 6:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(105, 7);
					
				}
				
				break;
				
			case 7:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(165, 8);
					
				}
				
				break;
				
			case 8:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(110, 9);
					
				}
				
				break;
				
			case 9:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(330, 10);
					
				}
				
				break;
				
			case 10:
				
				if (this.pl.utils.checkMobType(round, ent)){
					
					this.incrementKills(p);
					this.game.checkRoundChange(5, 11);
					
				}
				
				break;
				
			}
			
			if (this.pl.utils.getRound() != 0 && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds() && e.getEntity().getLocation().distance(this.getArena()) <= this.getArenaR()){
				
				EntityMeta meta = new EntityMeta(ent);
				
				meta.setArmour(new ItemStack[] {
						
						new ItemStack(Material.AIR, 1),
						new ItemStack(Material.AIR, 1),
						new ItemStack(Material.AIR, 1),
						new ItemStack(Material.AIR, 1)
						
				});
				
				e.getDrops().clear();
				
				if (random == 3){
					
					e.getDrops().add(this.pl.utils.getShardItem());
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDamageEvent e){
		
		if (!(e.getCause() == DamageCause.ENTITY_ATTACK) && this.pl.utils.getRound() != 0 && this.pl.utils.getRound() <= 10){
			
			LivingEntity ent = (LivingEntity) e.getEntity();
			
			if (e.getDamage() >= ent.getHealth()){
				
				e.setCancelled(true);
				ent.setHealth(ent.getMaxHealth());
				ent.teleport(this.pl.utils.getArena());
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e){
		
		Player p = e.getEntity();
		
		if (this.pl.utils.getRound() != 0 && this.pl.utils.getRound() <= 10 && p.getLocation().distance(this.pl.utils.getArena()) <= this.pl.utils.getArenaR()){
			
			this.sMM(p.getDisplayName() + " &dhas died in Mob Mondays! Warping them to spawn.");
			this.removePlayer(p);
			
			p.getInventory().setArmorContents(new ItemStack[] {
					
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1)
					
			});
			
			e.getDrops().clear();			
			p.performCommand("s");
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBossDeath(EntityDeathEvent e){
		
		LivingEntity ent = e.getEntity();
		String display = ent.getCustomName();
		
		if (display == null){
			
			display = "";
			
		}
		
		if (display.equals(this.AS("&b&lBOSS"))){
			
			List<Location> circle = this.pl.utils.makeCircle(ent.getLocation(), 5, 1, true, false, 0);
			EntityMeta meta = new EntityMeta(ent);
			
			meta.setArmour(new ItemStack[] {
					
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1),
					new ItemStack(Material.AIR, 1)
					
			});
			
			e.getDrops().clear();
			
			for (Location loc : circle){
				
				loc = this.checkForAir(loc);
				
				ent.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
				
			}
			
			ent.getWorld().playSound(ent.getLocation(), Sound.WITHER_DEATH, 1.0F, 1.0F);
			
			for (int i = 0; i < 20; i++){
				
				e.getDrops().add(this.pl.utils.getShardItem());
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFireballDamage(EntityDamageByEntityEvent e){
		
		DamageCause cause = e.getCause();
		
		if (cause == DamageCause.PROJECTILE){
			
			Projectile p = (Projectile) e.getDamager();
			
			if (this.pl.utils.fireball.containsKey(p)){
				
				this.pl.utils.fireball.remove(p);
				e.getEntity().setFireTicks(100);
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onOneHitKO(EntityDamageByEntityEvent e){
		
		if (e.getDamager() instanceof Player){
			
			Player p = (Player) e.getDamager();
			
			this.pl.utils.setVariables(p);
			
			if (this.pl.utils.getOneHitKO()){
				
				e.setDamage(1000.0);
				
			}
			
		}
		
		if (e.getDamager() instanceof Arrow){
			
			Arrow arrow = (Arrow) e.getDamager();
			
			if (arrow.getShooter() instanceof Player){
				
				Player p = (Player) arrow.getShooter();
				
				this.pl.utils.setVariables(p);
				
				if (this.pl.utils.getOneHitKO()){
					
					e.setDamage(1000.0);
					
				
				}
				
			}
			
		}
		
	}
	
	private void incrementKills(Player p){
		
		this.pl.utils.setVariables(p);
		this.pl.utils.incrementMobKills(1);
		this.pl.utils.incrementTotalKilled(1);
		
	}
	
}
