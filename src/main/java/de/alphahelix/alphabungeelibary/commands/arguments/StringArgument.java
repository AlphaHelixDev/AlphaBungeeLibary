package de.alphahelix.alphabungeelibary.commands.arguments;

public class StringArgument implements IArgument<String> {
    @Override
    public boolean isCorrect(String arg) {
        return true;
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}