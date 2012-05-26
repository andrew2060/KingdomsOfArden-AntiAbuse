package net.swagserv.andrew2060.antiabuse;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiAbuse extends JavaPlugin{
	public void onEnable() {
		//Fly cancellation for PvP check
		Bukkit.getServer().getPluginManager().registerEvents(new PvPFlyListener(), this);
		
	}	
}
