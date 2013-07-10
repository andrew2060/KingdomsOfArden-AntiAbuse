package net.kingdomsofarden.andrew2060.antiabuse;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FactionRelationEvent;
import com.massivecraft.factions.struct.Rel;

public class FactionsRelationsChangeListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRelation(FactionRelationEvent event) {
		if(event.getRelation() == Rel.ENEMY && (event.getOldRelation() == Rel.ALLY || event.getOldRelation() == Rel.TRUCE)) {
			Faction f = event.getFaction();
			Set<FPlayer> onlineFPSet = f.getFPlayersWhereOnline(true);
			Location home = f.getHome();
			Iterator<FPlayer> ip = onlineFPSet.iterator();
			while(ip.hasNext()) {
				FPlayer fp = ip.next();
				Player p = fp.getPlayer();
				if(Board.getFactionAt(p.getLocation()).equals(event.getTargetFaction())) {
					p.teleport(home, TeleportCause.PLUGIN);
					p.sendMessage(ChatColor.GRAY + "Teleported to your faction home due to being in newly enemied faction territory.");
				}
				continue;
			}
		}
	}
}
