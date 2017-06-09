/*
 *     Copyright (C) <2016>  <AlphaHelixDev>
 *
 *     This program is free software: you can redistribute it under the
 *     terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphabungeelibary.utils;

import de.alphahelix.alphabungeelibary.AlphaBungeeLibary;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Util {

    /**
     * Rounds a {@link Double} up
     *
     * @param value     the {@link Double} to round
     * @param precision the precision to round up to
     * @return the rounded up {@link Double}
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * Creates a cooldown
     *
     * @param length       the lenght of the cooldown in ticks
     * @param key          the key to add a cooldown for
     * @param cooldownList the {@link List} where the key is in
     */
    public static <T> void cooldown(int length, final T key, final List<T> cooldownList) {
        cooldownList.add(key);

        AlphaBungeeLibary.getInstance().getProxy().getScheduler().schedule(
                AlphaBungeeLibary.getInstance(),
                () -> cooldownList.remove(key),
                length * 20,
                TimeUnit.SECONDS);
    }

    public static void runLater(long secondsLater, boolean async, Runnable runnable) {
        if (async) {
            ProxyServer.getInstance().getScheduler().schedule(AlphaBungeeLibary.getInstance(),
                    () -> ProxyServer.getInstance().getScheduler().runAsync(AlphaBungeeLibary.getInstance(), runnable), secondsLater, TimeUnit.SECONDS);
        } else {
            ProxyServer.getInstance().getScheduler().schedule(AlphaBungeeLibary.getInstance(),
                    runnable, secondsLater, TimeUnit.SECONDS);
        }
    }

    public static void runTimer(long secondsLater, long period, boolean async, Runnable runnable) {
        if (async) {
            ProxyServer.getInstance().getScheduler().schedule(AlphaBungeeLibary.getInstance(), () -> ProxyServer.getInstance().getScheduler().runAsync(AlphaBungeeLibary.getInstance(), runnable), secondsLater, period, TimeUnit.SECONDS);
        } else {
            ProxyServer.getInstance().getScheduler().schedule(AlphaBungeeLibary.getInstance(), runnable, secondsLater, period, TimeUnit.SECONDS);
        }
    }

    /**
     * @return [] out of a ... array
     */
    @SafeVarargs
    public static <T> T[] makeArray(T... types) {
        return types;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    /**
     * Gets the key by its value
     *
     * @param map   the {@link Map} where the key & value is inside
     * @param value the value of the key
     * @return the key
     */
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static BaseComponent[] msg(String message) {
        return new ComponentBuilder(message).create();
    }
}
