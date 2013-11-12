package com.github.winneonsword.MM;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.winneonsword.MM.utils.UtilsGameplay;
import com.github.winneonsword.MM.utils.UtilsMM;

public class Gameplay extends UtilsGameplay implements Listener {
	
	private World world;
	
	private String[] welcome;
	private int runWelcome;
	private int welcomeSlide;
	
	public Gameplay(MainMM pl){
		
		super(pl);
		
		this.world = this.getArenaW();
		
	}
	
	public void startGame(){
		
		// Warning! Do not run this method several times in a row!
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			
			this.setVariables(p);
			this.giveGameItems();
			
		}
		
		this.setGameRule(world, "doDaylightCycle", false);
		this.setTime(world, 18000L);
		this.welcomePlayers();
		
	}
	
	public void endGame(){
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			final Player finalP = p;
			
			this.setVariables(p);
			this.clearInven();
			
			this.setGameRule(world, "doDaylightCycle", true);
			this.setTime(world, 0L);
			
			this.sMM("Mob Mondays has ended! Thank you for playing, warping you to spawn.");
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					finalP.performCommand("s");
					UtilsMM.removePlayer(finalP);
					
				}
				
			}, 40L);
			
		}
		
	}
	
	public void beginRound(int round){
		
		this.pl.utils.setRound(round);
		this.pl.utils.totalKilled = 0;
		
		this.clearMobs(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), this.getArenaR());
		
		switch (round){
		
		default:
			
			this.sMM("An unknown round has started! Stopping MM.");
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					endGame();
					
				}
				
			}, 40L);
			
			break;
			
		case 1:
			
			this.sMM("Round 1 has begun!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 50);
			break;
			
		case 2:
			
			this.sMM("Round 2 has begun!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 70);
			break;
			
		}
		
	}
	
	public void checkRoundChange(int mobs, int nextRound){
		
		if (mobs == this.pl.utils.totalKilled){
			
			this.beginRound(nextRound);
			
		}
		
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
				"Alright! Here we go! I'll make it easy for you the first round."
				
		});
		
		this.welcomeSlide = 0;
		
		this.runWelcome = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.pl, new Runnable(){
			
			public void run(){
				
				checkWelcome();
				
				UtilsMM.sMM(welcome[welcomeSlide]);
				welcomeSlide++;
				
			}
			
		}, 0L, 60L);
		
	}
	
	private void checkWelcome(){
		
		if (this.welcomeSlide == this.welcome.length){
			
			Bukkit.getServer().getScheduler().cancelTask(this.runWelcome);
			this.beginRound(1);
			
		}
		
	}
	
}
