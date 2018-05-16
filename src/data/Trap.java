package data;

import static helpers.Clock.*;

public class Trap extends Entity
{
	private static final long DURATION = 3;
	
	private Sprite trappedSprite;
	private long startSecond;
	
	public Trap(Tile startTile, TileGrid grid)
	{
		super(null, startTile, grid);
		System.out.println(getX() + " " + getY());
	}

	@Override
	public void update()
	{
		if (trappedSprite == null)
			return;
		if (getSecond() - startSecond == DURATION)
		{
			trappedSprite.toggleTrap();
			trappedSprite = null;
			remove();
		}
	}
	
	public boolean activated()
	{
		return trappedSprite != null;
	}
	
	public void setTrappedSprite(Sprite s)
	{
		trappedSprite = s;
		startSecond = getSecond();
	}
}
