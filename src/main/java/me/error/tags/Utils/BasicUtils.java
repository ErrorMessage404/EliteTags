package me.error.tags.Utils;

import me.error.tags.EliteTags;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicUtils {

    private static EliteTags plugin = EliteTags.plugin;

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

    public static String GetActiveTag(Player p) {
        String tag = new String(), displayname = new String();
        Integer id = null;
        plugin.cfgm.reloadData();
        List tags = plugin.getConfig().getList("Tags");
        if(plugin.cfgm.getData().contains(p.getUniqueId().toString())) {
            for (int x = 0; x < tags.size(); x++) {
                System.out.print(tags.get(x).toString());
                Pattern idPattern = Pattern.compile("(?<=, ID=)(.*)(?=, Permisson=)", Pattern.DOTALL);
                Matcher idMatcher = idPattern.matcher(tags.get(x).toString());
                if(idMatcher.find()) id = Integer.parseInt(idMatcher.group(1));

                Pattern displaynamePattern = Pattern.compile("(?<=, DisplayName=)(.*)(?=, Description=)", Pattern.DOTALL);
                Matcher displaynameMatcher = displaynamePattern.matcher(tags.get(x).toString());
                if(displaynameMatcher.find()) displayname = displaynameMatcher.group(1);

                if(plugin.cfgm.getData().getInt(p.getUniqueId().toString()) == id) {
                    tag = chat(displayname);
                } else if((Integer) plugin.cfgm.getData().getInt(p.getUniqueId().toString()) == null) {
                    tag = "";
                } else tag = "";
            }
        } else {
            tag = "";
        }
        return tag;
    }

}
