package fr.nebulo9.pulsedparty.commands.admin;

import fr.nebulo9.pulsarlib.command.PLCommand;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsarlib.message.color.ColorCode;
import fr.nebulo9.pulsedparty.PulsedParty;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PrivilegedCommand extends PLCommand {
    @Override
    public boolean checkArg(String[] strings, int i, String s) {
        return false;
    }

    @Override
    public boolean PLExecute(CommandSender commandSender, Command command, String[] args) {
        if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
            Message.senderMessage(commandSender, Arrays.toString(((List<String>)PulsedParty.getPPConfig().getValue("privileged")).toArray()));
        } else if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "add": {
                    switch(args[1].toLowerCase()) {
                        case "player": {
                            Player target = Bukkit.getPlayer(args[2]);
                            if(target != null) {
                                PulsedParty.getPPConfig().addPrivilileged(target.getUniqueId());
                                Message.senderMessage(commandSender,Message.build(
                                        "Added ",
                                        ColorCode.YELLOW.getChat(),
                                        target.getName(),
                                        ColorCode.RESET.getChat(),
                                        " to the privileged players."
                                ));
                                return true;
                            } else {
                                Message.senderErrorMessage(commandSender,"This player does not exist or is offline.");
                            }
                            break;
                        }
                        case "uuid": {
                            PulsedParty.getPPConfig().addPrivilileged(UUID.fromString(args[2]));
                            Message.senderMessage(commandSender,Message.build(
                                    "Added ",
                                    ColorCode.YELLOW.getChat(),
                                    args[2],
                                    ColorCode.RESET.getChat(),
                                    " to the privileged players."
                            ));
                            return true;
                        }
                    }
                    break;
                }
                case "remove": {
                    switch(args[1].toLowerCase()) {
                        case "player": {
                            Player target = Bukkit.getPlayer(args[2]);
                            if(target != null) {
                                PulsedParty.getPPConfig().removePrivileged(target.getUniqueId());
                                Message.senderMessage(commandSender, Message.build(
                                        "Removed ",
                                        ColorCode.YELLOW.getChat(),
                                        target.getName(),
                                        ColorCode.RESET.getChat(),
                                        " from the privileged players."
                                ));
                                return true;
                            }
                            break;
                        }
                        case "uuid": {
                            PulsedParty.getPPConfig().removePrivileged(UUID.fromString(args[2]));
                            Message.senderMessage(commandSender,Message.build(
                                    "Removed ",
                                    ColorCode.YELLOW.getChat(),
                                    args[2],
                                    ColorCode.RESET.getChat(),
                                    " from the privileged players."
                            ));
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }
}
