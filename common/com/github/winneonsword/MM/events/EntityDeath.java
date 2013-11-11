package com.github.winneonsword.MM.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.winneonsword.MM.Gameplay;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsGameplay;

public class EntityDeath extends UtilsGameplay implements Listener {
	
	private Gameplay game;
	
	public EntityDeath(MainMM pl){
		
		super(pl);
		
		this.game = new Gameplay(this.pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent e){
		
		int random = this.randomize(3);
		Bukkit.broadcastMessage(String.valueOf(this.getRound()));
		
		switch (this.getRound()){
		
		case 1:
			
			this.totalKilled++;
			this.s(e.getEntity().getKiller(), "Killed: "+ totalKilled);
			this.game.checkRoundChange(50, 2);
			break;
			
		}
		
		if (this.getRound() <= this.getTotalRounds()){
			
			e.getDrops().clear();
			
			if (random == 3){
				
				e.getDrops().add(this.getShard());
				
			}
			
		}
		
	}
	
}
