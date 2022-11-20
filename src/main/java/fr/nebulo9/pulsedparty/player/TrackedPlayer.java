package fr.nebulo9.pulsedparty.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public class TrackedPlayer extends PulsedPlayer {

    private boolean tracked;
    private UUID playerTracker;
    public TrackedPlayer(Player player) {
        super(player);
    }

    public boolean isTracked() {
        return tracked;
    }

    public UUID getPlayerTracker() {
        return playerTracker;
    }

    public void setPlayerTracker(UUID playerTracker) {
        this.playerTracker = playerTracker;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }
}