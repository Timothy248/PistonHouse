package ch.timxxx.pistonHouse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.util.Scoreboards;

public class SneakCommand implements CommandExecutor {
	
	FileManager file = new FileManager("players.yml");
	Scoreboards board = new Scoreboards();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			
			// Get the player and get the invisible team
			Player player = (Player) sender;
			Team invisible = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("invisible");
			
			// Player isn't invisible
			if(!getInvisible(player.getName())) { 
				Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName());
				setTeam(team.getName(), player.getName());
				
				invisible.addEntry(player.getName());
				player.sendMessage("§7Dein Nametag ist nun §6unsichtbar§7!");	
					
			// Player is invisible
			} else {
				String team = getTeam(player.getName());
				Team trueTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team);
				trueTeam.addEntry(player.getName());
				player.sendMessage("§7Dein Nametag ist nun §6sichtbar§7!");
			}
				
			// Change the invisible var in the conf team
			if(getInvisible(player.getName())) {
				setInvisible(false, player.getName());
				board.deactivateSneak(player);
			} else { 
				setInvisible(true, player.getName());
				board.activateSneak(player);
			}
			
		}
		
		return false;
	}
	
	public void setTeam(String team, String player) {
		file.set("default." + player, "team", team);
	}
	
	public String getTeam(String player) {
		Object _team = file.get("default." + player, "team");
		String team = _team.toString();
		
		return team;
	}
	
	public void setInvisible(boolean inv, String player) {
		file.set("default." + player, "invisible", inv);
	}
	
	public boolean getInvisible(String player) {
		Object _inv = file.get("default." + player, "invisible");
		boolean inv = (boolean) _inv;
		
		return inv;
	}

}
