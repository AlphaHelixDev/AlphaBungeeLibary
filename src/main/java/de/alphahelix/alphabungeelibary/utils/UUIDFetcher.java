package de.alphahelix.alphabungeelibary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class UUIDFetcher {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapterWrapper()).create();
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

    private static final HashMap<UUID, String> names = new HashMap<>();
    private static final HashMap<String, UUID> uuids = new HashMap<>();

    /**
     * Gets the UUID of a {@link ProxiedPlayer}
     *
     * @param p the {@link ProxiedPlayer} to get its UUID from
     * @return the {@link UUID} of the {@link ProxiedPlayer}
     */
    public static UUID getUUID(ProxiedPlayer p) {
        return getUUID(p.getName());
    }

    /**
     * Gets the {@link UUID} of a {@link String}
     *
     * @param name the name of the {@link ProxiedPlayer}
     * @return the {@link UUID} of the {@link ProxiedPlayer}
     */
    public static UUID getUUID(String name) {
        if (name == null)
            return UUID.randomUUID();
        name = name.toLowerCase();

        if (uuids.containsKey(name))
            return uuids.get(name);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(UUID_URL, name, System.currentTimeMillis() / 1000)).openConnection();
            connection.setReadTimeout(5000);

            PlayerUUID player = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())),
                    PlayerUUID.class);

            if (player == null)
                return null;

            if (player.getId() == null)
                return null;

            uuids.put(name, player.getId());

            return player.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the name of a {@link UUID}
     *
     * @param uuid the {@link UUID} of the {@link ProxiedPlayer}
     * @return the name of the {@link ProxiedPlayer}
     */
    public static String getName(UUID uuid) {

        if (names.containsKey(uuid))
            return names.get(uuid);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(NAME_URL, UUIDTypeAdapterWrapper.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);

            PlayerUUID[] allUserNames = gson.fromJson(
                    new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID[].class);
            PlayerUUID currentName = allUserNames[allUserNames.length - 1];

            if (currentName == null)
                return "StevenIsDaBossOver16Chars";

            if (currentName.getName() == null) {
                return "StevenIsDaBossOver16Chars";
            }

            names.put(uuid, currentName.getName());

            return currentName.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "StevenIsDaBossOver16Chars";
        }
    }
}

class PlayerUUID {

    private String name;
    private UUID id;

    String getName() {
        return name;
    }

    UUID getId() {
        return id;
    }

}

