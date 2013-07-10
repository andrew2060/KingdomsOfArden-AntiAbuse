package net.kingdomsofarden.andrew2060.antiabuse;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.herocraftonline.heroes.api.events.HeroEnterCombatEvent;
import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class PvPListener implements Listener{
	@EventHandler(priority=EventPriority.MONITOR)
	public void onCombatEnter(HeroEnterCombatEvent event) {
	    
		if(!(event.getTarget() instanceof Player)) {
			return;
		}
		Player p = event.getHero().getPlayer();
		if(p.hasPermission("antiabuse.bypass.fly")) {
            return;
        }
		if(p.getAllowFlight()) {
			p.sendMessage(ChatColor.GRAY + "Creative styled flight has been toggled off due to entering combat: use /fly to toggle it on again after leaving combat!");
			p.setAllowFlight(false);
			if(p.isFlying()) {
				p.sendMessage(ChatColor.GRAY + "You have been exited out of flight due to entering combat!");
				p.setFlying(false);
			}
		}
		Player p2 = (Player)event.getTarget();
		if(p2.getAllowFlight()) {
			p2.sendMessage(ChatColor.GRAY + "Creative styled flight has been toggled off due to entering combat: use /fly to toggle it on again after leaving combat!");
			p2.setAllowFlight(false);
			if(p2.isFlying()) {
				p2.sendMessage(ChatColor.GRAY + "You have been exited out of flight due to entering combat!");
				p2.setFlying(false);
			}
		}	
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onSkillUse(SkillUseEvent event) {
		if(event.getPlayer().isFlying()) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.GRAY + "Cannot use skills while flying!");
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		//Because faction claims handled as chunks, if we don't change chunks we don't need to check fly state
		Chunk x = event.getFrom().getChunk();
		Chunk y = event.getTo().getChunk();
		if(x==y) {
			return;
		}
		//Handle flying check second because less laggy than faction check
		Player p = event.getPlayer();
		if(!(p.isFlying() || p.getAllowFlight())) {
			return;
		}
		if(p.hasPermission("antiabuse.bypass.fly")) {
			return;
		}
		//Now handle faction check
		FPlayer fPlayer = FPlayers.i.get(p);
		Faction to = Board.getFactionAt(event.getTo());
		if(to.getTag().equalsIgnoreCase(fPlayer.getFaction().getTag())) {
			return;
		}
		
		//Now cancel
		p.setAllowFlight(false);
		p.setFlying(false);
		p.sendMessage(ChatColor.GRAY + "Flying has been disabled due to flying in territory that is not your own");
	}
}
