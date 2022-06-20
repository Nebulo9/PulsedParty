package fr.nebulo9.pulsedparty.commands;

import fr.nebulo9.pulsarlib.command.PingCommand;
import fr.nebulo9.pulsarlib.manager.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager extends Manager {

    public CommandManager(JavaPlugin instance) {
        super(instance);
    }

    @Override
    public void register() {
        instance.getCommand("ping").setExecutor(new PingCommand());

    }
}
