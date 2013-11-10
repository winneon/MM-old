package com.github.winneonsword.MM;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.winneonsword.MM.commands.MMCommand;
import com.github.winneonsword.MM.utils.UtilsMM;

public class MainMM extends JavaPlugin {
	
	public WCAPI api;
	public WCManager wcm;
	
	public static YamlConfiguration config = new YamlConfiguration();
	public static YamlConfiguration datacore = new YamlConfiguration();
	public static YamlConfiguration help = new YamlConfiguration();
	
	private File configFile;
	private File datacoreFile;
	private File helpFile;
	
	private File file;
	private YamlConfiguration yaml;
	
	@Override
	public void onEnable(){
		
		Plugin WCAPI = Bukkit.getServer().getPluginManager().getPlugin("WCAPI");
		this.api = (WCAPI) WCAPI;
		this.wcm = new WCManager(api);
		
		try {
			
			this.firstRun();
			
		} catch (Exception e){
			
			this.getLogger().log(Level.SEVERE, "Failed to set the YAML configurations. Disabling Mob Mondays.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
			
		}
		
		this.loadYMLs();
		this.setClassInfo();
		
		this.registerCommands();
		this.registerListeners();
		
		this.getLogger().log(Level.INFO, "Mob Mondays has been hooked and enabled alongside WCAPI!");
		
	}
	
	@Override
	public void onDisable(){
		
		this.saveDisableVariables();
		this.getLogger().log(Level.INFO, "Mob Mondays has been disabled.");
		
	}
	
	public void saveYMLs(){
		
		try {
			
			this.config.save(configFile);
			this.datacore.save(datacoreFile);
			this.help.save(helpFile);
			
		} catch (IOException e){
			
			this.getLogger().log(Level.SEVERE, "Failed to save the Mob Mondays YMLs.");
			e.printStackTrace();
			
		}
		
	}
	
	public void loadYMLs(){
		
		try {
			
			this.config.load(configFile);
			this.datacore.load(datacoreFile);
			this.help.load(helpFile);
			
		} catch (Exception e){
			
			this.getLogger().log(Level.SEVERE, "Failed to load the Mob Mondays YMLs.");
			e.printStackTrace();
			
		}
		
	}
	
	private void registerCommands(){
		
		this.getCommand("mm").setExecutor(new MMCommand(this));
		
	}
	
	private void registerListeners(){
		
		
		
	}
	
	private void firstRun() throws Exception {
		
		String[] yamls = {
				
				"config",
				"datacore",
				"help"
				
		};
		
		for (int i = 0; i < yamls.length; i++){
			
			this.file = new File("./plugins/MobMondays/" + yamls[i] + ".yml");
			
			if (!(file.exists())){
				
				file.createNewFile();
				
			}
			
			this.yaml = YamlConfiguration.loadConfiguration(file);
			
			switch (i){
			
			case 0:
				
				this.config = yaml;
				this.configFile = file;
				break;
				
			case 1:
				
				this.datacore = yaml;
				this.datacoreFile = file;
				break;
				
			case 2:
				
				this.help = yaml;
				this.helpFile = file;
				break;
			
			}
			
		}
		
	}
	
	private void saveDisableVariables(){
		
		this.datacore.set("playerList", UtilsMM.playerList);
		this.datacore.set("arenaLoc.arenaX", UtilsMM.getArenaX());
		this.datacore.set("arenaLoc.arenaY", UtilsMM.getArenaY());
		this.datacore.set("arenaLoc.arenaZ", UtilsMM.getArenaZ());
		this.datacore.set("arenaLoc.arenaW", UtilsMM.getArenaW().getName());
		this.saveYMLs();
		
	}
	
	private void setClassInfo(){
		
		String[] c = UtilsMM.getClassArray();
		
		String medicDescription = "A support class that is focused on healing.";
		String medicAlpha = "Give yourself Regeneration III for 1 minute.";
		String medicOmega = "Give everyone including yourself Regeneration III for 1 minute.";
		
		String spiritDescription = "A defensive class that is focused on flying and strategy.";
		String spiritAlpha = "Give yourself the ability to fly for 1 minute.";
		String spiritOmega = "Give everyone including yourself the ability to fly for 1 minute.";
		
		String warriorDescription = "An offensive class that is focused on battle.";
		String warriorAlpha = "The ability to 1 - Hit KO any mob for 10 seconds. (Not including bosses.)";
		String warriorOmega = "Recieve a battle axe that allows for a super - knockback for every mob around you. (3 time use.)";
		
		String infernoDescription = "An offensive class that is focused on fire and strategy.";
		String infernoAlpha = "The ability to shoot small fireballs at mobs for 30 seconds.";
		String infernoOmega = "Forge a fire - ring that sets all mobs around you on fire for an infinite amount of time. (1 time use.)";
		
		String roadrunnerDescription = "A defensive class that is focused on speed and manuveuring.";
		String roadrunnerAlpha = "Give yourself Speed IV for 1 minute.";
		String roadrunnerOmega = "Give everyone including yourself Speed IV for 1 minute.";
		
		String sniperDescription = "An offensive class that is focused on stealth and fast actions.";
		String sniperAlpha = "Headshot (1 - Hit KO) all mobs you shoot for 20 seconds.";
		String sniperOmega = "Send a barrage of arrows all around you at all mobs. (1 time use.)";
		
		this.datacore.set("Classes." + c[0] + ".description", medicDescription);
		this.datacore.set("Classes." + c[0] + ".alpha", medicAlpha);
		this.datacore.set("Classes." + c[0] + ".omega", medicOmega);
		
		this.datacore.set("Classes." + c[1] + ".description", spiritDescription);
		this.datacore.set("Classes." + c[1] + ".alpha", spiritAlpha);
		this.datacore.set("Classes." + c[1] + ".omega", spiritOmega);
		
		this.datacore.set("Classes." + c[2] + ".description", warriorDescription);
		this.datacore.set("Classes." + c[2] + ".alpha", warriorAlpha);
		this.datacore.set("Classes." + c[2] + ".omega", warriorOmega);
		
		this.datacore.set("Classes." + c[3] + ".description", infernoDescription);
		this.datacore.set("Classes." + c[3] + ".alpha", infernoAlpha);
		this.datacore.set("Classes." + c[3] + ".omega", infernoOmega);
		
		this.datacore.set("Classes." + c[4] + ".description", roadrunnerDescription);
		this.datacore.set("Classes." + c[4] + ".alpha", roadrunnerAlpha);
		this.datacore.set("Classes." + c[4] + ".omega", roadrunnerOmega);
		
		this.datacore.set("Classes." + c[5] + ".description", sniperDescription);
		this.datacore.set("Classes." + c[5] + ".alpha", sniperAlpha);
		this.datacore.set("Classes." + c[5] + ".omega", sniperOmega);
		
		this.saveYMLs();
		
	}
	
}
