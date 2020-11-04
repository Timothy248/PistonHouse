package ch.timxxx.pistonHouse.mobcoins;

import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;
import ch.timxxx.pistonHouse.util.Scoreboards;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobCoinListener implements Listener {
	
	MobcoinAPI mbc = new MobcoinAPI();
	Scoreboards board = new Scoreboards();
	
	Entity killed;
	Entity killer;
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		killed = event.getEntity();
		killer = event.getEntity().getKiller();
		
		if (killer instanceof Player) addMobCoins();
	    
	}
	
	private void addMobCoins() {
		double reward = mbc.getReward(killed);
		mbc.addMobcoins((Player) killer, reward);
	}
}



