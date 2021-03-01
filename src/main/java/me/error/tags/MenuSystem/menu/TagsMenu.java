package me.error.tags.MenuSystem.menu;

import de.tr7zw.nbtapi.NBTItem;
import me.error.tags.EliteTags;
import me.error.tags.MenuSystem.Menu;
import me.error.tags.MenuSystem.PlayerMenuUtility;
import me.error.tags.Utils.BasicUtils;
import me.error.tags.Utils.ConsoleUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class TagsMenu extends Menu {

    private static EliteTags plugin = EliteTags.plugin;

    public TagsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public String getMenuName() {
        return BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.MenuTitle"));
    }

    public int getSlots() {
        return plugin.getConfig().getInt("Customization.TagsMenu.MenuSize");
    }

    public void handleMenu(InventoryClickEvent e) {

        NBTItem i_nbt = new NBTItem(e.getCurrentItem());
        System.out.print(i_nbt.getCompound("TagID").getInteger("TagID"));

    }

    public void setMenuItems() {

        ArrayList<ItemStack> tagItems = new ArrayList<ItemStack>();
        Player p = playerMenuUtility.getOwner();


    }
}
