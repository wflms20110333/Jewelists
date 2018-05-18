package data;

import static helpers.Clock.getSeconds;

import org.newdawn.slick.opengl.Texture;

/**
 * The Projectile class represents projectiles that players can shoot in any of
 * the four cardinal directions. Projectiles deal damage upon hit.
 * 
 * @author An Nguyen
 */
public class Projectile extends Entity
{
	/**
	 * Properties of Projectiles.
	 */
	public static final float SPEED = 400;
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
			remove();
		else {
			damage(getGrid().getMovingEntity(getCurrentTile()));
			
			Tile current = getGrid().getTile(getCurrentTile().getIndX(), getCurrentTile().getIndY());
			// Evolution of wall
			if (current.getType() == TileType.Wall1) {
				getGrid().setTile(current, TileType.Wall2);
				remove();
				return;
			} else if (current.getType() == TileType.Wall2) {
				getGrid().setTile(current, TileType.Wall3);
				remove();
				return;
			}
			else if (current.getType() == TileType.Wall3) {
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
	
	public void damage(Entity moving) {
		if (moving != null && moving instanceof Sprite)
		{
			Player player = ((Sprite) moving).getPlayer();
			if (player != owner)
			{
				player.heal(-BASE_DMG * multiplier);
				remove();
			}
		}
		if (moving != null && moving instanceof Monster) {
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
