package com.github.winneonsword.MM.events;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.ClassAbility;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;
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
			String display = meta.getDisplayName();
			String lore = meta.getLore().get(0);
			
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
			String display = meta.getDisplayName();
			String lore = meta.getLore().get(0);
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
			String display = meta.getDisplayName();
			String lore = meta.getLore().get(0);
			
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
	
	private void knockbackEntity(Player p, Entity e, double speed){
		
		Location loc = e.getLocation();	
		
		loc.setY(loc.getY() + 1);
		
		Vector vect = loc.toVector().subtract(p.getLocation().toVector()).normalize();
		
		e.setVelocity(vect.multiply(speed));
		
	}
	
}
