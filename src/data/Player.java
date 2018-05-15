package data;

import static helpers.Artist.quickLoad;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

/**
 * The Player class blah blah
 * 
 * @author Elizabeth Zou
 */
public class Player
{
	/**
	 * The cost to destroy a wall.
	 */
	private static final int WALL_COST = 2;
	private static final int DEFAULT_HEALTH = 10;
	
	/**
	 * The grid of the game that the Player interacts with.
	 */
	private TileGrid grid;
	
	/**
	 * The keyboard commands of the Player.
	 */
	private int[] keys = new int[9];
	
	private Sprite sprite;
	
	private int totalJewels;
	private int health, maxhealth;
	
	private Queue<Deposit> deposits;
	
	private Deposit currentDeposit;
	
	/**
	 * The tile type of the opposing Player's deposits.
	 */
	private TileType otherPlayerDeposit;
	
	
	
	/**
	 * Constructs a Player.
	 * 
	 * @param grid the grid of the game that the player interacts with
	 * @param keys the keyboard commands of the player
	 * @param texture the texture of the sprite of the player
	 * @param other the tile type of the opposing player's deposits
	 */
	public Player(TileGrid grid, int[] keys, Texture texture, TileType other)
	{
		this.grid = grid;
		for (int i = 0; i < this.keys.length; i++)
			this.keys[i] = keys[i];
		health = maxhealth = DEFAULT_HEALTH;
		Tile tile = grid.randEmptyTile();
		sprite = new Sprite(texture, tile, grid, 10, this);
		deposits = new LinkedList<Deposit>();
		currentDeposit = new Deposit(quickLoad(TileType.Deposit1.textureName), tile, grid); // lol change texture
		deposits.add(currentDeposit);
		otherPlayerDeposit = other;
	}
	
	public float getPercent() {
		return (float) health / maxhealth;
	}
	
	public int getHealth() {
		return health;
	}

	public void sethealth(int health) {
		this.health = health;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxhealth = maxhealth;
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
			// new deposit
		}
		if (Keyboard.isKeyDown(keys[6]) && Keyboard.getEventKeyState())
		{
			Tile tile = sprite.currTile();
			if (tile.getType() == TileType.Cave) // && spendJewels(WALL_COST))
				grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Wall);
		}
		if (Keyboard.isKeyDown(keys[7]) && Keyboard.getEventKeyState())
		{
			// bomb
		}
		if (Keyboard.isKeyDown(keys[8]) && Keyboard.getEventKeyState())
		{
			// trap
		}
		
		for (Deposit d : deposits) {
			//d.update();
			d.draw();
		}
		sprite.update();
		sprite.draw();
	}
	
	private void attack(Tile tile)
	{
		// graphics of attacking??
		if (tile.getType() == TileType.Wall) // && spendJewels(WALL_COST))
			grid.setTile(tile.getIndX(), tile.getIndY(), TileType.Cave);
		if (tile.getType() == otherPlayerDeposit) {
			// steal from deposit
		}
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
		if (totalJewels < count)
			return false;
		totalJewels -= count;
		while (count > 0)
		{
			Deposit d = deposits.peek();
			if (d.getNumJewels() <= count)
			{
				count -= d.getNumJewels();
				deposits.poll();
			}
			else
			{
				d.remove(count);
				count = 0;
			}
		}
		return true;
	}
	
	/**
	 * Collects a jewel.
	 * 
	 * @param j the jewel to collect
	 */
	public void collect(Jewel j)
	{
		currentDeposit.add(j.getValue());
		totalJewels += j.getValue();
		j.remove();
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
		for (Deposit d : deposits)
			d.setGrid(tg);
	}
}
