package data;

import static helpers.Clock.delta;

import org.newdawn.slick.opengl.Texture;

/**
 * The Sprite class blah blah
 * 
 * @author Elizabeth Zou
 */

public class Sprite extends Entity
{
	/**
	 * The player that the Sprite belongs to.
	 */
	private Player player;
	
	/**
	 * The speed of the Sprite.
	 */
	private float speed;
	
	/**
	 * The direction the Sprite is currently moving.
	 */
	private char direction;
	
	/**
	 * The tile the Sprite is currently moving into.
	 */
	private Tile nextTile;
	
	private boolean trapped;
	
	/**
	 * Constructs a Sprite.
	 * 
	 * @param texture the texture of the sprite
	 * @param startTile the starting tile of the sprite
	 * @param grid the grid in which the sprite exists
	 * @param speed the speed of the sprite
	 */
	public Sprite(Texture texture, Tile startTile, TileGrid grid, float speed, Player player)
	{
		super(texture, startTile, grid);
		this.speed = speed;
		this.player = player;
		trapped = false;
	}
	
	/**
	 * Updates the status of the sprite. A sprite stops after moving into a
	 * cell.
	 */
	@Override
	public void update()
	{
		System.out.println("lol yeah");
		if (trapped)
		{
			System.out.println("I'M TRAPPED");
			System.out.println(getX() + " " + getY());
			return;
		}
		
		// collect jewels
		Tile[] check = new Tile[3];
		check[0] = currTile();
		check[1] = getGrid().right(check[0]);
		check[2] = getGrid().down(check[0]);
		for (int i = 0; i < check.length; i++)
		{
			Tile t = check[i];
			if (t == null)
				continue;
			if (i != 0 && !in(t))
				continue;
			Entity e = getGrid().getEntity(t);
			if (e == null)
				continue;
			if (e instanceof Jewel)
			{
				player.collect((Jewel) e);
			}
		}
		
		// move
		if (nextTile == null)
			return;
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		if (direction == 'U')
		{
			float y = getY() - delta() * speed;
			if (nextY > y)
			{
				setY(nextY);
				nextTile = null;
				checkTrap();
			}
			else
				setY(y);
		}
		else if (direction == 'L')
		{
			float x = getX() - delta() * speed;
			if (nextX > x)
			{
				setX(nextX);
				nextTile = null;
				checkTrap();
			}
			else
				setX(x);
		}
		else if (direction == 'D')
		{
			float y = getY() + delta() * speed;
			if (nextY < y)
			{
				setY(nextY);
				nextTile = null;
				checkTrap();
			}
			else
				setY(y);
		}
		else if (direction == 'R')
		{
			float x = getX() + delta() * speed;
			if (nextX < x)
			{
				setX(nextX);
				nextTile = null;
				checkTrap();
			}
			else
				setX(x);
		}
	}
	
	/**
	 * Updates the path of the sprite by setting the direction the sprite is
	 * moving and the tile the sprite is moving into. The path is successfully
	 * updated only if the tile in the given direction is valid to move into.
	 * 
	 * @param d the given direction
	 */
	public void updatePath(char d)
	{
		if (nextTile == null)
		{
			direction = d;
			Tile current = currTile();
			if (direction == 'U' && getGrid().canEnter(current.getIndX(), current.getIndY() - 1))
			{
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() - 1);
			}
			else if (direction == 'L' && getGrid().canEnter(current.getIndX() - 1, current.getIndY()))
			{
				nextTile = getGrid().getTile(current.getIndX() - 1, current.getIndY());
			}
			else if (direction == 'D' && getGrid().canEnter(current.getIndX(), current.getIndY() + 1))
			{
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() + 1);
			}
			else if (direction == 'R' && getGrid().canEnter(current.getIndX() + 1, current.getIndY()))
			{
				nextTile = getGrid().getTile(current.getIndX() + 1, current.getIndY());
			}
		}
	}
	
	private void checkTrap()
	{
		Entity e = getGrid().getEntity(currTile());
		if (e instanceof Trap && getX() == e.getX() && getY() == e.getY())
		{
			Trap trap = (Trap) e;
			if (trap.activated())
				return;
			trapped = true;
			trap.setTrappedSprite(this);
		}
	}
	
	public boolean in(Tile tile)
	{
		float tx = getX();
		float tX = tx + getWidth();
		float ty = getY();
		float tY = ty + getHeight();
		int ox = tile.getX();
		int oX = ox + TileGrid.SIZE;
		int oy = tile.getY();
		int oY = oy + TileGrid.SIZE;
		return tx < oX && tX > ox && ty < oY && tY > oY;
	}
	
	public void toggleTrap()
	{
		trapped =  !trapped;
	}
}
