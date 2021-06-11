package net.dukthosting.skyblockaddons;

import org.bukkit.plugin.java.JavaPlugin;

class PortalManager {

    public JavaPlugin plugin;

    public PortalManager (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private JSONObject getPortalSaveData(UUID uuid) {
        File saveFile = new File(pluginFolder + "/portals.json");
        JSONObject data = null;
        if(saveFile.exists()) {
            try {
                FileReader reader = new FileReader(saveFile);
                try {
                    Object rawSaveData = new JSONParser().parse(reader);
                    data = (JSONObject) rawSaveData
                } catch (ParseException e) {
                    plugin.getLogger().severe("Error parsing portal config file.");
                    plugin.getLogger().severe(e.toString());
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Error reading portal config file.");
                plugin.getLogger().severe(e.toString());
            }
        } else {
            data = new JSONObject();
        }

        return data;
    }

    public boolean updatePlayerWorld(JSONObject data) {
        Boolean result = null;

        if (!data.exists) {
            result = false;
        } else {
            File saveFile = new File(pluginFolder + "portals.json");
            JSONObject currentSaveData = data.data;

            try {
                FileWriter writer = new FileWriter(saveFile);
                writer.write(data.toJSONString());
                writer.flush();
                writer.close();
                result = true;
            } catch (IOException e) {
                plugin.getLogger().severe("Error writing to player data file for " + uuid.toString() + ".");
                plugin.getLogger().severe(e.toString());
                result = false;
            }
        }

        return result;
    }
}