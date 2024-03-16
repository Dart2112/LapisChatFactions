package net.lapismc.lapischat.factions.channels;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
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
        FPlayer player = FPlayers.getInstance().getByPlayer(p.getPlayer());
        if (player.getFaction().isWilderness()
                || player.getFaction().isSafeZone()
                || player.getFaction().isWarZone()) {
            return list;
        }
        plugin.getPlayerFromFaction(list, player.getFaction());
        return list;
    }
}
