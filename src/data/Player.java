package data;

import static helpers.Artist.*;
import static helpers.Clock.getSeconds;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

/**
 * The Player class manages the assets and properties of a player in the game.
 * 
 * @author Elizabeth Zou
 * @author An Nguyen
 */
public class Player
{
	/**
	 * The default health of a Player.
	 */
	private static final int DEFAULT_HEALTH = 10;
	
	/**
	 * The costs to build and destroy a wall.
	 */
	private static final int WALL_COST = 4;
	
	/**
	 * The cost to build a trap.
	 */
	private static final int TRAP_COST = 4;
	
	/**
	 * The cool down period (in seconds) between firing two projectiles.
	 */
	private static final float COOLDOWN_PER_ATTACK = .4f;
	
	/**
	 * The time (in seconds) after which a dead sprite respawns.
	 */
	private static final int RESPAWN_TIME = 5;
	
	/**
	 * The reload speed for being able to fire more projectiles.
	 */
	private static final float RELOAD_SPEED = .5f;
	
	/**
	 * The maximum number of projectiles the Player can fire in a row.
	 */
	private static final int MAX_BULLET = 8;
	
	/**
	 * The game that the Player interacts with.
	 */
	private Game game;
	
	/**
	 * The grid of the game that the Player interacts with.
	 */
	private TileGrid grid;
	
	/**
	 * The ability manager that manages the Player's abilities.
	 */
	private AbilityManager abilityManager;
	
	/**
	 * The keyboard commands of the Player.
	 */
	private int[] keys;
	
	/**
	 * The maximum health of the Player.
	 */
	private float maxHealth;
	
	/**
	 * The current health of the Player.
	 */
	private float health;
	
	/**
	 * The sprite of the Player.
	 */
	private Sprite sprite;
	
	/**
	 * The number of jewels the Player possesses.
	 */
	private int jewels;
	
	/**
	 * The current score of the Player.
	 */
	private float score;
	
	/**
	 * The status manager that manages the statuses of effects.
	 */
	private StatusManager statuses;
	
	/**
	 * The Player's color, which is used to display information such as the
	 * player's health and score.
	 */
	private Color color;
	
	/**
	 * The color of the Player's projectiles.
	 */
	private String projectileColor;
	
	/**
	 * The time (in seconds) before the Player can fire another projectile.
	 */
	private float timeUntilAttack;
	
	/**
	 * Whether the Player has been killed.
	 */
	private boolean dead;
	
	/**
	 * The remaining number of bullets the Player can fire.
	 */
	private float bullets;
	
	/**
	 * Constructs a Player.
	 * 
	 * @param game the game that the player interacts with
	 * @param grid the grid of the game that the player interacts with
	 * @param keys the keyboard commands of the player
	 * @param texture the texture of the sprite of the player
	 * @param color the player's color
	 * @param projetileColor the color of the player's projectiles
	 */
	public Player(Game game, TileGrid grid, int[] keys, Texture texture, Color color, String projectileColor)
	{
		this.game = game;
		this.grid = grid;
		this.color = color;
		this.keys = new int[keys.length];
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
		this.projectileColor = projectileColor;
		Tile tile = grid.randEmptyTile();
		score = 1;
		health = maxHealth = DEFAULT_HEALTH;
		sprite = new Sprite(texture, tile, grid, this);
		jewels = 0;
		statuses = new StatusManager();
		abilityManager = new AbilityManager(this);
		dead = false;
	}
	
	/**
	 * Returns the sprite of the player.
	 * 
	 * @return the sprite of the player
	 */
	public Sprite getSprite()
	{
		return sprite;
	}
	
	/**
	 * Returns the health percentage of the player.
	 * 
	 * @return the health percentage of the player
	 */
	public float getPercent()
	{
		return health / maxHealth;
	}
	
	/**
	 * Returns the player's color.
	 * 
	 * @return the player's color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Returns the health of the player.
	 * 
	 * @return the health of the player
	 */
	public float getHealth()
	{
		return health;
	}
	
	/**
	 * Changes the player's health by a given value.
	 * 
	 * @param heal the given value
	 */
	public void heal(float heal)
	{
		this.health += heal;
		if (health > maxHealth)
			health = maxHealth;
		if (health < 0)
			health = 0;
	}
	
	/**
	 * Returns the maximum health of the player.
	 * 
	 * @return the maximum health of the player
	 */
	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	/**
	 * Sets the maximum health of the player.
	 * 
	 * @param maxhealth the new maximum health of the player
	 */
	public void setMaxhealth(int maxhealth)
	{
		this.maxHealth = maxhealth;
	}
	
	/**
	 * Adds a status effect.
	 * 
	 * @param effect the given effect
	 * @param seconds the duration of the effect
	 */
	public void addStatus(Status effect, float seconds)
	{
		statuses.addStatus(effect, seconds);
	}
	
	/**
	 * Returns whether a given effect is active.
	 * 
	 * @param effect the given effect
	 * @return whether the given effect is active
	 */
	public boolean statusActive(Status effect)
	{
		return statuses.statusActive(effect);
	}
	
	/**
	 * Returns the score of the player.
	 * 
	 * @return the score of the player
	 */
	public float getScore()
	{
		return score;
	}
	
	/**
	 * Adds a given value to the player's score.
	 * 
	 * @param score the given value
	 */
	public void addScore(float score)
	{
		this.score += score;
	}
	
	/**
	 * Adds a given number of jewels to the number of jewels the player possesses
	 * 
	 * @param number the given number of jewels
	 */
	public void addJewel(int number)
	{
		this.jewels += number;
	}
	
	/**
	 * Updates the status of the player. This includes updating the assets of
	 * the player and checking for keyboard commands.
	 */
	public void update()
	{
		if (dead)
		{
			heal(maxHealth * Clock.getSeconds() / RESPAWN_TIME);
			if (health == maxHealth) {
				dead = false;
				sprite.toggleVisibility();
				sprite.setCurrentTile(grid.randEmptyTile());
				grid.setOccupied(sprite.getCurrentTile(), sprite);
			}
		}
		if (!dead && health <= 0)
		{
			dead = true;
			sprite.kill();
			sprite.toggleVisibility();
		}
		
		reload();
		
		if (statusActive(Status.POISON))
			heal(-Status.POISON.getMultiplier() * getSeconds());
		
		Keyboard.next();
		
		if (!statusActive(Status.STUN) && !dead) {
			char[] updates = new char[] {'U', 'L', 'D', 'R'};
			// priority - attack, movement, setting walls, setting traps
			if (Keyboard.isKeyDown(keys[4]) && Keyboard.getEventKeyState())
				attack();
			for (int i = 0; i < updates.length; i++)
				if (Keyboard.isKeyDown(keys[i]) && Keyboard.getEventKeyState())
					sprite.updatePath(updates[i]);
			if (Keyboard.isKeyDown(keys[5]) && Keyboard.getEventKeyState())
			{
				Tile tile = sprite.getCurrentTile();
				if (tile.getType() == TileType.Cave && spendJewels(WALL_COST))
					grid.setTile(tile, TileType.Wall1);
			}
			if (Keyboard.isKeyDown(keys[6]) && Keyboard.getEventKeyState())
			{
				// trap
				Tile tile = sprite.getCurrentTile();
				if (grid.getEntity(tile) == null && spendJewels(TRAP_COST))
				{
					Trap trap = new Trap(tile, grid);
					grid.setEntity(tile, trap);
					game.addTrap(trap);
				}
			}
			if (Keyboard.isKeyDown(keys[7]) && Keyboard.getEventKeyState())
				abilityManager.activate();
		}
		
		timeUntilAttack -= Clock.getSeconds();
		abilityManager.update();
		statuses.update();
		sprite.update();
	}
	
	/**
	 * Returns the time (in seconds) before the cool down of the player's
	 * ability is complete.
	 * 
	 * @return the time (in seconds) before the cool down of the player's
	 * 		   ability is complete.
	 */
	public float getCooldownLeft()
	{
		return abilityManager.getCooldownLeft();
	}
	
	/**
	 * Fires a projectile in the direction the sprite is currently facing, if
	 * permitted.
	 */
	private void attack()
	{
		if (timeUntilAttack > 0 || bullets < 1)
			return;
		for (int i = 0; i < TileGrid.order.length; i++)
		{
			if (getSprite().getFacingDirection() == TileGrid.order[i])
			{
				Tile currentTile = getSprite().getCurrentTile();
				Tile nextTile = new Tile(
					currentTile.getIndX() + TileGrid.changeX[i], 
						currentTile.getIndY() + TileGrid.changeY[i], 
					0, 0, TileType.Cave
				);
				char direction = getSprite().getFacingDirection();
				if (statusActive(Status.DMG_BOOST))
					getGame().addProjectile(new Projectile(quickLoad("projectile_" + projectileColor + "_" + direction), 
							nextTile, grid, this, direction, Status.DMG_BOOST.getMultiplier()));
				else
					getGame().addProjectile(new Projectile(quickLoad("projectile_" + projectileColor + "_" + direction), 
							nextTile, grid, this, direction));
				break;
			}
		}
		
		bullets--;
		timeUntilAttack = COOLDOWN_PER_ATTACK;
	}
	
	/**
	 * Reloads the remaining number of bullets the player can fire.
	 */
	private void reload()
	{
		bullets += RELOAD_SPEED * Clock.getSeconds();
		if (bullets > MAX_BULLET)
			bullets = MAX_BULLET;
	}
	
	/**
	 * Returns the game that the player interacts with.
	 * 
	 * @return the game that the player interacts with
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * Expends a specified number of jewels. If the player does not possess
	 * enough jewels to cover the entire expenditure, none will be expended.
	 * 
	 * @param count the number of jewels to expend
	 * @return whether or not the jewels have been successfully expended
	 */
	private boolean spendJewels(int count)
	{
		if (jewels < count)
			return false;
		jewels -= count;
		return true;
	}
	
	/**
	 * Returns the number of jewels the player possesses.
	 * 
	 * @return the number of jewels the player possesses
	 */
	public int getJewels()
	{
		return jewels;
	}
	
	/**
	 * Returns the ability of the player.
	 * 
	 * @return the ability of the player
	 */
	public Ability getAbility()
	{
		return abilityManager.getAbility();
	}
	
	/**
	 * Collects a jewel.
	 * 
	 * @param j the jewel to collect
	 */
	public void collect(Jewel j)
	{
		if (j.exists())
		{
			jewels += j.getValue();
			j.remove();
		}
	}
}
