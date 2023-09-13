package ch.timxxx.pistonHouse.mobcoins;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.util.Scoreboards;

public class MobcoinAPI {

	public FileManager file;
	public FileManager config;
	Scoreboards board = new Scoreboards();
	
	public MobcoinAPI() {
		file = new FileManager("players.yml");
		config = new FileManager("config.yml");
	}
	
	public void setMobcoins(Player player, double mobcoins) {
		board.refreshScoreboard(player, mobcoins);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {}
		file.set("default." + player.getName(), "mobcoins", mobcoins);
	}
	
	public double getMobcoins(Player player) {
		Object _mobcoins = file.get("default." + player.getName(), "mobcoins");
		if(_mobcoins == null) {
			file.set("default." + player.getName(), "mobcoins", 0.0);
			_mobcoins = 0.0;
		}
		
		double mobcoins = Double.valueOf(_mobcoins.toString());
		return mobcoins;
	}
	
	public void addMobcoins(Player player, double amount) {
		setMobcoins(player, getMobcoins(player) + amount);
		board.addMobcoins(player, amount);
	}
	
	public void subtractMobcoins(Player player, double amount) {
		setMobcoins(player, getMobcoins(player) - amount);
		board.subtractMobcoins(player, amount);
	}
	
	public double getReward(Entity entity) {
		String killed = "" + entity;
		Object _reward = config.get("mobcoins", killed);
		if(_reward == null) {
			config.set("mobcoins", killed, 0.25);
			_reward = 0.25;
		}
		
		double reward = Double.valueOf(_reward.toString());
		return reward;
	}
	 
}
