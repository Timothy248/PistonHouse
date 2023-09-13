package ch.timxxx.pistonHouse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			double x = Math.round(player.getLocation().getX());
			double z = Math.round(player.getLocation().getZ());
			
			if(args.length == 0) {
				player.sendMessage("§7X: §6" + x + "§7, Z: §6" + z);
				
			} else if(args.length == 1) {
				
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					target.sendMessage("§7Position von §6" + player.getName() + "§7: X: §a" + x + "§7, Z: §a" + z);
					
				} else player.sendMessage("§7Dieser Spieler ist §cnicht §7online!");
				
			}
		}
		
		return false;
	}

}
