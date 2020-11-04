package ch.timxxx.pistonHouse.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import ch.timxxx.pistonHouse.commands.EnderchestCommand;
import ch.timxxx.pistonHouse.commands.FeedCommand;
import ch.timxxx.pistonHouse.commands.HealCommand;
import ch.timxxx.pistonHouse.commands.InventoryCommand;
import ch.timxxx.pistonHouse.commands.LocationCommand;
import ch.timxxx.pistonHouse.commands.PayCommand;
import ch.timxxx.pistonHouse.commands.PingCommand;
import ch.timxxx.pistonHouse.commands.SneakCommand;
import ch.timxxx.pistonHouse.commands.ToDoCommand;
import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.listener.JoinListener;
import ch.timxxx.pistonHouse.listener.LeaveListener;
import ch.timxxx.pistonHouse.listener.MessageListener;
import ch.timxxx.pistonHouse.mobcoins.MobCoinListener;
import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;
import ch.timxxx.pistonHouse.mobcoins.MobcoinBalanceCommand;
import ch.timxxx.pistonHouse.shop.Shop;
import ch.timxxx.pistonHouse.util.Scoreboards;

public class Main extends JavaPlugin {

	private static Main plugin;
	FileManager playerFile;
	PluginManager pluginManager;
	Scoreboards board;
	MobcoinAPI mbc;
	
	public void onEnable() {
		plugin = this;
		playerFile = new FileManager("players.yml");
		board = new Scoreboards();
		pluginManager = Bukkit.getPluginManager();
		mbc = new MobcoinAPI();
		
		Shop shop = new Shop();
		
		// Commands
		getCommand("balance").setExecutor(new MobcoinBalanceCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		getCommand("enderchest").setExecutor(new EnderchestCommand());
		getCommand("location").setExecutor(new LocationCommand());
		getCommand("ping").setExecutor(new PingCommand());
		getCommand("sneak").setExecutor(new SneakCommand());
		getCommand("inventory").setExecutor(new InventoryCommand());
		getCommand("todo").setExecutor(new ToDoCommand());
		
		getCommand("shop").setExecutor(shop);
	
		// Events
		pluginManager.registerEvents(new MobCoinListener(), this);
		pluginManager.registerEvents(shop, this);
		pluginManager.registerEvents(new JoinListener(), this);
		pluginManager.registerEvents(new LeaveListener(), this);
		pluginManager.registerEvents(new MessageListener(), this);
		
		// Create the shop variable for all online players (/reload, etc.)
		for(Player current : Bukkit.getOnlinePlayers()) {
			shop.putShop(current);
			board.setScoreboard(current);
			current.setCollidable(false);
		}
		
		new BukkitRunnable() {

			@Override
			public void run() {
				for(Player current : Bukkit.getOnlinePlayers()) {
					board.refreshScoreboard(current, Double.valueOf(playerFile.get("default." +  current.getName(), "mobcoins").toString()));
				}
			}
			
		}.runTaskTimer(this, 20, 100);
	
	}
	
	public void onDisable() {
		
		for(Player current : Bukkit.getOnlinePlayers()) {
			current.closeInventory();
		}
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
}
