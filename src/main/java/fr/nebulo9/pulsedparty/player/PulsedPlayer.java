package fr.nebulo9.pulsedparty.player;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.player.PLPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PulsedPlayer extends PLPlayer {

    public PulsedPlayer(Player player) {
        super(player.getUniqueId(),player.getName());
    }
}