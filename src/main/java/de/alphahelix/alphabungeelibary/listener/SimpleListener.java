package de.alphahelix.alphabungeelibary.listener;

import de.alphahelix.alphabungeelibary.AlphaBungeeLibary;
import net.md_5.bungee.api.plugin.Listener;

public class SimpleListener implements Listener {

    /**
     * Create a new {@link SimpleListener} to automatically register the {@link Listener}
     */
    public SimpleListener() {
        AlphaBungeeLibary.getInstance().getProxy().getPluginManager().registerListener(AlphaBungeeLibary.getInstance(), this);
    }
}
