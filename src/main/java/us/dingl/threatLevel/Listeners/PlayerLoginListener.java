package us.dingl.threatLevel.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import us.dingl.threatLevel.ThreatLevel;

public class PlayerLoginListener implements Listener {

    private final ThreatLevel plugin;

    public PlayerLoginListener(ThreatLevel plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        plugin.setOptedIn(playerName, plugin.isOptedIn(playerName));
    }
}
