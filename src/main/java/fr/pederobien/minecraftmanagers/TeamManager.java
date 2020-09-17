package fr.pederobien.minecraftmanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {
	private static final Random RANDOM = new Random();

	/**
	 * Create a team on the server.
	 * 
	 * @param teamName The name of the team.
	 */
	public static Team createTeam(String teamName) {
		return getMainScoreboard().registerNewTeam(teamName);

	}

	/**
	 * Create a team on the server and set its color.
	 * 
	 * @param teamName The team's name.
	 * @param color    The team's color.
	 * @return The created team.
	 */
	public static Team createTeam(String teamName, ChatColor color) {
		Team team = createTeam(teamName);
		if (team != null)
			team.setColor(color);
		return team;
	}

	/**
	 * Create a team on the server, set its color and add each player in the given stream to the created team.
	 * 
	 * @param teamName The team's name.
	 * @param color    The team's color.
	 * @param players  The team's players.
	 * @return The created team.
	 */
	public static Team createTeam(String teamName, ChatColor color, Stream<Player> players) {
		Team team = createTeam(teamName);
		if (team == null)
			return null;

		team.setColor(color);
		players.forEach(player -> team.addEntry(player.getName()));
		return team;
	}

	/**
	 * Remove the specified team from the server.
	 * 
	 * @param teamName The team'name to remove.
	 */
	public static void removeTeam(String teamName) {
		BukkitManager.dispatchCommand("team remove " + teamName);
	}

	/**
	 * Remove the specified player from the given team.
	 * 
	 * @param teamName The team's name to modify.
	 * @param player   The player to remove.
	 * 
	 * @throws IllegalStateException if this team has been unregistered.
	 */
	public static void removePlayerFromTeam(String teamName, Player player) {
		Optional<Team> team = getTeam(teamName);
		if (team.isPresent())
			removePlayerFromTeam(team.get(), player);
	}

	/**
	 * Remove each player present in the stream from the given team.
	 * 
	 * @param teamName The team's name to modify.
	 * @param players  A stream that contains all players to remove from the specified team.
	 */
	public static void removePlayersFromTeam(String teamName, Stream<Player> players) {
		Optional<Team> team = getTeam(teamName);
		if (team.isPresent())
			removePlayersFromTeam(team.get(), players);
	}

	/**
	 * Remove all player in the specified team.
	 * 
	 * @param team The team to modify.
	 */
	public static void removeAllPlayersFromTeam(String teamName) {
		Optional<Team> team = getTeam(teamName);
		if (team.isPresent())
			removeAllPlayersFromTeam(team.get());
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
	 * @return A stream that contains all registered team in the server.
	 */
	public static Stream<Team> getTeams() {
		return new ArrayList<Team>(getMainScoreboard().getTeams()).stream();
	}

	/**
	 * Get all players registered in the given team.
	 * 
	 * @param team The team used to get its players.
	 * @return A stream that contains all players in this team.
	 */
	public static Stream<Player> getPlayers(Team team) {
		List<Player> players = new ArrayList<Player>();
		for (String pl : team.getEntries()) {
			Player player = Bukkit.getPlayer(pl);
			if (player != null)
				players.add(player);
		}
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
		return team.isPresent() ? team.get().getColor() : ChatColor.RESET;
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
		return getTeams().map(t -> getPlayers(t)).reduce(Stream.of(), (players, playersTeam) -> Stream.concat(players, playersTeam));
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
		Optional<Team> optTeam = getTeam(player);
		return optTeam.isPresent() ? getPlayers(optTeam.get()).filter(p -> !p.equals(player)) : Stream.of();
	}

	/**
	 * Get colleagues of the given player that verify the given predicate. These colleagues correspond to the other players of the
	 * given player's team.
	 * 
	 * @param player    The player used to get its colleagues.
	 * @param predicate A filter for the players' selection.
	 * 
	 * @return All players in the team of the given player without the specified player.
	 */
	public static Stream<Player> getColleagues(Player player, Predicate<Player> predicate) {
		Optional<Team> optTeam = getTeam(player);
		return optTeam.isPresent() ? getPlayers(optTeam.get()).filter(p -> !p.equals(player)).filter(predicate) : Stream.of();
	}

	/**
	 * Create a {@link ColleagueInfo} for the given source player and the given colleague. This colleague info contains informations
	 * about the colleague : for example the distance between this two players or the direction of colleague in comparison with the
	 * source player.
	 * 
	 * @param source    The player used as reference for the colleague info.
	 * @param colleague The player used to know more information about him in comparison to the source player.
	 * 
	 * @return A ColleagueInfo.
	 */
	public static ColleagueInfo createColleagueInfo(Player source, Player colleague) {
		return new ColleagueInfo(source, colleague);
	}

	/**
	 * For each colleague of this player, create a new {@link PlayerLocation}.
	 * 
	 * @param player The player used to get information of its colleagues.
	 * 
	 * @return A stream that contains information about its colleagues.
	 */
	public static Stream<ColleagueInfo> getColleaguesInfo(Player player) {
		return getColleagues(player).map(colleague -> createColleagueInfo(player, colleague));
	}

	/**
	 * Filter the stream returned by {@link #getColleagues(Player)} using the specified predicate, and create a new
	 * {@link PlayerLocation} for each remaining colleague.
	 * 
	 * @param player    The player used to get information of its colleagues.
	 * @param predicate A filter for the players' selection.
	 * 
	 * @return A stream that contains information about its colleagues.
	 */
	public static Stream<ColleagueInfo> getColleaguesInfo(Player player, Predicate<Player> predicate) {
		return getColleagues(player).filter(predicate).map(colleague -> createColleagueInfo(player, colleague));
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
	public static Optional<Player> getRandomColleague(Player player) {
		return getRandom(getColleagues(player).collect(Collectors.toList()));
	}

	/**
	 * Filter the stream returned by {@link #getColleagues(Player)} using the specified predicate, then collect this stream using
	 * {@link Collectors#toList()} and finally choose a random player from this list.
	 * 
	 * @param player The player used to get its colleagues.
	 * @return A player from the same team as the given player.
	 * 
	 * @see #getColleagues(Player)
	 * @see #getRandom(Stream)
	 */
	public static Optional<Player> getRandomColleague(Player player, Predicate<Player> predicate) {
		return getRandom(getColleagues(player).filter(predicate).collect(Collectors.toList()));
	}

	/**
	 * Get a random player from the specified stream. First the stream is collected, this means that this method is a terminal
	 * operation for the given stream. Then through a random object, get a random player.
	 * 
	 * @param players A stream used to get a random player.
	 * @return A player randomly choosen.
	 */
	public static Optional<Player> getRandom(List<Player> players) {
		Random rand = new Random();
		if (players.size() == 0)
			return Optional.empty();
		return Optional.of(players.get(rand.nextInt(players.size())));
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
		players.forEach(p -> addPlayerToTeam(team, p));
	}

	/**
	 * Add the given player to the team associated to the specified name if it exists.
	 * 
	 * @param teamName The team's name.
	 * @param player   The player to add.
	 * 
	 * @return True if the team has been found, false otherwise.
	 */
	public static boolean addPlayerToTeam(String teamName, Player player) {
		Optional<Team> optTeam = getTeam(teamName);
		if (!optTeam.isPresent())
			return false;

		addPlayerToTeam(optTeam.get(), player);
		return true;
	}

	/**
	 * Add each player in the given stream to the team associated to the specified name if it exists.
	 * 
	 * @param teamName The team's name.
	 * @param players  A stream that contains players to add.
	 * 
	 * @return True if the team has been found, false otherwise.
	 */
	public static boolean addPlayersToTeam(String teamName, Stream<Player> players) {
		Optional<Team> optTeam = getTeam(teamName);
		if (!optTeam.isPresent())
			return false;

		addPlayersToTeam(optTeam.get(), players);
		return true;
	}

	/**
	 * Remove the given player from its team. It equivalent to do in minecraft : <code>/team remove player.getName()</code>.
	 * 
	 * @param player The player to remove.
	 */
	public static void removePlayerFromTeam(Player player) {
		BukkitManager.dispatchCommand("team leave " + player.getName());
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
	 * Remove each team present in the stream from the server.
	 * 
	 * @param teamNames A stream that contains the name of each team to remove from the server.
	 * 
	 * @see #removeTeam(Team)
	 */
	public static void removeTeams(Stream<String> teamNames) {
		teamNames.forEach(teamName -> removeTeam(teamName));
	}

	/**
	 * Remove all teams registered on the server.
	 * 
	 * @see #removeTeam(Stream)
	 */
	public static void removeAllTeam() {
		removeTeams(getTeams().map(team -> team.getName()));
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

	/**
	 * Mix the original list randomly. The original list is not modify.
	 * 
	 * @param <T>      The type of element in the list.
	 * @param original The list to mix.
	 * @return The randomly mixed list.
	 */
	public static <T> List<T> mix(List<T> original) {
		List<T> randoms = new ArrayList<T>();
		int size = original.size();
		for (int i = 0; i < size; i++) {
			T random = original.get(RANDOM.nextInt(original.size()));
			randoms.add(random);
			original.remove(random);
		}
		return randoms;
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

	private static Scoreboard getMainScoreboard() {
		return BukkitManager.getScoreboardManager().getMainScoreboard();
	}

	private static void removePlayerFromTeam(Team team, Player player) {
		team.removeEntry(player.getName());
	}

	private static void removePlayersFromTeam(Team team, Stream<Player> players) {
		players.forEach(player -> removePlayerFromTeam(team, player));
	}

	private static void removeAllPlayersFromTeam(Team team) {
		removePlayersFromTeam(team, getPlayers(team));
	}

	public static class ColleagueInfo {
		private Player source, colleague;
		private int distance;
		private double yaw;
		private boolean isInDifferentWorld;
		private EArrows arrow;

		public ColleagueInfo(Player source, Player colleague) {
			this.source = source;
			this.colleague = colleague;
			this.distance = (int) WorldManager.getDistance2D(source.getLocation(), colleague.getLocation());
			this.yaw = WorldManager.getYaw(source, colleague.getLocation());
			this.isInDifferentWorld = source.getWorld() != colleague.getWorld();
			this.arrow = EArrows.getArrow(yaw);
		}

		/**
		 * @return The source player for this PlayerLocation.
		 */
		public Player getSource() {
			return source;
		}

		/**
		 * @return The player used to know the distance and the orientation between the source player and this player.
		 */
		public Player getColleague() {
			return colleague;
		}

		/**
		 * @return The distance between the source player and the other player.
		 */
		public int getDistance() {
			return distance;
		}

		/**
		 * @return The yaw between the source player and the other player. This angle is in range [-180;180]
		 */
		public double getYaw() {
			return yaw;
		}

		/**
		 * If both players are in different world, the distance between those player is {@link Double#NaN}.
		 * 
		 * @return True if the player source and the colleague are in different world.
		 * 
		 * @see #getDistance()
		 */
		public boolean isInDifferentWorld() {
			return isInDifferentWorld;
		}

		/**
		 * @return The arrow associated to the yaw.
		 * 
		 * @see #getYaw()
		 * @see EArrows#getArrow(double)
		 */
		public EArrows getArrow() {
			return arrow;
		}
	}
}