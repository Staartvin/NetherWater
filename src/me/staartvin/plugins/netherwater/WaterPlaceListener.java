// 
// Decompiled by Procyon v0.5.36
// 

package me.staartvin.plugins.netherwater;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WaterPlaceListener implements Listener {
    NetherWater plugin;

    public WaterPlaceListener(final NetherWater plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        final World world = event.getClickedBlock().getWorld();

        if (!plugin.getConfigManager().isWorldEnabled(world.getName())) return;

        if (world.getEnvironment() != World.Environment.NETHER) return;

        if (event.getItem().getType() != Material.WATER_BUCKET) return;

        // Cancel the event.
        event.setCancelled(true);

        // Set clicked block to water.
        event.getClickedBlock().getRelative(event.getBlockFace()).setType(Material.WATER);

        // Set item (from filled bucket to empty bucket).
        event.getItem().setType(Material.BUCKET);
    }
}
