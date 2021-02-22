package me.error.tags.MenuSystem.menu;

import me.error.tags.EliteTags;
import me.error.tags.MenuSystem.Menu;
import me.error.tags.Utils.BasicUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;\
import java.util.ArrayList;

public class TagsMenu extends Menu {

    private static EliteTags plugin;

    public String getMenuName() {
        return BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.MenuTitle"));
    }

    public int getSlots() {
        return plugin.getConfig().getInt("Customization.TagsMenu.MenuSize");
    }

    public void handleMenu(InventoryClickEvent e) {



    }

    public void setMenuItems() {

        ArrayList<ItemStack> tagItems = new ArrayList<ItemStack>();

    }
}
