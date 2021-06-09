package net.dukthosting.skyblockaddons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockAddons extends JavaPlugin implements Listener {

    FileConfiguration config;
    DataManager data = new DataManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        // register the plugins events
        this.getServer().getPluginManager().registerEvents(this, this);

        // save the default config, if it doesnt exist
        this.saveDefaultConfig();

        config = getConfig();

        // create the plugins data directory, if it doesnt exist
        data.createDataDirectory();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        // create the player's data directory
        Player player = event.getPlayer();
        data.createPlayerSaveFile(player.getUniqueId());
        sendToWorld(player, data.getPlayerWorld(player.getUniqueId()));
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        sendToWorld(event.getEntity(),  event.getEntity().getWorld().getName());
    }

    public void sendToWorld(Player player, String worldName) {
        // get the spawn coordinates of the world
        double x = (double) config.getInt("worlds." + worldName + ".x");
        double y = (double) config.getInt("worlds." + worldName + ".y");
        double z = (double) config.getInt("worlds." + worldName + ".z");

        // teleport the player to the world
        player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z));
    }
}