package com.github.winneonsword.MM;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.winneonsword.MM.utils.UtilsGameplay;

public class Gameplay extends UtilsGameplay implements Listener {
	
	public Gameplay(MainMM pl){
		
		super(pl);
		
	}
	
	public void startGame(Player p){
		
		// Warning! Do not run this method several times in a row!
		
		this.distributeItems();
		
	}
	
}
