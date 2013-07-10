package net.swagserv.andrew2060.antiabuse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class AntiAbuse extends JavaPlugin{
	public class FlyCommandExecutor implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command,
				String label, String[] args) {
			if(!sender.hasPermission("antiabuse.fly")) {
				sender.sendMessage("No Permission!");
				return true;
			}
			if(!(sender instanceof Player)) {
				return true;
			}
			Player p = (Player)sender;
			FPlayer fP = FPlayers.i.get(p);
			Location loc = p.getLocation();
			Faction f = Board.getFactionAt(loc);
			if(!sender.hasPermission("antiabuse.bypass.fly")) {
				if(!f.getTag().equalsIgnoreCase(fP.getFaction().getTag())) {
					sender.sendMessage("Cannot use outside of your own territory");
					return true;
				}
			}
			boolean toggle = p.getAllowFlight();
			p.setAllowFlight(!toggle);
			if(!toggle == false) {
				p.sendMessage(ChatColor.GRAY + "Exited Fly");
			} else {
				p.sendMessage(ChatColor.GRAY + "Fly Enabled");
			}
			return true;
		}

	}

	public void onEnable() {
		//Fly cancellation for PvP check
		Bukkit.getServer().getPluginManager().registerEvents(new PvPListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FactionsRelationsChangeListener(), this);
		getCommand("fly").setExecutor(new FlyCommandExecutor());
	}	
}
