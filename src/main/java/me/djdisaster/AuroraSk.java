package me.djdisaster;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AuroraSk extends JavaPlugin {

    private AuroraSk instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;

        addon = Skript.registerAddon(this);
        getLogger().info("AuroraSk is now enabled.");
        try {
            //This will register all our syntax for us. Explained below
            addon.loadClasses("me.djdisaster", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
