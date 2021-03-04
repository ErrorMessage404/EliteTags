package me.error.tags.Commands;

import me.error.tags.EliteTags;
import me.error.tags.MenuSystem.menu.TagsMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TagCommand implements CommandExecutor {

    private EliteTags plugin;

    public TagCommand(EliteTags plugin) {
        this.plugin = plugin;
        plugin.getCommand("tag").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        new TagsMenu(EliteTags.getPlayerMenuUtility(p)).open();

        return true;
    }
}
