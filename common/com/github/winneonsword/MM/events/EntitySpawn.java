package com.github.winneonsword.MM.events;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.winneonsword.MM.EntityMeta;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsMM;

public class EntitySpawn extends UtilsMM implements Listener {
	
	private int counter = 0;
	
	public EntitySpawn(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent e){
		
		boolean check = this.pl.utils.getBossSpawn();
		
		if (check){
			
			LivingEntity ent = e.getEntity();
			EntityMeta entMeta = new EntityMeta(ent);
			ItemStack[] armour = this.pl.utils.getBossArmour();
			
			ArrayUtils.reverse(armour);			
			entMeta.setArmour(armour);
			ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999 * 20, 2));
			ent.setCustomName(this.AS("&b&lBOSS"));
			
			this.counter++;
			
			if (this.counter == 3){
				
				this.counter = 0;
				
				this.pl.utils.setBossSpawn(false);
				
			}
			
		}
		
	}
	
}
