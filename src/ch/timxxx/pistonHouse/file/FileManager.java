package ch.timxxx.pistonHouse.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ch.timxxx.pistonHouse.main.Main;

public class FileManager {
	
	private String fileName;
	private File file;
	private FileConfiguration config;
		
	public FileManager(String fileName) {
		this.fileName = fileName;
		
		load();
	}
	
	// Load the YML file
	private void load() {
		file = new File(Main.getPlugin().getDataFolder().getPath(), fileName);
		if(!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				Bukkit.broadcastMessage("§c[FILE] FAILED TO CREATE FILE");
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	private void reload() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	// Setter
	public void set(String path, String key, Object value) {
		checkSection(path);
		config.set(path + "." + key, value);
		save();
	}
	
	// Getter
	public Object get(String path, String key) {
		checkSection(path);
		Object value = config.get(path + "." + key);
		save();
		return value;
	}
	
	public String getList(String path) {
		checkSection(path);
		String list = "";
		
		for(String element : config.getConfigurationSection(path).getKeys(false)) {
			list += "> " + element;
			list += "\n";
		}
		save();
		return list;
	}
	
	// Remove things
	public void removeElements(String path) {
		checkSection(path);
		for(String e : config.getConfigurationSection(path).getKeys(true)) {
			config.set(path + "." + e, null);
		}
		save();
	}
	
	public void remove(String path, String key) {
		checkSection(path);
		config.set(path + "." + key, null);
		save();
	}
	
	private void checkSection(String path) {
		reload();
		if(!config.isConfigurationSection(path)) {
			createSection(path);
			save();
		}
	}
	
	private void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			Bukkit.broadcastMessage("§c[FILE] FAILED TO SAVE DOCUMENT: " + file.getName());
		}
	}
	
	private void createSection(String section) {
		config.createSection(section);
		save();
	}
	
	public void setList(String path, ArrayList<String> list) {
		config.set(path, list);
		save();
	}
	
	@SuppressWarnings("unused")
	private ArrayList<String> getArrayList(String path) {
		ArrayList<String> list = (ArrayList<String>) config.getStringList(path);
		return list;
	}
	
}
