package de.alphahelix.alphabungeelibary.commands.arguments;


import de.alphahelix.alphabungeelibary.utils.UUIDFetcher;

import java.util.UUID;

public class OfflinePlayerArgument implements IArgument<UUID> {

    @Override
    public boolean isCorrect(String arg) {
        return UUIDFetcher.getUUID(arg) != null;
    }

    @Override
    public UUID get(String arg) {
        if (isCorrect(arg))
            return UUIDFetcher.getUUID(arg);
        return null;
    }
}
