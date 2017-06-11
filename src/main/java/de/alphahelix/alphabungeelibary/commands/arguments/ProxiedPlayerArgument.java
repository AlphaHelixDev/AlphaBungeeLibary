package de.alphahelix.alphabungeelibary.commands.arguments;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerArgument implements IArgument<ProxiedPlayer> {

    @Override
    public boolean isCorrect(String arg) {
        return ProxyServer.getInstance().getPlayer(arg) != null;
    }

    @Override
    public ProxiedPlayer get(String arg) {
        if (isCorrect(arg))
            return ProxyServer.getInstance().getPlayer(arg);
        return null;
    }
}
