package de.alphahelix.alphabungeelibary;

import net.md_5.bungee.api.plugin.Plugin;

public class AlphaBungeeLibary extends Plugin {

    private static AlphaBungeeLibary instance;

    public static AlphaBungeeLibary getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }
}