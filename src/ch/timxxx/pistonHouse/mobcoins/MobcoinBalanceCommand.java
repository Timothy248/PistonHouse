package ch.timxxx.pistonHouse.mobcoins;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobcoinBalanceCommand implements CommandExecutor{

	MobcoinAPI mbc;
	Player player;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		mbc = new MobcoinAPI();
		
		if(sender instanceof Player) {
			player = (Player) sender;
			
			if(args.length == 0) sendMobcoins();
			else if(args.length == 1) sendMobcoins(args);
			else sender.sendMessage("");
			
		} else sender.sendMessage("§cDiesen Command dürfen nur Spieler benutzen!");
		
		return false;
	}
	
	private void sendMobcoins() {
		double mobcoins = mbc.getMobcoins(player);
		if(mobcoins == 1) player.sendMessage("§7Du hast §9ein §7Mobcoin.");
		else player.sendMessage("§7Du hast §9" + mobcoins + "§7 Mobcoins.");
	}
	
	private void sendMobcoins(String[] args) {
		Player person = Bukkit.getPlayer(args[0]);
		if(person == null) player.sendMessage("§7Dieser Spieler ist §6nicht §7online!");
		else player.sendMessage("§9" + person.getName() + "§7 hat §a" + mbc.getMobcoins(person) + "§7 Mobcoins.");
	}

}
