package de.alphahelix.alphabungeelibary.files;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleYAMLFile {

    private final String name;
    private final String pluginPath;
    private Configuration file;

    /**
     * Creates a new {@link File} on the BungeeCord Proxy
     *
     * @param plugin   the name of the folder
     * @param fileName the name of the file
     */
    public SimpleYAMLFile(String plugin, String fileName) {
        this.name = fileName;
        this.pluginPath = plugin;
        try {
            File configFile = new File(pluginPath, name);

            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }

            file = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(pluginPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the {@link File}
     */
    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(file, new File(pluginPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new default value to the {@link File}
     *
     * @param path  the path where the value should be saved at
     * @param value the value wich should be saved
     */
    public void setDefault(String path, Object value) {
        Object obj = file.get(path);
        if (obj == null) {
            file.set(path, value);
            save();
        }
    }

    /**
     * Gets a {@link String} with ColorCodes instead of '&~'
     *
     * @param path the path where the {@link String} is located at
     * @return the colorized {@link String}
     */
    public String getColorString(String path) {
        return ChatColor.translateAlternateColorCodes('&', file.getString(path));
    }

    /**
     * Call this method in your Constructor and override it in your class.
     */
    public void addValues() {

    }

    /**
     * Saves a {@link java.util.List} with {@link String} inside the File
     *
     * @param path   the path where the {@link java.util.List} should be saved at
     * @param values the {@link String}s you want to save
     */
    public void setStringList(String path, String... values) {
        ArrayList<String> toSave = Lists.newArrayList(values);

        setDefault(path, toSave);
    }

    /**
     * Gets the {@link File} to get normal Strings, int, doubles, StringLists etc.
     *
     * @return the {@link File}
     */
    public Configuration getFile() {
        return file;
    }
}
