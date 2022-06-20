package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsarlib.message.color.ColorCode;
import fr.nebulo9.pulsedparty.PulsedParty;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MentionListener implements Listener {
    private final PulsedParty instance;

    public MentionListener(PulsedParty instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Pattern pattern = Pattern.compile("/@(\\w|-)+/g");
        Matcher matcher = pattern.matcher(message);
        Set<String> mentions = new HashSet<>();
        Set<Player> mentionnedPlayers = new HashSet<>();
        while(matcher.find()) {
            String matchedSequence = matcher.group();
            Player target = Bukkit.getPlayer(matchedSequence.substring(1));
            if(target != null) {
                mentions.add(matchedSequence);
                mentionnedPlayers.add(target);
            }
        }
        if(mentions.size() > 0 && mentionnedPlayers.size() > 0) {
            mentions.forEach(mention -> {
                event.setMessage(message.replace(mention, Message.build(ColorCode.LIGHT_PURPLE.getChat(),ColorCode.BOLD.getChat(),mention,ColorCode.RESET.getChat())));
            });
            mentionnedPlayers.forEach(player -> {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            });
        }
    }
}
