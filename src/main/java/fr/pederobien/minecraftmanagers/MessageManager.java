package fr.pederobien.minecraftmanagers;

import java.util.List;
import java.util.StringJoiner;
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
		players.forEach(p -> sendMessage(p, message));
	}

	/**
	 * Send all messages to all players present in the given stream.
	 * 
	 * @param players  A stream that contains all players to send all messages to.
	 * @param messages An array that contains all messages to send to all players present in the previous stream.
	 */
	public static void sendMessage(Stream<Player> players, String... messages) {
		players.forEach(p -> sendMessage(p, messages));
	}

	/**
	 * Send all messages to the given player. This messages are concatenated together and the result is send to the player. This
	 * method is equivalent to minecraft title commands. If the length of <code>messages</code> equals 0, then do nothing.
	 * 
	 * @param option   Option to send the message as title, subtitle or in actionbar.
	 * @param player   The player that will receive the message.
	 * @param messages An array that contains all messages to send to the player.
	 */
	public static void sendMessage(DisplayOption option, Player player, TitleMessage... messages) {
		StringJoiner cmd = new StringJoiner(" ");
		cmd.add("title").add(player.getName()).add(option.toString());
		if (messages.length > 1) {
			StringJoiner msgJoiner = new StringJoiner(", ", "[", "]");
			msgJoiner.add("\"\"");
			for (TitleMessage message : messages)
				msgJoiner.add(message.toJson());
			cmd.add(msgJoiner.toString());
			BukkitManager.dispatchCommand(cmd.toString());
		} else if (messages.length == 1) {
			cmd.add(messages[0].toJson());
			BukkitManager.dispatchCommand(cmd.toString());
		}
	}

	/**
	 * Send all messages to the given player. This messages are concatenated together and the result is send to the player. This
	 * method is equivalent to minecraft title commands.
	 * 
	 * @param option   Option to send the message as title, subtitle or in actionbar.
	 * @param player   The player that will receive the message.
	 * @param messages An array that contains all messages to send to the player.
	 */
	public static void sendMessage(DisplayOption option, Stream<Player> players, TitleMessage... messages) {
		players.forEach(player -> sendMessage(option, player, messages));
	}

	/**
	 * Send all messages to the given player. This messages are concatenated together and the result is send to the player. This
	 * method is equivalent to minecraft title commands. If the size of the list equals 0, then do nothing.
	 * 
	 * @param option   Option to send the message as title, subtitle or in actionbar.
	 * @param player   The player that will receive the message.
	 * @param messages A list that contains all messages to send to the player.
	 */
	public static void sendMessage(DisplayOption option, Player player, List<TitleMessage> messages) {
		StringJoiner cmd = new StringJoiner(" ");
		cmd.add("title").add(player.getName()).add(option.toString());
		if (messages.size() > 1) {
			StringJoiner msgJoiner = new StringJoiner(", ", "[", "]");
			msgJoiner.add("\"\"");
			for (TitleMessage message : messages)
				msgJoiner.add(message.toJson());
			cmd.add(msgJoiner.toString());
			BukkitManager.dispatchCommand(cmd.toString());
		} else if (messages.size() == 1) {
			cmd.add(messages.get(0).toJson());
			BukkitManager.dispatchCommand(cmd.toString());
		}
	}

	/**
	 * Send all messages to the given player. This messages are concatenated together and the result is send to the player. This
	 * method is equivalent to minecraft title commands.
	 * 
	 * @param option   Option to send the message as title, subtitle or in actionbar.
	 * @param player   The player that will receive the message.
	 * @param messages A list that contains all messages to send to the player.
	 */
	public static void sendMessage(DisplayOption option, Stream<Player> players, List<TitleMessage> messages) {
		players.forEach(player -> sendMessage(option, player, messages));
	}

	public enum DisplayOption {
		TITLE("title"), SUB_TITLE("subtitle"), ACTION_BAR("actionbar");

		private String name;

		private DisplayOption(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static class TitleMessage {
		private String message, color;
		private boolean isBold, isItalic;

		private TitleMessage(String message, boolean isBold, boolean isItalic, String color) {
			this.message = message;
			this.isBold = isBold;
			this.isItalic = isItalic;
			this.color = color;
		}

		/**
		 * Create message to send to a player and with message characteristics. The value of parameter colour is not the char associated
		 * to the {@link ChatColor} but the "human reading" name such as : red, purple, green, dark_blue...
		 * 
		 * @param message The message to send to the player.
		 * @param bold    If true the message should be in bold.
		 * @param italic  If true the message should be in italic.
		 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
		 * 
		 * @return The created message based on the given parameters.
		 */
		public static TitleMessage of(String message, boolean isBold, boolean isItalic, String color) {
			return new TitleMessage(message, isBold, isItalic, color);
		}

		/**
		 * Create message to send to a player and with message characteristics. The value of parameter colour is not the char associated
		 * to the {@link ChatColor} but the "human reading" name such as : red, purple, green, dark_blue... This message is not coloured
		 * (white).
		 * 
		 * @param message The message to send to the player.
		 * @param bold    If true the message should be in bold.
		 * @param italic  If true the message should be in italic.
		 * 
		 * @return The created message based on the given parameters.
		 */
		public static TitleMessage of(String message, boolean isBold, boolean isItalic) {
			return of(message, isBold, isItalic, null);
		}

		/**
		 * Create message to send to a player and with message characteristics. The value of parameter colour is not the char associated
		 * to the {@link ChatColor} but the "human reading" name such as : red, purple, green, dark_blue... This message is neither in
		 * italic nor in bold.
		 * 
		 * @param message The message to send to the player.
		 * @param color   If the colour is not null and the colour name is correct, then the message is displayed with the given colour.
		 * 
		 * @return The created message based on the given parameters.
		 */
		public static TitleMessage of(String message, String color) {
			return of(message, false, false, color);
		}

		@Override
		public String toString() {
			StringJoiner joiner = new StringJoiner(", ", "{", "}");
			joiner.add("text:" + message);
			joiner.add("bold:" + isBold);
			joiner.add("italic:" + isItalic);
			joiner.add("color:" + color);
			return joiner.toString();
		}

		public String toJson() {
			StringJoiner joiner = new StringJoiner(", ", "{", "}");
			join(joiner, true, "text", message);
			join(joiner, isBold, "bold", true);
			join(joiner, isItalic, "italic", true);
			join(joiner, color != null, "color", color);
			return joiner.toString();
		}

		/**
		 * @return The message of to send to send to a player.
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @return The color of this message.
		 */
		public String getColor() {
			return color;
		}

		/**
		 * @return True if the message is in <b>bold</b>, false otherwise.
		 */
		public boolean isBold() {
			return isBold;
		}

		/**
		 * @return True if this message is in <i>italic</i>, false otherwise.
		 */
		public boolean isItalic() {
			return isItalic;
		}

		private void join(StringJoiner joiner, boolean condition, String key, Object value) {
			if (condition)
				joiner.add("\"" + key + "\":\"" + value + "\"");
		}
	}
}
