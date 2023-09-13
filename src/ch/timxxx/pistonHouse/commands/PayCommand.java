package ch.timxxx.pistonHouse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;

public class PayCommand implements CommandExecutor {
	
	MobcoinAPI mbc;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		mbc = new MobcoinAPI();
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 2) {
				
				double mobcoins = mbc.getMobcoins(player);
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target != null) {
					
					try {
						
						double amount = Double.parseDouble(args[1]);
						
						if(mobcoins >= amount) {
					
							mbc.subtractMobcoins(player, amount);
							mbc.addMobcoins(target, amount);
							player.sendMessage("§7Du hast dem Spieler §9" + target.getName() + "§a " + amount + " §7Mobcoins überwiesen!");
							target.sendMessage("§7Der Spieler §9" + player.getName() + "§7 hat dir §a" + amount + " §7Mobcoins überwiesen!");
							
							
						} else player.sendMessage("§7Du hast §czu wenig §7Mobcoins dafür!");
						
					} catch(Exception e) {
						player.sendMessage("§cDas ist keine gültige Zahl!");
					}
					
					
				} else player.sendMessage("§cDieser Spieler ist nicht online!");
				
			} else player.sendMessage("§cBitte benutze /pay [player] [amount]");
		
		}
		
		return false;

	}
	
}
