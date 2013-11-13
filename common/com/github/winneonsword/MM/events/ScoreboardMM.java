package com.github.winneonsword.MM.events;

import org.apache.commons.lang.WordUtils;
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
		
		if (this.getScoreboard()){
			
			Scoreboard board = p.getScoreboard();
			Objective mm = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
			
			this.pl.utils.setVariables(p);
			
			if (mm == null){
				
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				board = manager.getNewScoreboard();
				mm = board.registerNewObjective("mm", "dummy");
				
				mm.setDisplaySlot(DisplaySlot.SIDEBAR);
				this.pl.utils.setMobKills(0);
				
			}
			
			this.setScores(mm, p);
			p.setScoreboard(board);
			
		} else {
			
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			
		}
		
	}
	
	private void setScores(Objective obj, Player p){
		
		Score round = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Round:")));
		Score clazz = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&e" + WordUtils.capitalize(this.getClass(p)))));
		
		if (this.getClass(p).equals("roadrunner")){
			
			clazz = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&eRR")));
			
		}
		
		Score shards = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Shards:")));
		Score mobKills = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Mob Kills:")));
		
		obj.setDisplayName(this.AS("&d&lMob Mondays"));
		round.setScore(this.pl.utils.getRound());
		clazz.setScore(0);
		shards.setScore(0);
		mobKills.setScore(this.pl.utils.getMobKills());
		
	}
	
}
