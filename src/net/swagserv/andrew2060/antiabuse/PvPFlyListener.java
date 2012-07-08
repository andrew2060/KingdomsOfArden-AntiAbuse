package net.swagserv.andrew2060.antiabuse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPFlyListener implements Listener{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCombatEnter(EntityDamageByEntityEvent event) {
		Entity target=event.getEntity();
		Entity subject=event.getDamager();
		if(target instanceof Player && subject instanceof Player) {
			Boolean targetflightstate= ((Player)target).isFlying();
			Boolean subjectflightstate = ((Player)subject).isFlying();
			if(subjectflightstate == true) {
				((Player)subject).setFlying(false);
				((Player)subject).setAllowFlight(false);
				((Player)subject).sendMessage(ChatColor.GRAY + "You have been exited out of flight mode due to entering combat");
			}
			if(targetflightstate == true) {
				((Player)target).setFlying(false);
				((Player)target).setAllowFlight(false);
				((Player)target).sendMessage(ChatColor.GRAY + "You have been exited out of flight mode due to entering combat");

			}
		}
		
	}
}
