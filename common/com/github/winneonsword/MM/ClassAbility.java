package com.github.winneonsword.MM;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsMM;

@SuppressWarnings("deprecation")
public class ClassAbility extends UtilsMM{
	
	private String name;
	private Player p;
	
	public ClassAbility(MainMM pl, Player p, String name) throws InvalidClassException {
		
		super(pl);
		
		List<String> c = this.getClassList();
		
		if (c.contains(name)){
			
			this.name = name;
			this.p = p;
			
		} else {
			
			throw new InvalidClassException(name);
			
		}
		
	}
	
	public boolean getAlphaAbility(){
		
		return this.alphaAbility(p, name);
		
	}
	
	public boolean getOmegaAbility(){
		
		return this.omegaAbility(p, name);
		
	}
	
	private boolean alphaAbility(Player p, String name){
		
		final Player finalP = p;
		final PlayerInventory inven = p.getInventory();
		
		switch (name){
		
		case "medic":
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 2));
			this.s(p, "You have given yourself &6Regeneration III &dfor one minute!");
			this.s(p, "&c5 shards have been withdrawn.");
			break;
			
		case "spirit":
			
			p.setAllowFlight(true);
			this.s(p, "You have given yourself &6fly mode &dfor one minute!");
			this.s(p, "&c5 shards have been withdrawn.");
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					finalP.setAllowFlight(false);
					finalP.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 4));
					UtilsMM.s(finalP, "Your &6fly mode &dhas worn off!");
					
				}
				
			}, 1200);
			
			break;
			
		case "warrior":
			
			this.s(p, "You can now &61 - Hit KO &dany mob besides bosses for 10 seconds!");
			this.s(p, "&c5 shards have been withdrawn.");
			this.pl.utils.setVariables(p);
			this.pl.utils.setOneHitKO(true);
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					UtilsMM.s(finalP, "Your &61 - Hit KO &dability has worn off!");
					UtilsMM.pl.utils.setOneHitKO(false);
					
				}
				
			}, 200);
			
			break;
			
		case "inferno":
			
			this.s(p, "You have been given an &6Inferno Wand &dthat allows you to shoot fireballs on click for 30 seconds!");
			this.s(p, "&c5 shards have been withdrawn.");
			inven.addItem(this.pl.utils.getInfernoWand("alpha"));
			p.updateInventory();
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					inven.remove(Material.STICK);
					UtilsMM.s(finalP, "Your &6Inferno Wand &dhas worn out!");
					
				}
				
			}, 600L);
			
			break;
			
		case "roadrunner":
			
			
			break;
			
		case "sniper":
			
			
			break;
			
		}
		
		return true;
		
	}
	
	private boolean omegaAbility(Player p, String name){
		
		final Player finalP = p;
		PlayerInventory inven = p.getInventory();
		
		switch (name){
		
		case "medic":
			
			this.s(p, "You have given everyone &6Regeneration III &dfor one minute!");
			this.s(p, "&c8 shards have been withdrawn.");
			
			for (int i = 0; i < this.getPlayerList().size(); i++){
				
				String pl = this.getPlayerList().get(i);
				Player player = Bukkit.getPlayer(pl);
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 2));
				
				if (player != p){
					
					this.s(player, p.getDisplayName() + " &dhas given you &6Regeneration III &dfor one minute!");
					
				}
				
			}
			
			break;
			
		case "spirit":
			
			this.s(p, "You have given everyone &6fly mode &dfor one minute!");
			this.s(p, "&c8 shards have been withdrawn.");
			
			for (int i = 0; i < this.getPlayerList().size(); i++){
				
				String pl = this.getPlayerList().get(i);
				final Player player = Bukkit.getPlayer(pl);
				
				player.setAllowFlight(true);
				
				if (player != p){
					
					this.s(player, p.getDisplayName() + " &dhas given you &6fly mode &dfor one minute!");
					
				}
				
				this.delay(this.pl, new Runnable(){
					
					public void run(){
						
						player.setAllowFlight(false);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 4));
						UtilsMM.s(finalP, "Your &6fly mode &dhas worn off!");
						
					}
					
				}, 1200);
				
			}
			
			break;
			
		case "warrior":
			
			this.s(p, "You have been given a &6Warrior Axe &dthat knockbacks all mobs around you on click! (3 time use.)");
			this.s(p, "&c8 shards have been withdrawn.");
			inven.addItem(this.pl.utils.getWarriorAxe());
			p.updateInventory();
			break;
			
		case "inferno":
			
			this.s(p, "You have been given an &6Inferno Wand &dthat allows you to forge a fire ring around you! (1 time use.)");
			this.s(p, "&c8 shards have been withdrawn.");
			inven.addItem(this.pl.utils.getInfernoWand("omega"));
			p.updateInventory();
			break;
			
		case "roadrunner":
			
			
			break;
			
		case "sniper":
			
			
			break;
			
		}
		
		return true;
		
	}
	
}
