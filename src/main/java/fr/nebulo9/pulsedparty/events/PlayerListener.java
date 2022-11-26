package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.PulsedParty;
import fr.nebulo9.pulsedparty.player.PulsedPlayer;
import fr.nebulo9.pulsedparty.player.TrackedPlayer;
import fr.nebulo9.pulsedparty.player.TrackingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
    public void onPlayerLeave(PlayerQuitEvent event) {
        PulsedPlayer player = new PulsedPlayer(event.getPlayer());
        TRACKING_PLAYERS.removeIf(p -> p.getPlayerTarget().equals(player.getUUID()));
        TRACKED_PLAYERS.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player bPlayer = event.getPlayer();
        PulsedPlayer player = new PulsedPlayer(bPlayer);
        int trackingIndex = TRACKING_PLAYERS.indexOf(player);
        int trackedIndex = TRACKED_PLAYERS.indexOf(player);
        if(trackingIndex > -1) {
            TrackingPlayer trackingPlayer = TRACKING_PLAYERS.get(trackingIndex);
            SimplifiedLocation trackingPlayerLoc = new SimplifiedLocation(bPlayer.getLocation());
            Map<String, Double> distMap = trackingPlayerLoc.distance(trackingPlayer.getLocationTarget());
            String dist = distMap.get("distance").toString();
            StringBuilder message = new StringBuilder();
            if(trackingPlayer.isTrackingLocation()) {
                SimplifiedLocation targetLoc = trackingPlayer.getLocationTarget();
                message.append((int)targetLoc.getX()).append(" ").append((int)targetLoc.getY()).append(" ").append((int)targetLoc.getZ());
            } else if(trackingPlayer.isTrackingPlayer()) {
                Player bTarget = Bukkit.getPlayer(trackingPlayer.getUUID());
                for (TrackedPlayer tp : TRACKED_PLAYERS) {
                    if(tp.getUUID().equals(trackingPlayer.getPlayerTarget())) {
                        message.append(tp.getName());
                        break;
                    }
                }
                if(bTarget.getWorld().getEnvironment() != bPlayer.getWorld().getEnvironment()) {
                    distMap = Map.of("distance",0d,"diffX",0d,"diffZ",0d);
                    dist = "?";
                }
            }
            String dir = dir(distMap.get("diffX").intValue(),distMap.get("diffZ").intValue());
            message.append(dist).append("m").append(dir);
        }
        if(trackedIndex > -1) {
            TrackedPlayer trackedPlayer = TRACKED_PLAYERS.get(trackedIndex);
            for (TrackingPlayer tp : TRACKING_PLAYERS) {
                if(tp.getUUID().equals(trackedPlayer.getPlayerTracker())) {
                    SimplifiedLocation loc = new SimplifiedLocation(bPlayer.getLocation());
                    tp.setLocationTarget(loc);
                }
            }

        }
    }

    public String dir(int diffX, int diffZ) {
        if(diffX < 0 ) {
            if(diffZ < 0) {
                return "🡿";
            } else if(diffZ > 0) {
                return "🡼";
            }
            return "🡸";
        } else if(diffX > 0) {
            if(diffZ < 0) {
                return "🡾";
            } else if(diffZ > 0) {
                return "🡽";
            }
            return "🡺";
        } else {
            if(diffZ <= 0) {
                return "🡻";
            } else {
                return "🡹";
            }
        }
    }
}
