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
	
	/**
	 * Constructs a Sprite.
	 * 
	 * @param texture the texture of the sprite
	 * @param startTile the starting tile of the sprite
	 * @param grid the grid in which the sprite exists
	 * @param speed the speed of the sprite
	 */
	public Sprite(Texture texture, Tile startTile, TileGrid grid, float speed)
	{
		super(texture, startTile, grid);
		this.speed = speed;
	}
	
	/**
	 * Updates the status of the sprite. A sprite stops after moving into a
	 * cell.
	 */
	@Override
	public void update()
	{
		if (nextTile == null)
			return;
		int nextX = nextTile.getIndX() * TileGrid.SIZE;
		int nextY = nextTile.getIndY() * TileGrid.SIZE;
		if (direction == 'U')
		{
			float y = getY() - delta() * speed;
			if (nextY > y)
			{
				setY(nextY);
				nextTile = null;
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
}
