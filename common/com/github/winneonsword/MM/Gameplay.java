package com.github.winneonsword.MM;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.winneonsword.MM.utils.UtilsGameplay;

public class Gameplay extends UtilsGameplay implements Listener {
	
	public Gameplay(MainMM pl){
		
		super(pl);
		
	}
	
	public void startGame(){
		
		// Warning! Do not run this method several times in a row!
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			
			this.setVariables(p);
			this.giveGameItems();
			
		}
		
	}
	
}
