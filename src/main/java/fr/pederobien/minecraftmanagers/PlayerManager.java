package fr.pederobien.minecraftmanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {
	public static final int MAX_EFFECT_DURATION = 999999;
	public static final int MAX_EFFECT_AMPLIFIER = 99;

	/**
	 * @return A stream that contains all players currently logged into the server.
	 */
	public static Stream<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for (Player player : BukkitManager.getOnlinePlayers())
			players.add(player);
		return players.stream();
	}

	/**
	 * @return The number of player currently logged into the server
	 */
	public static long getNumberOfPlayer() {
		return getPlayers().count();
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
		return BukkitManager.getPlayer(name);
	}

	/**
	 * Get the locale associated to the player. This locale correspond to the language used by the player to player minecraft.
	 * 
	 * @param player The player used to get its language.
	 * @return The player's language if it exists, null otherwise.
	 * 
	 * @see Locale#forLanguageTag(String)
	 */
	public static Locale getPlayerLocale(Player player) {
		return Locale.forLanguageTag(player.getLocale().replace('_', '-'));
	}

	/**
	 * Clear the inventory of the specified player.
	 * 
	 * @param player The player used to clear its inventory.
	 */
	public static void removeInventoryOfPlayer(Player player) {
		player.getInventory().clear();
	}

	/**
	 * Clear the inventory of each player in the stream.
	 * 
	 * @param players A stream that contains all players which the inventory should be cleared.
	 */
	public static void removeInventoryOfPlayers(Stream<Player> players) {
		players.peek(p -> removeInventoryOfPlayer(p));
	}

	/**
	 * Clear the inventory of each player currently logged into the server.
	 * 
	 * @see #removeInventoryOfPlayers(Stream)
	 * @see #getPlayers()
	 */
	public static void removeInventoryOfPlayers() {
		removeInventoryOfPlayers(getPlayers());
	}

	/**
	 * Set the experience level of the specified player.
	 * 
	 * @param player The player used to modify its experience level.
	 * @param level  The experience level the player should have after the call to this method.
	 */
	public static void setExpLevelOfPlayer(Player player, int level) {
		player.giveExpLevels(-player.getTotalExperience() + level);
	}

	/**
	 * Set the experience level of all players present in the stream.
	 * 
	 * @param players A stream that contains all players which the experience level should be changed.
	 * @param level   The new experience level of each player in the stream.
	 */
	public static void setExpLevelOfPlayers(Stream<Player> players, int level) {
		players.peek(p -> setExpLevelOfPlayer(p, level));
	}

	/**
	 * Set the experience level of each player currently logged into the server.
	 * 
	 * @param level The experience level of each player after the call to this method.
	 * @see #setExpLevelOfPlayer(Player, int)
	 * @see #getPlayers()
	 */
	public static void setExpLevelOfPlayers(int level) {
		setExpLevelOfPlayers(getPlayers(), level);
	}

	/**
	 * Call the method {@link #setExpLevelOfPlayers(int)} with value 0.
	 */
	public static void resetLevelOfPlayers() {
		setExpLevelOfPlayers(0);
	}

	/**
	 * Set the food level for the specified player.
	 * 
	 * @param player The player used to modify its food level.
	 * @param level  The food level the player should have after the call to this method.
	 */
	public static void setFoodLevelOfPlayer(Player player, int level) {
		player.setFoodLevel(level);
	}

	/**
	 * Set the food level of each player present in the stream.
	 * 
	 * @param players A stream that contains all players which the food level should be changed.
	 * @param level   The food level of each player after the call to this method.
	 * @see #setFoodLevelOfPlayer(Player, int)
	 */
	public static void setFoodLevelOfPlayers(Stream<Player> players, int level) {
		players.peek(p -> setFoodLevelOfPlayer(p, level));
	}

	/**
	 * Set the food level of each player currently logged into the server.
	 * 
	 * @param level The food level of each player after the call to this method.
	 * @see #setFoodLevelOfPlayer(Player, int)
	 * @see #getPlayers()
	 */
	public static void setFoodLevelOfPlayers(int level) {
		setFoodLevelOfPlayers(getPlayers(), level);
	}

	/**
	 * Call the method {@link #setFoodLevelOfPlayers(int)} with value 20.
	 */
	public static void maxFoodForPlayers() {
		setFoodLevelOfPlayers(20);
	}

	/**
	 * Change the max health value of the specified player. Utilisation example :
	 * 
	 * <ul>
	 * <li>setMaxHealthOfPlayer(3) correspond to 1 hearts and half.</li>
	 * <li>setMaxHealthOfPlayer(10) correspond to 5 hearts.</li>
	 * </ul>
	 * 
	 * @param player The player used to modify its max health value.
	 * @param level  The max health value the player should have after the call to this method.
	 */
	public static void setMaxHealthOfPlayer(Player player, double level) {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(level);
	}

	/**
	 * Set the max health value of each player present in the stream.
	 * 
	 * @param players A stream that contains all players which the max health value should be changed.
	 * @param level   The max health of each player after the call to this method.
	 * @see #setFoodLevelOfPlayer(Player, int)
	 */
	public static void setMaxHealthOfPlayers(Stream<Player> players, double level) {
		players.peek(p -> setMaxHealthOfPlayer(p, level));
	}

	/**
	 * Set the max health value of each player currently logged into the server.
	 * 
	 * @param level The max health all player should have after the call to this method.
	 */
	public static void setMaxHealthOfPlayers(double level) {
		setMaxHealthOfPlayers(getPlayers(), level);
	}

	/**
	 * Call the method {@link #setMaxHealthOfPlayer(Player, double)} with level = 20.
	 * 
	 * @param player The player used to modify its max health value.
	 */
	public static void resetMaxHealthOfPlayer(Player player) {
		setMaxHealthOfPlayer(player, 20.0);
	}

	/**
	 * Reset the max health value of each player currently logged into the server.
	 * 
	 * @see #resetMaxHealthOfPlayer(Player)
	 */
	public static void resetMaxHealthOfPlayers() {
		getPlayers().forEach(p -> resetMaxHealthOfPlayer(p));
	}

	/**
	 * Set the health of the specified player.
	 * 
	 * @param player The player used to modify its health level.
	 * @param level  The health level the player should have after the call to this method.
	 */
	public static void setHealthOfPlayer(Player player, double level) {
		player.setHealth(level);
	}

	/**
	 * Set the health level of each player present in the stream.
	 * 
	 * @param players A stream that contains all players which the health should be changed.
	 * @param level   The health of each player after the call to this method.
	 */
	public static void setHealthOfPlayers(Stream<Player> players, double level) {
		players.peek(p -> setHealthOfPlayer(p, level));
	}

	/**
	 * Reset the health of each player currently logged into the server.
	 * 
	 * @param level The health of each players after the call to this method.
	 * @see #setHealthOfPlayers(Stream, double)
	 */
	public static void setHealthOfPlayers(double level) {
		setHealthOfPlayers(getPlayers(), level);
	}

	/**
	 * Call the method {@link #setHealthOfPlayers(double)} with level = 20.
	 */
	public static void maxLifeToPlayers() {
		setHealthOfPlayers(20);
	}

	/**
	 * Get a stream that contains all player that have the specified game mode from the given stream.
	 * 
	 * @param players The initial stream used to perform a selection.
	 * @param mode    The condition of the previous selection.
	 * @return A steam that contains all players from the initial stream with the specified game mode.
	 */
	public static Stream<Player> getPlayersOnMode(Stream<Player> players, GameMode mode) {
		return players.filter(p -> p.getGameMode().equals(mode));
	}

	/**
	 * Get a stream that contains all player with the specified game mode.
	 * 
	 * @param mode The game mode used to filter the stream returned by {@link #getPlayers()}
	 * @return A stream that contains all players with the specified game mode.
	 * 
	 * @see #getPlayersOnMode(Stream, GameMode)
	 */
	public static Stream<Player> getPlayersOnMode(GameMode mode) {
		return getPlayersOnMode(getPlayers(), mode);
	}

	/**
	 * Based on the method {@link #getPlayersOnMode(GameMode)} this method count the number of element in the returned stream.
	 * 
	 * @param mode The game mode used to filter the stream returned by {@link #getPlayers()}
	 * @return The number of player with the specified game mode.
	 */
	public static long getNumberOfPlayersOnMode(GameMode mode) {
		return getPlayersOnMode(mode).count();
	}

	/**
	 * Set the game mode of the specified player.
	 * 
	 * @param player The player used to modify its game mode.
	 * @param mode   The new game mode of the player.
	 */
	public static void setGameModeOfPlayer(Player player, GameMode mode) {
		player.setGameMode(mode);
	}

	/**
	 * Set the game mode of each player in the stream.
	 * 
	 * @param players A stream that contains all player which the game mode should be changed.
	 * @param mode    The new game mode of each player in the specified stream.
	 */
	public static void setGameModeOfPlayers(Stream<Player> players, GameMode mode) {
		players.peek(p -> setGameModeOfPlayer(p, mode));
	}

	/**
	 * All players that have the game mode <code>oldMode</code> will have the game mode <code>newMode</code>.
	 * 
	 * @param oldMode The game mode used to filter the stream returned by {@link #getPlayers()}
	 * @param newMode The new game mode of each players in the previous stream.
	 * 
	 * @see #getPlayersOnMode(GameMode)
	 * @see #setGameModeOfPlayers(Stream, GameMode)
	 */
	public static void setGameModeOfPlayersOnMode(GameMode oldMode, GameMode newMode) {
		setGameModeOfPlayers(getPlayersOnMode(oldMode), newMode);
	}

	/**
	 * Set the game mode of all players currently logged into the server.
	 * 
	 * @param mode The new game mode of players.
	 */
	public static void setGameModeOfAllPlayers(GameMode mode) {
		setGameModeOfPlayers(getPlayers(), mode);
	}

	/**
	 * Teleport the player to the given location. If this entity is riding a vehicle, it will be dismounted prior to teleportation.
	 *
	 * @param player   The player to teleport.
	 * @param location New location to teleport the player to.
	 */
	public static void teleporte(Player player, Location location) {
		player.teleport(location);
	}

	/**
	 * Teleport each player present in the stream <code>players</code> at the given location.
	 * 
	 * @param players  A stream that contains all player to teleport.
	 * @param location The new location to teleport all player to.
	 * 
	 * @see #teleporte(Player, Location)
	 */
	public static void teleportePlayers(Stream<Player> players, Location location) {
		players.peek(p -> teleporte(p, location));
	}

	/**
	 * Teleport the player to the target Entity. If this entity is riding a vehicle, it will be dismounted prior to teleportation.
	 *
	 * @param player The player to teleport.
	 * @param entity The entity to teleport the player to.
	 */
	public static void teleporte(Player player, Entity entity) {
		player.teleport(entity);
	}

	/**
	 * Teleport each player present in the stream <code>players</code> to given entity.
	 * 
	 * @param players A stream that contains all player to teleport.
	 * @param entity  The entity to teleport all player to.
	 * 
	 * @see #teleporte(Player, Location)
	 */
	public static void teleportePlayers(Stream<Player> players, Entity entity) {
		players.forEach(p -> teleporte(p, entity));
	}

	/**
	 * Teleport all players currently logged into the server to the specified location.
	 * 
	 * @param location The new location to teleport all player to.
	 * 
	 * @see #getPlayers()
	 * @see #teleporte(Player, Location)
	 */
	public static void teleporteAllPlayers(Location location) {
		getPlayers().forEach(p -> teleporte(p, location));
	}

	/**
	 * Teleport all players currently logged into the server to the specified entity.
	 * 
	 * @param entity The entity to teleport all player to.
	 * 
	 * @see #getPlayers()
	 * @see #teleporte(Player, Location)
	 */
	public static void teleporteAllPlayers(Entity entity) {
		getPlayers().forEach(p -> teleporte(p, entity));
	}

	/**
	 * Drop all item present in the player's inventory at the current location of the player.
	 * 
	 * @param player The player used to drop its inventory.
	 * 
	 * @see World#dropItemNaturally(Location, ItemStack)
	 */
	public static void dropPlayerInventoryItemNaturally(Player player) {
		for (ItemStack item : player.getInventory())
			if (item != null)
				player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
		player.getInventory().clear();
	}

	/**
	 * Call method {@link #dropPlayerInventoryItemNaturally(Player)} for each player present in the specified stream.
	 * 
	 * @param players A stream that contains all player to drop their inventory.
	 */
	public static void dropPlayersInventoryItemNaturally(Stream<Player> players) {
		players.peek(p -> dropPlayerInventoryItemNaturally(p));
	}

	/**
	 * Call method {@link #dropPlayersInventoryItemNaturally(Stream)} with stream returned by {@link #getPlayers()}
	 */
	public static void dropPlayersInventoryItemNaturally() {
		dropPlayersInventoryItemNaturally(getPlayers());
	}

	/**
	 * Kill the specified player. This method is equivalent to call {@link #setHealthOfPlayer(Player, double)} with health = 0.
	 * 
	 * @param player The player to kill.
	 */
	public static void killPlayer(Player player) {
		setHealthOfPlayer(player, 0);
	}

	/**
	 * Kill all player present in the stream.
	 * 
	 * @param players A stream that contains all player to kill.
	 * 
	 * @see #killPlayer(Player)
	 */
	public static void killPlayers(Stream<Player> players) {
		players.peek(p -> killPlayer(p));
	}
}
