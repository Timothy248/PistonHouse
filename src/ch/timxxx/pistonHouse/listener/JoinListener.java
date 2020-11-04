package ch.timxxx.pistonHouse.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import ch.timxxx.pistonHouse.commands.SneakCommand;
import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.shop.Shop;
import ch.timxxx.pistonHouse.util.Scoreboards;

public class JoinListener implements Listener {
	
	private SneakCommand sneak = new SneakCommand();
	FileManager file = new FileManager("players.yml");
	Scoreboards board = new Scoreboards();
	Shop shop = new Shop();

	@EventHandler
	public void handlePlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setCollidable(false);
		
		// Set the scoreboard
		sneak.setInvisible(false, player.getName());
		board.setScoreboard(player);
		
		if(!player.hasPlayedBefore()) {
			setupPlayer(player);
		} else reset(player);
		
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(file.get("default." + player.getName(), "team").toString());
		
		// Join Message
		event.setJoinMessage("§7Der Spieler §f" + team.getColor() + player.getName() + " §7hat den Server betreten");
		
		// Tab list
		player.setPlayerListName(team.getColor() + team.getDisplayName() + " §8| §7" + player.getName());
		
		player.setSneaking(true);
	}
	
	// Add Player to default team on first join
	private void setupPlayer(Player player) {
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Extern");
		team.addEntry(player.getName());
		
		file.set("default." + player.getName(), "team", team.getName());
		file.set("default." + player.getName(), "invisible", false);
		file.set("default." + player.getName(), "mobcoins", 0);
	}
	
	
	// Reset the player to default settings
	private void reset(Player player) {
		resetSneak(player);
		
		shop.putShop(player);
	}
	
	
	// Reset for /sneak command
	private void resetSneak(Player player) {
		String team = sneak.getTeam(player.getName());
		Team trueTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team);
		trueTeam.addEntry(player.getName());
		
	}
}
