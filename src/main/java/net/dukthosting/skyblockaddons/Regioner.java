package net.dukthosting.skyblockaddons;

import org.bukkit.Location;

public class Regioner {
    public static boolean getInRegion (Location target, Location dim1, Location dim2) {
        boolean inRegion = false;
        if((target.getBlockX() > dim1.getBlockX()) && (target.getBlockX() < dim2.getBlockX())){
            if((target.getBlockY() > dim1.getBlockY()) && (target.getBlockY() < dim2.getBlockY())){
                if((target.getBlockZ() > dim1.getBlockZ()) && (target.getBlockZ() < dim2.getBlockZ())){
                    inRegion = true;
                }
            }
        }

        return inRegion;
    }

}
