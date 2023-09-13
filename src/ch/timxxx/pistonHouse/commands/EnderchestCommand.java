package ch.timxxx.pistonHouse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;

public class EnderchestCommand implements CommandExecutor {

	MobcoinAPI mbc;
	double price = 5.0;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		mbc = new MobcoinAPI();
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 0) {
				
				if(mbc.getMobcoins(player) >= price) {
					
					player.openInventory(player.getEnderChest());
					mbc.subtractMobcoins(player, price);
					player.sendMessage("§7Du hast deine §aEnderchest §7für §6" + price + "§7 Mobcoins geöffnet!");
					
				} else player.sendMessage("§7Du hast dafür §czu wenig §7Mobcoins!");
				
			} else {
			
				if(args.length == 1 && player.hasPermission("pistonHouse.Enderchest")) {
					
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						
						player.openInventory(target.getEnderChest());
						player.sendMessage("§7Du hast die Enderchest von §6" + args[0] + "§7 geöffnet!");
						
					} else player.sendMessage("§7Dieser Spieler ist §4nicht §7online!");
					
				} else player.sendMessage("§cBenutzung: /enderchest, /echest, /ec");
					
			}
			
		} else sender.sendMessage("Diesen Command dürfen nur Spieler benutzen!");
		
		return false;
	}

}
