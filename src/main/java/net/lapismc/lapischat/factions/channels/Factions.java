package net.lapismc.lapischat.factions.channels;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.lapismc.lapischat.factions.LapisChatFactions;
import net.lapismc.lapischat.framework.Channel;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class Factions extends Channel {

    private LapisChatFactions plugin;

    public Factions(LapisChatFactions plugin) {
        super("Factions", "f", ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("Prefixes.Factions")), new Permission("LapisChat.Factions"));
        this.plugin = plugin;
    }

    @Override
    protected String format(ChatPlayer from, String msg, String format) {
        return applyDefaultFormat(from, msg, format);
    }

    @Override
    public List<ChatPlayer> getRecipients(ChatPlayer p) {
        List<ChatPlayer> list = new ArrayList<>();
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
