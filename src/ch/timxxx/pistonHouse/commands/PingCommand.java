package ch.timxxx.pistonHouse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.EntityPlayer;

public class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			try {
				
				CraftPlayer cp = (CraftPlayer) player;
				EntityPlayer ep = cp.getHandle();
				int ping = ep.ping;
				
				player.sendMessage("§7Ping: §6" + ping + "ms");
				
			} catch(Exception e) {
				e.printStackTrace();
				player.sendMessage("§cEin Fehler ist aufgetreten!");
			}
				
		}
		
		return false;
	}

}
