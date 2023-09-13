package ch.timxxx.pistonHouse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;

public class FeedCommand implements CommandExecutor {

	MobcoinAPI mbc;
	double price = 1.5;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		mbc = new MobcoinAPI();
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 0) {
				
				if(mbc.getMobcoins(player) >= price) {
					
					if(player.getFoodLevel() < 20) {
					
						player.setFoodLevel(20);
						mbc.subtractMobcoins(player, price);
						player.sendMessage("�7Du hast �agegessen �7f�r �6" + price + "�7 Mobcoins!");
					
					} else player.sendMessage("�7Du bist schon satt!");
						
				} else player.sendMessage("�7Du hast daf�r �czu wenig �7Mobcoins!");
				
			} else player.sendMessage("�cBenutzung: /feed");
			
		} else sender.sendMessage("Diesen Command d�rfen nur Spieler benutzen!");
		
		return false;
	}

}