package sk.GGGEDR.ChatPerGroup;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Core extends JavaPlugin implements Listener {

    LuckPerms api;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        getServer().getPluginManager().registerEvents(this, this);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
    }

    @Override
        public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onChatting(AsyncPlayerChatEvent e) {

        if (!(getConfig().getString("pergroup." + api.getUserManager().getUser(e.getPlayer().getName()).getPrimaryGroup()) == null)){
            e.setFormat(colorMSG(getConfig().getString("pergroup." + api.getUserManager().getUser(e.getPlayer().getName()).getPrimaryGroup()).replaceAll("<nick>", e.getPlayer().getName()).replaceAll("<msg>", e.getMessage())));
        } else {
            e.setFormat(colorMSG(getConfig().getString("default").replaceAll("<nick>", e.getPlayer().getName()).replaceAll("<msg>", e.getMessage())));
        }
    }




    public String colorMSG(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
