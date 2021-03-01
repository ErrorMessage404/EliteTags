package me.error.tags.Utils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import me.error.tags.EliteTags;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {

    private static EliteTags plugin = EliteTags.plugin;

    public static ItemStack TagItem(Player p, String name, String formatedName, String description, ArrayList<String> lore, int ID, Boolean requiresPermission, String permission) {
        // Creating Tag Item
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.TagsItem.Material")));
        ItemStack FinalItem;

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

        // Enchant Item If User Has Tag Enabled
        if(plugin.cfgm.getData().getString(p.getUniqueId().toString()).equals(name)) {
            i.addEnchantment(Enchantment.LURE, 1);
            iMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        // Setting Lore
        ArrayList<String> i_lore = new ArrayList<String>();
        for (int x = 0; x < lore.size(); x++) {
            i_lore.add(BasicUtils.chat(lore.get(x).replace("{tag_name}", name)
                    .replace("{tag_displayName}", formatedName)
                    .replace("{tag_description}", description)
                    .replace("{tag_status}", i_status)));
        }
        iMeta.setLore(i_lore);

        // Applying Meta To ItemStack
        i.setItemMeta(iMeta);

        // Adding ID NBT
        NBTItem i_nbt = new NBTItem(i);
        NBTCompound i_nbt_id = i_nbt.addCompound("TagID");
        i_nbt_id.setInteger("TagID", ID);
        FinalItem = i_nbt.getItem();

        return FinalItem;
    }

    public static ArrayList<ItemStack> CreateAllTagItems(Player player) {
        ArrayList<ItemStack> TagItems = new ArrayList<ItemStack>();
        List TagsConfig = plugin.getConfig().getMapList("Tags");
//        System.out.println(TagsConfig);

        for (int x = 0; x < TagsConfig.size(); x++) {
//            TagItem(player, TagsConfig.get(x))
        }

        return TagItems;
    }

}
