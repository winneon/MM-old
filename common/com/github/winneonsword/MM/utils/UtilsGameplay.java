package com.github.winneonsword.MM.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.winneonsword.MM.ClassData;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;

public class UtilsGameplay extends UtilsMM {
	
	private PlayerInventory inven;
	private String clazz;
	private ClassData data = null;
	
	private String[] welcome;
	private int runWelcome;
	private int welcomeSlide;
	
	public UtilsGameplay(MainMM pl){
		
		super(pl);
		
	}
	
	public void giveGameItems(){
		
		this.inven.clear();
		this.giveClassItems();
		this.giveStandardItems();
		
	}
	
	public void giveClassItems(){
		
		this.inven.setArmorContents(data.getArmour());
		this.inven.setItem(0, data.getWeapon());
		
		if (this.clazz.equals("sniper")){
			
			this.inven.setItem(9, new ItemStack(Material.ARROW, 1));
			
		}
		
	}
	
	public void giveStandardItems(){
		
		this.inven.setItem(7, data.getAlphaDisc());
		this.inven.setItem(8, data.getOmegaDisc());
		
	}
	
	public void welcomePlayers(){
		
		this.welcome = UtilsMM.AS(new String[] {
				
				"Welcome to Mob Mondays!",
				"Please read carefully for the rules!",
				"No leaving the arena via any command except /mm leave!",
				"If you find a glitch, please report it to &7Winneon&d!",
				"Do &c&lNOT &dask for heals or feeds! These get distributed automatically every round!",
				"All MM related commands are located under /mm ? or /mm help!",
				"The player list is located under /mm list!",
				"To view your stats such as shards, mob kills, etc, type /mm stats or look at the sidebar!",
				"To use your alpha ability, right click the green music disc!",
				"To use your omega ability, right click the gold music disc!",
				"Remember, abilities use shards as a cost! The prices are listed /mm class <class>!",
				"To deposit the shards, you collect, just right click them!",
				"&c&lWARNING: &cMM will be difficult. Work together with your teammates to stay alive.",
				"Alright! Here we go!"
				
		});
		
		this.welcomeSlide = 0;
		
		this.runWelcome = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.pl, new Runnable(){
			
			public void run(){
				
				UtilsMM.sMM(welcome[welcomeSlide]);
				welcomeSlide++;
				
				checkWelcome();
				
			}
			
		}, 0L, 60L);
		
	}
	
	private void checkWelcome(){
		
		if (this.welcomeSlide > this.welcome.length){
			
			Bukkit.getServer().getScheduler().cancelTask(this.runWelcome);
			
		}
		
	}
	
	public void setVariables(Player p){
		
		this.inven = p.getInventory();
		this.clazz = this.getClass(p);
		
		try {
			
			this.data = new ClassData(clazz);
			
		} catch (InvalidClassException e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
