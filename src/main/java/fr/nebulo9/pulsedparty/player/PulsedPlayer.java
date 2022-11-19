package fr.nebulo9.pulsedparty.player;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.player.PLPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PulsedPlayer extends PLPlayer {
    private boolean tracking;
    private boolean tracked;

    public PulsedPlayer(Player player) {
        super(player.getUniqueId(),player.getName());
        this.tracked = false;
        this.tracking = false;
    }

    public boolean isTracking() {
        return tracking;
    }

    public boolean isTracked() {
        return tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }
}