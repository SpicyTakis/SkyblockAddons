package net.dukthosting.skyblockaddons;

import org.json.simple.JSONObject;

public class PlayerData {

    public boolean exists;
    public JSONObject data;

    public PlayerData(boolean fileExists, JSONObject playerData) {
        exists = fileExists;
        data = playerData;
    }

}
