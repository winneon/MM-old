package com.github.winneonsword.MM.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsMM;

public class PlayerInteract extends UtilsMM implements Listener {
	
	public PlayerInteract(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRightClick(PlayerInteractEvent e){
		
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
	
}
