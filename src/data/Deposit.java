package data;

import org.newdawn.slick.opengl.Texture;

/**
 * The Deposit class blah blah
 * 
 * @author Elizabeth Zou
 */
public class Deposit extends Entity
{
	/**
	 * The number of jewels stored in this deposit.
	 */
	private int numJewels;
	
	/**
	 * Constructs a Deposit.
	 * 
	 * @param texture the texture of the deposit
	 * @param startTile the tile at which the deposit is located
	 * @param grid the grid in which the deposit exists
	 */
	public Deposit(Texture texture, Tile startTile, TileGrid grid)
	{
		super(texture, startTile, grid);
		this.numJewels = 0;
	}
	
	/**
	 * Adds a given number of jewels to the deposit.
	 * 
	 * @param change the number of jewels to add
	 */
	public void add(int change)
	{
		numJewels += change;
	}
	
	/**
	 * Removes a given number of jewels from the deposit.
	 * 
	 * @param change the number of jewels to remove
	 */
	public void remove(int change)
	{
		numJewels -= change;
	}
	
	/**
	 * Returns the number of jewels stored in the deposit.
	 * 
	 * @return the number of jewels stored in the deposit
	 */
	public int getNumJewels()
	{
		return numJewels;
	}
	
	/**
	 * Lol??
	 */
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
	}
}
