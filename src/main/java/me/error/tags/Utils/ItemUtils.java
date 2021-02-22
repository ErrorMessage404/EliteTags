package me.error.tags.Utils;

import me.error.tags.EliteTags;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    private static EliteTags plugin;

    public static ItemStack TagItem(Player p, String name, String formatedName, String description, String[] lore, Boolean requiresPermission, String permission) {
        // Creating Tag Item
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.TagsItem.Material")));

        // Getting Tag Item Meta
        ItemMeta iMeta = i.getItemMeta();

        // Getting Tag Status
        Boolean status;
        String i_status;
        if(requiresPermission) {
            if(plugin.getConfig().getBoolean("Permissions.UseCustomPermissions")) {
                if(p.hasPermission(permission)) status = true;
                else status = false;
            } else {
                if(p.hasPermission("elitetags.access." + name)) status = true;
                else status = false;
            }
        } else status = true;
        if(status) i_status = BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.TagsItem.TagStatus_UnlockedPlaceholder"));
        else i_status = BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.TagsItem.TagStatus_LockedPlaceholder"));

        // Setting Item Name
        String i_name = plugin.getConfig().getString("Customization.TagsMenu.TagsItem.DisplayName")
                .replace("{tag_name}", name)
                .replace("{tag_displayName}", formatedName)
                .replace("{tag_description}", description)
                .replace("{tag_status}", i_status);
        iMeta.setDisplayName(BasicUtils.chat(i_name));

        return i;
    }
}
