package data;

import static helpers.Clock.getSeconds;

import org.newdawn.slick.opengl.Texture;

public class Projectile extends Entity {
	
	public static final float speed = 400;
	public static final float BASE_DMG = 3;
	
	Tile nextTile;
	int direction;
	float multiplier; // damage multiplier
	boolean removed;
	Player owner;
	
	public Projectile(Texture texture, Tile startTile, TileGrid grid, Player owner)
	{
		this(texture, startTile, grid, owner, 'U');
	}
	
	public Projectile(Texture texture, Tile startTile, TileGrid grid, Player owner, char direction)
	{
		this(texture, startTile, grid, owner, direction, 1);
	}
	
	public Projectile(Texture texture, Tile startTile, TileGrid grid, Player owner, char direction, float multiplier)
	{
		super(texture, startTile, grid);
		this.direction = 0;
		for (int k = 0; k < TileGrid.order.length; k++)
		{
			if (direction == TileGrid.order[k])
				this.direction = k;
		}
		this.multiplier = multiplier;
		this.owner = owner;
	}
	
	public boolean outOfBounds()
	{
		return !getGrid().validIndex(getCurrentTile().getIndX(), getCurrentTile().getIndY());
	}
	
	@Override
	public void update()
	{
		if (nextTile == null)
			nextTile = new Tile(getCurrentTile().getIndX() + TileGrid.changeX[direction], 
					getCurrentTile().getIndY() + TileGrid.changeY[direction], 0, 0, TileType.Cave);
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		
		if (outOfBounds())
		{
			remove();
		}
		else
		{
			Entity moving = getGrid().getMovingEntity(getCurrentTile());
			if (moving != null && moving instanceof Sprite)
			{
				Player player = ((Sprite) moving).getPlayer();
				if (player != owner)
				{
					player.heal(-BASE_DMG * multiplier);
					remove();
				}
			}
		}
		
		if (getGrid().validIndex(nextTile.getIndX(), nextTile.getIndY()))
		{
			Entity moving = getGrid().getMovingEntity(nextTile);
			if (moving != null && moving instanceof Sprite)
			{
				Player player = ((Sprite) moving).getPlayer();
				if (player != owner)
				{
					player.heal(-BASE_DMG * multiplier);
					remove();
				}
			}
		}
		
		// compute position
		float x = getX() + getSeconds() * speed * TileGrid.changeX[direction];
		float y = getY() + getSeconds() * speed * TileGrid.changeY[direction];
		
				
		if (TileGrid.changeX[direction] * (nextX - x) < 0)
			x = nextX;
		if (TileGrid.changeY[direction] * (nextY - y) < 0)
			y = nextY;
				
		setX(x);
		setY(y);
				
		if (x == nextX && y == nextY)
		{
			setCurrentTile(nextTile);
			nextTile = new Tile(getCurrentTile().getIndX() + TileGrid.changeX[direction], 
					getCurrentTile().getIndY() + TileGrid.changeY[direction], 0, 0, TileType.Cave);
		}
		
		draw();
	}
	
	public boolean getRemoved()
	{
		return removed;
	}
	
	@Override
	public void remove()
	{
		removed = true;
	}
}
