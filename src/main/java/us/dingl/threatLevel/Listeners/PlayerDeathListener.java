package us.dingl.threatLevel.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import us.dingl.threatLevel.ThreatLevel;

public class PlayerDeathListener implements Listener {

    private final ThreatLevel plugin;

    public PlayerDeathListener(ThreatLevel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        String victimName = victim.getName();
        int victimThreatLevel = plugin.getThreatLevel(victimName);
        ItemStack bountyItem = getItemStack(victimThreatLevel);

        if (victim.getKiller() instanceof Player killer) {
            String killerName = killer.getName();
            int killerThreatLevel = plugin.getThreatLevel(killerName);

            if (!plugin.isWarTime()) {
                if (!plugin.isOptedIn(victimName)) {
                    plugin.setThreatLevel(killerName, killerThreatLevel + 3);
                    killer.sendMessage("§cYou have gained 3 threat levels for killing an opted out player.");
                    if (!plugin.isOptedIn(killerName)) {
                        plugin.setOptedIn(killerName, true);
                        killer.sendMessage("§cYou have been opted in due to killing a player.");
                    }
                } else if (plugin.getThreatLevel(victimName) > 0) {
                    killer.sendMessage("§aYou have not gained any threat level as the person you have killed had threat level.");
                    if (victimThreatLevel > 0) {
                        killer.getInventory().addItem(bountyItem);
                    }
                    plugin.setThreatLevel(victimName, victimThreatLevel - 1);
                }
            } else {
                killer.sendMessage("§aYou have not gained any threat level as it is war time.");
            }
        }
    }

    private static @NotNull ItemStack getItemStack(int victimThreatLevel) {
        int victimBounty = 0;

        if (victimThreatLevel == 1) {
            victimBounty += 16;
        } else if (victimThreatLevel == 2) {
            victimBounty += 32;
        } else if (victimThreatLevel == 3) {
            victimBounty += 48;
        } else if (victimThreatLevel == 4) {
            victimBounty += 64;
        } else if (victimThreatLevel >= 5) {
            victimBounty += 64;
            victimBounty += 16 * (victimThreatLevel - 4);
        }

        if (victimBounty == 0) {
            return new ItemStack(Material.AIR);
        } else {
            return new ItemStack(Material.DIAMOND, victimBounty);
        }
    }
}
