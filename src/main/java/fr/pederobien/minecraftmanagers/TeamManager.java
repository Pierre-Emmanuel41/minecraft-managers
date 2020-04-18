package fr.pederobien.minecraftmanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TeamManager {
	private static final Random RANDOM = new Random();

	/**
	 * @return A stream that contains all registered team in the server.
	 */
	public static Stream<Team> getTeams() {
		return new ArrayList<Team>(BukkitManager.getScoreboardManager().getMainScoreboard().getTeams()).stream();
	}

	/**
	 * Get all players registered in the given team.
	 * 
	 * @param team The team used to get its players.
	 * @return A stream that contains all players in this team.
	 */
	public static Stream<Player> getPlayers(Team team) {
		List<Player> players = new ArrayList<Player>();
		for (String pl : team.getEntries())
			players.add(Bukkit.getPlayer(pl));
		return players.stream();
	}

	/**
	 * Get The team associated to the given name.
	 * 
	 * @param name The name associated to the team.
	 * @return An optional that contains the team associated with the name if it exist, an empty optional otherwise.
	 */
	public static Optional<Team> getTeam(String name) {
		return getTeams().filter(t -> t.getName().equals(name)).findFirst();
	}

	/**
	 * Get The team associated to the given color.
	 * 
	 * @param color The color associated to the team.
	 * @return An optional that contains the team associated with the color if it exist, an empty optional otherwise.
	 */
	public static Optional<Team> getTeam(ChatColor color) {
		return getTeams().filter(t -> t.getColor().equals(color)).findFirst();
	}

	/**
	 * Get the team associated to the given player.
	 * 
	 * @param player The player used to get its team.
	 * @return An optional that contains the team in which the player is registered, an empty Optional otherwise.
	 */
	public static Optional<Team> getTeam(Player player) {
		return getTeams().filter(t -> getPlayers(t).collect(Collectors.toList()).contains(player)).findFirst();
	}

	/**
	 * Get the color associated to the given player. This method try to get the team in which the player is registered and return its
	 * color if not nul.
	 * 
	 * @param player The player used to get the associated color.
	 * @return {@link ChatColor#RESET} if the player is not registered into a team, the team color otherwise.
	 */
	public static ChatColor getColor(Player player) {
		Optional<Team> team = getTeam(player);
		return team.isPresent() ? ChatColor.RESET : team.get().getColor();
	}

	/**
	 * Return the number of player registered into the given team.
	 * 
	 * @param team The team used to get the number of registered player.
	 * @return The number of player registered in the given team.
	 */
	public static int getNumberOfPlayers(Team team) {
		return (int) getPlayers(team).count();
	}

	/**
	 * @return An optional that contains all registered players in a team.
	 */
	public static Stream<Player> getPlayersInTeam() {
		return getTeams().map(t -> getPlayers(t)).reduce(Stream.of(), (player, elt) -> Stream.concat(player, elt));
	}

	/**
	 * @return The number of player registered in a team.
	 * 
	 * @see #getPlayersInTeam()
	 */
	public static int getNumberOfPlayerInTeam() {
		return (int) getPlayersInTeam().count();
	}

	/**
	 * Get colleagues of the given player. These colleagues correspond to the other players of the given player's team.
	 * 
	 * @param player The player used to get its colleagues.
	 * @return All players in the team of the given player without the specified player.
	 */
	public static Stream<Player> getColleagues(Player player) {
		return getPlayers(getTeam(player).get()).filter(p -> !p.equals(player));
	}

	/**
	 * Get a random colleague of the specified player.
	 * 
	 * @param player The player used to get its colleagues.
	 * @return A player from the same team as the given player.
	 * 
	 * @see #getColleagues(Player)
	 * @see #getRandom(Stream)
	 */
	public static Player getRandomColleague(Player player) {
		return getRandom(getColleagues(player));
	}

	/**
	 * Get a random player from the specified stream. First the stream is collected, this means that this method is a terminal
	 * operation for the given stream. Then through a random object, get a random player.
	 * 
	 * @param players A stream used to get a random player.
	 * @return A player randomly choosen.
	 */
	public static Player getRandom(Stream<Player> players) {
		List<Player> playersAsList = players.collect(Collectors.toList());
		Random rand = new Random();
		return playersAsList.get(rand.nextInt(playersAsList.size()));
	}

	/**
	 * Get a stream that contains player from the given team with the specified game mode.
	 * 
	 * @param team The team used to get its players.
	 * @param mode The condition to filter the stream return by {@link #getPlayers(Team)}.
	 * @return A stream that contains all players from the given team with the specified game mode.
	 */
	public static Stream<Player> getPlayersOnModeInTeam(Team team, GameMode mode) {
		return PlayerManager.getPlayersOnMode(getPlayers(team), mode);
	}

	/**
	 * Get the number of player from the specified team who have the given game mode.
	 * 
	 * @param team The team used to get its players.
	 * @param mode The condition to filter the stream return by {@link #getPlayers(Team)}.
	 * @return The number of player from the team who have the specified game mode.
	 */
	public static int getNumberTeamPlayersOnMode(Team team, GameMode mode) {
		return (int) getPlayersOnModeInTeam(team, mode).count();
	}

	/**
	 * Create a team on the server. To simplify the way using this method, if you don't want to specify the display name of the team,
	 * then displayName = null.
	 * 
	 * @param teamName    The name of the team.
	 * @param displayName The display name of the team.
	 */
	public static Team createTeam(String teamName, String displayName) {
		StringBuilder builder = new StringBuilder("team add " + teamName);
		builder.append(displayName == null ? "" : "\"" + displayName + "\"");
		BukkitManager.dispatchCommand(builder.toString());
		return getTeam(teamName).get();
	}

	/**
	 * Create a team on the server using method {@link #createTeam(String, String)} with displayName equals null.
	 * 
	 * @param teamName The name of the team.
	 */
	public static Team createTeam(String teamName) {
		return createTeam(teamName, null);
	}

	/**
	 * Add the player to the given team.
	 * 
	 * @param team   The team that will receive the player.
	 * @param player The player to add to the team.
	 */
	public static void addPlayerToTeam(Team team, Player player) {
		BukkitManager.dispatchCommand("team join " + team.getName() + " " + player.getName());
	}

	/**
	 * Add each player from the stream in the given team.
	 * 
	 * @param team    The team that will receive all players.
	 * @param players Players to add to the team.
	 */
	public static void addPlayersToTeam(Team team, Stream<Player> players) {
		players.peek(p -> addPlayerToTeam(team, p));
	}

	/**
	 * Set the display name of the given team.
	 * 
	 * @param team           The team to modify.
	 * @param newDisplayName The new team's display name.
	 * 
	 * @throws IllegalArgumentException if displayName is longer than 128 characters.
	 * @throws IllegalStateException    if the team has been unregistered.
	 */
	public static void modifyDisplayNameTeam(Team team, String newDisplayName) {
		team.setDisplayName(newDisplayName);
	}

	/**
	 * Set the color of the given team <br>
	 * This only sets the team outline, other occurrences of colors such as in names are handled by prefixes / suffixes.
	 * 
	 * @param team  The team to modify.
	 * @param color The new team's color.
	 */
	public static void modifyColorTeam(Team team, ChatColor color) {
		team.setColor(color);
	}

	/**
	 * Sets the team friendly fire state
	 * 
	 * @param team    The team to modify.
	 * @param enabled true if friendly fire is to be allowed.
	 * 
	 * @throws IllegalStateException if the team has been unregistered.
	 */
	public static void modifyFriendFire(Team team, boolean enabled) {
		team.setAllowFriendlyFire(enabled);
	}

	/**
	 * Remove the specified player from the given team.
	 * 
	 * @param team   The team to modify.
	 * @param player The player to remove.
	 * 
	 * @throws IllegalStateException if this team has been unregistered.
	 */
	public static void removePlayerFromTeam(Team team, Player player) {
		team.removeEntry(player.getName());
	}

	/**
	 * Remove each player present in the stream from the given team.
	 * 
	 * @param team    The team to modify.
	 * @param players A stream that contains all players to remove from the specified team.
	 */
	public static void removePlayersFromTeam(Team team, Stream<Player> players) {
		players.peek(p -> removePlayerFromTeam(team, p));
	}

	/**
	 * Remove all player in the specified team.
	 * 
	 * @param team The team to modify.
	 */
	public static void removeAllPlayersFromTeam(Team team) {
		removePlayersFromTeam(team, getPlayers(team));
	}

	/**
	 * Remove the player from its initial team (if any) and add it to the given team.
	 * 
	 * @param player The player to move.
	 * @param to     The target team.
	 */
	public static void movePlayer(Player player, Team to) {
		Optional<Team> team = getTeam(player);
		if (team.isPresent())
			removePlayerFromTeam(team.get(), player);
		addPlayerToTeam(to, player);
	}

	/**
	 * Remove the specified team from the server.
	 * 
	 * @param team The team to remove.
	 */
	public static void removeTeam(Team team) {
		BukkitManager.dispatchCommand("team remove " + team.getName());
	}

	/**
	 * Remove each team present in the stream from the server.
	 * 
	 * @param teams A stream that contains all teams to remove from the server.
	 * 
	 * @see #removeTeam(Team)
	 */
	public static void removeTeam(Stream<Team> teams) {
		teams.peek(t -> removeTeam(t));
	}

	/**
	 * Remove all teams registered on the server.
	 * 
	 * @see #removeTeam(Stream)
	 */
	public static void removeAllTeam() {
		removeTeam(getTeams());
	}

	/**
	 * Dispatch all players currently logged into the server into teams. To simplify the way of using this method, it possible to put
	 * -1 for <code>maxPlayerInTeam</code>. In that case, players are directly dispatched into the given teams.
	 * 
	 * @param teams           A list of team in which players are dispatched.
	 * @param maxPlayerInTeam The max player allowed per team.
	 * 
	 * @throws UnsupportedOperationException If there are not enough players.
	 * @throws UnsupportedOperationException If there are not enough teams.
	 */
	public static void dispatchPlayerRandomlyInTeam(List<Team> teams, int maxPlayerInTeam) {
		for (Team team : teams)
			removeAllPlayersFromTeam(team);

		List<Player> players = PlayerManager.getPlayers().collect(Collectors.toList());
		List<Team> copy = new ArrayList<Team>(teams);

		if (maxPlayerInTeam == -1)
			dispatchPlayers(copy, players);
		else {
			checkEnoughPlayers(maxPlayerInTeam, players.size());
			int nbTeam = checkEnoughTeam(maxPlayerInTeam, players.size(), copy.size());

			for (int i = 0; i < teams.size() - nbTeam; i++)
				copy.remove(RANDOM.nextInt(copy.size()));

			dispatchPlayers(copy, players, maxPlayerInTeam);
		}
	}

	private static void checkEnoughPlayers(int maxPlayerInTeam, int nbPlayer) {
		if (nbPlayer <= maxPlayerInTeam)
			throw new UnsupportedOperationException("There is not enough player");
	}

	private static int checkEnoughTeam(int maxPlayerInTeam, int nbPlayer, int nbTeam) {
		int nbTeams = nbPlayer / maxPlayerInTeam + (nbPlayer % maxPlayerInTeam > 0 ? 1 : 0);
		if (nbTeam < nbTeams)
			throw new UnsupportedOperationException("There is not enough team");
		return nbTeams;
	}

	private static void dispatchPlayers(List<Team> copy, List<Player> players, int maxPlayerInTeam) {
		for (int i = 0; i < players.size(); i++) {
			Team randomTeam = copy.get(RANDOM.nextInt(copy.size()));
			addPlayerToTeam(randomTeam, players.get(i));
			removeTeam(copy, randomTeam, maxPlayerInTeam);
		}
	}

	private static void dispatchPlayers(List<Team> copy, List<Player> players) {
		int quotient = players.size() / copy.size();
		int reste = players.size() % copy.size();

		for (Player player : players) {
			int maxPlayer = quotient + (reste > 0 ? 1 : 0);

			Team randomTeam;
			boolean teamRemoved;
			do {
				randomTeam = copy.get(RANDOM.nextInt(copy.size()));
			} while (teamRemoved = removeTeam(copy, randomTeam, maxPlayer));

			addPlayerToTeam(randomTeam, player);

			if (teamRemoved)
				reste--;
		}
	}

	private static boolean removeTeam(List<Team> teams, Team randomTeam, int maxPlayer) {
		boolean removed = getNumberOfPlayers(randomTeam) == maxPlayer;
		if (removed)
			teams.remove(randomTeam);
		return removed;
	}
}