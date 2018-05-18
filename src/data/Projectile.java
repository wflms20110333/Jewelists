package data;

import static helpers.Clock.getSeconds;

import org.newdawn.slick.opengl.Texture;

/**
 * The Projectile class represents projectiles that players can shoot in any of
 * the four cardinal directions. Projectiles inflict damage upon striking.
 * 
 * @author An Nguyen
 * @author Collin McMahon
 * Dependencies: slick(to manage textures)
 */
public class Projectile extends Entity
{
	/**
	 * Properties of Projectiles.
	 */
	public static final float SPEED = 600;
	public static final float BASE_DMG = 3;
	public static final float STUN_DURATION = 2;
	
	/**
	 * The tile the Projectile is currently moving into.
	 */
	Tile nextTile;
	
	/**
	 * The direction the Projectile is currently moving in.
	 */
	int direction;
	
	/**
	 * The damage multiplier of the Projectile.
	 */
	float multiplier;
	
	/**
	 * The owner of the Projectile.
	 */
	Player owner;
	
	/**
	 * Constructs a Projectile.
	 * 
	 * @param texture the texture of the projectile
	 * @param startTile the starting tile of the projectile
	 * @param grid the grid in which the projectile exists
	 * @param owner the owner of the projectile
	 */
	public Projectile(Texture texture, Tile startTile, TileGrid grid, Player owner)
	{
		this(texture, startTile, grid, owner, 'U');
	}
	
	/**
	 * Constructs a Projectile.
	 * 
	 * @param texture the texture of the projectile
	 * @param startTile the starting tile of the projectile
	 * @param grid the grid in which the projectile exists
	 * @param owner the owner of the projectile
	 * @param direction the direction the projectile is currently moving in
	 */
	public Projectile(Texture texture, Tile startTile, TileGrid grid, Player owner, char direction)
	{
		this(texture, startTile, grid, owner, direction, 1);
	}
	
	/**
	 * Constructs a Projectile.
	 * 
	 * @param texture the texture of the projectile
	 * @param startTile the starting tile of the projectile
	 * @param grid the grid in which the projectile exists
	 * @param owner the owner of the projectile
	 * @param direction the direction the projectile is currently moving in
	 * @param multiplier the damage multiplier of the projectile
	 */
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
	
	/**
	 * Returns whether the projectile is outside the bounds of the grid in
	 * which it exists.
	 * 
	 * @return whether the projectile is outside the bounds of the grid in
	 * 		   which it exists
	 */
	public boolean outOfBounds()
	{
		return !getGrid().validIndex(getCurrentTile().getIndX(), getCurrentTile().getIndY());
	}
	
	/**
	 * Updates the position of the projectile, inflicting damage and removing
	 * itself if striking a target, and removing itself if it has moved outside
	 * the bounds.
	 */
	@Override
	public void update()
	{
		if (nextTile == null)
			nextTile = new Tile(getCurrentTile().getIndX() + TileGrid.changeX[direction], 
					getCurrentTile().getIndY() + TileGrid.changeY[direction], 0, 0, TileType.Cave);
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		
		if (outOfBounds())
			remove();
		else
		{
			damage(getGrid().getMovingEntity(getCurrentTile()));
			
			Tile current = getGrid().getTile(getCurrentTile().getIndX(), getCurrentTile().getIndY());
			// Evolution of wall
			if (current.getType() == TileType.Wall1)
			{
				getGrid().setTile(current, TileType.Wall2);
				remove();
				return;
			}
			else if (current.getType() == TileType.Wall2)
			{
				getGrid().setTile(current, TileType.Wall3);
				remove();
				return;
			}
			else if (current.getType() == TileType.Wall3)
			{
				getGrid().setTile(current, TileType.Cave);
				remove();
				return;
			}
		}
		
		if (getGrid().validIndex(nextTile.getIndX(), nextTile.getIndY()))
			damage(getGrid().getMovingEntity(nextTile));
		
		// compute position
		float x = getX() + getSeconds() * SPEED * TileGrid.changeX[direction];
		float y = getY() + getSeconds() * SPEED * TileGrid.changeY[direction];
		
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
	
	/**
	 * Inflicts damage on a given moving entity that the projectile has struck.
	 * 
	 * @param moving the given moving entity
	 */
	public void damage(Entity moving)
	{
		if (moving != null && moving instanceof Sprite)
		{
			Player player = ((Sprite) moving).getPlayer();
			if (player != owner)
			{
				player.heal(-BASE_DMG * multiplier);
				remove();
			}
		}
		if (moving != null && moving instanceof Monster)
		{
			Monster monster = (Monster) moving;
			monster.heal(-BASE_DMG * multiplier);
			if (multiplier > 1)
				monster.addStatus(Status.STUN, STUN_DURATION);
			if (!monster.exists())
				owner.addJewel(Monster.REWARD_VALUE);
			remove();
		}
	}
}
