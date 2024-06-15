package ru.pigtu.pigchat.Listeners;

import ru.pigtu.pigchat.PigChat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        String format = PigChat.getInstance().getConfig().getString("PigFormat");
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            format = PlaceholderAPI.setPlaceholders(p, format);
        }

        format = this.colorize(this.hex(format));
        message = p.hasPermission("pig.colors") ? this.colorize(message) : message;
        format = format.replace("{name}", p.getName()).replace("{displayname}", p.getDisplayName()).replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{message}", message).replace("{world}", p.getWorld().getName()).replace("%", "%%");
        e.setMessage(message);
        e.setFormat(format);
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String hex(String msg) {
        if (Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]) < 16) {
            return msg;
        } else {
            Matcher matcher = Pattern.compile("&(#\\w{6})").matcher(ChatColor.translateAlternateColorCodes('&', msg));
            StringBuffer buffer = new StringBuffer();

            while(matcher.find()) {
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());
            }

            return matcher.appendTail(buffer).toString();
        }
    }
}