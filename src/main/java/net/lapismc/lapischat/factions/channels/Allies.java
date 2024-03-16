package net.lapismc.lapischat.factions.channels;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.perms.Relation;
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
        FPlayer player = FPlayers.getInstance().getByPlayer(p.getPlayer());
        if (player.getFaction().isWilderness()
                || player.getFaction().isSafeZone()
                || player.getFaction().isWarZone()) {
            return list;
        }
        for (Faction f : Factions.getInstance().getAllFactions()) {
            if (f.getRelationWish(player.getFaction()).equals(Relation.ALLY)) {
                plugin.getPlayerFromFaction(list, f);
            }
        }
        plugin.getPlayerFromFaction(list, player.getFaction());
        return list;
    }
}
