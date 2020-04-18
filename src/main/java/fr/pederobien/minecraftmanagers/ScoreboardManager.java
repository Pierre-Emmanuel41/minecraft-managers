package fr.pederobien.minecraftmanagers;

import java.util.List;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

	/**
	 * @return A new empty scoreboard.
	 */
	public static Scoreboard createScoreboard() {
		return BukkitManager.getScoreboardManager().getNewScoreboard();
	}

	/**
	 * Register an objective to the given scoreboard.
	 * 
	 * @param scoreboard  The scoreboard used to register a new objective.
	 * @param name        Name of the Objective.
	 * @param criteria    Criteria for the Objective.
	 * @param displayName Name displayed to players for the Objective.
	 * @param slot        display slot to change, or null to not display.
	 * 
	 * @return The registered Objective
	 * 
	 * @throws IllegalArgumentException if name is null
	 * @throws IllegalArgumentException if name is longer than 16 characters.
	 * @throws IllegalArgumentException if criteria is null
	 * @throws IllegalArgumentException if displayName is null
	 * @throws IllegalArgumentException if displayName is longer than 128 characters.
	 * @throws IllegalArgumentException if an objective by that name already exists
	 */
	public static Objective createObjective(Scoreboard scoreboard, String name, String criteria, String displayName, DisplaySlot slot) {
		Objective obj = scoreboard.registerNewObjective(name, criteria, displayName);
		obj.setDisplaySlot(slot);
		return obj;
	}

	/**
	 * Create a new objective using {@link #createObjective(String, String, String, DisplaySlot)} with name equals Objective and
	 * criteria equals dummy.
	 * 
	 * @param displayName Name displayed to players for the Objective.
	 * @param slot        display slot to change, or null to not display.
	 * 
	 * @return The registered objective.
	 */
	public static Objective createObjective(Scoreboard scoreboard, String displayName, DisplaySlot slot) {
		return createObjective(scoreboard, "Objective", "dummy", displayName, slot);
	}

	/**
	 * Set the specified scoreboard for the given player.
	 * 
	 * @param player     The player that receive the scoreboard.
	 * @param scoreboard The scoreboard to give to the player.
	 * 
	 * @see Player#setScoreboard(Scoreboard)
	 */
	public static void setPlayerScoreboard(Player player, Scoreboard scoreboard) {
		player.setScoreboard(scoreboard);
	}

	/**
	 * All of players present in the given stream will have the same scoreboard.
	 * 
	 * @param players    A stream that contains all player that will receive the scoreboard.
	 * @param scoreboard The scoreboard to give to each player present in the previous stream.
	 */
	public static void setPlayersScoreboard(Stream<Player> players, Scoreboard scoreboard) {
		players.peek(p -> setPlayerScoreboard(p, scoreboard));
	}

	/**
	 * Add an entry to the end of the objective.
	 * 
	 * @param objective The objective in which the entry should be added.
	 * @param entry     The entry to add.
	 */
	public static void addEntry(Objective objective, String entry) {
		objective.getScore(entry).setScore(objective.getScoreboard().getEntries().size());
	}

	/**
	 * Add the list of entry to the end of the objective.
	 * 
	 * @param objective The objective in which all entries should be added.
	 * @param entries   The list of entry to add.
	 * 
	 * @see #addEntry(Objective, String)
	 */
	public static void addEntries(Objective objective, List<String> entries) {
		entries.forEach(s -> addEntry(objective, s));
	}

	/**
	 * Remove the scoreboard of the given player. This method is equivalent to setting a new empty scoreboard to the player.
	 * 
	 * @param player The player used to remove its scoreboard.
	 */
	public static void removePlayerScoreboard(Player player) {
		setPlayerScoreboard(player, createScoreboard());
	}

	/**
	 * Remove the scoreboard of each player present in the given stream.
	 * 
	 * @param players A stream that contains all player used to remove their scoreboard.
	 * 
	 * @see #removePlayerScoreboard(Player)
	 */
	public static void removePlayersScoreboard(Stream<Player> players) {
		players.peek(p -> removePlayerScoreboard(p));
	}
}
