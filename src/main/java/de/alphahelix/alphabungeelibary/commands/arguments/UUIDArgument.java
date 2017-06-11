package de.alphahelix.alphabungeelibary.commands.arguments;


import de.alphahelix.alphabungeelibary.utils.UUIDFetcher;

import java.util.UUID;

public class UUIDArgument extends Argument<UUID> {
    @Override
    public boolean matches() {
        return UUIDFetcher.getUUID(getEnteredArgument()) != null;
    }

    @Override
    public UUID fromArgument() {
        if (matches())
            return UUIDFetcher.getUUID(getEnteredArgument());
        return null;
    }
}
