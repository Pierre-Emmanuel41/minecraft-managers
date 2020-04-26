package fr.pederobien.minecraftmanagers;

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
