package fr.pederobien.minecraftmanagers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionManager {
	public static final int MAX_EFFECT_DURATION = 999999;
	public static final int MIN_EFFECT_DURATION = 20;
	public static final int MAX_EFFECT_AMPLIFIER = 99;
	public static final int MIN_EFFECT_AMPLIFIER = 1;

	/**
	 * Creates a potion effect with no defined color.
	 *
	 * @param type      effect type
	 * @param duration  measured in ticks, see {@link PotionEffect#getDuration()}
	 * @param amplifier the amplifier, see {@link PotionEffect#getAmplifier()}
	 * @param ambient   the ambient status, see {@link PotionEffect#isAmbient()}
	 * @param particles the particle status, see {@link PotionEffect#hasParticles()}
	 * 
	 * @return The associated effect.
	 */
	public static PotionEffect createEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
		return new PotionEffect(type, duration, amplifier, ambient, particles);
	}

	/**
	 * Creates a potion effect with no defined color. This method call
	 * {@link #createEffect(PotionEffectType, int, int, boolean, boolean)} with boolean particles equals true.
	 *
	 * @param type      effect type
	 * @param duration  measured in ticks, see {@link PotionEffect#getDuration()}
	 * @param amplifier the amplifier, see {@link PotionEffect#getAmplifier()}
	 * @param ambient   the ambient status, see {@link PotionEffect#isAmbient()}
	 * 
	 * @return The associated effect.
	 * 
	 */
	public static PotionEffect createEffect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
		return createEffect(type, duration, amplifier, ambient, true);
	}

	/**
	 * Creates a potion effect with no defined color. This method call {@link #createEffect(PotionEffectType, int, int, boolean)} with
	 * boolean ambient equals true.
	 *
	 * @param type      effect type
	 * @param duration  measured in ticks, see {@link PotionEffect#getDuration()}
	 * @param amplifier the amplifier, see {@link PotionEffect#getAmplifier()}
	 * 
	 * @return The associated effect.
	 */
	public static PotionEffect createEffect(PotionEffectType type, int duration, int amplifier) {
		return createEffect(type, duration, amplifier, true);
	}

	/**
	 * Creates a potion effect with no defined color. This method call {@link #createEffect(PotionEffectType, int, int)} with duration
	 * equals {@value #MAX_EFFECT_DURATION} and amplifier equals {@value #MAX_EFFECT_AMPLIFIER}
	 *
	 * @param type effect type
	 * 
	 * @return The associated effect.
	 */
	public static PotionEffect createEffectMaxDurationAndAmplifier(PotionEffectType type) {
		return createEffect(type, MAX_EFFECT_DURATION, MAX_EFFECT_AMPLIFIER);
	}

	/**
	 * Create a stream based on all type presents in the given <code>types</code> and for each of them create an effect using method
	 * {@link #createEffectMaxDurationAndAmplifier(PotionEffectType)}
	 * 
	 * @param types A list of potion effect type used to create effect.
	 * @return A stream that contains all effects associated to all potion effect type.
	 */
	public static Stream<PotionEffect> createEffectsMaxDurationAndAmplifier(PotionEffectType... types) {
		return Stream.of(types).map(t -> createEffectMaxDurationAndAmplifier(t));
	}

	/**
	 * Creates a potion effect with no defined color. This method call {@link #createEffect(PotionEffectType, int, int)} with duration
	 * equals {@value #MIN_EFFECT_DURATION} and amplifier equals {@value #MIN_EFFECT_AMPLIFIER}
	 *
	 * @param type effect type
	 * 
	 * @return The associated effect.
	 */
	public static PotionEffect createEffectMinDurationAndAmplifier(PotionEffectType type) {
		return createEffect(type, MIN_EFFECT_DURATION, MIN_EFFECT_AMPLIFIER);
	}

	/**
	 * Create a stream based on all type presents in the given <code>types</code> and for each of them create an effect using method
	 * {@link #createEffectMinDurationAndAmplifier(PotionEffectType)}
	 * 
	 * @param types An list of potion effect type used to create effect.
	 * @return A stream that contains all effects associated to all potion effect type.
	 */
	public static Stream<PotionEffect> createEffectsMinDurationAndAmplifier(PotionEffectType... types) {
		return Stream.of(types).map(t -> createEffectMinDurationAndAmplifier(t));
	}

	/**
	 * Adds the given {@link PotionEffect} to the specified player.
	 * <p>
	 * Only one potion effect can be present for a given {@link PotionEffectType}.
	 *
	 * @param player The player that receive the effect.
	 * @param effect PotionEffect to be added
	 */
	public static void giveEffect(Player player, PotionEffect effect) {
		player.addPotionEffect(effect);
	}

	/**
	 * Adds each effect in the given array <code>effects</code>to the specified player.
	 * <p>
	 * Only one potion effect can be present for a given {@link PotionEffectType}.
	 *
	 * @param player  The player that receive the effect.
	 * @param effects An array that contains PotionEffect to be added.
	 */
	public static void giveEffects(Player player, PotionEffect... effects) {
		for (PotionEffect effect : effects)
			player.addPotionEffect(effect, true);
	}

	/**
	 * Adds all effect present in the stream <code>effects</code> to the given player.
	 * 
	 * @param player  The player to add effects.
	 * @param effects A stream that contains all effects to add to the player.
	 * 
	 * @see #giveEffect(Player, PotionEffect)
	 */
	public static void giveEffects(Player player, Stream<PotionEffect> effects) {
		effects.forEach(e -> giveEffect(player, e));
	}

	/**
	 * This method create an effect for each potion effect type present in the array <code>types</code> using
	 * {@link #createEffectsMaxDurationAndAmplifier(PotionEffectType...)} and add them to the given player.
	 * 
	 * @param player The player that receive all effects.
	 * @param types  An array that contains potion effect type used to create effect.
	 * 
	 * @see #giveEffects(Player, Stream)
	 */
	public static void giveEffectMaxDurationAndAmplifier(Player player, PotionEffectType... types) {
		giveEffects(player, createEffectsMaxDurationAndAmplifier(types));
	}

	/**
	 * This method create an effect for each potion effect type present in the array <code>types</code> using
	 * {@link #createEffectsMinDurationAndAmplifier(PotionEffectType...)} and add them to the given player.
	 * 
	 * @param player The player that receive all effects.
	 * @param types  An array that contains potion effect type used to create effect.
	 * 
	 * @see #giveEffects(Player, Stream)
	 * 
	 */
	public static void giveEffectMinDurationAndAmplifier(Player player, PotionEffectType... types) {
		giveEffects(player, createEffectsMinDurationAndAmplifier(types));
	}

	/**
	 * Adds the given effect to all player present in the specified stream.
	 * 
	 * @param players A stream that contains players used to add an effect.
	 * @param effect  The effect to add.
	 * 
	 * @see #giveEffect(Player, PotionEffect)
	 */
	public static void giveEffect(Stream<Player> players, PotionEffect effect) {
		players.forEach(p -> giveEffect(p, effect));
	}

	/**
	 * Add to each player present in the stream <code>players</code> all effects present in the stream <code>effects</code>
	 * 
	 * @param players A stream that contains all player to add effects.
	 * @param effects A stream that contains all effects to add to each player.
	 * 
	 * @see #giveEffect(Player, PotionEffect)
	 */
	public static void giveEffects(Stream<Player> players, Stream<PotionEffect> effects) {
		List<Player> pl = players.collect(Collectors.toList());
		List<PotionEffect> ef = effects.collect(Collectors.toList());
		for (Player p : pl)
			for (PotionEffect e : ef)
				giveEffect(p, e);
	}

	/**
	 * Give the specified effect to each player with the specified game mode.
	 * 
	 * @param mode   The game mode used to filter the stream that contains all player currently logged into the server.
	 * @param effect The effect to give to each player present in the previous stream.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #giveEffect(Stream, PotionEffect)
	 */
	public static void giveEffectToPlayersOnMode(GameMode mode, PotionEffect effect) {
		giveEffect(PlayerManager.getPlayersOnMode(mode), effect);
	}

	/**
	 * Give effect present in the stream <code>effects</code> to each player with the specified game mode.
	 * 
	 * @param mode    The game mode used to filter the stream that contains all player currently logged into the server.
	 * @param effects A stream that contains all effect to add to the previsous stream of player.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #giveEffects(Stream, Stream)
	 */
	public static void giveEffectsToPlayersOnMode(GameMode mode, Stream<PotionEffect> effects) {
		giveEffects(PlayerManager.getPlayersOnMode(mode), effects);
	}

	/**
	 * Removes any effects present of the given {@link PotionEffectType}.
	 *
	 * @param player The player used to remove an effect.
	 * @param type   The potion type to remove.
	 */
	public static void removeEffect(Player player, PotionEffectType type) {
		player.removePotionEffect(type);
	}

	/**
	 * Removes any effects present of the given {@link PotionEffectType}.
	 *
	 * @param player The player used to remove the effects.
	 * @param types  A stream that contains all potion effect type used to remove the associated effect.
	 * 
	 * @see #removeEffect(Player, PotionEffectType)
	 */
	public static void removeEffect(Player player, Stream<PotionEffectType> types) {
		types.forEach(t -> removeEffect(player, t));
	}

	/**
	 * Removes any effects present of the given {@link PotionEffectType}.
	 *
	 * @param player The player used to remove the effects.
	 * @param types  A stream that contains all potion effect type used to remove the associated effect.
	 * 
	 * @see #removeEffects(Stream, Stream)
	 */
	public static void removeEffect(Player player, PotionEffectType... types) {
		removeEffect(player, Stream.of(types));
	}

	/**
	 * Removes any effect present of the given {@link PotionEffectType} from each player present in the specified stream.
	 * 
	 * @param players A stream that contains all player used to remove effect.
	 * @param type    The potion type to remove.
	 * 
	 * @see #removeEffect(Player, PotionEffectType)
	 */
	public static void removeEffect(Stream<Player> players, PotionEffectType type) {
		players.forEach(p -> removeEffect(p, type));
	}

	/**
	 * Remove each effect associated to potion effect type present in the stream <code>types</code> from each player present in the
	 * stream <code>players</code>
	 * 
	 * @param players A stream that contains all player used to remove all effect.
	 * @param types   A stream that contains all potion type to remove from all player.
	 * 
	 * @see #removeEffect(Player, PotionEffectType)
	 */
	public static void removeEffects(Stream<Player> players, Stream<PotionEffectType> types) {
		List<Player> pl = players.collect(Collectors.toList());
		List<PotionEffectType> ty = types.collect(Collectors.toList());
		for (Player p : pl)
			for (PotionEffectType t : ty)
				removeEffect(p, t);
	}

	/**
	 * Remove the specified effect associated to the given potion effect type to each player with the specified game mode.
	 * 
	 * @param mode The game mode used to filter the stream that contains all player currently logged into the server.
	 * @param type The type of effect to remove from each player present in the previous stream.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #removeEffect(Stream, PotionEffect)
	 */
	public static void removeEffectToPlayerOnMode(GameMode mode, PotionEffectType type) {
		removeEffect(PlayerManager.getPlayersOnMode(mode), type);
	}

	/**
	 * Remove all effect associated to all potion effect type present in the stream <code>types</code> from each player with the
	 * specified game mode.
	 * 
	 * @param mode  The game mode used to filter the stream that contains all player currently logged into the server.
	 * @param types A stream that contains all potion effect type to remove from each player present in the previous stream.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #removeEffects(Stream, Stream)
	 */
	public static void removeEffectsToPlayerOnMode(GameMode mode, Stream<PotionEffectType> types) {
		removeEffects(PlayerManager.getPlayersOnMode(mode), types);
	}

	/**
	 * Remove all effect associated to all potion effect type present in <code>types</code> from each player with the specified game
	 * mode.
	 * 
	 * @param mode  The game mode used to filter the stream that contains all player currently logged into the server.
	 * @param types A stream that contains all potion effect type to remove from each player present in the previous stream.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #removeEffectsToPlayerOnMode(Stream, Stream)
	 */
	public static void removeEffectsToPlayerOnMode(GameMode mode, PotionEffectType... types) {
		removeEffectsToPlayerOnMode(mode, Stream.of(types));
	}

	/**
	 * Clear the list of active potion effect of the specified player.
	 * 
	 * @param player The player used to remove all effect.
	 * 
	 * @see Player#getActivePotionEffects()
	 * @see #removeEffect(Player, PotionEffectType)
	 */
	public static void removeAllEffects(Player player) {
		player.getActivePotionEffects().stream().forEach(e -> removeEffect(player, e.getType()));
	}

	/**
	 * Remove all active effect from each player present in the given stream.
	 * 
	 * @param players A stream that contains all player used to remove all effects.
	 * 
	 * @see #removeAllEffects(Player)
	 */
	public static void removeAllEffects(Stream<Player> players) {
		players.forEach(p -> removeAllEffects(p));
	}

	/**
	 * Remove all active effect from each player with the specified game mode.
	 * 
	 * @param mode The game mode used to filter the stream that contains all player currently logged into the server.
	 * 
	 * @see PlayerManager#getPlayersOnMode(GameMode)
	 * @see #removeAllEffects(Stream)
	 */
	public static void removeAllEffectsToPlayerOnMode(GameMode mode) {
		removeAllEffects(PlayerManager.getPlayersOnMode(mode));
	}
}
