package net.pasmc.login.events;

import net.pasmc.login.Login;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LoginEvent implements Listener {

    @EventHandler
    public void onLogin(fr.xephi.authme.events.LoginEvent e) {
        JoinEvent.Logined.put(e.getPlayer().getName(),true);
        if (JoinEvent.getPlayerInWhiteList(e.getPlayer().getName())) {
            Run(e.getPlayer().getName(),"Lobby#1");
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
