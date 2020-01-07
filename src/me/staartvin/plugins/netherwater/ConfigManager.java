package me.staartvin.plugins.netherwater;

import java.io.File;
import java.util.List;

public class ConfigManager {

    private NetherWater plugin;

    public ConfigManager(NetherWater instance) {
        this.plugin = instance;

        loadConfig();
    }

    private void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveDefaultConfig();
        } else {
            plugin.reloadConfig();
        }
    }

    /**
     * Toggle whether water can be placed in a world. The boolean that returns indicates whether placing on this
     * world is now enabled or disabled.
     * @param worldName Name of the world to toggle
     * @return true if the world has been toggled to true, otherwise false.
     */
    public boolean toggleWorld(String worldName) {

        boolean enabled = this.isWorldEnabled(worldName);

        setWorldEnabled(worldName, !enabled);

        return !enabled;
    }

    /**
     * Check whether placing water is enabled on the given world.
     * @param worldName Name of the world to check.
     * @return true if water may be placed on this world, false otherwise.
     */
    public boolean isWorldEnabled(String worldName) {
        return plugin.getConfig().getStringList("allowed worlds").contains(worldName);
    }

    /**
     * Set whether a player can place water on the given world.
     * @param worldName Name of the world
     * @param enabled Whether water can be placed on this world.
     */
    public void setWorldEnabled(String worldName, boolean enabled) {
        List<String> enabledWorlds = plugin.getConfig().getStringList("allowed worlds");

        if (enabled) {
            // Only add to the list if it isn't already in there. We don't like duplicates.
            if (!enabledWorlds.contains(worldName)) {
                enabledWorlds.add(worldName);
            }
        } else {
            enabledWorlds.remove(worldName);
        }

        plugin.getConfig().set("allowed worlds", enabledWorlds);

        plugin.saveConfig();
    }
}
