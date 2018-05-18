package data;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

/**
 * The Ability enum represents the different abilities that a player can
 * possess or use.
 * 
 * @author An Nguyen
 */
public enum Ability
{
	// buff
	/**
	 * Speeds up a player's movement.
	 */
	SPEED("ability_speed", true),
	
	/**
	 * Boosts the damages of a player's attacks.
	 */
	DMG_BOOST("ability_dmg_boost", true),
	
	/**
	 * Gives a player the ability to collect the jewels in adjacent cells.
	 */
	MAGNET("ability_magnet", true, 3),
	
	// activated instantly
	/**
	 * Teleports a player's sprite in its current direction.
	 */
	BLINK("ability_blink", 3),
	
	/**
	 * Increases a player's health.
	 */
	HEAL("ability_heal", 2),
	
	/**
	 * Slows down the opposing player, if they are within a certain distance
	 * from a player.
	 */
	SLOW("ability_slow", true, 3);
	
	private static final Ability[] abilities = values();
	
	public static final long BUFF_DURATION = 10;
	public static final long BUFF_COOLDOWN = 10;
	
	private final float duration;
	private final float cooldown;
	private final int value;
	private final String name;
	
	private final Texture texture;
	
	private Ability(String name)
	{
		this(name, false);
	}
	
	private Ability(String name, boolean buff)
	{
		this(name, buff, -1);
	}
	
	private Ability(String name, int value)
	{
		this(name, false, value);
	}
	
	private Ability(String name, boolean buff, int value)
	{
		if (buff)
			this.duration = BUFF_DURATION;
		else
			this.duration = -1;
		this.cooldown = BUFF_COOLDOWN;
		this.value = value;
		this.name = name;
		this.texture = Artist.quickLoad(name);
	}
	
	public float getDuration()
	{
		return duration;
	}
	
	public float getCooldown()
	{
		return cooldown;
	}
	
	public long getValue()
	{
		return value;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public static Ability random()
	{
		return abilities[(int) (Math.random() * abilities.length)];
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}