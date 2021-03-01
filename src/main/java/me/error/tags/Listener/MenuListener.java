package me.error.tags.Listener;

import me.error.tags.EliteTags;
import me.error.tags.MenuSystem.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    private EliteTags plugin;

    public MenuListener(EliteTags plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null) return;
        InventoryHolder holder = e.getClickedInventory().getHolder();

        if(holder instanceof Menu) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getType() == Material.AIR) return;
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }

    }

}
