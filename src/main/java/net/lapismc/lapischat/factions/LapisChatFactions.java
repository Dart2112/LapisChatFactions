package net.lapismc.lapischat.factions;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import net.lapismc.lapischat.LapisChat;
import net.lapismc.lapischat.api.ChannelAPI;
import net.lapismc.lapischat.events.LapisChatEvent;
import net.lapismc.lapischat.events.LapisMessageEvent;
import net.lapismc.lapischat.factions.channels.Allies;
import net.lapismc.lapischat.factions.channels.Factions;
import net.lapismc.lapischat.framework.ChatPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public final class LapisChatFactions extends JavaPlugin implements Listener {

    private UUID consoleUUID = UUID.nameUUIDFromBytes("Console".getBytes());

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ChannelAPI channelManager = new ChannelAPI(this);
        channelManager.addChannel(new Factions(this));
        channelManager.addChannel(new Allies(this));
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info(getName() + " v" + getDescription().getVersion() + " has been enabled");
    }

    @EventHandler
    public void lapisChatEvent(LapisChatEvent e) {
        FPlayer player = FPlayers.getInstance().getByPlayer(e.getSender().getPlayer());
        if (player.getFaction().isWilderness()) {
            e.applyFormat("{FACTION}", "");
        } else {
            e.applyFormat("{FACTION}", getFormat(player));
        }
    }

    @EventHandler
    public void lapisMessageEvent(LapisMessageEvent e) {
        if (e.getSender().equals(consoleUUID)) {
            e.applyFormat("{SENDER_FACTION}", "");
        } else {
            FPlayer sender = FPlayers.getInstance().getByPlayer(Bukkit.getPlayer(e.getSender()));
            e.applyFormat("{SENDER_FACTION}", getFormat(sender));
        }
        if (e.getReceiver().equals(consoleUUID)) {
            e.applyFormat("{RECEIVER_FACTION}", "");
        } else {
            FPlayer sender = FPlayers.getInstance().getByPlayer(Bukkit.getPlayer(e.getReceiver()));
            e.applyFormat("{SENDER_FACTION}", getFormat(sender));
        }
    }

    public String getValue(String key) {
        return getConfig().getString("Channels." + key);
    }

    private String getFormat(FPlayer player) {
        String format = getConfig().getString("Format");
        format = format.replace("{FACTION_NAME}", player.getFaction().getTag());
        format = format.replace("{ROLE_NAME}", player.getRole().nicename);
        format = format.replace("{ROLE_PREFIX}", player.getRole().getPrefix());
        return format;
    }

    /**
     * Adds all online chat players from this faction to the supplied list
     *
     * @param list The list the players should be added too
     * @param f    The faction to check for online players
     */
    public void getPlayerFromFaction(Set<ChatPlayer> list, Faction f) {
        for (FPlayer players : f.getFPlayers()) {
            Player p = players.getPlayer();
            if (p != null) {
                ChatPlayer chatPlayer = LapisChat.getInstance().getPlayer(p.getUniqueId());
                if (chatPlayer.getOfflinePlayer().isOnline()) {
                    list.add(chatPlayer);
                }
            }
        }
    }
}
