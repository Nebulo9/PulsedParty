package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.PulsedParty;
import fr.nebulo9.pulsedparty.player.PulsedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PlayerListener implements Listener {
    private final PulsedParty instance;
    public static final Map<PulsedPlayer, SimplifiedLocation> COORDINATES_TRACKINGS = new HashMap<>();
    public static final Map<PulsedPlayer,PulsedPlayer> PLAYER_TRACKINGS = new HashMap<>();

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
        PulsedPlayer player = new PulsedPlayer(event.getPlayer());
        if(player.isTracking()) {

        }
        if(player.isTracked()) {
            
        }
    }
}
