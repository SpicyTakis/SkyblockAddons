package net.dukthosting.skyblockaddons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("addPortal") && player.hasPermission("skyblockaddons.addportal")) {

        }

        return false;
    }

}
