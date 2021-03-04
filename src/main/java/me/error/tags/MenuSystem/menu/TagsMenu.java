package me.error.tags.MenuSystem.menu;

import de.tr7zw.nbtapi.NBTItem;
import me.error.tags.EliteTags;
import me.error.tags.MenuSystem.Menu;
import me.error.tags.MenuSystem.PaginatedMenu;
import me.error.tags.MenuSystem.PlayerMenuUtility;
import me.error.tags.Utils.BasicUtils;
import me.error.tags.Utils.ConsoleUtils;
import me.error.tags.Utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TagsMenu extends PaginatedMenu {

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

        if(i_nbt.getCompound("TagID") != null) {
            plugin.cfgm.getData().set(playerMenuUtility.getOwner().getUniqueId().toString(), i_nbt.getCompound("TagID").getInteger("TagID"));
            plugin.cfgm.saveData();
            plugin.cfgm.reloadData();
            e.getWhoClicked().closeInventory();
            super.open();
        } else if(i_nbt.getCompound("I_Use") != null) {
            if(i_nbt.getCompound("I_Use").getString("I_Use").equals("NextPage")) {
                e.setCancelled(true);
                if (page == 0){
                    playerMenuUtility.getOwner().sendMessage("You are already on the first page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            } else if(i_nbt.getCompound("I_Use").getString("I_Use").equals("PreviousPage")) {
                e.setCancelled(true);
                if (!((index + 1) >= ItemUtils.CreateAllTagItems(playerMenuUtility.getOwner()).size())){
                    page = page + 1;
                    super.open();
                }else{
                    playerMenuUtility.getOwner().sendMessage("You are on the last page.");
                }
            } else if(i_nbt.getCompound("I_Use").getString("I_Use").equals("ClearTag")) {
                plugin.cfgm.getData().set(playerMenuUtility.getOwner().getUniqueId().toString(), 0);
                plugin.cfgm.saveData();
                plugin.cfgm.reloadData();
                e.getWhoClicked().closeInventory();
                super.open();
            } else if(i_nbt.getCompound("I_Use").getString("I_Use").equals("Filler")) {
                e.setCancelled(true);
            }
        }
    }

    public void setMenuItems() {

        addMenuBorder();

        Player p = playerMenuUtility.getOwner();
        ArrayList<ItemStack> tagItems = ItemUtils.CreateAllTagItems(p);

        if(tagItems != null && !tagItems.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= tagItems.size()) break;
                if (tagItems.get(index) != null){
                    inventory.addItem(tagItems.get(index));
                }
            }
        }

        if(page == 0) {
            if(maxItemsPerPage == 27) {
                inventory.setItem(48, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 20) {
                inventory.setItem(39, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 13) {
                inventory.setItem(30, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 7) {
                inventory.setItem(21, super.FILLER_GLASS);
            }
        }

        if ((index + 1) >= ItemUtils.CreateAllTagItems(p).size()){
            if(maxItemsPerPage == 27) {
                inventory.setItem(50, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 20) {
                inventory.setItem(41, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 13) {
                inventory.setItem(32, super.FILLER_GLASS);
            } else if(maxItemsPerPage == 7) {
                inventory.setItem(23, super.FILLER_GLASS);
            }
        }
    }
}
