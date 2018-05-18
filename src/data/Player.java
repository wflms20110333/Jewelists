package data;

import static helpers.Artist.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import data.AbilityManager.Ability;
import helpers.Clock;

/**
 * The Player class blah blah
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
	private static final int WALL_COST = 2;
	
	/**
	 * The cost to build a trap.
	 */
	private static final int TRAP_COST = 4;
	
	private static final float COOLDOWN_PER_ATTACK = .4f;
	
	private static final int RESPAWN_TIME = 5;
	
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
	private int[] keys = new int[9];
	
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
	
	private Color color;
	
	private String projectileColor;
	
	private float timeUntilAttack;
	
	private boolean dead;
	
	/**
	 * Constructs a Player.
	 * 
	 * @param game the game that the player interacts with
	 * @param grid the grid of the game that the player interacts with
	 * @param keys the keyboard commands of the player
	 * @param texture the texture of the sprite of the player
	 */
	public Player(Game game, TileGrid grid, int[] keys, Texture texture, Color color, String projectileColor)
	{
		this.game = game;
		this.grid = grid;
		this.color = color;
		this.projectileColor = projectileColor;
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
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
	 * @param heal the amount to heal by
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
	public void addStatus(Status effect, long seconds)
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
	 * Sets the score of the player.
	 * 
	 * @param score the new score of the player
	 */
	public void setScore(long score)
	{
		this.score = score;
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
		
		Keyboard.next();
		
		if (!statusActive(Status.STUN) && !dead) {
			char[] updates = new char[] {'U', 'L', 'D', 'R'};
			if (sprite.onCenterArea())
				score += Clock.getSeconds();
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
					grid.setTile(tile, TileType.Wall);
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
	
	public float getCooldownLeft()
	{
		return abilityManager.getCooldownLeft();
	}
	
	private void attack()
	{
		if (timeUntilAttack > 0)
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
		
		timeUntilAttack = COOLDOWN_PER_ATTACK;
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
	 * Expends a specified number of jewels from the player's deposits. If the
	 * player does not possess enough jewels to cover the entire expenditure,
	 * none will be expended.
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
	
	/**
	 * Sets the grid of the game that the player interacts with.
	 * 
	 * @param tg the new grid
	 */
	public void setGrid(TileGrid tg)
	{
		grid = tg;
		sprite.setGrid(tg);
	}
}
