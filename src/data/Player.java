package data;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import data.AbilityManager.Ability;

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
	private static final int DESTROY_WALL_COST = 5;
	
	/**
	 * The cost to build a trap.
	 */
	private static final int TRAP_COST = 4;
	
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
	private int maxHealth;
	
	/**
	 * The current health of the Player.
	 */
	private int health;
	
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
	private long score;
	
	/**
	 * The status manager that manages the statuses of effects.
	 */
	private StatusManager statuses;
	
	/**
	 * Constructs a Player.
	 * 
	 * @param game the game that the player interacts with
	 * @param grid the grid of the game that the player interacts with
	 * @param keys the keyboard commands of the player
	 * @param texture the texture of the sprite of the player
	 */
	public Player(Game game, TileGrid grid, int[] keys, Texture texture)
	{
		this.game = game;
		this.grid = grid;
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
		Tile tile = grid.randEmptyTile();
		score = 1;
		health = maxHealth = DEFAULT_HEALTH;
		sprite = new Sprite(texture, tile, grid, this);
		jewels = 0;
		statuses = new StatusManager();
		abilityManager = new AbilityManager(this);
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
		return (float) health / maxHealth;
	}
	
	/**
	 * Returns the health of the player.
	 * 
	 * @return the health of the player
	 */
	public int getHealth()
	{
		return health;
	}
	
	/**
	 * Sets the health of the player.
	 * 
	 * @param health the new health of the player
	 */
	public void setHealth(int health)
	{
		this.health = health;
		if (health > maxHealth)
			health = maxHealth;
	}
	
	/**
	 * Returns the maximum health of the player.
	 * 
	 * @return the maximum health of the player
	 */
	public int getMaxHealth()
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
	public long getScore()
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
		Keyboard.next();
		
		char[] updates = new char[] {'U', 'L', 'D', 'R'};
		
		if (!statusActive(Status.STUN))
			for (int i = 0; i < updates.length; i++)
				if (Keyboard.isKeyDown(keys[i]) && Keyboard.getEventKeyState())
					sprite.updatePath(updates[i]);
		
		// shift
		if (Keyboard.isKeyDown(keys[4]) && Keyboard.getEventKeyState())
		{
			if (Keyboard.isKeyDown(keys[0]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.up(sprite.getCurrentTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[1]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.left(sprite.getCurrentTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[2]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.down(sprite.getCurrentTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[3]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.right(sprite.getCurrentTile());
				if (tgt != null)
					attack(tgt);
			}
		}

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
		{
			abilityManager.activate();
		}
		if (Keyboard.isKeyDown(keys[8]) && Keyboard.getEventKeyState())
		{
			
		}
		
		abilityManager.update();
		statuses.update();
		sprite.update();
		sprite.draw();
	}
	
	/**
	 * Attacks a given tile, if there is anything in the tile to be attacked.
	 * 
	 * @param tile the given tile
	 */
	private void attack(Tile tile)
	{
		// graphics of attacking??
		if (tile.getType() == TileType.Wall && spendJewels(DESTROY_WALL_COST))
			grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Cave);
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
