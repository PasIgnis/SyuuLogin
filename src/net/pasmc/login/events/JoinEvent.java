package net.pasmc.login.events;

import net.pasmc.login.Login;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.xephi.authme.api.NewAPI;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class JoinEvent implements Listener {

    NewAPI authme = NewAPI.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        for (Player player : Login.instance.getServer().getOnlinePlayers()) {
            p.hidePlayer(player);
            player.hidePlayer(p);
        }
    }

    @EventHandler
    public void onAnyMovement(PlayerMoveEvent e) {
        if (authme.isAuthenticated(e.getPlayer())) {
        Run(e.getPlayer().getName(),"Hub");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (authme.isAuthenticated(e.getPlayer())) {
            e.setCancelled(true);
        }
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

