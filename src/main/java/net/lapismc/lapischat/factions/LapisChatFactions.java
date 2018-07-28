package net.lapismc.lapischat.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import net.lapismc.lapischat.ChannelManager;
import net.lapismc.lapischat.LapisChat;
import net.lapismc.lapischat.events.LapisChatEvent;
import net.lapismc.lapischat.factions.channels.Allies;
import net.lapismc.lapischat.factions.channels.Factions;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class LapisChatFactions extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ChannelManager channelManager = LapisChat.getInstance().channelManager;
        channelManager.addChannel(new Factions(this));
        channelManager.addChannel(new Allies(this));
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void lapisChatEvent(LapisChatEvent e) {
        MPlayer player = MPlayer.get(e.getSender().getPlayer());
        if (player.getFaction().isNone()) {
            e.applyFormat("{FACTION}", "");
        } else {
            e.applyFormat("{FACTION}", getFormat(player));
        }
    }

    private String getFormat(MPlayer player) {
        String format = getConfig().getString("Format");
        format = format.replace("{FACTION_NAME}", player.getFactionName());
        format = format.replace("{ROLE_NAME}", player.getRole().getName());
        format = format.replace("{ROLE_PREFIX}", player.getRole().getPrefix());
        return format;
    }

    public void getPlayerFromFaction(List<ChatPlayer> list, Faction f) {
        for (MPlayer players : f.getMPlayers()) {
            UUID uuid = players.getUuid();
            ChatPlayer chatPlayer = LapisChat.getInstance().getPlayer(uuid);
            if (chatPlayer.getOfflinePlayer().isOnline()) {
                list.add(chatPlayer);
            }
        }
    }
}
