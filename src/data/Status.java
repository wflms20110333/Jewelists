package data;

public enum Status {
	SPEED(2f), MAGNET, SLOW(.2f), STUN, DMG_BOOST(2f);
	
	// Multiplier used for many status effects
	// with speed, it would be speed multiplier. With something like a stun, this should be set to -1
	private final float multiplier;
	
	private Status() {
		this(-1);
	}
	
	private Status(float multiplier) {
		this.multiplier = multiplier;
	}
	
	public float getMultiplier() {
		return multiplier;
	}
}
