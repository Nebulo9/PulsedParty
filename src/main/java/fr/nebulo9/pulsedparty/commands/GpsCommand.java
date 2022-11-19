package fr.nebulo9.pulsedparty.commands;

import fr.nebulo9.pulsarlib.command.PLPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GpsCommand extends PLPlayerCommand {

    @Override
    public boolean PLPExecute(Player player, Command command, String[] args) {
        int nbArgs = args.length;
        if(nbArgs == 1) {
        }
        return false;
    }
}