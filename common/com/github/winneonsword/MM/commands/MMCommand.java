package com.github.winneonsword.MM.commands;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.winneonsword.MM.ClassInfo;
import com.github.winneonsword.MM.Gameplay;
import com.github.winneonsword.MM.MainMM;
import com.github.winneonsword.MM.exceptions.InvalidClassException;
import com.github.winneonsword.MM.utils.UtilsMM;

public class MMCommand extends UtilsMM implements CommandExecutor {
	
	private Gameplay game;
	
	public MMCommand(MainMM pl){
		
		super(pl);
		
		this.game = new Gameplay(this.pl);
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		final Player finalP = p;
		
		if (label.equalsIgnoreCase("mm")){
			
			if (args.length == 0){
				
				this.s(p, "Try /mm ? or /mm help.");
				
			} else {
				
				switch (args[0].toLowerCase()){
				
				default:
					
					this.s(p, "&cUnknown argument. Try /mm ? or /mm help.");
					break;
					
				case "?": case "help":
					
					String[] categories = {
							
							this.WC + "Mob Mondays Help Panel",
							"&5- &d/mm ? &5// &dThe main help panel.",
							"&5- &d/mm ? general &5// &dThe general MM commands.",
							"&5- &d/mm ? staff &5// &dThe staff MM commands."
							
					};
					
					if (args.length == 1){
						
						this.s(p, categories);
						
					} else {
						
						switch (args[1].toLowerCase()){
						
						case "general":
							
							String[] general = {
									
									this.WC + "Mob Mondays General Commands",
									"&5- &d/mm ? &5// &dThe main help panel.",
									"&5- &d/mm join <class> &5// &dJoin an MM game.",
									"&5- &d/mm leave &5// &dLeave an MM game.",
									"&5- &d/mm list &5// &dView the player list.",
									"&5- &d/mm class <class | list> &5// &dStats of a class or the class list.",
									
							};
							
							this.s(p, general);
							break;
							
						case "staff":
							
							if (!(p.hasPermission("wa.staff"))){
								
								this.s(p, "&cDoes it look like you have permission to use this?");
								
							}
							
							String[] staff = {
									
									this.WC + "Mob Mondays Staff Commands",
									"&5- &d/mm start &5// &dStart an MM game.",
									"&5- &d/mm stop &5// &dStop an MM game. THERE IS NO CONFIRM!",
									"&5- &d/mm toggle &5// &dToggle the open status of MM.",
									"&5- &d/mm add <player> <class> &5// &dAdd a player from MM. DEBUG ONLY.",
									"&5- &d/mm remove <player> &5// &dRemove a player from MM. DEBUG ONLY.",
									"&5- &d/mm disable &5// &dDisable Mob Mondays."
									
							};
							
							this.s(p, staff);							
							break;
							
						default:
							
							this.s(p, categories);
							break;
							
						}
						
					}
					
					break;
					
				case "toggle":
					
					if (!(p.hasPermission("wa.staff"))){
						
						this.s(p, "&cDoes it look like you have permission to use this?");
						
					} else {
						
						boolean check = this.checkArena();
						
						if (!(check)){
							
							this.s(p, "&cThe arena has not been set! Type /mm set to set the arena!");
							
						} else {
							
							boolean toggle = this.toggleJoin();
							
							if (toggle){
								
								this.s(p, "Toggled open status of MM to &6open&d.");
								
							} else {
								
								this.s(p, "Toggled open status of MM to &6closed&d.");
								
							}
							
						}
						
					}
					
					break;
					
				case "join":
					
					if (!(this.toggle)){
						
						this.s(p, "&cJoining of MM is not open at the moment!");
						
					} else if (args.length == 1){
						
						this.s(p, "&cCorrect usage: /mm join <class> - For classes, type /mm class list.");
						
					} else {			
						
						try {
							
							this.getClassInfo(args[1].toLowerCase());
							
						} catch (InvalidClassException e){
							
							this.s(p, "&cThat is not a valid class! Type /mm class list for a list of classes.");
							
							return true;
							
						}
						
						boolean addPlayer = this.addPlayer(p, args[1]);
						
						if (!(addPlayer)){
							
							this.changingClass.put(p.getName(), args[1]);
							this.s(p, "&cYou are already in a class! To change classes, type /mm confirm.");
							this.delay(pl, new Runnable(){
								
								public void run(){
									
									if (UtilsMM.changingClass.containsKey(finalP.getName())){
										
										UtilsMM.changingClass.remove(finalP.getName());
										UtilsMM.s(finalP, "20 seconds have passed! You have automatically cancelled changing classes.");
										
									}
									
								}
								
							}, 400L);
							
						} else {
							
							this.s(p, "You have joined Mob Mondays as a &6" + args[1] + "&d! Warping you to the arena.");
							this.delay(pl, new Runnable(){
								
								public void run(){
									
									finalP.teleport(UtilsMM.getArena());
									
								}
								
							}, 40L);
							
						}
						
					}
					
					break;
					
				case "leave":
					
					boolean removePlayer = this.removePlayer(p);
					
					if (!(removePlayer)){
						
						this.s(p, "&cYou are not in Mob Mondays! Type /mm join <class> to join.");
						
					} else {
						
						this.s(p, "You have left Mob Mondays! Warping you to spawn.");
						this.delay(pl, new Runnable(){
							
							public void run(){
								
								finalP.performCommand("s");
								
							}
							
						}, 20L);
						
					}
					
					break;
					
				case "list":
					
					StringBuilder sb = new StringBuilder();
					List<String> playerList = this.getPlayerList();
					
					if (playerList.isEmpty()){
						
						this.s(p, "&cThere are no players in MM at the moment!");
						
					} else {
						
						for (int i = 0; i < playerList.size(); i++){
							
							String pString = playerList.get(i);
							Player player = Bukkit.getPlayer(pString);
							String nick = player.getDisplayName();
							
							sb.append("&d, " + nick);
							
						}
						
						String list = sb.toString().replaceFirst("&d, ", "");
						
						this.s(p, "&6Players: " + list);
						
					}
					
					break;
					
				case "class":
					
					if (args.length == 1){
						
						this.s(p, "&cCorrect usage: /mm class <class | list> - The list shows a list of all classes.");
						
					} else {
						
						String[] c = this.getClassArray();
						ClassInfo name = null;
						
						if (args[1].equalsIgnoreCase("list")){
							
							this.s(p, "Class List:");
							
							for (int i = 0; i < c.length; i++){
								
								p.sendMessage(this.AS("&5- &6" + WordUtils.capitalize(c[i])));
								
							}
							
							
						} else {
							
							try {
								
								name = this.getClassInfo(args[1]);
								
							} catch (InvalidClassException e){
								
								this.s(p, "&cThat is not a valid class! Type /mm class list for a list of classes.");
							
								return true;
								
							}
							
							String[] info = {
									
									this.WC + "&6" + name.getClassName() + "'s &dInfo / Stats",
									"&5- &dDescription: &6" + name.getClassDescription(),
									"&5- &dAlpha Ability: &6" + name.getClassAlpha(),
									"&5- &dOmega Ability: &6" + name.getClassOmega()
									
							};
							
							this.s(p, info);
							
						}
						
					}
					
					break;
					
				case "set":
					
					if (!(p.hasPermission("wa.staff"))){
						
						this.s(p, "&cDoes it look like you have permission to use this?");
						
					} else {
						
						if (args.length == 1){
							
							this.setArena(p, 100);
							this.s(p, "Set the arena to &6" + this.getArenaX() + "&d, &6" + this.getArenaY() + "&d, &6" + this.getArenaZ() + "&d in world &6" + this.getArenaW().getName() + " &dwith a default radius of &6" + this.getArenaR() + "&d!");
							
						} else {
							
							boolean check = this.parseInteger(args[1]);
							
							if (check){
								
								this.setArena(p, Integer.parseInt(args[1]));
								this.s(p, "Set the arena to &6" + this.getArenaX() + "&d, &6" + this.getArenaY() + "&d, &6" + this.getArenaZ() + "&d in world &6" + this.getArenaW().getName() + " &dwith a radius of &6" + this.getArenaR() + "&d!");
								
							} else {
								
								this.s(p, "&cCorrect usage: /mm set [radius] - The radius MUST be a number.");
								
							}
							
						}
						
					}
					
					break;
					
				case "start":
					
					if (!(p.hasPermission("wa.staff"))){
						
						this.s(p, "&cDoes it look like you have permission to use this?");
						
					} else if (this.getPlayerList().size() < 1){
						
						this.s(p, "&cThere are not at least 5 people in MM yet!");
						
					} else {
											
						this.game.startGame();
						
					}
					
					break;
					
				case "stop":
					
					if (!(p.hasPermission("wa.staff"))){
						
						this.s(p, "&cDoes it look like you have permission to use this?");
						
					} else {
						
						this.game.endGame();
						
					}
					
					break;
					
				case "add":
					
					if (args.length == 1 || args.length == 2){
						
						this.s(p, "&cCorrect Usage: /mm add <player> <class> - Adds a player to MM manually.");
						
					} else {
						
						try {
							
							this.getClassInfo(args[1]);
							
						} catch (InvalidClassException e){
							
							this.s(p, "&cThat is not a valid class!");
							
							return true;
							
						}
						
						Player pl;
						
						try {
							
							pl = Bukkit.getPlayer(args[1]);
							
						} catch (NullPointerException e){
							
							this.s(p, "&cThat player is not online!");
							
							return true;
							
						}
						
						boolean add = this.addPlayer(pl, args[1]);
						
						if (add){
							
							this.s(p, "Added the player &7" + pl.getDisplayName() + " &dto MM as the class &6" + args[1] + "&d!");
							
						} else {
							
							this.s(p, "The player &7" + pl.getDisplayName() + " &dhas already joined MM!");
							
						}
						
					}
					
					break;
					
				case "remove":
					
					if (args.length == 1){
						
						this.s(p, "&cCorrect usage: /mm remove <player> - Removes a player from MM.");
						
					} else {
						
						OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
						
						boolean remove = this.removePlayer(pl);
						
						if (remove){
							
							this.s(p, "Removed the player &7" + pl.getName() + " &dfrom MM.");
							
						} else {
							
							this.s(p, "The player &7" + pl.getName() + " &dhas not joined MM!");
							
						}
						
					}
					
					break;
					
				case "confirm":
					
					if (this.changingClass.containsKey(p.getName())){
						
						this.s(p, "You have changed classes to &6" + this.changingClass.get(p.getName()) + "&d!");
						this.changeClass(p, this.changingClass.get(p.getName()));
						
					} else {
						
						this.s(p, "&cYou are not changing a class at the moment!");
						
					}
					
					break;
					
				case "cancel":
					
					if (this.changingClass.containsKey(p.getName())){
						
						this.s(p, "You have cancelled changing classes!");
						this.changingClass.remove(p.getName());
						
					} else {
						
						this.s(p, "&cYou are not changing a class at the moment!");
						
					}
					
					break;
					
				case "disable":
					
					if (!(p.hasPermission("wa.staff"))){
						
						this.s(p, "&cDoes it look like you have permission to use this?");
						
					} else {
						
						Bukkit.getPluginManager().disablePlugin(pl);
						this.s(p, "Successfully disabled Mob Mondays.");
						
					}
					
					break;
					
				}
				
				this.saveVariables();
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}
