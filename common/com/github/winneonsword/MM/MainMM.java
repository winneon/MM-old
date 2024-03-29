package com.github.winneonsword.MM;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;
import com.github.winneonsword.MM.commands.MMCommand;
import com.github.winneonsword.MM.events.EntityDeath;
import com.github.winneonsword.MM.events.EntitySpawn;
import com.github.winneonsword.MM.events.MiscEvents;
import com.github.winneonsword.MM.events.PlayerInteract;
import com.github.winneonsword.MM.events.ScoreboardMM;
import com.github.winneonsword.MM.utils.UtilsGameplay;
import com.github.winneonsword.MM.utils.UtilsMM;

public class MainMM extends JavaPlugin {
	
	public WCAPI api;
	public WCManager wcm;
	public PluginManager pm;
	public InventoryManager invManager;
	public UtilsGameplay utils;
	
	public int repeat;
	
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
		this.pm = this.getServer().getPluginManager();
		this.invManager = new InventoryManager(api);
		
		try {
			
			this.firstRun();
			
		} catch (Exception e){
			
			this.getLogger().log(Level.SEVERE, "Failed to set the YAML configurations. Disabling Mob Mondays.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
			
		}
		
		this.loadYMLs();
		
		this.utils = new UtilsGameplay(this);
		
		this.registerCommands();
		this.registerListeners();
		
		this.repeat = this.utils.repeat(this, new Runnable(){
			
			public void run(){
				
				for (int i = 0; i < utils.getPlayerList().size(); i++){
					
					String pl = utils.getPlayerList().get(i);
					Player p = Bukkit.getPlayer(pl);
					
					Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
					
				}
				
			}
			
		}, 100L, 100L);
		
		this.getLogger().log(Level.INFO, "Mob Mondays has been hooked and enabled alongside WCAPI!");
		
	}
	
	@Override
	public void onDisable(){
		
		UtilsMM.saveVariables();
		this.getServer().getScheduler().cancelTask(repeat);
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
		
		this.pm.registerEvents(new MiscEvents(this), this);
		this.pm.registerEvents(new EntityDeath(this), this);
		this.pm.registerEvents(new ScoreboardMM(this), this);
		this.pm.registerEvents(new PlayerInteract(this), this);
		this.pm.registerEvents(new EntitySpawn(this), this);
		
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
		
		if (!(configFile.exists())){
			
			configFile.getParentFile().mkdirs();
			this.copy(this.getResource("config.yml"), configFile);
			
		}
		
		if (!(datacoreFile.exists())){
			
			datacoreFile.getParentFile().mkdirs();
			this.copy(this.getResource("datacore.yml"), datacoreFile);
			
		}
		
		if (!(helpFile.exists())){
			
			helpFile.getParentFile().mkdirs();
			this.copy(this.getResource("help.yml"), helpFile);
			
		}
		
		this.datacore.set("round", 0);
		
	}
	
	private void copy(InputStream in, File file){
		
		try {
			
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = in.read(buf)) > 0){
				
				out.write(buf, 0, len);
				
			}
			
			out.close();
			in.close();
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
	}
	
}
