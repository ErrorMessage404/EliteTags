package me.error.tags.Utils;

import me.error.tags.EliteTags;
import org.bukkit.Bukkit;

public class ConsoleUtils {

    private static EliteTags plugin = EliteTags.plugin;
    private static final String consolePrefix = "[EliteTags]";

    public static void Warn(String s) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + " *WARNING* " + s);
    }

    public static void Error(String s) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + " *ERROR* " + s);
    }

    public static void FatalError(String s) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + " *FATAL ERROR* " + s);
    }

    public static void Info(String s) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + " *INFO* " + s);
    }

    public static void Msg(String s) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + " " + s);
    }

}
