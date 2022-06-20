package fr.nebulo9.pulsedparty.events;

import fr.nebulo9.pulsarlib.manager.Manager;
import fr.nebulo9.pulsedparty.PulsedParty;

public class ListenerManager extends Manager {

    public ListenerManager(PulsedParty instance) {
        super(instance);
    }

    @Override
    public void register() {
        instance.getServer().getPluginManager().registerEvents(new PlayerListener((PulsedParty)instance),instance);
        instance.getServer().getPluginManager().registerEvents(new MentionListener((PulsedParty)instance),instance);
        instance.getServer().getPluginManager().registerEvents(new BedSpawnListener((PulsedParty) instance),instance);
    }
}
