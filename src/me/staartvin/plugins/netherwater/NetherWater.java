// 
// Decompiled by Procyon v0.5.36
// 

package me.staartvin.plugins.netherwater;

import org.bukkit.plugin.java.JavaPlugin;

public class NetherWater extends JavaPlugin
{
    private ConfigManager configManager;
    private CommandManager commandManager = new CommandManager(this);
    
    public void onEnable() {

        // Enable config.
        setConfigManager(new ConfigManager(this));

        // Enable listener to check when water is placed.
        this.getServer().getPluginManager().registerEvents(new WaterPlaceListener(this), this);

        // Enable command manager.
        this.getCommand("netherwater").setExecutor(commandManager);
        this.getCommand("netherwater").setTabCompleter(commandManager);
    }
    
    public void onDisable() {
        this.saveConfig();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
