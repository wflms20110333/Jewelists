package data;

import static helpers.Clock.*;

/**
 * The Ability Manager class manages the abilities a player can possess or use.
 * 
 * @author An Nguyen
 */
public class AbilityManager
{
	/**
	 * The player whose abilities are managed.
	 */
	Player player;
	
	/**
	 * The player's current ability.
	 */
	Ability ability;
	
	/**
	 * The number of seconds that the player has been cooling down (before
	 * getting assigned a new random ability).
	 */
	float cooldownTick;
	
	/**
	 * Construct an AbilityManager, starting with a random ability.
	 * 
	 * @param player the player whose abilities are managed
	 */
	public AbilityManager(Player player)
	{
		this(player, Ability.random());
	}
	
	/**
	 * Constructs an AbilityManager with a given starting ability.
	 * 
	 * @param player the player whose abilities are managed
	 * @param ability the given starting ability
	 */
	public AbilityManager(Player player, Ability ability)
	{
		this.player = player;
		this.ability = ability;
		this.cooldownTick = -1;
	}
	
	/**
	 * Increments the cool down time if the player is currently cooling down,
	 * and assigns the player a new random ability if cool down is complete.
	 */
	public void update()
	{
		if (cooldownTick != -1)
			cooldownTick += getSeconds();
		if (cooldownTick >= ability.getCooldown())
		{
			cooldownTick = -1;
			ability = Ability.random();
		}
	}
	
	/**
	 * Activates the player's current ability.
	 */
	public void activate()
	{
		if (cooldownTick == -1)
		{
			boolean applied = false;
			if (ability == Ability.SPEED)
				applied = applySpeed();
			if (ability == Ability.DMG_BOOST)
				applied = applyDmgBoost();
			if (ability == Ability.MAGNET)
				applied = applyMagnet();
			if (ability == Ability.SLOW)
				applied = applySlow();
			if (ability == Ability.BLINK)
				applied = blink();
			if (ability == Ability.HEAL)
				applied = heal();
			
			if (applied)
				cooldownTick = 0;
		}
	}
	
	/**
	 * Returns the time (in seconds) before cool down is complete.
	 * 
	 * @return the time (in seconds) before cool down is complete
	 */
	public float getCooldownLeft()
	{
		if (cooldownTick == -1)
			return 0;
		return ability.getCooldown() - cooldownTick;
	}
	
	/**
	 * Returns the player's current ability.
	 * 
	 * @return the player's current ability
	 */
	public Ability getAbility()
	{
		return ability;
	}
	
	/**
	 * Speeds up the player's movement.
	 * 
	 * @return true
	 */
	private boolean applySpeed()
	{
		player.addStatus(Status.SPEED, ability.getDuration());
		return true;
	}
	
	/**
	 * Boosts the damages of the player's attacks.
	 * 
	 * @return true
	 */
	private boolean applyDmgBoost()
	{
		player.addStatus(Status.DMG_BOOST, ability.getDuration());
		return true;
	}
	
	/**
	 * Gives the player the ability to collect the jewels in adjacent cells.
	 * 
	 * @return true
	 */
	private boolean applyMagnet()
	{
		player.addStatus(Status.MAGNET, ability.getDuration());
		return true;
	}
	
	/**
	 * Teleports the player's sprite in its current direction.
	 * 
	 * @return whether the teleportation was successful
	 */
	private boolean blink()
	{
		Sprite sprite = player.getSprite();
		char direction = sprite.getFacingDirection();
		Tile thisTile = sprite.getCurrentTile();
		TileGrid grid = sprite.getGrid();
		
		for (int k = 0; k < TileGrid.order.length; k++)
		{
			if (direction == TileGrid.order[k])
			{
				int nextX = thisTile.getIndX() + TileGrid.changeX[k] * (int) ability.getValue();
				int nextY = thisTile.getIndY() + TileGrid.changeY[k] * (int) ability.getValue();
				if (grid.canEnter(nextX, nextY)) {
					Tile nextTile = sprite.getGrid().getTile(nextX, nextY);
					player.getSprite().blink(nextTile);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Increases the player's health.
	 * 
	 * @return true
	 */
	private boolean heal()
	{
		player.heal((int) ability.getValue());
		return true;
	}
	
	/**
	 * Slows down the opposing player, if they are within a certain distance
	 * from the player.
	 * 
	 * @return true
	 */
	private boolean applySlow()
	{
		Sprite sprite = player.getSprite();
		Tile thisTile = sprite.getCurrentTile();
		
		int radius = (int) ability.getValue();
		// apply slow in a radius
		for (Player other : player.getGame().getPlayers())
		{
			if (other == player)
				continue;
			Tile theirTile = other.getSprite().getCurrentTile();
			if (Math.abs(thisTile.getIndX() - theirTile.getIndX()) <= radius &&
				Math.abs(thisTile.getIndY() - theirTile.getIndY()) <= radius)
					other.addStatus(Status.SLOW, ability.getDuration());
		}
		
		return true;
	}
}
