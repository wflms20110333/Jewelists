package data;

import static helpers.Clock.getSeconds;

import java.time.Year;

import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

/**
 * The Sprite class blah blah
 * 
 * @author Elizabeth Zou
 */

public class Sprite extends Entity
{
	
	public static final int DEFAULT_SPEED = 100;
	
	private static final char[] order = {'U', 'R', 'L', 'D'};
	// Change in X relative to order Up, Right, Left, Down;
	private static final int[] changeX = {0, 1, -1, 0};
	private static final int[] changeY = {-1, 0, 0, 1};
	
	private Player player;
	
	private float speed;
	
	private char direction;
	
	/**
	 * The tile the Sprite is currently moving into.
	 */
	private Tile nextTile;
	
	
	public Sprite(Texture texture, Tile startTile, TileGrid grid, Player player) {
		this(texture, startTile, grid, DEFAULT_SPEED, player);
	}
	
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
		getGrid().toggleOccupied(startTile);
		this.speed = speed;
		this.player = player;
	}
	
	/**
	 * Updates the status of the sprite. A sprite stops after moving into a
	 * cell.
	 */
	@Override
	public void update()
	{
		// collect jewels
		Tile[] check = new Tile[5];
		check[0] = currTile();
		check[1] = getGrid().right(check[0]);
		check[2] = getGrid().down(check[0]);
		check[3] = getGrid().left(check[0]);
		check[4] = getGrid().up(check[0]);
		
		for (int i = 0; i < check.length; i++)
		{
			Tile t = check[i];
			if (t == null)
				continue;
			// hey magnet allows u to collect multiple
			if (i != 0 && !in(t) && !player.statusActive(Status.MAGNET))
				continue;
			Entity e = getGrid().getEntity(t);
			if (e == null)
				continue;
			if (e instanceof Jewel)
				player.collect((Jewel) e);
		}
		
		// move
		if (nextTile == null)
			return;
		
		float adjusted_speed = speed;
		if (player.statusActive(Status.SLOW))
			adjusted_speed *= Status.SLOW.getMultiplier();
		if (player.statusActive(Status.SPEED))
			adjusted_speed *= Status.SPEED.getMultiplier();
		
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		
		for (int k = 0; k < order.length; k++) {
			if (direction == order[k]) {
				// compute position
				float x = getX() + getSeconds() * adjusted_speed * changeX[k];
				float y = getY() + getSeconds() * adjusted_speed * changeY[k];
				
				// adjust for overshot
				if (changeX[k] * (nextX - x) < 0)
					x = nextX;
				if (changeY[k] * (nextY - y) < 0)
					y = nextY;
				
				setX(x);
				setY(y);
				
				if (x == nextX && y == nextY) {
					nextTile = null;
					checkTrap();
				}
			}
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
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() - 1);
			else if (direction == 'L' && getGrid().canEnter(current.getIndX() - 1, current.getIndY()))
				nextTile = getGrid().getTile(current.getIndX() - 1, current.getIndY());
			else if (direction == 'D' && getGrid().canEnter(current.getIndX(), current.getIndY() + 1))
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() + 1);
			else if (direction == 'R' && getGrid().canEnter(current.getIndX() + 1, current.getIndY()))
				nextTile = getGrid().getTile(current.getIndX() + 1, current.getIndY());
			if (nextTile != null)
				getGrid().toggleOccupied(nextTile);
		}
	}
	
	/**
	 * Checks if the tile the sprite is currently on contains an inactivated
	 * trap. If such a trap is found, the sprite will become trapped by it.
	 */
	private void checkTrap()
	{
		Entity e = getGrid().getEntity(currTile());
		if (e instanceof Trap && getX() == e.getX() && getY() == e.getY())
		{
			Trap trap = (Trap) e;
			if (trap.getBufferPassed() && !trap.activated())
				trap.activate(this);
		}
	}
	
	/**
	 * Returns whether the sprite currently occupies, partially or completely,
	 * a given cell.
	 * 
	 * @param tile the tile that forms the given cell
	 * @return whether the sprite currently occupies, partially or completely,
	 * 		   the given cell
	 */
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
	
	public Player getPlayer() {
		return player;
	}
}
