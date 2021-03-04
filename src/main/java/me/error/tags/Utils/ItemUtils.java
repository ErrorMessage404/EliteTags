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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Enchant Item If User Has Tag Enabled
        if(plugin.cfgm.getData().contains(p.getUniqueId().toString())) {
            if(plugin.cfgm.getData().getString(p.getUniqueId().toString()).equals(String.valueOf(ID))) {
                FinalItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                ItemMeta fMeta = FinalItem.getItemMeta();
                fMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                FinalItem.setItemMeta(fMeta);
            }
        }

        return FinalItem;
    }

    public static ArrayList<ItemStack> CreateAllTagItems(Player player) {
        ArrayList<ItemStack> TagItems = new ArrayList<ItemStack>();
        List tags = plugin.getConfig().getList("Tags");
        for (int x = 0; x < tags.size(); x++) {
            String name = null, displayname = null, description = null, permission = null;
            Boolean requiresPermission = null;
            Integer id = null;

            Pattern namePattern = Pattern.compile("(?<=Name=)(.*)(?=, DisplayName=)", Pattern.DOTALL);
            Matcher nameMatcher = namePattern.matcher(tags.get(x).toString());
            if(nameMatcher.find()) name = nameMatcher.group(1);

            Pattern displaynamePattern = Pattern.compile("(?<=, DisplayName=)(.*)(?=, Description=)", Pattern.DOTALL);
            Matcher displaynameMatcher = displaynamePattern.matcher(tags.get(x).toString());
            if(displaynameMatcher.find()) displayname = displaynameMatcher.group(1);

            Pattern descriptionPattern = Pattern.compile("(?<=, Description=)(.*)(?=, RequiresPermission=)", Pattern.DOTALL);
            Matcher descriptionMatcher = descriptionPattern.matcher(tags.get(x).toString());
            if(descriptionMatcher.find()) description = descriptionMatcher.group(1);

            Pattern requiresPermissionPattern = Pattern.compile("(?<=, RequiresPermission=)(.*)(?=, ID=)", Pattern.DOTALL);
            Matcher requiresPermissionMatcher = requiresPermissionPattern.matcher(tags.get(x).toString());
            if(requiresPermissionMatcher.find()) requiresPermission = Boolean.parseBoolean(requiresPermissionMatcher.group(1));

            Pattern idPattern = Pattern.compile("(?<=, ID=)(.*)(?=, Permisson=)", Pattern.DOTALL);
            Matcher idMatcher = idPattern.matcher(tags.get(x).toString());
            if(idMatcher.find()) id = Integer.parseInt(idMatcher.group(1));

            Pattern permissionPattern = Pattern.compile("(?<=, Permisson=)(.*)(?=})", Pattern.DOTALL);
            Matcher permissionMatcher = permissionPattern.matcher(tags.get(x).toString());
            if(permissionMatcher.find()) permission = permissionMatcher.group(1);

            if(name == null || displayname == null || description == null || id == null || requiresPermission == null || permission == null) return null;
            else {
                ItemStack i = TagItem(player, name, displayname, description, (ArrayList<String>) plugin.getConfig().getList("Customization.TagsMenu.TagsItem.Lore"), id, requiresPermission, permission);
                TagItems.add(i);
            }
        }
        return TagItems;
    }

    public static ItemStack getClearTagItem() {
        ItemStack fi;
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.Material")));
        ItemMeta iM = i.getItemMeta();
        iM.setDisplayName(BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.DisplayName")));
        if(plugin.getConfig().getList("Customization.TagsMenu.ClearTagItem.Lore").size() > 0) {
            ArrayList<String> iL = new ArrayList<>();
            for (int x = 0; x < plugin.getConfig().getList("Customization.TagsMenu.ClearTagItem.Lore").size(); x++) {
                String e = BasicUtils.chat((String) plugin.getConfig().getList("Customization.TagsMenu.ClearTagItem.Lore").get(x));
                iL.add(e);
            }
            iM.setLore(iL);
        }
        i.setItemMeta(iM);
//        if(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.Material")).equals(Material.STAINED_CLAY) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.Material")).equals(Material.STAINED_GLASS) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.Material")).equals(Material.STAINED_GLASS_PANE) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.ClearTagItem.Material")).equals(Material.WOOL)) {
//            i.setDurability((short) plugin.getConfig().getInt("Customization.TagsMenu.ClearTagItem.Color"));
//        }

        NBTItem i_nbt = new NBTItem(i);
        NBTCompound i_nbt_id = i_nbt.addCompound("I_Use");
        i_nbt_id.setString("I_Use", "ClearTag");
        fi = i_nbt.getItem();

        if(plugin.getConfig().getBoolean("Customization.TagsMenu.ClearTagItem.Glowing") == true) {
            fi.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta fiM = fi.getItemMeta();
            fiM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            fi.setItemMeta(fiM);
        }

        return fi;
    }

    public static ItemStack getNextPageItem() {
        ItemStack fi;
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.NextPage.Material")));
        ItemMeta iM = i.getItemMeta();
        iM.setDisplayName(BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.NextPage.DisplayName")));
        if(plugin.getConfig().getList("Customization.TagsMenu.NextPage.Lore").size() > 0) {
            ArrayList<String> iL = new ArrayList<>();
            for (int x = 0; x < plugin.getConfig().getList("Customization.TagsMenu.NextPage.Lore").size(); x++) {
                String e = BasicUtils.chat((String) plugin.getConfig().getList("Customization.TagsMenu.NextPage.Lore").get(x));
                iL.add(e);
            }
            iM.setLore(iL);
        }
        i.setItemMeta(iM);
//        if(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.NextPage.Material")).equals(Material.STAINED_CLAY) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.NextPage.Material")).equals(Material.STAINED_GLASS) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.NextPage.Material")).equals(Material.STAINED_GLASS_PANE) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.NextPage.Material")).equals(Material.WOOL)) {
//            i.setDurability((short) plugin.getConfig().getInt("Customization.TagsMenu.NextPage.Color"));
//        }

        NBTItem i_nbt = new NBTItem(i);
        NBTCompound i_nbt_id = i_nbt.addCompound("I_Use");
        i_nbt_id.setString("I_Use", "NextPage");
        fi = i_nbt.getItem();

        if(plugin.getConfig().getBoolean("Customization.TagsMenu.NextPage.Glowing") == true) {
            fi.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta fiM = fi.getItemMeta();
            fiM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            fi.setItemMeta(fiM);
        }

        return fi;
    }

    public static ItemStack getPreviousPageItem() {
        ItemStack fi;
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.Material")));
        ItemMeta iM = i.getItemMeta();
        iM.setDisplayName(BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.DisplayName")));
        if(plugin.getConfig().getList("Customization.TagsMenu.PreviousPage.Lore").size() > 0) {
            ArrayList<String> iL = new ArrayList<>();
            for (int x = 0; x < plugin.getConfig().getList("Customization.TagsMenu.PreviousPage.Lore").size(); x++) {
                String e = BasicUtils.chat((String) plugin.getConfig().getList("Customization.TagsMenu.PreviousPage.Lore").get(x));
                iL.add(e);
            }
            iM.setLore(iL);
        }
        i.setItemMeta(iM);
//        if(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.Material")).equals(Material.STAINED_CLAY) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.Material")).equals(Material.STAINED_GLASS) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.Material")).equals(Material.STAINED_GLASS_PANE) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.PreviousPage.Material")).equals(Material.WOOL)) {
//            i.setDurability((short) plugin.getConfig().getInt("Customization.TagsMenu.PreviousPage.Color"));
//        }

        NBTItem i_nbt = new NBTItem(i);
        NBTCompound i_nbt_id = i_nbt.addCompound("I_Use");
        i_nbt_id.setString("I_Use", "PreviousPage");
        fi = i_nbt.getItem();

        if(plugin.getConfig().getBoolean("Customization.TagsMenu.PreviousPage.Glowing") == true) {
            fi.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta fiM = fi.getItemMeta();
            fiM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            fi.setItemMeta(fiM);
        }

        return fi;
    }

    public static ItemStack getFillerItem() {
        ItemStack fi;
        ItemStack i = new ItemStack(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.Filler.Material")));
        ItemMeta iM = i.getItemMeta();
        iM.setDisplayName(BasicUtils.chat(plugin.getConfig().getString("Customization.TagsMenu.Filler.DisplayName")));
        if(plugin.getConfig().getList("Customization.TagsMenu.Filler.Lore").size() > 0) {
            ArrayList<String> iL = new ArrayList<>();
            for (int x = 0; x < plugin.getConfig().getList("Customization.TagsMenu.Filler.Lore").size(); x++) {
                String e = BasicUtils.chat((String) plugin.getConfig().getList("Customization.TagsMenu.Filler.Lore").get(x));
                iL.add(e);
            }
            iM.setLore(iL);
        }
        i.setItemMeta(iM);
        if(Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.Filler.Material")).equals(Material.STAINED_CLAY) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.Filler.Material")).equals(Material.STAINED_GLASS) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.Filler.Material")).equals(Material.STAINED_GLASS_PANE) || Material.valueOf(plugin.getConfig().getString("Customization.TagsMenu.Filler.Material")).equals(Material.WOOL)) {
            i.setDurability((short) plugin.getConfig().getInt("Customization.TagsMenu.Filler.Color"));
        }

        NBTItem i_nbt = new NBTItem(i);
        NBTCompound i_nbt_id = i_nbt.addCompound("I_Use");
        i_nbt_id.setString("I_Use", "Filler");
        fi = i_nbt.getItem();

        if(plugin.getConfig().getBoolean("Customization.TagsMenu.Filler.Glowing") == true) {
            fi.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta fiM = fi.getItemMeta();
            fiM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            fi.setItemMeta(fiM);
        }

        return fi;
    }
}
