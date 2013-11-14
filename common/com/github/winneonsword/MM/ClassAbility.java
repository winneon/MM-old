package com.github.winneonsword.MM;

import java.util.List;

import org.bukkit.entity.Player;

import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsMM;

public class ClassAbility extends UtilsMM{
	
	private String name;
	private Player p;
	
	public ClassAbility(MainMM pl, Player p, String name) throws InvalidClassException {
		
		super(pl);
		
		List<String> c = this.getClassList();
		
		if (c.contains(name)){
			
			this.name = name;
			this.p = p;
			
		}		
		
	}
	
	public boolean getAlphaAbility(){
		
		return this.alphaAbility(p, name);
		
	}
	
	public boolean getOmegaAbility(){
		
		return this.omegaAbility(p, name);
		
	}
	
	private boolean alphaAbility(Player p, String name){
		
		switch (name){
		
		case "medic":
			
			
			break;
			
		case "spirit":
			
			
			break;
			
		case "warrior":
			
			
			break;
			
		case "inferno":
			
			
			break;
			
		case "roadrunner":
			
			
			break;
			
		case "sniper":
			
			
			break;
			
		}
		
		return true;
		
	}
	
	private boolean omegaAbility(Player p, String name){
		
		switch (name){
		
		
		
		}
		
		return true;
		
	}
	
}
