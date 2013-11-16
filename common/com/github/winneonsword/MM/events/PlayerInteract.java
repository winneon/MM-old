package com.github.winneonsword.MM.events;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.ClassAbility;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsGameplay;
import com.github.winneonsword.MM.utils.UtilsMM;

public class PlayerInteract extends UtilsMM implements Listener {
	
	public PlayerInteract(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerShard(PlayerInteractEvent e){
		
		Action act = e.getAction();
		
		if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK){
			
			Player p = e.getPlayer();
			PlayerInventory inven = p.getInventory();
			ItemStack item = inven.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			String display = "";
			
			if (meta.hasDisplayName()){
				
				display = meta.getDisplayName();
				
			}
			
			String lore = "";
			
			if (meta.hasLore()){
				
				lore = meta.getLore().get(0);
				
			}
			
			if (display.equals(this.AS("&d&lElemental Shard")) && lore.equals(this.AS("&6Right click me!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				int amount = item.getAmount();
				
				this.pl.utils.setVariables(p);
				this.pl.utils.incrementShards(amount);
				
				inven.setItemInHand(new ItemStack(Material.AIR, 1));
				
				if (amount == 1){
					
					this.s(p, "You have desposited &61 &dshard!");
					
				} else {
					
					this.s(p, "You have desposited &6" + amount + " &dshards!");
					
				}
				
				Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerAbility(PlayerInteractEvent e){
		
		Action act = e.getAction();
		
		if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK){
			
			Player p = e.getPlayer();
			PlayerInventory inven = p.getInventory();
			ItemStack item = inven.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			String display = "";
			
			if (meta.hasDisplayName()){
				
				display = meta.getDisplayName();
				
			}
			
			String lore = "";
			
			if (meta.hasLore()){
				
				lore = meta.getLore().get(0);
				
			}
			
			ClassAbility ability = null;
			
			if (display.equals(this.AS("&2&lAlpha Ability")) && lore.equals(this.AS("&6Right click me!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				this.pl.utils.setVariables(p);
				
				if (this.pl.utils.getShards() < 5){
					
					this.s(p, "&cYou do not have 5 shards deposited!");
					
				} else {
					
					try {
						
						ability = new ClassAbility(this.pl, p, this.getClass(p));
						
					} catch (InvalidClassException i){
						
						Bukkit.getLogger().log(Level.SEVERE, "Failed to run " + p.getName() + "'s alpha ability.");
						
						return;
						
					}
					
					this.pl.utils.setShards(this.pl.utils.getShards() - 5);
					Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
					ability.getAlphaAbility();
					
				}
				
			}
			
			if (display.equals(this.AS("&6&lOmega Ability")) && lore.equals(this.AS("&6Right click me!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				this.pl.utils.setVariables(p);
				
				if (this.pl.utils.getShards() < 8){
					
					this.s(p, "&cYou do not have 8 shards deposited!");
					
				} else {
					
					try {
						
						ability = new ClassAbility(this.pl, p, this.getClass(p));
						
					} catch (InvalidClassException i){
						
						Bukkit.getLogger().log(Level.SEVERE, "Failed to run " + p.getName() + "'s omega ability.");
						
						return;
						
					}
					
					this.pl.utils.setShards(this.pl.utils.getShards() - 8);
					Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
					ability.getOmegaAbility();
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityKnockback(PlayerInteractEvent e){
		
		Action act = e.getAction();
		
		if (act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK){
			
			Player p = e.getPlayer();
			World world = p.getWorld();
			PlayerInventory inven = p.getInventory();
			ItemStack item = inven.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			String display = "";
			
			if (meta.hasDisplayName()){
				
				display = meta.getDisplayName();
				
			}
			
			String lore = "";
			
			if (meta.hasLore()){
				
				lore = meta.getLore().get(0);
				
			}
			
			if (display.equals(this.AS("&e&lWarrior Axe")) && lore.equals(this.AS("&6Use this to knockback mobs!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				this.pl.utils.setVariables(p);
				
				for (Entity ent : world.getEntities()){
					
					if (ent.getLocation().distance(p.getLocation()) <= 10 && !(ent instanceof Player)){
						
						this.knockbackEntity(p, ent, 2);
						
					}
					
				}
				
				int knockback = this.pl.utils.getKnockbackCounter();
				
				if (knockback < 2){
					
					this.pl.utils.incrementKnockbackCounter(1);
					this.s(p, "You have &6" + (3 - this.pl.utils.getKnockbackCounter()) + " &duses left on your &6Warrior Axe&d!");
					
				} else {
					
					this.pl.utils.setKnockbackCounter(0);
					this.s(p, "Your &6Warrior Axe &dhas run out of uses!");
					inven.setItemInHand(new ItemStack(Material.AIR, 1));
					
				}
				
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInfernoAbility(PlayerInteractEvent e){
		
		Action act = e.getAction();
		
		if (act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK){
			
			final Player p = e.getPlayer();
			World world = p.getWorld();
			PlayerInventory inven = p.getInventory();
			ItemStack item = inven.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			String display = "";
			String lore = "";
			
			if (meta.hasDisplayName()){
				
				display = meta.getDisplayName();
				
			}
			
			if (meta.hasLore()){
				
				lore = meta.getLore().get(0);
				
			}
			
			if (display.equals(this.AS("&e&lInferno Wand")) && lore.equals(this.AS("&6Use this to shoot fireballs!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				Location eye = p.getEyeLocation();
				Vector vect = eye.getDirection().multiply(2);
				final Projectile projectile = world.spawn(eye.add(vect.getX(), vect.getY(), vect.getZ()), Fireball.class);
				
				this.pl.utils.fireball.put(projectile, true);
				projectile.setShooter(p);
				projectile.setVelocity(vect);
				world.playSound(p.getLocation(), Sound.GHAST_FIREBALL, 1.0F, 1.0F);
				
				this.delay(this.pl, new Runnable(){
					
					public void run(){
						
						if (projectile != null){
							
							pl.utils.fireball.remove(projectile);
							projectile.remove();
							
						}
						
					}
					
				}, 100L);
				
			}
			
			if (display.equals(this.AS("&e&lInferno Wand")) && lore.equals(this.AS("&6Use this to forge a fire ring around you!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
				
				List<Location> circle = UtilsGameplay.makeCircle(p.getLocation(), 5, 1, true, false, 0);
				List<Location> circle2 = UtilsGameplay.makeCircle(p.getLocation(), 4, 1, true, false, 0);
				
				for (Location loc : circle){
					
					loc = this.checkForAir(loc);
					final Location loc2 = loc;
					
					for (int i = 0; i < 3; i++){
						
						world.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
						world.playSound(loc, Sound.GHAST_FIREBALL, 1.0F, 0.5F);
						
					}
					
					loc.getBlock().setType(Material.FIRE);
					
					this.delay(this.pl, new Runnable(){
						
						public void run(){
							
							if (loc2.getBlock().getType() == Material.FIRE){
								
								loc2.getBlock().setType(Material.AIR);
								
							}
							
						}
						
					}, 200L);
					
				}
				
				for (Location loc : circle2){
					
					loc = this.checkForAir(loc);
					final Location loc2 = loc;
					
					loc.getBlock().setType(Material.FIRE);
					
					this.delay(this.pl, new Runnable(){
						
						public void run(){
							
							if (loc2.getBlock().getType() == Material.FIRE){
								
								loc2.getBlock().setType(Material.AIR);
								
							}
							
						}
						
					}, 200L);
					
				}
				
				this.s(p, "Your &6Inferno Wand &dhas run out of uses!");
				inven.setItemInHand(new ItemStack(Material.AIR, 1));
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onSniperOmega(ProjectileHitEvent e){
		
		Player p = (Player) e.getEntity().getShooter();
		PlayerInventory inven = p.getInventory();
		ItemStack item = inven.getItemInHand();
		ItemMeta meta = item.getItemMeta();
		String display = "";
		
		if (meta.hasDisplayName()){
			
			display = meta.getDisplayName();
			
		}
		
		String lore = "";
		
		if (meta.hasLore()){
			
			lore = meta.getLore().get(0);
			
		}
		
		if (display.equals(this.AS("&e&lExplosive Bow")) && lore.equals(this.AS("&6Use this to fire an explosive arrow!")) && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds()){
			
			
			
		}
		
	}
	
	private void knockbackEntity(Player p, Entity e, double speed){
		
		Location loc = e.getLocation();	
		
		loc.setY(loc.getY() + 1);
		
		Vector vect = loc.toVector().subtract(p.getLocation().toVector()).normalize();
		
		e.setVelocity(vect.multiply(speed));
		
	}
	
}
