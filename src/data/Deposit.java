package data;

import org.newdawn.slick.opengl.Texture;

public class Deposit extends Entity
{
	private int numJewels;
	
	public Deposit(Texture texture, Tile startTile, TileGrid grid, int numJewels)
	{
		super(texture, startTile, grid);
		this.numJewels = numJewels;
	}
	
	public void add(int change)
	{
		numJewels += change;
	}
	
	public void remove(int change)
	{
		numJewels -= change;
	}
	
	public int getNumJewels()
	{
		return numJewels;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
	}
}
