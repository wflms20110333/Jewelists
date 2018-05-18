package data;

/**
 * The Status enum represents different status effects.
 * 
 * @author An Nguyen
 * Dependencies: slick(to manage textures)
 * @author Collin McMahon
 */
public enum Status
{
	/**
	 * Speeds up the movement of the sprite.
	 */
	SPEED(2f),
	
	/**
	 * Allows the sprite to collect any jewels in any surrounding cells.
	 */
	MAGNET(3),
	
	/**
	 * Slows down the movement of the sprite.
	 */
	SLOW(.5f),
	
	/**
	 * Inhibits any movement of the sprite.
	 */
	STUN,
	
	/**
	 * Increases the damage caused by the sprite's attacks.
	 */
	DMG_BOOST(2f),
	
	/**
	 * Gradually decrease a person's health
	 */
	POISON(.7f);
	
	/**
	 * A multiplier used for many status effects. For example, it would be a
	 * speed multiplier for speed, while for something like a stun, this should
	 * be set to -1 (invalid).
	 */
	private final float multiplier;
	
	/**
	 * Constructs a Status without a multiplier.
	 */
	private Status()
	{
		this(-1);
	}
	
	/**
	 * Constructs a Status with a given multiplier.
	 * 
	 * @param multiplier the given multiplier
	 */
	private Status(float multiplier)
	{
		this.multiplier = multiplier;
	}
	
	/**
	 * Returns the multiplier of the status.
	 * 
	 * @return the multiplier of the status
	 */
	public float getMultiplier()
	{
		return multiplier;
	}
}
