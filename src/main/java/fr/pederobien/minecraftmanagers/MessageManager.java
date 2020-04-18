package fr.pederobien.minecraftmanagers;

import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageManager {

	/**
	 * Broadcast a message to all players.
	 * <p>
	 * This is the same as calling {@link Bukkit#broadcast(java.lang.String, java.lang.String)} to
	 * {@link Server#BROADCAST_CHANNEL_USERS}
	 *
	 * @param message the message
	 */
	public static void broadcastMessage(String message) {
		BukkitManager.broadcastMessage(message);
	}

	/**
	 * Send the specified message to the given player.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message to send to the player.
	 * 
	 * @see Player#sendMessage(String)
	 */
	public static void sendMessage(Player player, String message) {
		player.sendMessage(message);
	}

	/**
	 * Send all messages to the given player.
	 * 
	 * @param player   The player that will receive all messages.
	 * @param messages An array that contains all messages to send to the player.
	 */
	public static void sendMessage(Player player, String... messages) {
		for (String message : messages)
			sendMessage(player, message);
	}

	/**
	 * Send the specified message to all players present in the stream.
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param message The message to send to all players in the previous stream.
	 */
	public static void sendMessage(Stream<Player> players, String message) {
		players.peek(p -> sendMessage(p, message));
	}

	/**
	 * Send all messages to all players present in the given stream.
	 * 
	 * @param players  A stream that contains all players to send all messages.
	 * @param messages An array that contains all messages to send to all players present in the previous stream.
	 */
	public static void sendMessage(Stream<Player> players, String... messages) {
		players.peek(p -> sendMessage(p, messages));
	}

	/**
	 * Send a message as title to the given player and modify message characteristics. The value of parameter color is not the char
	 * associated to the {@link ChatColor} but the "human reading" name such as : red, purple, magenta, dark_blue...
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message to send to sen the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageAsTitle(Player player, String message, boolean bold, boolean italic, String color) {
		sendMessage("title", player, message, bold, italic, color);
	}

	/**
	 * Send a message as title to each player present in the given stream and modify message characteristics using the method
	 * {@link #sendMessageAsTitle(Player, String, boolean, boolean, String)}
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param message The message to send to sen the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageAsTitle(Stream<Player> players, String message, boolean bold, boolean italic, String color) {
		players.peek(p -> sendMessageAsTitle(players, message, bold, italic, color));
	}

	/**
	 * Send the specified message as title to the given player using
	 * {@link #sendMessageAsTitle(Player, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsTitle(Player player, String message) {
		sendMessageAsTitle(player, message, false, false, null);
	}

	/**
	 * Send the specified message as title to the given player using
	 * {@link #sendMessageAsTitle(Player, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsTitle(Player player, String message, String color) {
		sendMessageAsTitle(player, message, false, false, color);
	}

	/**
	 * Send the specified message as title to each player present in the given stream using
	 * {@link #sendMessageAsTitle(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsTitle(Stream<Player> players, String message) {
		sendMessageAsTitle(players, message, false, false, null);
	}

	/**
	 * Send the specified message as title to the each player present in the stream using
	 * {@link #sendMessageAsTitle(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsTitle(Stream<Player> players, String message, String color) {
		sendMessageAsTitle(players, message, false, false, color);
	}

	/**
	 * Send a message as subtitle to the given player and modify message characteristics. The value of parameter color is not the char
	 * associated to the {@link ChatColor} but the "human reading" name such as : red, purple, magenta, dark_blue...
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message to send to sen the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageAsSubTitle(Player player, String message, boolean bold, boolean italic, String color) {
		sendMessage("subtitle", player, message, bold, italic, color);
	}

	/**
	 * Send a message as subtitle to each player present in the given stream and modify message characteristics using the method
	 * {@link #sendMessageAsSubTitle(Player, String, boolean, boolean, String)}
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param message The message to send to sen the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageAsSubTitle(Stream<Player> players, String message, boolean bold, boolean italic, String color) {
		players.peek(p -> sendMessageAsSubTitle(players, message, bold, italic, color));
	}

	/**
	 * Send the specified message as subtitle to the given player using
	 * {@link #sendMessageAsSubTitle(Player, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsSubTitle(Player player, String message) {
		sendMessageAsSubTitle(player, message, false, false, null);
	}

	/**
	 * Send the specified message as subtitle to the given player using
	 * {@link #sendMessageAsSubTitle(Player, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsSubTitle(Player player, String message, String color) {
		sendMessageAsSubTitle(player, message, false, false, color);
	}

	/**
	 * Send the specified message as subtitle to each player present in the given stream using
	 * {@link #sendMessageAsSubTitle(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsSubTitle(Stream<Player> players, String message) {
		sendMessageAsSubTitle(players, message, false, false, null);
	}

	/**
	 * Send the specified message as subtitle to the each player present int the stream using
	 * {@link #sendMessageAsSubTitle(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageAsSubTitle(Stream<Player> players, String message, String color) {
		sendMessageAsSubTitle(players, message, false, false, color);
	}

	/**
	 * Send a message in the action bar to the given player and modify message characteristics. The value of parameter color is not
	 * the char associated to the {@link ChatColor} but the "human reading" name such as : red, purple, magenta, dark_blue...
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message to send to sen the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageInActionBar(Player player, String message, boolean bold, boolean italic, String color) {
		sendMessage("actionbar", player, message, bold, italic, color);
	}

	/**
	 * Send a message in the action bar to each player present in the given stream and modify message characteristics using the method
	 * {@link #sendMessageInActionBar(Player, String, boolean, boolean, String)}.
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param message The message to send to the player.
	 * @param bold    If true the message is in bold. If false the message is not in bold.
	 * @param italic  If true the message is in italic. If false the message is not in italic.
	 * @param color   If the color is not null and the color name is correct, then the message is displayed with the given color.
	 */
	public static void sendMessageInActionBar(Stream<Player> players, String message, boolean bold, boolean italic, String color) {
		players.peek(p -> sendMessageInActionBar(players, message, bold, italic, color));
	}

	/**
	 * Send the specified message in the action bar of the given player using
	 * {@link #sendMessageInActionBar(Player, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageInActionBar(Player player, String message) {
		sendMessageInActionBar(player, message, false, false, null);
	}

	/**
	 * Send the specified message in the action bar of the given player using
	 * {@link #sendMessageInActionBar(Player, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageInActionBar(Player player, String message, String color) {
		sendMessageInActionBar(player, message, false, false, color);
	}

	/**
	 * Send the specified message in the action bar of each player present in the given stream using
	 * {@link #sendMessageInActionBar(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false and color
	 * equals null.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageInActionBar(Stream<Player> players, String message) {
		sendMessageInActionBar(players, message, false, false, null);
	}

	/**
	 * Send the specified message in the action of each player present in the stream using
	 * {@link #sendMessageInActionBar(Stream, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessageInActionBar(Stream<Player> players, String message, String color) {
		sendMessageInActionBar(players, message, false, false, color);
	}

	private static void sendMessage(String where, Player player, String message, boolean bold, boolean italic, String color) {
		StringBuilder builder = new StringBuilder("title" + player.getName() + " ");
		builder.append(where + " ");
		builder.append("{\"text\":\"" + message + "\", ");
		builder.append(bold == false ? "" : "\"bold\":\"" + bold + "\", ");
		builder.append(italic == false ? "" : "\"italic\":\"" + italic + "\", ");
		builder.append(color == null ? "" : "\"color\":\"" + color + "\"");
		builder.append("}");
		BukkitManager.dispatchCommand(builder.toString());
	}
}
