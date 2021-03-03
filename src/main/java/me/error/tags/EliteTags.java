package me.error.tags;

import me.error.tags.Commands.TagCommand;
import me.error.tags.Listener.MenuListener;
import me.error.tags.Managers.ConfigManager;
import me.error.tags.MenuSystem.PlayerMenuUtility;
import me.error.tags.Utils.ConsoleUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class EliteTags extends JavaPlugin {

    public static EliteTags plugin;
    public static Boolean debug;
    public static ConfigManager cfgm; // Used for loading data.yml

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<Player, PlayerMenuUtility>();

    @Override
    public void onEnable() {
        debug = this.getConfig().getBoolean("Debug");

        ConsoleUtils.Msg("* * * EliteTags Is Enabling * * *");
        if (debug) ConsoleUtils.Info("Debug Is Enabled, There Will Be Extra Logging");

        if (debug) ConsoleUtils.Info("Begining Registering Managers...");
        registerManagers();
        if (debug) ConsoleUtils.Info("Finished Registering Managers.");

        if (debug) ConsoleUtils.Info("Begining Registering Commands...");
        registerCommands();
        if (debug) ConsoleUtils.Info("Finished Registering Commands.");

        if (debug) ConsoleUtils.Info("Begining Registering Events...");
        registerEvents();
        if (debug) ConsoleUtils.Info("Finished Registering Events.");

        if (debug) ConsoleUtils.Info("Begining Registering Placeholders...");
        registerPlaceholders();
        if (debug) ConsoleUtils.Info("Finished Registering Placeholders.");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;

    }

    @Override
    public void onDisable () {

    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(p)) {
            return playerMenuUtilityMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        }
    }

    //Register Placeholders
    public void registerPlaceholders() {
        new PAPIExpansion(this).register();
    }

    // Register Commands
    public void registerCommands() {
        if (debug) ConsoleUtils.Info("Registering Tag Command...");
        new TagCommand(this);
        if (debug) ConsoleUtils.Info("Finished Registering Tag Command.");

        if (debug) ConsoleUtils.Info("Successfully Registered 1/1 Commands.");
    }

    // Register Events
    public void registerEvents() {
        if (debug) ConsoleUtils.Info("Registering Menu Listener...");
        new MenuListener(this);
        if (debug) ConsoleUtils.Info("Finished Registering Menu Listener.");

        if (debug) ConsoleUtils.Info("Successfully Registered 1/1 Listeners.");
    }

    // Register Managers
    public void registerManagers() {
        if (debug) ConsoleUtils.Info("Registering Config Manager...");
        cfgm = new ConfigManager(this);
        if (debug) ConsoleUtils.Info("Finished Registering Config Manager.");

        // data.yml
        cfgm.setupData();
        cfgm.reloadData();
        cfgm.reloadData();
        if (debug) ConsoleUtils.Info("Successfully Registered 1/1 Managers.");
    }


}
