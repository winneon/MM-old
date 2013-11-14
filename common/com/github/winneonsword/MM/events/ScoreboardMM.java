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
import com.github.winneonsword.MM.utils.UtilsMM;

public class ScoreboardMM extends UtilsMM implements Listener {
	
	public ScoreboardMM(MainMM pl){
		
		super(pl);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onScoreboard(ScoreboardUpdateEvent e){
		
		Player p = e.getPlayer();
		Scoreboard board = p.getScoreboard();
		Objective mm = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		
		if (this.pl.utils.getScoreboard()){
			
			if (mm == null){
				
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				board = manager.getNewScoreboard();
				mm = board.registerNewObjective("mm", "dummy");
				
				mm.setDisplaySlot(DisplaySlot.SIDEBAR);
				
			}
			
			this.pl.utils.setVariables(p);
			this.setScores(mm, p);
			p.setScoreboard(board);
			
		} else {
			
			if (mm != null){
				
				if (mm.getDisplayName().startsWith(this.AS("&e&l"))){
					
					p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
					
				}
				
			}
			
		}
		
	}
	
	private void setScores(Objective obj, Player p){
		
		Score round = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Round:")));
		Score shards = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Shards:")));
		Score mobKills = obj.getScore(Bukkit.getOfflinePlayer(this.AS("&6Mob Kills:")));
		
		obj.setDisplayName(this.AS("&e&l" + WordUtils.capitalize(this.getClass(p))));
		round.setScore(this.pl.utils.getRound());
		shards.setScore(this.pl.utils.getShards());
		mobKills.setScore(this.pl.utils.getMobKills());
		
	}
	
}
