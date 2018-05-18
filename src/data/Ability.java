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
	/**
	 * Speeds up a player's movement. This is a buff.
	 */
	SPEED("ability_speed", true),
	
	/**
	 * Boosts the damages of a player's attacks. This is a buff.
	 */
	DMG_BOOST("ability_dmg_boost", true),
	
	/**
	 * Gives a player the ability to collect the jewels in adjacent cells. This
	 * is a buff.
	 */
	MAGNET("ability_magnet", true, 3),
	
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
	
	/**
	 * All the possible abilities.
	 */
	private static final Ability[] abilities = values();
	
	/**
	 * The length of the durations of buffs (in seconds).
	 */
	public static final long BUFF_DURATION = 10;
	
	/**
	 * The length of the cool downs of abilities (in seconds).
	 */
	public static final long BUFF_COOLDOWN = 10;
	
	/**
	 * The duration (in seconds) of the Ability, -1 if it is not a buff.
	 */
	private final float duration;
	
	/**
	 * The length of the cool down (in seconds) of the Ability.
	 */
	private final float cooldown;
	
	/**
	 * The value of the Ability.
	 */
	private final int value;
	
	/**
	 * The name of the Ability.
	 */
	private final String name;
	
	/**
	 * The texture of the Ability.
	 */
	private final Texture texture;
	
	/**
	 * Constructs an Ability that is not a buff.
	 * 
	 * @param name the name of the ability
	 */
	private Ability(String name)
	{
		this(name, false);
	}
	
	/**
	 * Constructs an Ability.
	 * 
	 * @param name the name of the ability
	 * @param buff whether the ability is a buff
	 */
	private Ability(String name, boolean buff)
	{
		this(name, buff, -1);
	}
	
	/**
	 * Constructs an Ability with a value.
	 * 
	 * @param name the name of the ability
	 * @param value the value of the ability
	 */
	private Ability(String name, int value)
	{
		this(name, false, value);
	}
	
	/**
	 * Constructs an Ability.
	 * 
	 * @param name the name of the ability
	 * @param buff whether the ability is a buff
	 * @param value the value of the ability
	 */
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
	
	/**
	 * Returns the duration (in seconds) of the ability.
	 * 
	 * @return the duration (in seconds) of the ability
	 */
	public float getDuration()
	{
		return duration;
	}
	
	/**
	 * Returns the length of the cool down (in seconds) of the ability.
	 * 
	 * @return the length of the cool down (in seconds) of the ability
	 */
	public float getCooldown()
	{
		return cooldown;
	}
	
	/**
	 * Returns the value of the ability.
	 * 
	 * @return the value of the ability
	 */
	public long getValue()
	{
		return value;
	}
	
	/**
	 * Returns the texture of the ability.
	 * 
	 * @return the texture of the ability
	 */
	public Texture getTexture()
	{
		return texture;
	}
	
	/**
	 * Returns a random ability.
	 * 
	 * @return a random ability
	 */
	public static Ability random()
	{
		return abilities[(int) (Math.random() * abilities.length)];
	}
	
	/**
	 * Returns the name of the ability.
	 * 
	 * @return the name of the ability
	 */
	@Override
	public String toString()
	{
		return name;
	}
}