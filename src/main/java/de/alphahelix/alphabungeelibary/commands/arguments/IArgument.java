package de.alphahelix.alphabungeelibary.commands.arguments;

public interface IArgument<T> {

    boolean isCorrect(String arg);

    T get(String arg);
}
