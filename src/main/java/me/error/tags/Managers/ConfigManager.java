package me.error.tags.Managers;

import me.error.tags.EliteTags;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private EliteTags plugin;

    public ConfigManager(EliteTags plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration dataConfig;
    public File dataFile;

    ////////////////////////////////////////////////////////////
    //                                                        //
    ////////////////////////////////////////////////////////////

    public void setupData() {
        if(!plugin.getDataFolder().exists()) { plugin.getDataFolder().mkdir(); }
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch(IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("Could not create file");
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getData() {
        return dataConfig;
    }

    public void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Could not save file");
        }
    }

    public void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

}
