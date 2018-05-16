package data;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

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
	 * The keyboard commands of the Player.
	 */
	private int[] keys = new int[9];
	
	/**
	 * The maximum health of the Player.
	 */
	private int maxhealth;
	
	/**
	 * The health of the Player.
	 */
	private int health;
	
	/**
	 * The sprite of the Player.
	 */
	private Sprite sprite;
	
	/**
	 * The total number of jewels the Player possesses.
	 */
	private int jewels;
	
	/**
	 * The score of the Player.
	 */
	private long score;
	
	//private Queue<Deposit> deposits;
	
	//private Deposit currentDeposit;
	
	/**
	 * The tile type of the opposing Player's deposits.
	 */
	//private TileType otherPlayerDeposit;
	
	/**
	 * Constructs a Player.
	 * 
	 * @param game the game that the player interacts with
	 * @param grid the grid of the game that the player interacts with
	 * @param keys the keyboard commands of the player
	 * @param texture the texture of the sprite of the player
	 */
	public Player(Game game, TileGrid grid, int[] keys, Texture texture) //, TileType thisDeposit, TileType otherDeposit)
	{
		this.game = game;
		this.grid = grid;
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
		Tile tile = grid.randEmptyTile();
		score = 1;
		health = maxhealth = DEFAULT_HEALTH;
		sprite = new Sprite(texture, tile, grid, 10, this);
		jewels = 0;
		/*
		deposits = new LinkedList<Deposit>();
		currentDeposit = new Deposit(quickLoad(thisDeposit.textureName), tile, grid);
		deposits.add(currentDeposit);
		grid.setTile(tile.getIndX(), tile.getIndY(), thisDeposit);
		grid.setEntity(tile.getIndX(), tile.getIndY(), currentDeposit);
		otherPlayerDeposit = otherDeposit;
		*/
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
		return (float) health / maxhealth;
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
	public void sethealth(int health)
	{
		this.health = health;
	}
	
	/**
	 * Returns the maximum health of the player.
	 * 
	 * @return the maximum health of the player
	 */
	public int getMaxhealth()
	{
		return maxhealth;
	}
	
	/**
	 * Sets the maximum health of the player.
	 * 
	 * @param maxhealth the new maximum health of the player
	 */
	public void setMaxhealth(int maxhealth)
	{
		this.maxhealth = maxhealth;
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

	public void update()
	{
		/*
		 * if (Mouse.isButtonDown(0)) { //setTile(); }
		 */
		// while (Keyboard.next()) {
		Keyboard.next();
		
		char[] updates = new char[] {'U', 'L', 'D', 'R'};
		
		for (int i = 0; i < updates.length; i++)
			if (Keyboard.isKeyDown(keys[i]) && Keyboard.getEventKeyState())
				sprite.updatePath(updates[i]);
		
		// shift
		if (Keyboard.isKeyDown(keys[4]) && Keyboard.getEventKeyState())
		{
			if (Keyboard.isKeyDown(keys[0]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.up(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[1]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.left(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[2]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.down(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
			if (Keyboard.isKeyDown(keys[3]) && Keyboard.getEventKeyState())
			{
				Tile tgt = grid.right(sprite.currTile());
				if (tgt != null)
					attack(tgt);
			}
		}

		if (Keyboard.isKeyDown(keys[5]) && Keyboard.getEventKeyState())
		{
			Tile tile = sprite.currTile();
			if (tile.getType() == TileType.Cave && spendJewels(WALL_COST))
				grid.setTile(tile, TileType.Wall);
		}
		if (Keyboard.isKeyDown(keys[6]) && Keyboard.getEventKeyState())
		{
			// trap
			Tile tile = sprite.currTile();
			if (grid.getEntity(tile) == null && spendJewels(TRAP_COST))
			{
				Trap trap = new Trap(tile, grid);
				grid.setEntity(tile, trap);
				game.addTrap(trap);
			}
		}
		if (Keyboard.isKeyDown(keys[7]) && Keyboard.getEventKeyState())
		{
			// bomb
		}
		if (Keyboard.isKeyDown(keys[8]) && Keyboard.getEventKeyState())
		{
			
		}
		
		/*
		for (Deposit d : deposits) {
			//d.update();
			d.draw();
		}
		*/
		sprite.update();
		sprite.draw();
	}
	
	private void attack(Tile tile)
	{
		// graphics of attacking??
		if (tile.getType() == TileType.Wall && spendJewels(DESTROY_WALL_COST))
			grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Cave);
		/*
		if (tile.getType() == otherPlayerDeposit) {
			Deposit o = (Deposit) grid.getEntity(tile);
		}
		*/
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
		/*
		while (count > 0)
		{
			Deposit d = deposits.peek();
			if (d.getNumJewels() <= count)
			{
				count -= d.getNumJewels();
				if (deposits.size() > 1)
				{
					deposits.poll();
					grid.setTile(d.currTile(), TileType.Cave);
					grid.removeEntity(d.currTile());
				}
			}
			else
			{
				d.remove(count);
				count = 0;
			}
		}
		*/
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
	 * Collects a jewel.
	 * 
	 * @param j the jewel to collect
	 */
	public void collect(Jewel j)
	{
		if (j.exists())
		{
			//currentDeposit.add(j.getValue());
			jewels += j.getValue();
			j.remove();
		}
	}
	
	/**
	 * Sets a keyboard command.
	 * 
	 * @param index the index of the command in keys
	 * @param key the new keyboard command
	 */
	public void setKey(int index, int key)
	{
		keys[index] = key;
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
		//for (Deposit d : deposits)
			//d.setGrid(tg);
	}
}
