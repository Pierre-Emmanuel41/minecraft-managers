package fr.pederobien.minecraft.managers;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {
	private boolean isRegistered, isActivated;

	public EventListener() {
		isRegistered = false;
		isActivated = false;
	}

	/**
	 * Register this listener on the server if it is not already registered.
	 * 
	 * @param plugin The plugin for which the listener works.
	 */
	public final void register(Plugin plugin) {
		if (isRegistered)
			return;
		isRegistered = true;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * @return True if this listener is registered on the server, false otherwise.
	 */
	public boolean isRegistered() {
		return isRegistered;
	}

	/**
	 * If activated, the listener will react on minecraft events.
	 * 
	 * @param isActivated True if this listener is activated, false otherwise.
	 */
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 * @return True if this listener is activated ans react on minecraft events, false otherwise.
	 */
	public boolean isActivated() {
		return isActivated;
	}
}
