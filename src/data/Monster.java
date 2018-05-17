package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;

import java.util.ArrayList;

/**
 * The Monster class blah blah
 * 
 * @author Elizabeth Zou
 */

public class Monster extends Entity
{
	/**
	 * The speed of the Monster.
	 */
	private float speed;
	
	/**
	 * True if {@link #update} has never been called.
	 */
	private boolean first = true;
	
	/**
	 * The direction the Monster is currently moving.
	 */
	private char direction;
	
	/**
	 * The tile the Monster is currently moving into.
	 */
	private Tile nextTile, currentTile;
	
	/**
	 * A list of all the possible permutations of "ULDR", the four directions.
	 */
	private ArrayList<String> permutations;
	
	private static final char[] order = {'U', 'L', 'D', 'R'};
	// Change in X relative to order Up, Right, Left, Down;
	private static final int[] changeX = {0, -1, 0, 1};
	private static final int[] changeY = {-1, 0, 1, 0};
	
	/**
	 * Constructs a Monster.
	 * 
	 * @param texture the texture of the monster
	 * @param startTile the starting tile of the monster
	 * @param grid the grid in which the monster exists
	 * @param speed the speed of the monster
	 */
	public Monster(Texture texture, Tile startTile, TileGrid grid, float speed)
	{
		super(texture, startTile, grid);
		getGrid().toggleOccupied(startTile);
		this.currentTile = startTile;
		this.speed = speed;
		permutations = new ArrayList<>();
		genPerms("", "ULDR");
		randSetNextTile();
	}
	
	/**
	 * Private helper method that generates all the possible permutations of
	 * "ULDR", the four directions.
	 * 
	 * @param now the generated permuted string
	 * @param left a concatenation of the characters left to append
	 */
	private void genPerms(String now, String left)
	{
		if (left.length() == 0)
		{
			permutations.add(now);
			return;
		}
		for (int i = 0; i < left.length(); i++)
			genPerms(now + left.charAt(i), left.substring(0, i) + left.substring(i + 1));
	}
	
	/**
	 * Updates the status of the monster. A monster continues in a straight
	 * line until reaching an end, either at the border of the grid it exists
	 * in or due to a wall blocking its path. If this occurs, the monster will
	 * head towards a random direction, given that a path in this new direction
	 * is available.
	 */
	@Override
	public void update()
	{
		// first delta calculated is huge
		if (first)
			first = false;
		else
		{
			if (nextTile == null)
				randSetNextTile();
			if (nextTile == null)
				return;
			int nextX = nextTile.getIndX() * TileGrid.SIZE;
			int nextY = nextTile.getIndY() * TileGrid.SIZE;
			
			for (int k = 0; k < order.length; k++)
			{
				if (direction == order[k])
				{
					// compute position
					float x = getX() + getSeconds() * speed * changeX[k];
					float y = getY() + getSeconds() * speed * changeY[k];
					
					// adjust for overshot
					if (changeX[k] * (nextX - x) < 0)
						x = nextX;
					if (changeY[k] * (nextY - y) < 0)
						y = nextY;
					
					setX(x);
					setY(y);
					
					if (x == nextX && y == nextY)
					{
						getGrid().toggleOccupied(currentTile);
						currentTile = nextTile;
						nextTile = null;
						setNextTile(direction);
					}
				}
			}
		}
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * with a random direction.
	 */
	private void randSetNextTile()
	{
		setNextTile(permutations.get((int) (Math.random() * permutations.size())));
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * with a given ordering of the four directions, i.e. checks the
	 * availability in the four directions in a given order.
	 * 
	 * @param order the give ordering of the four directions
	 */
	private void setNextTile(String order)
	{
		for (char c : order.toCharArray())
		{
			int i = (int) (getX() / TileGrid.SIZE);
			int j = (int) (getY() / TileGrid.SIZE);
			if (c == 'U')
				j--;
			else if (c == 'L')
				i--;
			else if (c == 'D')
				j++;
			else if (c == 'R')
				i++;
			if (getGrid().canEnter(i, j))
			{
				nextTile = getGrid().getTile(i, j);
				getGrid().toggleOccupied(nextTile);
				direction = c;
				return;
			}
		}
	}
	
	/**
	 * Private helper method that sets the tile that the monster will move into
	 * given a preferred direction. If movement in this direction is allowed,
	 * the monster will continue to move in this direction; else the monster
	 * will proceed in a random available direction.
	 * 
	 * @param dir the preferred direction
	 */
	private void setNextTile(char dir)
	{
		if (nextTile != null)
			return;
		int i = currentTile.getIndX();
		int j = currentTile.getIndY();
		if (dir == 'U')
			j--;
		else if (dir == 'L')
			i--;
		else if (dir == 'D')
			j++;
		else if (dir == 'R')
			i++;
		if (getGrid().canEnter(i, j))
		{
			nextTile = getGrid().getTile(i, j);
			getGrid().toggleOccupied(nextTile);
			direction = dir;
		}
		else
			nextTile = null;
	}
	
	/**
	 * Returns the speed of the monster.
	 * 
	 * @return the speed of the monster
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the speed of the monster.
	 * 
	 * @param speed the new speed of the monster
	 */
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}
