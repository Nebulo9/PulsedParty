package fr.nebulo9.pulsedparty.commands;

import fr.nebulo9.pulsarlib.command.PLPlayerCommand;
import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.events.PlayerListener;
import fr.nebulo9.pulsedparty.player.TrackedPlayer;
import fr.nebulo9.pulsedparty.player.TrackingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GpsCommand extends PLPlayerCommand {

    @Override
    public boolean PLPExecute(Player player, Command command, String[] args) {
        int nbArgs = args.length;
        if(nbArgs == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                TrackingPlayer source = new TrackingPlayer(player);
                TrackedPlayer ptarget = new TrackedPlayer(target);
                source.setTrackingPlayer(true);
                ptarget.setTracked(true);
                PlayerListener.TRACKING_PLAYERS.add(source);
                PlayerListener.TRACKED_PLAYERS.add(ptarget);
                return true;
            }
            Message.playerErrorMessage(player,"This player does not exist or is offline");
        } else if(nbArgs > 1 && nbArgs <= 3) {
            List<Integer> coords;
            if(nbArgs == 2) {
                coords = new ArrayList<>(2);
            } else {
                coords = new ArrayList<>(3);
            }
            for(String arg : args) {
                try {
                    coords.add(Integer.parseInt(arg));
                } catch (NumberFormatException e) {
                    Message.playerErrorMessage(player,"Coordinates must not be decimals numbers");
                    return true;
                }
            }
            TrackingPlayer source = new TrackingPlayer(player);
            source.setTrackingLocation(true);
            World w = player.getWorld();
            double x = coords.get(0);
            double y,z;
            if(coords.size() == 2) {
                z = coords.get(1);
                y = w.getHighestBlockYAt((int)x,(int)z);
            } else {
                z = coords.get(2);
                y = coords.get(1);
            }
            SimplifiedLocation l = new SimplifiedLocation(w.getName(),x,y,z);
            source.setLocationTarget(l);
            return true;
        }
        return false;
    }
}