package ch.timxxx.pistonHouse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			Player player = (Player) sender;	
			
			if(args.length == 1 && player.hasPermission("pistonHouse.Inventory")) {
					
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
						
					player.openInventory(target.getInventory());
					player.sendMessage("§7Du hast das Inventar von §6" + args[0] + "§7 geöffnet!");
						
				} else player.sendMessage("§7Dieser Spieler ist §4nicht §7online!");
					
			}
			
		} else sender.sendMessage("Diesen Command dürfen nur Spieler benutzen!");
		
		return false;
	}

}