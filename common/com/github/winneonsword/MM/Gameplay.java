package com.github.winneonsword.MM;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.winneonsword.MM.utils.UtilsGameplay;

public class Gameplay extends UtilsGameplay implements Listener {
	
	public Gameplay(MainMM pl, Player p){
		
		super(pl, p);
		
	}
	
	public void startGame(){
		
		// Warning! Do not run this method several times in a row!
		
		for (String pl : this.getPlayerList()){
			
			Player p = Bukkit.getPlayer(pl);
			
			this.giveClassItems(p);
			
		}
		
	}
	
}
