package fr.pederobien.minecraft.managers;

import java.util.HashMap;
import java.util.Map;

import fr.pederobien.utils.Range;

public enum EArrows {
	/**
	 * Default unicode : \u2191
	 */
	TOP("\u2191"),

	/**
	 * Default unicode : \u2b08
	 */
	TOP_RIGHT("\u2b08"),

	/**
	 * Default unicode : \u2192
	 */
	RIGHT("\u2192"),

	/**
	 * Default unicode : \u2b0a
	 */
	BOTTOM_RIGHT("\u2b0a"),

	/**
	 * Default unicode : \u2193
	 */
	BOTTOM("\u2193"),

	/**
	 * Default unicode : \u2b0b
	 */
	BOTTOM_LEFT("\u2b0b"),

	/**
	 * Default unicode : \u2190
	 */
	LEFT("\u2190"),

	/**
	 * Default unicode : \u2b09
	 */
	TOP_LEFT("\u2b09");

	private static Map<Range<Double>, EArrows> map;
	private String unicode;

	private EArrows(String unicode) {
		this.unicode = unicode;
	}

	/**
	 * Return the {@link EArrows} associated to the given yaw.
	 * 
	 * @param yaw The yaw used to get the associated arrow.
	 * 
	 * @return The arrow associated to the given yaw.
	 */
	public static EArrows getArrow(double yaw) {
		for (Map.Entry<Range<Double>, EArrows> entry : map.entrySet())
			if (entry.getKey().contains(yaw))
				return entry.getValue();

		// Should never returns null
		return null;
	}

	@Override
	public String toString() {
		return "unicode=" + unicode;
	}

	/**
	 * @return The unicode used to display this arrow.
	 */
	public String getUnicode() {
		return unicode;
	}

	/**
	 * Set the unicode for this arrow.
	 * 
	 * @param unicode The new unicode of this arrow.
	 */
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}

	static {
		map = new HashMap<Range<Double>, EArrows>();
		map.put(Range.of(-22.5, 22.5), EArrows.TOP);

		// From 22.5� to 180�
		map.put(Range.of(22.5, 67.5), EArrows.TOP_LEFT);
		map.put(Range.of(67.5, 112.5), EArrows.LEFT);
		map.put(Range.of(112.5, 157.5), EArrows.BOTTOM_LEFT);
		map.put(Range.of(157.5, 181.0), EArrows.BOTTOM);

		// From -180� to -22.5�
		map.put(Range.of(-181.0, -157.5), EArrows.BOTTOM);
		map.put(Range.of(-157.5, -112.5), EArrows.BOTTOM_RIGHT);
		map.put(Range.of(-112.5, -67.5), EArrows.RIGHT);
		map.put(Range.of(-67.5, -22.5), EArrows.TOP_RIGHT);
	}
}
