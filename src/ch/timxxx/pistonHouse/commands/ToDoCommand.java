package ch.timxxx.pistonHouse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ch.timxxx.pistonHouse.file.FileManager;

public class ToDoCommand implements CommandExecutor {

	FileManager file = new FileManager("ToDo.yml");
	private String prefix = "§a[ToDo] §r";
	
	private CommandSender sender;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		this.sender = sender;
		
		if(args.length == 0) sendList();
		else if(args.length == 1) mode(args);
		else if(args.length == 2) interactList(args);
		else usage();
		
		return false;
	}
	
	
	// Send the usage
	private void usage() {
		sender.sendMessage("§cBenutzung: /todo <add|remove> <todo>");
	}
	
	// Send a Message
	private void send(Object message) {
		sender.sendMessage(prefix + message.toString());
	}
	
	// Send the ToDo list
	private void sendList() {
		if(file.getList("default").isEmpty()) send("§7Die Liste ist leer!");
		else send("§7ToDo Liste:\n§7" + file.getList("default"));
	}
	
	// Check if the player removes or adds ToDo
	private void interactList(String[] args) {
		if(args[0].equalsIgnoreCase("add")) add(args[1]);
		else if(args[0].equalsIgnoreCase("remove")) remove(args[1]);
		else usage();
	}
	
	// Add element to the ToDo list
	private void add(String ToDo) {
		file.set("default", ToDo , ToDo);
		send("§7Erfolgreich §6hinzugefügt§7:\n" + file.getList("default"));
	}
	
	// Remove element from the ToDo list
	private void remove(String ToDo) {
		if(file.getList("default").isEmpty()) send("§7Die Liste ist leer!"); 
		else {
			file.remove("default", ToDo);
			send("§7Erfolgreich §6entfernt§7:\n" + file.getList("default"));
		}
	}
	
	// Checks if the player wants to modify the ToDo list
	private void mode(String[] args) {
		if(args[0].equalsIgnoreCase("clear")) removeAll();
		else usage();
	}
	
	// Remove all elements from list
	private void removeAll() {
		if(sender.hasPermission("PistonHouse.ToDo.clear")) {
			file.removeElements("default");
			send("§7Erfolgreich §6resettet!");
		}
		
	}
	
}






