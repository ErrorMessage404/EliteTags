package me.error.tags;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.error.tags.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EliteTags extends JavaPlugin {

    public static EliteTags plugin;

    public static ConfigManager cfgm; // Used for loading data.yml


    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("* * * EliteTags Is Enabling * * *");

        registerManagers();
        registerCommands();
        registerEvents();
        registerPlaceholders();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;
    }

    //Register Placeholders
    public void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("EliteTags", new PlaceholderHook() {
            @Override
            public String onRequest(OfflinePlayer p, String params) {
                if(p != null && p.isOnline()) return onPlaceholderRequest(p.getPlayer(), params);
                if(params.equalsIgnoreCase("tag_current")) return "N/A";

                return null;
            }

            @Override
            public String onPlaceholderRequest(Player p, String params) {
                if(p == null) return null;
                if(params.equalsIgnoreCase("creator")) return "ErrorMessage_404";
                if(params.equalsIgnoreCase("tag_current")) return "N/A";

                return null;
            }
        });
    }

    // Register Commands
    public void registerCommands() {
    }

    // Register Events
    public void registerEvents() {
    }

    // Register Managers
    public void registerManagers() {
        cfgm = new ConfigManager(this);
        // data.yml
        cfgm.setupData();
        cfgm.reloadData();
        cfgm.reloadData();
    }


}
