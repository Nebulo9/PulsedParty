package fr.nebulo9.pulsedparty;

import fr.nebulo9.pulsarlib.PLConfig;
import fr.nebulo9.pulsarlib.PulsarLib;
import fr.nebulo9.pulsarlib.message.Message;
import org.bukkit.plugin.java.JavaPlugin;

public final class PulsedParty extends JavaPlugin {

    public static final String MINECRAFT_VERSION = PulsarLib.MINECRAFT_VERSION;

    private static PLConfig config;

    @Override
    public void onEnable() {
        PulsarLib.setPlugin(this);
        Message.logInfo(Message.build("Using PulsarLib ",PulsarLib.VERSION));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
