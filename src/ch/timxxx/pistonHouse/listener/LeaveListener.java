package ch.timxxx.pistonHouse.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import ch.timxxx.pistonHouse.file.FileManager;

public class LeaveListener implements Listener {

	FileManager file = new FileManager("players.yml");
	
	@EventHandler
	public void handlePlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(file.get("default." + player.getName(), "team").toString());
		
		event.setQuitMessage("§7Der Spieler §f" + team.getColor() + player.getName() + " §7hat den Server verlassen");
	}
	
}
