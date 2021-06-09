package net.dukthosting.skyblockaddons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockAddons extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();
    DataManager data = new DataManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        data.createDataDirectory();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        data.createPlayerSaveFile(player.getUniqueId());
    }

    public void sendToWorld(Player player, String worldName) {
        double x = (double) config.getInt("worlds." + worldName + ".x");
        double y = (double) config.getInt("worlds." + worldName + ".y");
        double z = (double) config.getInt("worlds." + worldName + ".z");
        player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z));
    }
}
