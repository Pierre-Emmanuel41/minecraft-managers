package fr.pederobien.minecraft.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import com.google.common.collect.ImmutableList;

public class BukkitManager {

	/**
	 * Gets a view of all currently logged in players. This {@linkplain Collections#unmodifiableCollection(Collection) view} is a
	 * reused object, making some operations like {@link Collection#size()} zero-allocation.
	 * <p>
	 * The collection is a view backed by the internal representation, such that, changes to the internal state of the server will be
	 * reflected immediately. However, the reuse of the returned collection (identity) is not strictly guaranteed for future or all
	 * implementations. Casting the collection, or relying on interface implementations (like {@link Serializable} or {@link List}),
	 * is deprecated.
	 * <p>
	 * Iteration behavior is undefined outside of self-contained main-thread uses. Normal and immediate iterator use without
	 * consequences that affect the collection are fully supported. The effects following (non-exhaustive)
	 * {@link Entity#teleport(Location) teleportation}, {@link Player#setHealth(double) death}, and {@link Player#kickPlayer( String)
	 * kicking} are undefined. Any use of this collection from asynchronous threads is unsafe.
	 * <p>
	 * For safe consequential iteration or mimicking the old array behavior, using {@link Collection#toArray(Object[])} is
	 * recommended. For making snapshots, {@link ImmutableList#copyOf(Collection)} is recommended.
	 *
	 * @return a view of currently online players.
	 */
	public static Collection<? extends Player> getOnlinePlayers() {
		return Bukkit.getOnlinePlayers();
	}

	/**
	 * Gets every player that has ever played on this server.
	 *
	 * @return an array containing all previous players
	 */
	public static OfflinePlayer[] getOfflinePlayers() {
		return Bukkit.getOfflinePlayers();
	}

	/**
	 * Gets a player object by the given username.
	 * <p>
	 * This method may not return objects for offline players.
	 *
	 * @param name the name to look up
	 * @return a player if one was found, null otherwise
	 */
	public static Player getPlayer(String name) {
		return Bukkit.getPlayer(name);
	}

	/**
	 * Gets a {@link ConsoleCommandSender} that may be used as an input source for this server.
	 *
	 * @return a console command sender
	 */
	public static ConsoleCommandSender getConsoleSender() {
		return Bukkit.getConsoleSender();
	}

	/**
	 * Dispatches a command on this server, and executes it if found.
	 *
	 * @param sender      the apparent sender of the command
	 * @param commandLine the command + arguments. Example: <code>test abc
	 *     123</code>
	 * @throws CommandException thrown when the executor for the given command fails with an unhandled exception
	 */
	public static void dispatchCommand(CommandSender sender, String command) {
		Bukkit.getServer().dispatchCommand(sender, command);
	}

	/**
	 * Dispatches a command on this server, and executes it if found. It's equivalent to call
	 * {@link #dispatchCommand(CommandSender, String)} with {@link Bukkit#getConsoleSender()}.
	 *
	 * @param commandLine the command + arguments. Example: <code>test abc
	 *     123</code>
	 * @throws CommandException thrown when the executor for the given command fails with an unhandled exception
	 */
	public static void dispatchCommand(String command) {
		dispatchCommand(getConsoleSender(), command);
	}

	/**
	 * Creates a new {@link BlockData} instance with material and properties parsed from provided data.
	 *
	 * @param data data string
	 * @return new data instance
	 * @throws IllegalArgumentException if the specified data is not valid
	 */
	public static BlockData createBlockData(String data) {
		return Bukkit.createBlockData(data);
	}

	/**
	 * Gets a {@link ConsoleCommandSender} that may be used as an input source for this server.
	 *
	 * @return a console command sender
	 */
	public static ConsoleCommandSender getDefaultCommandSender() {
		return Bukkit.getConsoleSender();
	}

	/**
	 * Gets the instance of the scoreboard manager.
	 * <p>
	 * This will only exist after the first world has loaded.
	 *
	 * @return the scoreboard manager or null if no worlds are loaded.
	 */
	public static ScoreboardManager getScoreboardManager() {
		return Bukkit.getScoreboardManager();
	}

	/**
	 * Gets the plugin manager for interfacing with plugins.
	 *
	 * @return a plugin manager for this Server instance
	 */
	public static PluginManager getPluginManager() {
		return Bukkit.getServer().getPluginManager();
	}

	/**
	 * Gets the world with the given name.
	 *
	 * @param name the name of the world to retrieve
	 * @return a world with the given name, or null if none exists
	 */
	public static World getWorld(String name) {
		return Bukkit.getWorld(name);
	}

	/**
	 * Gets a list containing all player operators. This methods call {@link Server#getOperators()} and transform the set into a list.
	 *
	 * @return a list containing player operators
	 */
	public static List<OfflinePlayer> getOperators() {
		return new ArrayList<OfflinePlayer>(Bukkit.getServer().getOperators());
	}

	/**
	 * @return A stream that contains all object Player currently logged into the server.
	 */
	public static Stream<Player> getOnlineOperators() {
		return getOperators().stream().map(p -> p.getPlayer()).filter(p -> p != null);
	}

	/**
	 * Broadcast a message to all players.
	 * <p>
	 * This is the same as calling {@link Bukkit#broadcast(java.lang.String, java.lang.String)} to
	 * {@link Server#BROADCAST_CHANNEL_USERS}
	 *
	 * @param message the message
	 * @return the number of players
	 */
	public static void broadcastMessage(String message) {
		Bukkit.broadcastMessage(message);
	}

	/**
	 * Gets the scheduler for managing scheduled events.
	 *
	 * @return a scheduling service for this server
	 */
	public static BukkitScheduler getScheduler() {
		return Bukkit.getScheduler();
	}
}
