package de.alphahelix.alphabungeelibary.text;

import de.alphahelix.alphabungeelibary.commands.SimpleCommand;
import de.alphahelix.alphabungeelibary.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class ClickText {

    public static void sendClickText(ProxiedPlayer p, String text, TextAction action) {
        UUID id = UUIDFetcher.getUUID(p);
        new ActionTextCommand(id, action);

        TextComponent txt = new TextComponent(text);

        txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/atcPerform_" + id));

        p.sendMessage(txt);
    }

    public static void sendHoverClickText(ProxiedPlayer p, String clickText, String hovertext, TextAction clickAction) {
        UUID id = UUIDFetcher.getUUID(p);
        new ActionTextCommand(id, clickAction);

        TextComponent txt = new TextComponent(clickText);

        txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/atcPerform_" + id));
        txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hovertext).create()));

        p.sendMessage(txt);
    }

    private static class ActionTextCommand extends SimpleCommand {

        private TextAction action;

        public ActionTextCommand(UUID player, TextAction action) {
            super("atcPerform_" + player.toString(), "", "");
            this.action = action;
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            action.run((ProxiedPlayer) sender);
        }
    }
}
