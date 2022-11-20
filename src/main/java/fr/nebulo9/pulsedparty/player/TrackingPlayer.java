package fr.nebulo9.pulsedparty.player;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.player.PLPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TrackingPlayer extends PulsedPlayer {

    private UUID playerTarget;
    private SimplifiedLocation locationTarget;
    private boolean trackingPlayer;
    private boolean trackingLocation;
    public TrackingPlayer(Player player) {
        super(player);
    }

    public UUID getPlayerTarget() {
        return playerTarget;
    }

    public boolean isTrackingPlayer() {
        return trackingPlayer;
    }

    public SimplifiedLocation getLocationTarget() {
        return locationTarget;
    }

    public boolean isTrackingLocation() {
        return trackingLocation;
    }

    public void setLocationTarget(SimplifiedLocation locationTarget) {
        this.locationTarget = locationTarget;
    }

    public void setPlayerTarget(UUID playerTarget) {
        this.playerTarget = playerTarget;
    }

    public void setTrackingPlayer(boolean trackingPlayer) {
        this.trackingPlayer = trackingPlayer;
    }

    public void setTrackingLocation(boolean trackingLocation) {
        this.trackingLocation = trackingLocation;
    }
}