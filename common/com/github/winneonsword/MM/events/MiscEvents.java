package com.github.winneonsword.MM.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsMM;

public class MiscEvents extends UtilsMM implements Listener {
	
	public MiscEvents(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogout(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		
		if (this.getPlayerList().contains(p.getName())){
			
			this.removePlayer(p);
			this.sMM(p.getDisplayName() + " &dhas left the arena because of a logout!");
			
		}
		
		this.saveVariables();
		
	}
	
}
