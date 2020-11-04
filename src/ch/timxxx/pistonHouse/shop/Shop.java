package ch.timxxx.pistonHouse.shop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ch.timxxx.pistonHouse.file.FileManager;
import ch.timxxx.pistonHouse.mobcoins.MobcoinAPI;

public class Shop implements CommandExecutor, Listener {
	
	MobcoinAPI mbc;
	FileManager file = new FileManager("players.yml");
	FileManager shopFile = new FileManager("shop.yml");
	
	private static HashMap<String, Boolean> shop = new HashMap<String, Boolean>();
	
	public void putShop(Player player) {
		shop.put(player.getName(), false);
	}
	
	// Cancel that the player can take items from the Shop
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent event) {
		
		Player player = (Player) event.getView().getPlayer();
		
		if(getShop(player.getName()) && isInShop(event)) {
			mbc = new MobcoinAPI();
			event.setCancelled(true);
		
			if(event.getAction() == InventoryAction.PICKUP_ALL || event.getAction() == InventoryAction.PICKUP_HALF) {
			
				Material material = event.getCurrentItem().getType();
				int price = getPrice(material);
			
				boolean placeholder = false;
				if(price == 0) placeholder = true;
			
				if(!placeholder && isInShop(event)) {
				
					double mobcoins = mbc.getMobcoins(player);
				
					if(mobcoins >= price) {
						mbc.subtractMobcoins(player, price);
					
						ArrayList<String> lore = new ArrayList<String>();
					
						ItemStack item = event.getCurrentItem();
						ItemMeta itemMeta = item.getItemMeta();
						List<String> itemLore = itemMeta.getLore();
					
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
					
						if(player.getInventory().firstEmpty() == -1) {
						
							World world = player.getWorld();
							world.dropItem(player.getLocation(), item);
						
						
						} else player.getInventory().addItem(item);
					
						player.sendMessage("§7Du hast dieses Item für §9" + getPrice(material) + " §7Mobcoins gekauft.");
						itemMeta.setLore(itemLore);
						item.setItemMeta(itemMeta);
					
					} else player.sendMessage("§7Du hast §czu wenig §7Mobcoins!");
				}
			}			
		}		
	}
	
	// Get if click is in the Shop
	private boolean isInShop(InventoryClickEvent event) {
		if(event.getRawSlot() < 4 * 9) return true;
		else if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) return true;
		else if(event.getAction() == InventoryAction.PICKUP_SOME) return true;
		else return false;
	}
	
	
	// Get if player closed the Shop
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		Player player = (Player) event.getPlayer();

		if(getShop(player.getName())) {
			setShop(player.getName(), false);
		}
	}
	
	// Shop command
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			shop.put(player.getName(), true);
				
			Inventory inv = Bukkit.createInventory(null, 4 * 9, "§6§lShop");
			openShop(player, inv);
			
		}
		return false;
	}
	
	// Set the shop boolean
	private void setShop(String player, boolean shop) {
		Shop.shop.put(player, shop);
	}
	
	// Get the shop value
	private boolean getShop(String player) {
		return shop.get(player);
	}
	
	// Open a Shop
	private void openShop(Player owner, Inventory inv) {
		
		createItems(inv);
		
		owner.openInventory(inv);
		setShop(owner.getName(), true);
		owner.sendMessage("§7Du hast den §9Shop §7geöffnet.");
		
	}
	
	// Create the Items for the Shop
	private void createItems(Inventory inv) {
		
		// Super Pickaxe
		ItemStack pickaxe = createItem(Material.DIAMOND_PICKAXE, "§6§l§oThe Miner", true, 0, 100);
		pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 5);
		pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		addItem(pickaxe, 100, 12, inv);
		
		// Super Shovel
		ItemStack shovel = createItem(Material.DIAMOND_SHOVEL, "§6§l§oThe Digger", true, 0, 100);
		shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		addItem(shovel, 100, 16, inv);
		
		// Super Axe
		ItemStack axe = createItem(Material.DIAMOND_AXE, "§6§l§oThe Logger", true, 0, 100);
		axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		addItem(axe, 100, 14, inv);
		
		// Super Sword
		ItemStack sword = createItem(Material.DIAMOND_SWORD, "§6§l§oThe Killer", true, 1000, 200);
		sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
		sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
		addItem(sword, 200, 10, inv);
		
		// Shulker shell
		ItemStack shell = createItem(Material.SHULKER_SHELL, 30);
		addItem(shell, 30, 22, inv);
	}
	
	// Create a single Item without display name
	private ItemStack createItem(Material material, int price) {
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Preis: §9§o" + price + " Mobcoins");
		
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		item.setItemMeta(meta); 
		
		return item;
	}
	
	// Create a single Item with display name
	private ItemStack createItem(Material material, String name, boolean unbreakable, int damage, int price) {
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Preis: §9§o" + price + " Mobcoins");
		
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setUnbreakable(unbreakable);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

		item.setItemMeta(meta); 
		
		return item;
	}
	
	// Add an item to an inventory
	private void addItem(ItemStack item, int price, int place, Inventory inv) {
		inv.setItem(place, item);
		setPrice(price, item.getType());
	}

	// Set Price of item and save in config
	private void setPrice(int price, Material data) {
		shopFile.set("prices", data.toString(), price);
	}
	
	
	// Get the price of item in config
	private int getPrice(Material data) {
		Object _price = shopFile.get("prices", data.toString());
		int price = (int) _price;

		return price;
	}

}
