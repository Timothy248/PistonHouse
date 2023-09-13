package ch.timxxx.pistonHouse.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.main.Main;

public class Scoreboards {

	FileManager file = new FileManager("players.yml");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm"); 
	
	// Default to set Scoreboard
	public void setScoreboard(Player player) {
		Board board = new Board(player.getUniqueId().hashCode());
		board.setTitle(" §7--= §6RG GAMERS §7=-- ");
		
		board.set(10, "");
		board.set(7, " ");
		
		double mobcoins = Double.valueOf(file.get("default." +  player.getName(), "mobcoins").toString());
		board.set(9, "§8» §7Mobcoins: §9§l" + mobcoins);
		
		int ping = ((CraftPlayer) player).getHandle().ping;
		board.set(6, "§8» §7Ping: §6" + ping + "ms");
		
		LocalDateTime now = LocalDateTime.now();
		board.set(5, "§8» §7Uhrzeit: §6" + dtf.format(now));
		
		
		board.setScoreboard(player);
		
		if((boolean) file.get("default." + player.getName(), "invisible")) {
			activateSneak(player);
		}
	}
	
	public void refreshScoreboard(Player player, double mobcoins) {
		Board board = new Board(player);
		
		board.set(9, "§8» §7Mobcoins: §9§l" + mobcoins);
		
		int ping = ((CraftPlayer) player).getHandle().ping;
		board.set(6, "§8» §7Ping: §6" + ping + "ms");
		
		LocalDateTime now = LocalDateTime.now();
		board.set(5, "§8» §7Uhrzeit: §6" + dtf.format(now));
	}
	
	public void activateSneak(Player player) {
		Board board = new Board(player);
		
		board.set(4, "  ");
		board.set(3, "§8» §7Sneak §aaktiviert§7!");
	}
	
	public void deactivateSneak(Player player) {
		Board board = new Board(player);
		
		board.remove(4);
		board.remove(3);
	}
	
	public void addMobcoins(Player player, double mobcoins) {
		Board board = new Board(player);
		board.set(8, "§8» §a+ §9§o" + mobcoins + "§7 Mobcoins");
		
		new BukkitRunnable() {

			@Override
			public void run() { 
				Board board = new Board(player);
				board.remove(8);
			}
			
		}.runTaskLater(Main.getPlugin(), 100);
	}
	
	public void subtractMobcoins(Player player, double mobcoins) {
		Board board = new Board(player);
		board.set(8, "§8» §c- §9§o" + mobcoins + "§7 Mobcoins");
		
		new BukkitRunnable() {

			@Override
			public void run() { 
				Board board = new Board(player);
				board.remove(8);
			}
			
		}.runTaskLater(Main.getPlugin(), 100);
	}

}
