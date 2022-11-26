package fr.nebulo9.pulsedparty.commands;

import fr.nebulo9.pulsarlib.command.PLPlayerCommand;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.events.PlayerListener;
import fr.nebulo9.pulsedparty.player.TrackingPlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GpsendCommand extends PLPlayerCommand {

    @Override
    public boolean PLPExecute(Player player, Command command, String[] strings) {
        TrackingPlayer pPlayer = new TrackingPlayer(player);
        if(PlayerListener.TRACKING_PLAYERS.contains(pPlayer)) {
            PlayerListener.TRACKED_PLAYERS.removeIf(p -> p.getPlayerTracker().equals(pPlayer.getUUID()));
            PlayerListener.TRACKING_PLAYERS.remove(pPlayer);
        }
        Message.playerErrorMessage(player,"Your not tracking any location or player.");
        return true;
    }
}