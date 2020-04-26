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
	 * @param players A stream that contains all players to send the specified message to.
	 * @param message The message to send to all players in the previous stream.
	 */
	public static void sendMessage(Stream<Player> players, String message) {
		players.peek(p -> sendMessage(p, message));
	}

	/**
	 * Send all messages to all players present in the given stream.
	 * 
	 * @param players  A stream that contains all players to send all messages to.
	 * @param messages An array that contains all messages to send to all players present in the previous stream.
	 */
	public static void sendMessage(Stream<Player> players, String... messages) {
		players.peek(p -> sendMessage(p, messages));
	}

	/**
	 * Send a message to the given player and with message characteristics. The value of parameter colour is not the char associated
	 * to the {@link ChatColor} but the "human reading" name such as : red, purple, green, dark_blue...
	 * 
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param player  The player that will receive the message.
	 * @param message The message to send to the player.
	 * @param bold    If true the message should be in bold.
	 * @param italic  If true the message should be in italic.
	 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
	 */
	public static void sendMessage(DisplayOption option, Player player, String message, boolean bold, boolean italic, String color) {
		StringBuilder builder = new StringBuilder("title " + player.getName() + " ");
		builder.append(option.toString() + " ");
		builder.append("{\"text\":\"" + message + "\", ");
		builder.append(bold == false ? "" : "\"bold\":\"" + bold + "\", ");
		builder.append(italic == false ? "" : "\"italic\":\"" + italic + "\", ");
		builder.append(color == null ? "" : "\"color\":\"" + color + "\"");
		builder.append("}");
		BukkitManager.dispatchCommand(builder.toString());
	}

	/**
	 * Send a message to each player present in the given stream and with message characteristics using the method
	 * {@link #sendMessage(DisplayOption, Player, String, boolean, boolean, String)}
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param message The message to send to the player.
	 * @param bold    If true the message should be in bold.
	 * @param italic  If true the message should be in italic.
	 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
	 */
	public static void sendMessage(Stream<Player> players, DisplayOption option, String message, boolean bold, boolean italic, String color) {
		players.peek(player -> sendMessage(option, player, message, bold, italic, color));
	}

	/**
	 * Send the specified message to the given player using
	 * {@link #sendMessage(DisplayOption, Player, String, boolean, boolean, String)} with bold equals false, italic equals false and
	 * color equals null.
	 * 
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param player  The player that will receive the message.
	 * @param message The message to send to the player.
	 */
	public static void sendMessage(DisplayOption option, Player player, String message) {
		sendMessage(option, player, message, false, false, null);
	}

	/**
	 * Send the specified message to the given player using
	 * {@link #sendMessage(DisplayOption, Player, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param player  The player that will receive the message.
	 * @param message The message that will be displayed on the player's screen.
	 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
	 */
	public static void sendMessage(DisplayOption option, Player player, String message, String color) {
		sendMessage(option, player, message, false, false, color);
	}

	/**
	 * Send the specified message to each player present in the given stream using
	 * {@link #sendMessage(Stream, DisplayOption, String, boolean, boolean, String)} with bold equals false, italic equals false and
	 * color equals null.
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param message The message that will be displayed on the player's screen.
	 */
	public static void sendMessage(Stream<Player> players, DisplayOption option, String message) {
		sendMessage(players, option, message, false, false, null);
	}

	/**
	 * Send the specified message to the each player present in the stream using
	 * {@link #sendMessage(Stream, DisplayOption, String, boolean, boolean, String)} with bold equals false, italic equals false.
	 * 
	 * @param players A stream that contains all players to send the specified message.
	 * @param option  Option to send the message as title, subtitle or in actionbar.
	 * @param message The message that will be displayed on the player's screen.
	 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
	 */
	public static void sendMessage(Stream<Player> players, DisplayOption option, String message, String color) {
		sendMessage(players, option, message, false, false, color);
	}
}
