package net.lapismc.lapischat.factions.channels;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.lapismc.lapischat.factions.LapisChatFactions;
import net.lapismc.lapischat.framework.Channel;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.permissions.Permission;

import java.util.HashSet;
import java.util.Set;

public class Allies extends Channel {

    private LapisChatFactions plugin;

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
        MPlayer player = MPlayer.get(p.getPlayer());
        if (player.getFaction().isNone()
                || player.getFaction().equals(FactionColl.get().getSafezone())
                || player.getFaction().equals(FactionColl.get().getWarzone())) {
            return list;
        }
        for (String s : player.getFaction().getRelationWishes().keySet()) {
            if (player.getFaction().getRelationWish(s).isFriend()) {
                plugin.getPlayerFromFaction(list, FactionColl.get().get(s));
            }
        }
        plugin.getPlayerFromFaction(list, player.getFaction());
        return list;
    }
}
