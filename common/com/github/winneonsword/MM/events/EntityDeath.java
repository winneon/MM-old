package com.github.winneonsword.MM.events;

import org.bukkit.entity.Player;
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
		
		if (e.getEntity().getKiller() instanceof Player){
			
			Player p = e.getEntity().getKiller();
			
			switch (this.pl.utils.getRound()){
			
			case 1:
				
				if (this.checkMobType(this.pl.utils.getRound(), e.getEntity())){
					
					this.pl.utils.totalKilled++;
					this.s(p, "Killed: "+ this.pl.utils.totalKilled);
					this.game.checkRoundChange(50, 2);
					
				}
				
				break;
				
			case 2:
				
				if (this.checkMobType(this.pl.utils.getRound(), e.getEntity())){
					
					this.pl.utils.totalKilled++;
					this.s(p, "Killed: " + this.pl.utils.totalKilled);
					this.game.checkRoundChange(70, 3);
					
				}
				
				break;
				
			case 3:
				
				if (this.checkMobType(this.pl.utils.getRound(), e.getEntity())){
					
					this.pl.utils.totalKilled++;
					this.s(p, "Killed: " + this.pl.utils.totalKilled);
					this.game.checkRoundChange(90, 4);
					
				}
				
				break;
				
			}
			
			if (this.pl.utils.getRound() != 0 && this.pl.utils.getRound() <= this.pl.utils.getTotalRounds() && e.getEntity().getLocation().distance(this.getArena()) <= this.getArenaR()){
				
				e.getDrops().clear();
				
				if (random == 3){
					
					e.getDrops().add(this.getShard());
					
				}
				
			}
			
		}
		
	}
	
}
