package net.lapismc.lapischat.factions.channels;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import net.lapismc.lapischat.factions.LapisChatFactions;
import net.lapismc.lapischat.framework.Channel;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.permissions.Permission;

import java.util.HashSet;
import java.util.Set;

public class Factions extends Channel {

    private final LapisChatFactions plugin;

    public Factions(LapisChatFactions plugin) {
        super("Factions", plugin.getValue("Factions.ShortName"), plugin.getValue("Factions.Prefix"),
                new Permission(plugin.getValue("Factions.Permission")), plugin.getValue("Factions.Format"));
        this.plugin = plugin;
    }

    @Override
    protected String format(ChatPlayer from, String msg, String format) {
        return applyDefaultFormat(from, msg, format);
    }

    @Override
    public Set<ChatPlayer> getRecipients(ChatPlayer p) {
        Set<ChatPlayer> list = new HashSet<>();
        FPlayer player = FPlayers.fPlayers().get(p.getPlayer());
        if (player.faction().isWilderness()
                || player.faction().isSafeZone()
                || player.faction().isWarZone()) {
            return list;
        }
        plugin.getPlayerFromFaction(list, player.faction());
        return list;
    }
}
