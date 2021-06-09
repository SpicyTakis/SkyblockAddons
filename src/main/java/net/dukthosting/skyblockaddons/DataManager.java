package net.dukthosting.skyblockaddons;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class DataManager {

    public JavaPlugin plugin;

    private final String pluginFolder;

    public DataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        pluginFolder = this.plugin.getDataFolder().getAbsolutePath();
    }

    public void createDataDirectory() {
        File saveDir = new File(pluginFolder + "/data/");
        try {
            if (saveDir.createNewFile()) {
                plugin.getLogger().info("Data directory already exists.");
            } else {
                plugin.getLogger().info("Data directory doesn't exist, creating it.");
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error creating data directory.");
            plugin.getLogger().severe(e.toString());
        }
    }

    public boolean updatePlayerWorld(UUID uuid, String worldname) {
        Boolean result = null;
        PlayerData data = getPlayerSaveData(uuid);
        if (!data.exists) {
            result = false;
        } else {
            File saveFile = new File(pluginFolder + "/data/" + uuid.toString() + ".json");
            JSONObject currentSaveData = data.data;

            try {
                FileWriter writer = new FileWriter(saveFile);
                currentSaveData.put("world", worldname);
                writer.write(currentSaveData.toJSONString());
                result = true;
            } catch (IOException e) {
                plugin.getLogger().severe("Error writing to player data file for " + uuid.toString() + ".");
                plugin.getLogger().severe(e.toString());
                result = false;
            }
        }

        return result;
    }

    public String getPlayerWorld(UUID uuid) {
        File saveFile = createPlayerSaveFile(uuid);
        PlayerData saveData = getPlayerSaveData(uuid);
        return (String) saveData.data.get("world");
    }

    public File createPlayerSaveFile(UUID uuid) {
        // lets create the save file
        File saveFile = new File(pluginFolder + "/data/" + uuid.toString() + ".json");
        try {
            if (saveFile.createNewFile()) {
                plugin.getLogger().info("Player data file for " + uuid.toString() + " already exists, not changing it.");
                return saveFile;
            } else {
                plugin.getLogger().info("Player data file for " + uuid.toString() + " doesn't exist, creating it.");
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error creating player data file for " + uuid.toString() + ".");
            plugin.getLogger().severe(e.toString());
        }

        // lets write the default config to the save file
        if (!saveFile.exists()) {
            try {
                FileWriter writer = new FileWriter(saveFile);
                JSONObject dataTemplate = new JSONObject();
                dataTemplate.put("world", "world");
                writer.write(dataTemplate.toJSONString());
            } catch (IOException e) {
                plugin.getLogger().severe("Error writing to player data file for " + uuid.toString() + ".");
                plugin.getLogger().severe(e.toString());
            }
        }

        return saveFile;
    }

    private PlayerData getPlayerSaveData(UUID uuid) {
        File saveFile = new File(pluginFolder + "/data/" + uuid.toString() + ".json");
        PlayerData data = null;
        if(saveFile.exists()) {
            try {
                FileReader reader = new FileReader(saveFile);
                try {
                    Object rawSaveData = new JSONParser().parse(reader);
                    data = new PlayerData(true, (JSONObject) rawSaveData);
                } catch (ParseException e) {
                    plugin.getLogger().severe("Error parsing player data file for " + uuid.toString() + ".");
                    plugin.getLogger().severe(e.toString());
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Error reading player data file for " + uuid.toString() + ".");
                plugin.getLogger().severe(e.toString());
            }
        } else {
            data = new PlayerData(false, new JSONObject());
        }

        return data;
    }

    private void deletePlayerSaveData (UUID uuid) {

    }
}
