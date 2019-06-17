package net.pasmc.login.events;

import net.pasmc.login.Login;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JoinEvent implements Listener {

    public static Map<String, Boolean> Logined;

    public JoinEvent() {
        Logined = new HashMap<String, Boolean>();
    }

    public static String getWhiteList() {
        return "Uncle_Lee;Fernsehens;Mxmimi;TheColdWinterHTC;beibao233;EnderRain_MoYu;DreamWalker_;GMengZhi;Sunny_GYL;Nico_2333;LonelyCa7;luoliaaaa;A3tEmis;Misoryan";
    }

    public static Boolean getPlayerInWhiteList(String player) {
        for (String whitelist : getWhiteList().split(";")) {
            if (whitelist.equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        Logined.put(p.getName(),false);
        for (Player player : Login.instance.getServer().getOnlinePlayers()) {
            p.hidePlayer(player);
            player.hidePlayer(p);
        }
    }

    @EventHandler
    public void onAnyMovement(PlayerMoveEvent e) {
        if (Logined.get(e.getPlayer().getName())) {
            Logined.put(e.getPlayer().getName(),false);
            new BukkitRunnable() {
                @Override
                public void run() { Run(e.getPlayer().getName(),"Lobby#1");
                }
            }.runTaskLater(Login.instance,40L);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Logined.put(e.getPlayer().getName(),false);
    }

    public void Run(final String PName, final String ServerName) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("ConnectOther");
            out.writeUTF(PName);
            out.writeUTF(ServerName);
        } catch (IOException ex) {
        }
        Bukkit.getServer().sendPluginMessage(Login.instance, "BungeeCord", b.toByteArray());
    }

}

