package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsedparty.PulsedParty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerListener implements Listener {
    private final PulsedParty instance;

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
}
