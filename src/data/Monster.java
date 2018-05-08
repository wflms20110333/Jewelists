package data;
import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;

public class Monster extends Entity
{
	private int health;
	private float speed;
	private boolean first = true;
	private boolean moveX = true;
	private boolean movePos = true;
	
	public Monster(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed)
	{
		super(texture, startTile, grid, width, height);
		this.speed = speed;
	}
	
	@Override
	public void update()
	{
		// first delta calculated is huge
		if (first)
			first = false;
		else {
			updatePath();
			if (moveX)
				if (movePos)
					setX(getX() + delta() * speed);
				else
					setX(getX() - delta() * speed);
			else
				if (movePos)
					setY(getY() + delta() * speed);
				else
					setY(getY() - delta() * speed);
		}
	}
	
	private void updatePath()
	{
		int i = (int) (getX() / TileGrid.SIZE);
		int j = (int) (getY() / TileGrid.SIZE);
		Tile myTile = getGrid().getTile(i, j);
		if (moveX && movePos && !valid(i + 1, j, myTile.getType())) {
			if (Math.random() < 0.5) {
				if (valid(i, j + 1, myTile.getType())) {
					moveX = false;
					movePos = true;
					return;
				}
				else if (valid(i, j - 1, myTile.getType())) {
					moveX = false;
					movePos = false;
					return;
				}
			}
			else {
				if (valid(i, j - 1, myTile.getType())) {
					moveX = false;
					movePos = false;
					return;
				}
				else if (valid(i, j + 1, myTile.getType())) {
					moveX = false;
					movePos = true;
					return;
				}
			}
			if (valid(i - 1, j, myTile.getType())) {
				moveX = true;
				movePos = false;
				return;
			}
		}
		else if (moveX && !movePos && !valid(i - 1, j, myTile.getType())) {
			if (Math.random() < 0.5) {
				if (valid(i, j + 1, myTile.getType())) {
					moveX = false;
					movePos = true;
					return;
				}
				else if (valid(i, j - 1, myTile.getType())) {
					moveX = false;
					movePos = false;
					return;
				}
			}
			else {
				if (valid(i, j - 1, myTile.getType())) {
					moveX = false;
					movePos = false;
					return;
				}
				else if (valid(i, j + 1, myTile.getType())) {
					moveX = false;
					movePos = true;
					return;
				}
			}
			if (valid(i + 1, j, myTile.getType())) {
				moveX = true;
				movePos = true;
				return;
			}
		}
		else if (!moveX && movePos && !valid(i, j + 1, myTile.getType())) {
			if (Math.random() < 0.5) {
				if (valid(i + 1, j, myTile.getType())) {
					moveX = true;
					movePos = true;
					return;
				}
				else if (valid(i - 1, j, myTile.getType())) {
					moveX = true;
					movePos = false;
					return;
				}
			}
			else {
				if (valid(i - 1, j, myTile.getType())) {
					moveX = true;
					movePos = false;
					return;
				}
				else if (valid(i + 1, j, myTile.getType())) {
					moveX = true;
					movePos = true;
					return;
				}
			}
			if (valid(i, j - 1, myTile.getType())) {
				moveX = false;
				movePos = false;
				return;
			}
		}
		else if (!moveX && !movePos && !valid(i, j - 1, myTile.getType())) {
			if (Math.random() < 0.5) {
				if (valid(i + 1, j, myTile.getType())) {
					moveX = true;
					movePos = true;
					return;
				}
				else if (valid(i - 1, j, myTile.getType())) {
					moveX = true;
					movePos = false;
					return;
				}
			}
			else {
				if (valid(i - 1, j, myTile.getType())) {
					moveX = true;
					movePos = false;
					return;
				}
				else if (valid(i + 1, j, myTile.getType())) {
					moveX = true;
					movePos = true;
					return;
				}
			}
			if (valid(i, j + 1, myTile.getType())) {
				moveX = false;
				movePos = true;
				return;
			}
		}
	}
	
	private boolean valid(int i, int j, TileType type)
	{
		return !(i < 0 || j < 0 || i >= TileGrid.COLS || j >= TileGrid.ROWS || type != getGrid().getTile(i, j).getType());
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}
