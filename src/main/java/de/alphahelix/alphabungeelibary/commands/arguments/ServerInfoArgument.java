package de.alphahelix.alphabungeelibary.commands.arguments;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class ServerInfoArgument extends Argument<ServerInfo> {
    @Override
    public boolean matches() {
        return ProxyServer.getInstance().getServerInfo(getEnteredArgument()) != null;
    }

    @Override
    public ServerInfo fromArgument() {
        if (matches())
            return ProxyServer.getInstance().getServerInfo(getEnteredArgument());
        return null;
    }
}
