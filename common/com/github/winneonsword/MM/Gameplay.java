package com.github.winneonsword.MM;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
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
			
			this.pl.utils.setVariables(p);
			this.pl.utils.giveGameItems();
			
		}
		
		this.setGameRule(world, "doDaylightCycle", false);
		this.setTime(world, 18000L);
		this.pl.utils.setMobKills(0);
		this.pl.utils.setShards(0);
		this.pl.utils.setScoreboard(true);
		this.welcomePlayers();
		
	}
	
	public void endGame(){
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			final Player finalP = p;
			
			this.pl.utils.setVariables(p);
			this.pl.utils.clearInven();
			this.pl.utils.clearArmour();
			
			this.setGameRule(world, "doDaylightCycle", true);
			this.setTime(world, 0L);
			
			this.sMM("Mob Mondays has ended! Thank you for playing, warping you to spawn.");
			this.clearMobs(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), this.getArenaR());
			this.pl.utils.setScoreboard(false);
			
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
		this.pl.utils.setTotalKilled(0);
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
			
		}
		
		this.clearMobs(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), this.getArenaR());
		
		for (int i = 0; i < this.getPlayerList().size(); i++){
			
			String pl  = this.getPlayerList().get(i);
			Player p = Bukkit.getPlayer(pl);
			
			p.setHealth(20.0);
			p.setFoodLevel(20);
			p.setSaturation(1000);
			
		}
		
		switch (round){
		
		default:
			
			this.sMM("&cAn unknown round has started! Stopping MM.");
			
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
			
		case 3:
			
			this.sMM("Round 3 has begun!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SPIDER, 90);
			break;
			
		case 4:
			
			this.sMM("Round 4 has begun!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 60);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 60);
			break;
			
		case 5:
			
			this.sMM("The Round 5 BOSS has begun!");
			this.pl.utils.setBossSpawn(true);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 3);
			break;
			
		case 6:
			
			this.sMM("Round 6 has begun! Time to get a bit more difficult.");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SPIDER, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.BLAZE, 15);
			break;
			
		case 7:
			
			this.sMM("Round 7 has begun!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 40);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 40);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SPIDER, 40);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.BLAZE, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SLIME, 15);
			break;
			
		case 8:
			
			this.sMM("Round 8 has begun! Wow! You have gotten pretty far!");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 20);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.BLAZE, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SLIME, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.MAGMA_CUBE, 30);
			break;
			
		case 9:
			
			this.sMM("Round 9 has begun! Can you survive to face the final BOSS?");
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 60);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SKELETON, 60);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SPIDER, 60);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.BLAZE, 30);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.SLIME, 50);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.MAGMA_CUBE, 50);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.GHAST, 20);
			break;
			
		case 10:
			
			this.sMM("You've done it! The Round 10 BOSS has begun!");
			this.pl.utils.setBossSpawn(true);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.ZOMBIE, 3);
			this.spawnMob(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), EntityType.WITHER, 2);
			break;
			
		case 11:
			
			this.sMM("Just kidding! Congratulations on defeating all the rounds included in MM! Here is a gift for your accomplishment!");
			
			for (int i = 0; i < this.getPlayerList().size(); i++){
				
				String pl = this.getPlayerList().get(i);
				Player p = Bukkit.getPlayer(pl);
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " 10000");
				
			}
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					endGame();
					
				}
				
			}, 40L);
			
			break;
						
		}
		
	}
	
	public void checkRoundChange(int mobs, final int nextRound){
		
		if (mobs == this.pl.utils.getTotalKilled()){
			
			this.sMM("Round " + (nextRound - 1) + " has completed! Round " + nextRound + " will begin in 3 seconds.");
			this.clearMobs(world, this.getArenaX(), this.getArenaY(), this.getArenaZ(), this.getArenaR());
			
			this.delay(this.pl, new Runnable(){
				
				public void run(){
					
					beginRound(nextRound);
					
				}
				
			}, 60L);
			
		}
		
	}
	
	public void welcomePlayers(){
		
		this.welcome = UtilsMM.AS(new String[] {
				
				"Welcome to Mob Mondays!",
				"&c&lPlease&c read carefully for the rules!",
				"No leaving the arena via any command except /mm leave!",
				"If you find a glitch, please report it to &7Winneon&d!",
				"Do &c&lNOT&d ask for heals or feeds! These get distributed automatically every round!",
				"All MM related commands are located under /mm ? or /mm help!",
				"The player list is located under /mm list!",
				"To view your stats such as shards, mob kills, etc, look at the sidebar to the right!",
				"To use your alpha ability, right click the green music disc!",
				"To use your omega ability, right click the gold music disc!",
				"Remember, abilities use shards as a cost! The prices are listed /mm class <class>!",
				"To deposit the shards, you collect, just right click them!",
				"&c&lWARNING:&c MM will be difficult. Work together with your teammates to stay alive.",
				"Rounds 5 and 10 are BOSS rounds! These are extra difficult, so work together!",
				"Alright! Here we go! I'll make it easy for you the first round."
				
		});
		
		this.welcomeSlide = 0;
		
		this.runWelcome = this.repeat(this.pl, new Runnable(){
			
			public void run(){
				
				boolean check = checkWelcome();
				
				if (!(check)){
					
					UtilsMM.sMM(welcome[welcomeSlide]);
					welcomeSlide++;
					
				}
				
			}
			
		}, 0L, 60L);
		
	}
	
	private boolean checkWelcome(){
		
		if (this.welcomeSlide > (this.welcome.length - 1)){
			
			Bukkit.getServer().getScheduler().cancelTask(this.runWelcome);
			this.beginRound(1);
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
}
