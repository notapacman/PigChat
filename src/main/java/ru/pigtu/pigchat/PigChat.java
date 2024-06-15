package ru.pigtu.pigchat;

import ru.pigtu.pigchat.Listeners.AsyncPlayerChatListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PigChat extends JavaPlugin implements Listener {
    private static PigChat instance;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        this.getCommand("pigchat").setExecutor(this);
    }

    public void onDisable() {
        instance = null;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("pig.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.noperms")));
            return false;
        } else {
            this.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.reload")));
            return true;
        }
    }

    public static PigChat getInstance() {
        return instance;
    }
}