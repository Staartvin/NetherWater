// 
// Decompiled by Procyon v0.5.36
// 

package me.staartvin.plugins.netherwater;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter
{
    private NetherWater plugin;
    
    public CommandManager(final NetherWater plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (args.length > 1) {
            // Command to toggle on a world

            if (args[0].equalsIgnoreCase("toggle")) {

                String targetWorldName = null;

                // Find the matching world with the given name.
                for (World world : plugin.getServer().getWorlds()) {
                    if (world.getName().equalsIgnoreCase(args[1])) {
                        targetWorldName = world.getName();
                    }
                }

                // Notify sender if the given world does not exist.
                if (targetWorldName == null) {
                    sender.sendMessage(ChatColor.RED + "World '" + args[1] + "' does not exist.");
                    return true;
                }

                // Toggle world.
                boolean enabled = plugin.getConfigManager().toggleWorld(targetWorldName);

                // Let the user know.
                sender.sendMessage(ChatColor.GRAY + "Placing water on " + ChatColor.GOLD + targetWorldName + ChatColor.GRAY + " is now " + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));

                return true;
            }

        } else if (args.length > 0) {
            // Command to reload

            if (args[0].equalsIgnoreCase("reload")) {

                // Check permission.
                if (!sender.hasPermission("netherwater.reload")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                    return true;
                }

                // Reload config
                this.plugin.reloadConfig();

                sender.sendMessage(ChatColor.GREEN + "Configuration file reloaded.");

                return true;
            } else if (args[0].equalsIgnoreCase("toggle")) {
                sender.sendMessage(ChatColor.RED + "Please specify a world to toggle!");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Unknown command argument.");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0 || strings[0].equalsIgnoreCase("")) {
            return Arrays.asList("toggle", "reload");
        }

        if (strings[0].equalsIgnoreCase("toggle")) {
            return plugin.getServer().getWorlds().stream().map(World::getName).collect(Collectors.toList());
        }

        return null;
    }
}
