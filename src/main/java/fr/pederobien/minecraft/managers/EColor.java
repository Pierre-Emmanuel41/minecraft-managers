package fr.pederobien.minecraft.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

public enum EColor {
	AQUA(ChatColor.AQUA, "aqua"), DARK_AQUA(ChatColor.DARK_AQUA, "dark_aqua"), BLUE(ChatColor.BLUE, "blue"), DARK_BLUE(ChatColor.DARK_BLUE, "dark_blue"),
	BLACK(ChatColor.BLACK, "black"), GRAY(ChatColor.GRAY, "gray"), DARK_GRAY(ChatColor.DARK_GRAY, "dark_gray"), GREEN(ChatColor.GREEN, "green"),
	DARK_GREEN(ChatColor.DARK_GREEN, "dark_green"), RED(ChatColor.RED, "red"), DARK_RED(ChatColor.DARK_RED, "dark_red"), GOLD(ChatColor.GOLD, "gold"),
	YELLOW(ChatColor.YELLOW, "yellow"), PINK(ChatColor.LIGHT_PURPLE, "light_purple"), PURPLE(ChatColor.DARK_PURPLE, "dark_purple"), WHITE(ChatColor.WHITE, "white"),
	RESET(ChatColor.RESET, "reset");

	private static HashMap<ChatColor, EColor> mapColor = new HashMap<ChatColor, EColor>();
	private static HashMap<String, EColor> mapColorName = new HashMap<String, EColor>();
	private static List<String> colorsName = new ArrayList<String>();
	private ChatColor color;
	private String name;

	static {
		for (EColor color : values()) {
			mapColorName.put(color.toString(), color);
			colorsName.add(color.toString());
			mapColor.put(color.getChatColor(), color);
		}
	}

	/**
	 * Get the EColor corresponding to the given ChatColor.
	 * 
	 * @param color The color used as key to get the associated EColor.
	 * 
	 * @return The associated EColor.
	 */
	public static EColor getByColor(ChatColor color) {
		return mapColor.get(color);
	}

	/**
	 * Get the EColor corresponding to the given ChatColor.
	 * 
	 * @param colorName The name of the color used as key to get the associated EColor.
	 * 
	 * @return The associated EColor.
	 */
	public static EColor getByColorName(String colorName) {
		return mapColorName.get(colorName);
	}

	/**
	 * @return A list of each EColor's name.
	 */
	public static List<String> getColorsName() {
		return colorsName;
	}

	private EColor(ChatColor color, String name) {
		this.color = color;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Get a new String based on the given string. The initial string is not modified.
	 * 
	 * @param string The used to be shown in this EColor.
	 * 
	 * @return A new String equivalent to ChatColor + string + ChatColor.RESET.
	 */
	public String getInColor(String string) {
		return getInColor(string, EColor.RESET);
	}

	/**
	 * Get a new String based on the given string. The initial string is not modified.
	 * 
	 * @param string The used to be shown in this EColor.
	 * @param next   The color after to used after.
	 * 
	 * @return A new String equivalent to ChatColor + string + ChatColor.RESET.
	 */
	public String getInColor(String string, EColor next) {
		return new String(this.getChatColor() + string + next.getChatColor());
	}

	/**
	 * @return The minecraft ChatColor associated to this EColor.
	 */
	public ChatColor getChatColor() {
		return color;
	}

	/**
	 * @return {@link #getInColor(String)} with parameter string equals {@link #getName()}.
	 */
	public String getColoredColorName() {
		return getInColor(name);
	}
}