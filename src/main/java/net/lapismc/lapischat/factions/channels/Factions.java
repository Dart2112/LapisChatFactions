package net.lapismc.lapischat.factions.channels;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.lapismc.lapischat.factions.LapisChatFactions;
import net.lapismc.lapischat.framework.Channel;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.permissions.Permission;

import java.util.HashSet;
import java.util.Set;

public class Factions extends Channel {

    private LapisChatFactions plugin;

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
        MPlayer player = MPlayer.get(p.getPlayer());
        if (player.getFaction().isNone()
                || player.getFaction().equals(FactionColl.get().getSafezone())
                || player.getFaction().equals(FactionColl.get().getWarzone())) {
            return list;
        }
        plugin.getPlayerFromFaction(list, player.getFaction());
        return list;
    }
}
