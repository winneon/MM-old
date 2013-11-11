package com.github.winneonsword.MM;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.winneonsword.MM.commands.MMCommand;
import com.github.winneonsword.MM.utils.UtilsMM;

public class MainMM extends JavaPlugin {
	
	public WCAPI api;
	public WCManager wcm;
	public PluginManager pm;
	
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
		
		try {
			
			this.firstRun();
			
		} catch (Exception e){
			
			this.getLogger().log(Level.SEVERE, "Failed to set the YAML configurations. Disabling Mob Mondays.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
			
		}
		
		this.loadYMLs();
		this.setClassInfo();
		this.setClassData();
		
		this.registerCommands();
		this.registerListeners();
		
		this.getLogger().log(Level.INFO, "Mob Mondays has been hooked and enabled alongside WCAPI!");
		
	}
	
	@Override
	public void onDisable(){
		
		UtilsMM.saveVariables();
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
	
	private void setClassData(){
		
		String[] c = UtilsMM.getClassArray();
		
		ItemStack medicWeapon = this.api.invManager.makeItem(UtilsMM.AS("&6&lMedic Sword"), UtilsMM.AS("&6To only be used with a medic."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_SWORD, 1);
		ItemStack medicHelm = this.api.invManager.makeItem(UtilsMM.AS("&6&lMedic Helmet"), UtilsMM.AS("&6To only be used with a medic."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_HELMET, 1);
		ItemStack medicChest = this.api.invManager.makeItem(UtilsMM.AS("&6&lMedic Chestplate"), UtilsMM.AS("&6To only be used with a medic."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_CHESTPLATE, 1);
		ItemStack medicTrousers = this.api.invManager.makeItem(UtilsMM.AS("&6&lMedic Trousers"), UtilsMM.AS("&6To only be used with a medic."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_LEGGINGS, 1);
		ItemStack medicBooties = this.api.invManager.makeItem(UtilsMM.AS("&6&lMedic Booties"), UtilsMM.AS("&6To only be used with a medic."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_BOOTS, 1);
		
		medicHelm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		medicChest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		medicTrousers.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		medicBooties.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		
		ItemStack spiritWeapon = this.api.invManager.makeItem(UtilsMM.AS("&b&lSpirit Sword"), UtilsMM.AS("&6To only be used with a spirit."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_SWORD, 1);
		ItemStack spiritHelm = this.api.invManager.makeItem(UtilsMM.AS("&b&lSpirit Helmet"), UtilsMM.AS("&6To only be used with a spirit."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_HELMET, 1);
		ItemStack spiritChest = this.api.invManager.makeItem(UtilsMM.AS("&b&lSpirit Chestplate"), UtilsMM.AS("&6To only be used with a spirit."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_CHESTPLATE, 1);
		ItemStack spiritTrousers = this.api.invManager.makeItem(UtilsMM.AS("&b&lSpirit Trousers"), UtilsMM.AS("&6To only be used with a spirit."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_LEGGINGS, 1);
		ItemStack spiritBooties = this.api.invManager.makeItem(UtilsMM.AS("&b&lSpirit Booties"), UtilsMM.AS("&6To only be used with a spirit."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_BOOTS, 1);
		
		ItemStack warriorWeapon = this.api.invManager.makeItem(UtilsMM.AS("&c&lWarrior Sword"), UtilsMM.AS("&6To only be used with a warrior."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_SWORD, 1);
		ItemStack warriorHelm = this.api.invManager.makeItem(UtilsMM.AS("&c&lWarrior Helmet"), UtilsMM.AS("&6To only be used with a warrior."), true, Enchantment.DURABILITY, 10, 0, Material.DIAMOND_HELMET, 1);
		ItemStack warriorChest = this.api.invManager.makeItem(UtilsMM.AS("&c&lWarrior Chestplate"), UtilsMM.AS("&6To only be used with a warrior."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_CHESTPLATE, 1);
		ItemStack warriorTrousers = this.api.invManager.makeItem(UtilsMM.AS("&c&lWarrior Trousers"), UtilsMM.AS("&6To only be used with a warrior."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_LEGGINGS, 1);
		ItemStack warriorBooties = this.api.invManager.makeItem(UtilsMM.AS("&c&lWarrior Booties"), UtilsMM.AS("&6To only be used with a warrior."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_BOOTS, 1);
		
		ItemStack infernoWeapon = this.api.invManager.makeItem(UtilsMM.AS("&4&lInferno Sword"), UtilsMM.AS("&6To only be used with an inferno."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_SWORD, 1);
		ItemStack infernoHelm = this.api.invManager.makeItem(UtilsMM.AS("&4&lInferno Helmet"), UtilsMM.AS("&6To only be used with an inferno."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_HELMET, 1);
		ItemStack infernoChest = this.api.invManager.makeItem(UtilsMM.AS("&4&lInferno Chestplate"), UtilsMM.AS("&6To only be used with an inferno."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_CHESTPLATE, 1);
		ItemStack infernoTrousers = this.api.invManager.makeItem(UtilsMM.AS("&4&lInferno Trousers"), UtilsMM.AS("&6To only be used with an inferno."), true, Enchantment.DURABILITY, 10, 0, Material.LEATHER_LEGGINGS, 1);
		ItemStack infernoBooties = this.api.invManager.makeItem(UtilsMM.AS("&4&lInferno Booties"), UtilsMM.AS("&6To only be used with an inferno."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_BOOTS, 1);
		
		ItemStack roadrunnerWeapon = this.api.invManager.makeItem(UtilsMM.AS("&a&lRoadrunner Sword"), UtilsMM.AS("&6To only be used with a roadrunner."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_SWORD, 1);
		ItemStack roadrunnerHelm = this.api.invManager.makeItem(UtilsMM.AS("&a&lRoadrunner Helmet"), UtilsMM.AS("&6To only be used with a roadrunner."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_HELMET, 1);
		ItemStack roadrunnerChest = this.api.invManager.makeItem(UtilsMM.AS("&a&lRoadrunner Chestplate"), UtilsMM.AS("&6To only be used with a roadrunner."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_CHESTPLATE, 1);
		ItemStack roadrunnerTrousers = this.api.invManager.makeItem(UtilsMM.AS("&a&lRoadrunner Trousers"), UtilsMM.AS("&6To only be used with a roadrunner."), true, Enchantment.DURABILITY, 10, 0, Material.IRON_LEGGINGS, 1);
		ItemStack roadrunnerBooties = this.api.invManager.makeItem(UtilsMM.AS("&a&lRoadrunner Booties"), UtilsMM.AS("&6To only be used with a roadrunner."), true, Enchantment.DURABILITY, 10, 0, Material.GOLD_BOOTS, 1);
		
		ItemStack sniperWeapon = this.api.invManager.makeItem(UtilsMM.AS("&d&lSniper Bow"), UtilsMM.AS("&6To only be used with a sniper."), true, Enchantment.DURABILITY, 10, 0, Material.BOW, 1);
		ItemStack sniperHelm = this.api.invManager.makeItem(UtilsMM.AS("&d&lSniper Helmet"), UtilsMM.AS("&6To only be used with a sniper."), true, Enchantment.DURABILITY, 10, 0, Material.LEATHER_HELMET, 1);
		ItemStack sniperChest = this.api.invManager.makeItem(UtilsMM.AS("&d&lSniper Chestplate"), UtilsMM.AS("&6To only be used with a sniper."), true, Enchantment.DURABILITY, 10, 0, Material.LEATHER_CHESTPLATE, 1);
		ItemStack sniperTrousers = this.api.invManager.makeItem(UtilsMM.AS("&d&lSniper Trousers"), UtilsMM.AS("&6To only be used with a sniper."), true, Enchantment.DURABILITY, 10, 0, Material.LEATHER_LEGGINGS, 1);
		ItemStack sniperBooties = this.api.invManager.makeItem(UtilsMM.AS("&d&lSniper Booties"), UtilsMM.AS("&6To only be used with a sniper."), true, Enchantment.DURABILITY, 10, 0, Material.LEATHER_BOOTS, 1);
		
		sniperWeapon.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		sniperWeapon.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		sniperHelm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sniperChest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sniperTrousers.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sniperBooties.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		
		this.datacore.set("Classes." + c[0] + ".data.weapon", medicWeapon);
		this.datacore.set("Classes." + c[0] + ".data.helmet", medicHelm);
		this.datacore.set("Classes." + c[0] + ".data.chestplate", medicChest);
		this.datacore.set("Classes." + c[0] + ".data.trousers", medicTrousers);
		this.datacore.set("Classes." + c[0] + ".data.booties", medicBooties);
		
		this.datacore.set("Classes." + c[1] + ".data.weapon", spiritWeapon);
		this.datacore.set("Classes." + c[1] + ".data.helmet", spiritHelm);
		this.datacore.set("Classes." + c[1] + ".data.chestplate", spiritChest);
		this.datacore.set("Classes." + c[1] + ".data.trousers", spiritTrousers);
		this.datacore.set("Classes." + c[1] + ".data.booties", spiritBooties);
		
		this.datacore.set("Classes." + c[2] + ".data.weapon", warriorWeapon);
		this.datacore.set("Classes." + c[2] + ".data.helmet", warriorHelm);
		this.datacore.set("Classes." + c[2] + ".data.chestplate", warriorChest);
		this.datacore.set("Classes." + c[2] + ".data.trousers", warriorTrousers);
		this.datacore.set("Classes." + c[2] + ".data.booties", warriorBooties);
		
		this.datacore.set("Classes." + c[3] + ".data.weapon", infernoWeapon);
		this.datacore.set("Classes." + c[3] + ".data.helmet", infernoHelm);
		this.datacore.set("Classes." + c[3] + ".data.chestplate", infernoChest);
		this.datacore.set("Classes." + c[3] + ".data.trousers", infernoTrousers);
		this.datacore.set("Classes." + c[3] + ".data.booties", infernoBooties);
		
		this.datacore.set("Classes." + c[4] + ".data.weapon", roadrunnerWeapon);
		this.datacore.set("Classes." + c[4] + ".data.helmet", roadrunnerHelm);
		this.datacore.set("Classes." + c[4] + ".data.chestplate", roadrunnerChest);
		this.datacore.set("Classes." + c[4] + ".data.trousers", roadrunnerTrousers);
		this.datacore.set("Classes." + c[4] + ".data.booties", roadrunnerBooties);
		
		this.datacore.set("Classes." + c[5] + ".data.weapon", sniperWeapon);
		this.datacore.set("Classes." + c[5] + ".data.helmet", sniperHelm);
		this.datacore.set("Classes." + c[5] + ".data.chestplate", sniperChest);
		this.datacore.set("Classes." + c[5] + ".data.trousers", sniperTrousers);
		this.datacore.set("Classes." + c[5] + ".data.booties", sniperBooties);
		
		this.saveYMLs();
		
	}
	
}
