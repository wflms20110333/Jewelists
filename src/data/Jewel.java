package data;

import org.newdawn.slick.opengl.Texture;

/**
 * The Jewel class blah blah
 * 
 * @author Elizabeth Zou
 */

public class Jewel extends Entity
{
	/**
	 * The value of the Jewel.
	 */
	private int value;
	
	/**
	 * Constructs a Jewel.
	 * 
	 * @param texture the texture of the jewel
	 * @param startTile the tile at which the jewel is located
	 * @param grid the grid in which the jewel exists
	 * @param value the value of the jewel
	 */
	public Jewel(Texture texture, Tile startTile, TileGrid grid, int value)
	{
		super(texture, startTile, grid);
		this.value = value;
	}
	
	/**
	 * Lol?
	 */
	@Override
	public void update()
	{

	}
	
	/**
	 * Returns the value of the jewel.
	 * 
	 * @return the value of the jewel
	 */
	public int getValue()
	{
		return value;
	}
}
