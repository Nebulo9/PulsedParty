package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.PulsedParty;
import fr.nebulo9.pulsedparty.player.PulsedPlayer;
import fr.nebulo9.pulsedparty.player.TrackedPlayer;
import fr.nebulo9.pulsedparty.player.TrackingPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerListener implements Listener {
    private final PulsedParty instance;
    public static final ArrayList<TrackingPlayer> TRACKING_PLAYERS = new ArrayList<>();
    public static final ArrayList<TrackedPlayer> TRACKED_PLAYERS = new ArrayList<>();

    public PlayerListener(PulsedParty instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        if(PulsedParty.getPPConfig().isPrivileged(deadPlayer)) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            if((boolean)PulsedParty.getPPConfig().getValue("privilegedDeathDrops")) {
                int rand = new Random().nextInt(100);
                if(rand == 1) {
                    int drops = new Random().nextInt(3);
                    deadPlayer.getWorld().dropItem(deadPlayer.getLocation(),new ItemStack(Material.DIAMOND,drops));
                }
            }
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = PulsedParty.getPPConfig().getJoinMessage();
        if(!joinMessage.isEmpty()) {
            Message.playerMessage(event.getPlayer(),joinMessage);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player bPlayer = event.getPlayer();
        PulsedPlayer player = new PulsedPlayer(bPlayer);
        int trackingIndex = TRACKING_PLAYERS.indexOf(player);
        int trackedIndex = TRACKED_PLAYERS.indexOf(player);
        if(trackingIndex > -1) {
            TrackingPlayer trackingPlayer = TRACKING_PLAYERS.get(trackingIndex);
            if(trackingPlayer.isTrackingLocation()) {
                
            } else if(trackingPlayer.isTrackingPlayer()) {

            }
        }
        if(trackedIndex > -1) {
            TrackedPlayer trackedPlayer = TRACKED_PLAYERS.get(trackedIndex);
            TrackingPlayer source = null;
            for (TrackingPlayer tp : TRACKING_PLAYERS) {
                if(tp.getUUID().equals(trackedPlayer.getPlayerTracker())) {
                    source = tp;
                }
            }
            SimplifiedLocation loc = new SimplifiedLocation(bPlayer.getLocation());
            source.setLocationTarget(loc);
        }
    }
}
