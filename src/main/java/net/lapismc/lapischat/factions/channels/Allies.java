package net.lapismc.lapischat.factions.channels;

import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.FPlayers;
import dev.kitteh.factions.Faction;
import dev.kitteh.factions.permissible.Relation;
import net.lapismc.lapischat.factions.LapisChatFactions;
import net.lapismc.lapischat.framework.Channel;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.permissions.Permission;

import java.util.HashSet;
import java.util.Set;

public class Allies extends Channel {

    private final LapisChatFactions plugin;

    public Allies(LapisChatFactions plugin) {
        super("Allies", plugin.getValue("Allies.ShortName"), plugin.getValue("Allies.Prefix"),
                new Permission(plugin.getValue("Allies.Permission")), plugin.getValue("Allies.Format"));
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
        for (Faction f : dev.kitteh.factions.Factions.factions().all()) {
            if (f.relationWish(player.faction()).equals(Relation.ALLY)) {
                plugin.getPlayerFromFaction(list, f);
            }
        }
        plugin.getPlayerFromFaction(list, player.faction());
        return list;
    }
}
