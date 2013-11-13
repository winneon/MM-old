package com.github.winneonsword.MM.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.utils.UtilsGameplay;

public class ScoreboardMM extends UtilsGameplay implements Listener {
	
	public ScoreboardMM(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onScoreboard(ScoreboardUpdateEvent e){
		
		Player p = e.getPlayer();
		Objective mm = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		
		if (this.getScoreboard()){
			
			if (mm == null){
				
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getNewScoreboard();
				mm = board.registerNewObjective("mm", "dummy");
				
				mm.setDisplaySlot(DisplaySlot.SIDEBAR);
				
			}
			
			this.setScores(mm, p);
			
		}
		
	}
	
	private void setScores(Objective obj, Player p){
		
		this.setVariables(p);
		
		Score round = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Round:")));
		Score clazz = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Class: &e" + this.getClass(p))));
		Score shards = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&Shards:")));
		Score mobKills = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Mob Kills:")));
		
		obj.setDisplayName(this.AS("&d&lMob Mondays"));
		round.setScore(this.pl.utils.getRound());
		clazz.setScore(0);
		shards.setScore(0);
		mobKills.setScore(this.getMobKills());
		
	}
	
}
