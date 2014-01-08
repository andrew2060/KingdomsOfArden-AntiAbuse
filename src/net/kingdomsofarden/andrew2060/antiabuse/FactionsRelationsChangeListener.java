package net.kingdomsofarden.andrew2060.antiabuse;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.FactionsEventRelationChange;
import com.massivecraft.mcore.ps.PS;

public class FactionsRelationsChangeListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRelation(FactionsEventRelationChange event) {
		if(event.getNewRelation() == Rel.ENEMY) {
			Faction f = event.getFaction();
			List<Player> players = f.getOnlinePlayers();
			PS home = f.getHome();
			for(Player p : players) {
				if(BoardColls.get().getFactionAt(PS.valueOf(p.getLocation())).equals(event.getOtherFaction())) {
					p.teleport(home.asBukkitLocation(), TeleportCause.PLUGIN);
					p.sendMessage(ChatColor.GRAY + "Teleported to your faction home due to being in newly enemied faction territory.");
				}
				continue;
			}
		}
	}
}
