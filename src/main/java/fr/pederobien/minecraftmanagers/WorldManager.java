package fr.pederobien.minecraftmanagers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.bukkit.Axis;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class WorldManager {
	/**
	 * A list of all entity considered as mobs for players
	 */
	public static final Set<EntityType> MOBS;

	/**
	 * The surface world whose name is {@link #OVERWORLD_NAME}
	 */
	public static final World OVERWORLD;

	/**
	 * The surface world whose name is {@link #NETHER_WORLD_NAME}
	 */
	public static final World NETHER_WORLD;

	/**
	 * The surface world whose name is {@link #END_WORLD_NAME}
	 */
	public static final World END_WORLD;

	/**
	 * The normalised name associated to the world {@link #OVERWORLD}
	 */
	public static final String OVERWORLD_NAME = "Overworld";

	/**
	 * The normalised name associated to the world {@link #NETHER_WORLD}
	 */
	public static final String NETHER_WORLD_NAME = "Nether";

	/**
	 * The normalised name associated to the world {@link #END_WORLD}
	 */
	public static final String END_WORLD_NAME = "Ender";

	static {
		OVERWORLD = getWorld("world");
		NETHER_WORLD = getWorld("world_nether");
		END_WORLD = getWorld("world_the_end");

		MOBS = new HashSet<>();

		MOBS.add(EntityType.BLAZE);
		MOBS.add(EntityType.CAVE_SPIDER);
		MOBS.add(EntityType.CREEPER);
		MOBS.add(EntityType.DROWNED);
		MOBS.add(EntityType.ELDER_GUARDIAN);
		MOBS.add(EntityType.ENDER_DRAGON);
		MOBS.add(EntityType.ENDERMITE);
		MOBS.add(EntityType.EVOKER);
		MOBS.add(EntityType.GHAST);
		MOBS.add(EntityType.GIANT);
		MOBS.add(EntityType.GUARDIAN);
		MOBS.add(EntityType.HUSK);
		MOBS.add(EntityType.ILLUSIONER);
		MOBS.add(EntityType.MAGMA_CUBE);
		MOBS.add(EntityType.PHANTOM);
		MOBS.add(EntityType.PIG_ZOMBIE);
		MOBS.add(EntityType.SHULKER);
		MOBS.add(EntityType.SILVERFISH);
		MOBS.add(EntityType.SKELETON);
		MOBS.add(EntityType.SKELETON_HORSE);
		MOBS.add(EntityType.SLIME);
		MOBS.add(EntityType.SPIDER);
		MOBS.add(EntityType.STRAY);
		MOBS.add(EntityType.VEX);
		MOBS.add(EntityType.VINDICATOR);
		MOBS.add(EntityType.WITCH);
		MOBS.add(EntityType.WITHER);
		MOBS.add(EntityType.WITHER_SKELETON);
		MOBS.add(EntityType.ZOMBIE);
		MOBS.add(EntityType.ZOMBIE_VILLAGER);
	}

	/**
	 * Get the normalised name for the given world. For the overworld : "Overworld", for the nether : "Nether", for the end : "Ender".
	 * 
	 * @param world The world used to get its normalised name.
	 * @return The normalised name associated to the given world. If the world is not one of the previous three world, then the
	 *         returned name is null.
	 */
	public static String getWorldNameNormalised(World world) {
		if (world == OVERWORLD)
			return OVERWORLD_NAME;
		if (world == NETHER_WORLD)
			return NETHER_WORLD_NAME;
		if (world == END_WORLD)
			return END_WORLD_NAME;
		return null;
	}

	/**
	 * Gets the world with the given name.
	 *
	 * @param name the name of the world to retrieve
	 * @return a world with the given name, or null if none exists
	 * 
	 * @see BukkitManager#getWorld(String)
	 */
	public static World getWorld(String name) {
		switch (name) {
		case OVERWORLD_NAME:
			return OVERWORLD;
		case NETHER_WORLD_NAME:
			return NETHER_WORLD;
		case END_WORLD_NAME:
			return END_WORLD;
		default:
			return BukkitManager.getWorld(name);
		}
	}

	/**
	 * @return A list of worlds that contains the {@link #OVERWORLD}, {@link #NETHER_WORLD} and {@link #END_WORLD}
	 */
	public static List<World> getWorlds() {
		List<World> worlds = new ArrayList<World>();
		worlds.add(OVERWORLD);
		worlds.add(NETHER_WORLD);
		worlds.add(END_WORLD);
		return worlds;
	}

	/**
	 * @return A list of world's name that contains the {@link #OVERWORLD_NAME}, {@link #NETHER_WORLD_NAME} and
	 *         {@link #END_WORLD_NAME}
	 */
	public static List<String> getWorldNormalisedNames() {
		List<String> worlds = new ArrayList<String>();
		worlds.add(OVERWORLD_NAME);
		worlds.add(NETHER_WORLD_NAME);
		worlds.add(END_WORLD_NAME);
		return worlds;
	}

	/**
	 * Enable or not the pvp in the specified world.
	 * 
	 * @param world The world in which the pvp is enabled or not.
	 * @param pvp   True if the pvp is enabled, false otherwise.
	 */
	public static void setPVP(World world, boolean pvp) {
		world.setPVP(pvp);
	}

	/**
	 * This method call {@link #setPVP(World, boolean)} with world equals {@link #OVERWORLD}
	 * 
	 * @param pvp True if the pvp is enabled, false otherwise.
	 */
	public static void setPVPInOverworld(boolean pvp) {
		setPVP(OVERWORLD, pvp);
	}

	/**
	 * This method call {@link #setPVP(World, boolean)} with world equals {@link #NETHER_WORLD}
	 * 
	 * @param pvp True if the pvp is enabled, false otherwise.
	 */
	public static void setPVPInNether(boolean pvp) {
		setPVP(NETHER_WORLD, pvp);
	}

	/**
	 * This method call {@link #setPVP(World, boolean)} with world equals {@link #END_WORLD}
	 * 
	 * @param pvp True if the pvp is enabled, false otherwise.
	 */
	public static void setPVPInEnder(boolean pvp) {
		setPVP(END_WORLD, pvp);
	}

	/**
	 * Constructs a new Location with the given coordinates.
	 *
	 * @param world The world in which this location resides.
	 * @param x     The x-coordinate of this new location.
	 * @param y     The y-coordinate of this new location.
	 * @param z     The z-coordinate of this new location.
	 */
	public static Location createLocation(final World world, final double x, final double y, final double z) {
		return new Location(world, x, y, z);
	}

	/**
	 * Constructs a new Location with the given coordinates. This method call {@link #createLocation(World, double, double, double)}
	 * with world equals {@link #OVERWORLD}.
	 *
	 * @param x The x-coordinate of this new location.
	 * @param y The y-coordinate of this new location.
	 * @param z The z-coordinate of this new location.
	 */
	public static Location locationFromOverworld(int x, int y, int z) {
		return createLocation(OVERWORLD, x, y, z);
	}

	/**
	 * Constructs a new Location with the given coordinates. This method call {@link #createLocation(World, double, double, double)}
	 * with world equals {@link #NETHER_WORLD}.
	 *
	 * @param x The x-coordinate of this new location.
	 * @param y The y-coordinate of this new location.
	 * @param z The z-coordinate of this new location.
	 */
	public static Location locationFromNether(int x, int y, int z) {
		return createLocation(NETHER_WORLD, x, y, z);
	}

	/**
	 * Constructs a new Location with the given coordinates. This method call {@link #createLocation(World, double, double, double)}
	 * with world equals {@link #END_WORLD}.
	 *
	 * @param x The x-coordinate of this new location.
	 * @param y The y-coordinate of this new location.
	 * @param z The z-coordinate of this new location.
	 */
	public static Location locationFromEnder(int x, int y, int z) {
		return createLocation(END_WORLD, x, y, z);
	}

	/**
	 * Gets the {@link Block} in the specified world at the given coordinates.
	 * 
	 * @param world The world in which the research is made.
	 * @param x     X-coordinate of the block.
	 * @param y     Y-coordinate of the block.
	 * @param z     Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAt(World word, int x, int y, int z) {
		return word.getBlockAt(x, y, z);
	}

	/**
	 * Gets the {@link Block} at the given coordinates in the world associated to the given world name.
	 * 
	 * @param worldName The name of the world.
	 * @param x         X-coordinate of the block.
	 * @param y         Y-coordinate of the block.
	 * @param z         Z-coordinate of the block.
	 * 
	 * @see #getBlockAt(World, int, int, int)
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAt(String worldName, int x, int y, int z) {
		return getBlockAt(getWorld(worldName), x, y, z);
	}

	/**
	 * Gets the {@link Block} in the surface world at the given coordinates using {@link #getBlockAt(World, int, int, int)} with world
	 * equals {@link #OVERWORLD}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromOverworld(int x, int y, int z) {
		return getBlockAt(OVERWORLD, x, y, z);
	}

	/**
	 * Gets the {@link Block} in the nether world at the given coordinates using {@link #getBlockAt(World, int, int, int)} with world
	 * equals {@link #NETHER_WORLD}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromNether(int x, int y, int z) {
		return getBlockAt(NETHER_WORLD, x, y, z);
	}

	/**
	 * Gets the {@link Block} in the end world at the given coordinates using {@link #getBlockAt(World, int, int, int)} with world
	 * equals {@link #END_WORLD}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromEnder(int x, int y, int z) {
		return getBlockAt(END_WORLD, x, y, z);
	}

	/**
	 * Gets the {@link Block} in the specified world at the given {@link Location}.
	 *
	 * @param location Location of the block.
	 * 
	 * @return Block at the given location.
	 */
	public static Block getBlockAt(String world, Location location) {
		return getWorld(world).getBlockAt(location);
	}

	/**
	 * Gets the {@link Block} in the surface world at the given coordinates using {@link #getBlockAt(String, Location)} with world
	 * equals {@link #OVERWORLD_NAME}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromOverworld(Location location) {
		return getBlockAt(OVERWORLD_NAME, location);
	}

	/**
	 * Gets the {@link Block} in the nether world at the given coordinates using {@link #getBlockAt(String, Location)} with world
	 * equals {@link #NETHER_WORLD_NAME}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromNether(Location location) {
		return getBlockAt(NETHER_WORLD_NAME, location);
	}

	/**
	 * Gets the {@link Block} in the end world at the given coordinates using {@link #getBlockAt(String, Location)} with world equals
	 * {@link #END_WORLD_NAME}.
	 *
	 * @param x X-coordinate of the block.
	 * @param y Y-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Block at the given coordinates.
	 */
	public static Block getBlockAtFromEnder(Location location) {
		return getBlockAt(END_WORLD_NAME, location);
	}

	/**
	 * For the specified world, this method gets the lowest block at the given coordinates such that the block and all blocks above it
	 * are transparent for lighting purposes.
	 * 
	 * @param World The world to look for.
	 * @param x     X-coordinate of the block.
	 * @param z     Z-coordinate of the block.
	 * 
	 * @return Highest non-empty block.
	 */
	public static Block getHighestBlockYAt(World world, int x, int z) {
		return world.getHighestBlockAt(x, z);
	}

	/**
	 * Call the method {@link #getHighestBlockYAt(World, int, int)} with world equals {@link #OVERWORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Highest non-empty block.
	 */
	public static Block getFromOverworldHighestBlockYAt(int x, int z) {
		return getHighestBlockYAt(OVERWORLD, x, z);
	}

	/**
	 * Call the method {@link #getHighestBlockYAt(World, int, int)} with world equals {@link #NETHER_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Highest non-empty block.
	 */
	public static Block getFromNetherHighestBlockYAt(int x, int z) {
		return getHighestBlockYAt(NETHER_WORLD, x, z);
	}

	/**
	 * Call the method {@link #getHighestBlockYAt(World, int, int)} with world equals {@link #END_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 * 
	 * @return Highest non-empty block.
	 */
	public static Block getFromEnderHighestBlockYAt(int x, int z) {
		return getHighestBlockYAt(END_WORLD, x, z);
	}

	/**
	 * Return the block just below the given block. This method is equivalent to :
	 * <p>
	 * <code>block.getLocation().clone().add(new Vector(0, -1, 0)).getBlock();</code>
	 * 
	 * @param block The block used to get the block below.
	 * 
	 * @return The block just below the given block.
	 */
	public static Block getBelowBlock(Block block) {
		return block.getLocation().clone().add(new Vector(0, -1, 0)).getBlock();
	}

	/**
	 * Check if the {@link Material} of the given block is the specified material.
	 * 
	 * @param block    The block to check.
	 * @param material The material used for reference.
	 * 
	 * @return block.getType().equals(material);
	 */
	public static boolean isBlockTypeOf(Block block, Material material) {
		return block.getType().equals(material);
	}

	/**
	 * Check if the array of {@link Material} contains the material of the given block.
	 * 
	 * @param block     The block to check.
	 * @param materials An array of material used to test the previous block.
	 * @return True if the array contains the material of the block, false otherwise.
	 */
	public static boolean isBlockTypeOf(Block block, Material... materials) {
		for (Material material : materials)
			if (isBlockTypeOf(block, material))
				return true;
		return false;
	}

	/**
	 * Set the center of the border associated to the given world.
	 * 
	 * @param world The world which the border is associated with.
	 * @param x     X-coordinate of the block
	 * @param z     Z-coordinate of the block
	 */
	public static void setWorldBorderCenter(World world, int x, int z) {
		world.getWorldBorder().setCenter(x, z);
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, int, int)} with world equals {@link #OVERWORLD}
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setOverworldBorderCenter(int x, int z) {
		setWorldBorderCenter(OVERWORLD, x, z);
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, int, int)} with world equals {@link #NETHER_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setNetherBorderCenter(int x, int z) {
		setWorldBorderCenter(NETHER_WORLD, x, z);
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, int, int)} with world equals {@link #END_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setEnderBorderCenter(int x, int z) {
		setWorldBorderCenter(END_WORLD, x, z);
	}

	/**
	 * Set the center of the border associated to the given world.
	 * 
	 * @param world The world which the border is associated with.
	 * @param x     X-coordinate of the block.
	 * @param z     Z-coordinate of the block.
	 */
	public static void setWorldBorderCenter(World world, Block block) {
		world.getWorldBorder().setCenter(block.getLocation());
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, Block)} with world equals {@link #OVERWORLD}
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setOverworldBorderCenter(Block block) {
		setWorldBorderCenter(OVERWORLD, block);
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, Block)} with world equals {@link #NETHER_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setNetherBorderCenter(Block block) {
		setWorldBorderCenter(NETHER_WORLD, block);
	}

	/**
	 * Call the method {@link #setWorldBorderCenter(World, Block)} with world equals {@link #END_WORLD}.
	 * 
	 * @param x X-coordinate of the block.
	 * @param z Z-coordinate of the block.
	 */
	public static void setEnderBorderCenter(Block block) {
		setWorldBorderCenter(END_WORLD, block);
	}

	/**
	 * Set the diameter of the border associated to the given world.
	 * 
	 * @param world    The world which the border is associated with.
	 * @param diameter The diameter of the border in the specified world.
	 */
	public static void setWorldBorderDiameter(World world, double diameter) {
		world.getWorldBorder().setSize(diameter);
	}

	/**
	 * Call the method {@link #setWorldBorderDiamter(World, double)} with world equals {@link #OVERWORLD}
	 * 
	 * @param diameter The diameter of the border in the surface world.
	 */
	public static void setOverworldBorderDiameter(double diameter) {
		setWorldBorderDiameter(OVERWORLD, diameter);
	}

	/**
	 * Call the method {@link #setWorldBorderDiamter(World, double)} with world equals {@link #NETHER_WORLD}
	 * 
	 * @param diameter The diameter of the border in the nether world.
	 */
	public static void setNetherBorderDiameter(double diameter) {
		setWorldBorderDiameter(NETHER_WORLD, diameter);
	}

	/**
	 * Call the method {@link #setWorldBorderDiamter(World, double)} with world equals {@link #END_WORLD}
	 * 
	 * @param diameter The diameter of the border in the end world.
	 */
	public static void setEnderBorderDiameter(double diameter) {
		setWorldBorderDiameter(END_WORLD, diameter);
	}

	/**
	 * Move the border associated to the given world from its current size (diameter) to the given diameter.
	 * 
	 * @param world         the world which the border is associated with.
	 * @param finalDiameter The new side length of the border.
	 * @param seconds       The time in seconds in which the border grows or shrinks from the previous size to that being set.
	 */
	public static void moveBorder(World world, double finalDiameter, long seconds) {
		world.getWorldBorder().setSize(finalDiameter, seconds);
	}

	/**
	 * Get the current diameter of the border associated to the given world.
	 * 
	 * @param world The world which the border is associated with.
	 * 
	 * @see WorldBorder#getSize()
	 * 
	 * @return The current size of the border.
	 */
	public static Double getCurrentDiameter(World world) {
		return world.getWorldBorder().getSize();
	}

	/**
	 * Stop moving (growing or shrinking) the border associated to the given world.
	 * 
	 * @param world The world which the border is associated with.
	 */
	public static void stopBorder(World world) {
		world.getWorldBorder().setSize(getCurrentDiameter(world));
	}

	/**
	 * Reset the size of the border associated to the given world.
	 * 
	 * @param world The world which the border is associated with.
	 */
	public static void removeBorder(World world) {
		world.getWorldBorder().reset();
	}

	/**
	 * Set a game rule associated to the given world.
	 * 
	 * @param <T>      the value type of the GameRule.
	 * 
	 * @param world    The world in which the game rule is applied.
	 * @param gameRule the GameRule to update.
	 * @param value    the new value.
	 */
	public static <T> void setGameRule(World world, GameRule<T> gameRule, T value) {
		world.setGameRule(gameRule, value);
	}

	/**
	 * Sets the relative in-game time on the server.
	 * <p>
	 * The relative time is analogous to hours * 1000.
	 * <p>
	 * Note that setting the relative time below the current relative time will actually move the clock forward a day. If you require
	 * to rewind time, please see {@link #setFullTime(long)}.
	 * 
	 * @param world The world in which the time should be changed.
	 * @param time  The new relative time to set the in-game time to (in hours*1000).
	 * 
	 * @see World#setFullTime(long) Sets the absolute time of the given world.
	 */
	public static void setTime(World world, long time) {
		world.setTime(time);
	}

	/**
	 * Sets the in-game time on the server.
	 * <p>
	 * Note that this sets the full time of the world, which may cause adverse effects such as breaking redstone clocks and any
	 * scheduled events.
	 *
	 * @param world The world in which the time should be changed.
	 * @param time  The new absolute time to set this world to.
	 * 
	 * @see #setTime(long) Sets the relative time of this world.
	 */
	public static void setFullTime(World world, long time) {
		world.setFullTime(time);
	}

	/**
	 * Set the remaining time in ticks of the current conditions.
	 *
	 * @param world    The world in which weather conditions will change.
	 * @param duration Time in ticks.
	 */
	public static void setWeatherDuration(World world, int duration) {
		world.setWeatherDuration(duration);
	}

	/**
	 * Set whether it is thundering.
	 * 
	 * @param World      The world in which weather changes are applied.
	 * @param thundering Whether it is thundering.
	 */
	public static void setThundering(World world, boolean thundering) {
		world.setThundering(thundering);
	}

	/**
	 * Set whether there is a storm. A duration will be set for the new current conditions.
	 * 
	 * @param world    The world in which weather changes are applied.
	 * @param hasStorm Whether there is rain and snow.
	 */
	public static void setStorm(World world, boolean hasStorm) {
		world.setStorm(hasStorm);
	}

	/**
	 * Sets the spawn location of the given world. <br>
	 * The location provided must be equal to the specified world.
	 *
	 * @param world    The world in which the spawn location should be setted.
	 * @param location The {@link Location} to set the spawn for this world at.
	 */
	public static void setSpawnLocation(World world, Location location) {
		world.setSpawnLocation(location);
	}

	/**
	 * Gets the default spawn {@link Location} of this world.
	 *
	 * @return The spawn location of this world.
	 */
	public static Location getSpawnLocation(World world) {
		return world.getSpawnLocation();
	}

	/**
	 * Define two random coordinates x and z in range </br>
	 * <code>minX = center.getX() - bound / 2 + 1;</code></br>
	 * <code>minZ = minX - center.getX() + center.getZ();</code></br>
	 * <code>maxX = bound / 2 - 1 + center.getX();</code></br>
	 * <code>maxZ = maxX - center.getX() + center.getZ();</code></br>
	 * 
	 * @param world  The world which the location is associated with.
	 * @param center The center used to be sure the random location is inside the area represented by the center and the bound.
	 * @param bound  The upper bound used for {@link Random}. Must be positive.
	 * 
	 * @return A random location associated to the given world.
	 */
	public static Location getRandomlyLocation(World world, Block center, int bound) {
		int minX = center.getX() - bound / 2 + 1;
		int minZ = minX - center.getX() + center.getZ();
		int maxX = bound / 2 - 1 + center.getX();
		int maxZ = maxX - center.getX() + center.getZ();
		int randomX = 0, randomZ = 0;
		Random rand = new Random();

		do {
			randomX = rand.nextInt(maxX - minX) + minX;
			randomZ = rand.nextInt(maxZ - minZ) + minZ;
		} while (isBlockTypeOf(getBelowBlock(getHighestBlockYAt(world, randomX, randomZ)), Material.WATER, Material.LAVA));
		return getHighestBlockYAt(world, randomX, randomZ).getLocation();
	}

	/**
	 * Define two random coordinates x and z in range </br>
	 * <code>minX = center.getX() - bound / 2 + 1;</code></br>
	 * <code>minZ = minX - center.getX() + center.getZ();</code></br>
	 * <code>maxX = bound / 2 - 1 + center.getX();</code></br>
	 * <code>maxZ = maxX - center.getX() + center.getZ();</code></br>
	 * 
	 * @param center The center used to be sure the random location is inside the area represented by the center and the bound.
	 * @param bound  The upper bound used for {@link Random}. Must be positive.
	 * 
	 * @return A random location in the overworld.
	 * 
	 * @see #getRandomlyLocation(World, Block, int)
	 */
	public static Location getRandomlyLocationInOverworld(Block center, int bound) {
		return getRandomlyLocation(OVERWORLD, center, bound);
	}

	/**
	 * Define two random coordinates x and z in range </br>
	 * <code>minX = center.getX() - bound / 2 + 1;</code></br>
	 * <code>minZ = minX - center.getX() + center.getZ();</code></br>
	 * <code>maxX = bound / 2 - 1 + center.getX();</code></br>
	 * <code>maxZ = maxX - center.getX() + center.getZ();</code></br>
	 * 
	 * @param center The center used to be sure the random location is inside the area represented by the center and the bound.
	 * @param bound  The upper bound used for {@link Random}. Must be positive.
	 * 
	 * @return A random location in the nether.
	 * 
	 * @see #getRandomlyLocation(World, Block, int)
	 */
	public static Location getRandomlyLocationInNether(Block center, int bound) {
		return getRandomlyLocation(NETHER_WORLD, center, bound);
	}

	/**
	 * Define two random coordinates x and z in range </br>
	 * <code>minX = center.getX() - bound / 2 + 1;</code></br>
	 * <code>minZ = minX - center.getX() + center.getZ();</code></br>
	 * <code>maxX = bound / 2 - 1 + center.getX();</code></br>
	 * <code>maxZ = maxX - center.getX() + center.getZ();</code></br>
	 * 
	 * @param center The center used to be sure the random location is inside the area represented by the center and the bound.
	 * @param bound  The upper bound used for {@link Random}. Must be positive.
	 * 
	 * @return A random location in the ender.
	 * 
	 * @see #getRandomlyLocation(World, Block, int)
	 */
	public static Location getRandomlyLocationInEnd(Block center, int bound) {
		return getRandomlyLocation(END_WORLD, center, bound);
	}

	/**
	 * Get a stream that contains all players in the given world.
	 * 
	 * @param world The used used to get its players.
	 * 
	 * @see World#getPlayers()
	 * 
	 * @return a stream that contains players currently residing in the specified world.
	 */
	public static Stream<Player> getPlayerInWorld(World world) {
		return world.getPlayers().stream();
	}

	/**
	 * @return A stream that contains all players currently in the surface world.
	 */
	public static Stream<Player> getPlayersInOverworld() {
		return getPlayerInWorld(OVERWORLD);
	}

	/**
	 * @return A stream that contains all players currently in the nether world.
	 */
	public static Stream<Player> getPlayersInNether() {
		return getPlayerInWorld(NETHER_WORLD);
	}

	/**
	 * @return A stream that contains all players currently in the end world.
	 */
	public static Stream<Player> getPlayersInEnder() {
		return getPlayerInWorld(END_WORLD);
	}

	/**
	 * Get players currently in the given worlds.
	 * 
	 * @param worlds All words to check.
	 * @return A stream that contains all players in the specified worlds.
	 */
	public static Stream<Player> getPlayersInWorld(World... worlds) {
		List<Player> players = new ArrayList<Player>();
		for (World world : worlds)
			players.addAll(world.getPlayers());
		return players.stream();
	}

	/**
	 * Get the distance between two location by comparing only one coordinate.
	 * 
	 * @param from The location used as reference.
	 * @param to   The movable location.
	 * @param axis The axis used to compare the associated coordinate.
	 * @return The distance.
	 */
	public static double getDistance1D(Location from, Location to, Axis axis) {
		checkLocation(from, to);
		switch (axis) {
		case X:
			return from.getX() - to.getX();
		case Y:
			return from.getY() - to.getY();
		default:
			return from.getZ() - to.getZ();
		}
	}

	/**
	 * Get the distance between this location and another. The value of this method is not cached and uses a costly square-root
	 * function, so do not repeatedly call this method to get the location's magnitude. NaN will be returned if the inner result of
	 * the sqrt() function overflows, which will be caused if the distance is too long.
	 *
	 * @param from The location used as reference.
	 * @param to   The movable location.
	 * @throws IllegalArgumentException for differing worlds
	 * 
	 * @see #getScaredDistance2D(Location, Location)
	 */
	public static double getDistance2D(Location from, Location to) {
		return Math.sqrt(getSquaredDistance2D(from, to));
	}

	/**
	 * Get the squared distance between this location and another by comparing only their x and z coordinates.
	 *
	 * @param from The location used as reference.
	 * @param to   The movable location.
	 * @return the distance.
	 * 
	 * @throws IllegalArgumentException for differing worlds
	 * 
	 * @see Vector
	 */
	public static double getSquaredDistance2D(Location from, Location to) {
		try {
			checkLocation(from, to);
			return NumberConversions.square(from.getX() - to.getX()) + NumberConversions.square(from.getZ() - to.getZ());
		} catch (IllegalArgumentException e) {
			return Double.NaN;
		}
	}

	/**
	 * Returns whether the location vector is in an axis-aligned bounding surface X and Z.
	 * 
	 * @param location The location to check.
	 * @param from     The minimum location.
	 * @param to       The maximum location.
	 * 
	 * @return True if the location is in the AABB.
	 */
	public static boolean isInAABB2D(Location location, Location from, Location to) {
		return from.getX() <= location.getX() && location.getX() <= to.getX() && from.getZ() <= location.getZ() && location.getZ() <= to.getZ();
	}

	public static double getYaw(Player player, Location location) {
		int xrel = location.getBlockX() - player.getLocation().getBlockX();
		int zrel = location.getBlockZ() - player.getLocation().getBlockZ();
		float thetap1p2 = (float) Math.toDegrees(Math.atan2(xrel, zrel));
		double yawP1 = player.getLocation().getYaw();

		// Bring back yawP1 between [-180;180]
		yawP1 = yawP1 > 180 ? yawP1 - 360 : yawP1 < -180 ? yawP1 + 360 : yawP1;

		// Bring back thetap1p2 + yawP1 between [-180;180]
		return thetap1p2 + yawP1 > 180 ? thetap1p2 + yawP1 - 360 : thetap1p2 + yawP1 < -180 ? thetap1p2 + yawP1 + 360 : thetap1p2 + yawP1;
	}

	private static void checkLocation(Location from, Location to) {
		if (from == null || to == null) {
			throw new IllegalArgumentException("Cannot measure distance to a null location");
		} else if (from.getWorld() == null || to.getWorld() == null) {
			throw new IllegalArgumentException("Cannot measure distance to a null world");
		} else if (from.getWorld() != to.getWorld()) {
			throw new IllegalArgumentException("Cannot measure distance between " + from.getWorld().getName() + " and " + to.getWorld().getName());
		}
	}
}
