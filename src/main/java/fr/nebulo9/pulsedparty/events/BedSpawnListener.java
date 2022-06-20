package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsedparty.PulsedParty;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedSpawnListener implements Listener {
    private final PulsedParty instance;

    public BedSpawnListener(PulsedParty instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Location eventLocation = event.getBed().getLocation();
        if(PulsedParty.getPPConfig().isPrivileged(player)) {
            Location bedHome = PulsedParty.getPPConfig().getHome(player,"bed").getLocation().getLocation();
            if(!bedHome.equals(eventLocation)) {
                PulsedParty.getPPConfig().addHome(player,"bed",eventLocation);
            }
        }
    }
}
