package data;

import static helpers.Clock.delta;

import org.newdawn.slick.opengl.Texture;

public class Sprite extends Entity
{
	private char direction;
	private float speed;
	private Tile nextTile;

	public Sprite(Texture texture, Tile startTile, TileGrid grid, float speed)
	{
		super(texture, startTile, grid);
		this.speed = speed;
	}

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
