package me.error.tags.Utils;

import me.error.tags.EliteTags;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class BasicUtils {

    private static EliteTags plugin;

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

}
