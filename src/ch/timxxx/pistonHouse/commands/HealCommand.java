package ch.timxxx.pistonHouse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;

public class HealCommand implements CommandExecutor {

	MobcoinAPI mbc;
	double price = 10.0;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		mbc = new MobcoinAPI();
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 0) {
				
				if(mbc.getMobcoins(player) >= price) {
					
					double hearts = player.getHealthScale();
					
					if(player.getHealth() < hearts) {
					
						player.setHealth(hearts);
						mbc.subtractMobcoins(player, price);
						player.sendMessage("§7Du hast dich §ageheilt §7für §6" + price + "§7 Mobcoins!");
					
					} else player.sendMessage("§7Du bist schon vollgeheilt!");
						
				} else player.sendMessage("§7Du hast dafür §czu wenig §7Mobcoins!");
				
			} else player.sendMessage("§cBenutzung: /heal");
			
		} else sender.sendMessage("Diesen Command dürfen nur Spieler benutzen!");
		
		return false;
	}

}
