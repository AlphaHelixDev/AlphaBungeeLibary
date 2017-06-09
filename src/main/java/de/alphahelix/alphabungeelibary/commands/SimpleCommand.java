package de.alphahelix.alphabungeelibary.commands;

import de.alphahelix.alphabungeelibary.AlphaBungeeLibary;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public abstract class SimpleCommand extends Command {

    /**
     * Creates a new {@link SimpleCommand} to not manually register the command
     *
     * @param name the name of the command
     */
    public SimpleCommand(String name) {
        super(name);
        AlphaBungeeLibary.getInstance().getProxy().getPluginManager().registerCommand(AlphaBungeeLibary.getInstance(), this);
    }

    /**
     * Creates a new {@link SimpleCommand} to not manually register the command
     *
     * @param name       the commandname
     * @param permission the permission to execute this command
     * @param aliases    the aliases of this command
     */
    public SimpleCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        AlphaBungeeLibary.getInstance().getProxy().getPluginManager().registerCommand(AlphaBungeeLibary.getInstance(), this);
    }

    @Override
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Create a {@link String} out of a {@link String[]}
     *
     * @param args  The {@link String[]} which should be a {@link String}
     * @param start At which index of the array the {@link String} should start
     * @return the new created {@link String}
     */
    public String buildString(String[] args, int start) {
        String str = "";
        if (args.length > start) {
            str += args[start];
            for (int i = start + 1; i < args.length; i++) {
                str += " " + args[i];
            }
        }
        return str;
    }
}
