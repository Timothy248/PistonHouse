package ch.timxxx.pistonHouse.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import ch.timxxx.pistonHouse.file.FileManager;

public class MessageListener implements Listener {

	FileManager file = new FileManager("players.yml");
	
	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(file.get("default." + player.getName(), "team").toString());
		
		Bukkit.broadcastMessage(((team.getColor() + team.getDisplayName() + " §8| §7" + player.getName() + "§8: §f" + ChatColor.translateAlternateColorCodes('&', event.getMessage()))));
	}
	
}
