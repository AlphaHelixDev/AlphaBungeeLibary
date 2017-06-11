package de.alphahelix.alphabungeelibary.commands.arguments;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerArgument extends Argument<ProxiedPlayer> {
    @Override
    public boolean matches() {
        return ProxyServer.getInstance().getPlayer(getEnteredArgument()) != null;
    }

    @Override
    public ProxiedPlayer fromArgument() {
        if (matches())
            return ProxyServer.getInstance().getPlayer(getEnteredArgument());
        return null;
    }
}
