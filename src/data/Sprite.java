package data;

import static helpers.Clock.getSeconds;
import static helpers.Artist.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

/**
 * The Sprite class blah blah
 * 
 * @author Elizabeth Zou
 */
public class Sprite extends Entity
{
	/**
	 * The default speed of a Sprite.
	 */
	public static final int DEFAULT_SPEED = 100;
	
	/**
	 * The player that the Sprite belongs to.
	 */
	private Player player;
	
	/**
	 * The speed of the Sprite.
	 */
	private float speed;
	
	/**
	 * The direction the Sprite is currently moving in.
	 */
	private char direction;
	
	/**
	 * The tile the Sprite is currently moving into.
	 */
	private Tile nextTile;
	
	private static final Color BAR_COLOR = new Color(0.8f, 0.8f, 0.8f, 0.8f);
	private static final char[] DIRS = {'U', 'L', 'D', 'R'};
	private static final int[] BAR_DX = {4, 0, 4, TileGrid.SIZE - 4};
	private static final int[] BAR_DY = {0, 4, TileGrid.SIZE - 4, 4};
	private static final int[] BAR_WIDTH = {24, 4, 24, 4};
	private static final int[] BAR_HEIGHT = {4, 24, 4, 24};
	
	private boolean visible; 
	
	/**
	 * Constructs a Sprite.
	 * 
	 * @param texture the texture of the sprite
	 * @param startTile the starting tile of the sprite
	 * @param grid the grid in which the sprite exists
	 * @param player the player that the sprite belongs to
	 */
	public Sprite(Texture texture, Tile startTile, TileGrid grid, Player player)
	{
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
		getGrid().setOccupied(startTile, this);
		this.speed = speed;
		this.player = player;
		this.direction = 'U';
		this.visible = true;
	}
	
	/**
	 * Updates the status of the sprite. A sprite stops after moving into a
	 * cell.
	 */
	@Override
	public void update()
	{
		// collect jewels
		int range = 0;
		if (player.statusActive(Status.MAGNET))
			range = (int) Status.MAGNET.getMultiplier();
		
		for (Tile t : getGrid().getTilesInRange(getCurrentTile(), range))
		{
			if (t == null)
				continue;
			Entity e = getGrid().getEntity(t);
			if (e != null && e instanceof Jewel)
				player.collect((Jewel) e);
		}
		
		// move
		if (nextTile == null) {
			if (visible)
				draw();
			return;
		}
		
		float adjusted_speed = speed;
		if (player.statusActive(Status.SLOW))
			adjusted_speed *= Status.SLOW.getMultiplier();
		if (player.statusActive(Status.SPEED))
			adjusted_speed *= Status.SPEED.getMultiplier();
		
		int nextX = nextTile.getX();
		int nextY = nextTile.getY();
		
		for (int k = 0; k < TileGrid.order.length; k++)
		{
			if (direction == TileGrid.order[k])
			{
				// compute position
				float x = getX() + getSeconds() * adjusted_speed * TileGrid.changeX[k];
				float y = getY() + getSeconds() * adjusted_speed * TileGrid.changeY[k];
				
				// adjust for overshot
				if (TileGrid.changeX[k] * (nextX - x) < 0)
					x = nextX;
				if (TileGrid.changeY[k] * (nextY - y) < 0)
					y = nextY;
				
				setX(x);
				setY(y);
				
				if (x == nextX && y == nextY)
				{
					getGrid().setOccupied(getCurrentTile(), null);
					setCurrentTile(nextTile);
					nextTile = null;
					checkTrap();
				}
			}
		}
		
		if (visible)
			draw();
	}
	
	public void toggleVisibility() {
		this.visible = !visible;
	}
	
	public void kill() {
		getGrid().setOccupied(getCurrentTile(), null);
		if (nextTile != null)
			getGrid().setOccupied(nextTile, null);
		nextTile = null;
	}
	
	/**
	 * Blinks the sprite to the given tile.
	 * 
	 * @param tile the given tile
	 */
	public void blink(Tile tile)
	{
		getGrid().setOccupied(getCurrentTile(), null);
		if (nextTile != null)
			getGrid().setOccupied(nextTile, null);
		setCurrentTile(tile);
		getGrid().setOccupied(tile, this);
		setX(getCurrentTile().getX());
		setY(getCurrentTile().getY());
		nextTile = null;
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
			Tile current = getCurrentTile();
			if (direction == 'U' && getGrid().canEnter(current.getIndX(), current.getIndY() - 1))
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() - 1);
			else if (direction == 'L' && getGrid().canEnter(current.getIndX() - 1, current.getIndY()))
				nextTile = getGrid().getTile(current.getIndX() - 1, current.getIndY());
			else if (direction == 'D' && getGrid().canEnter(current.getIndX(), current.getIndY() + 1))
				nextTile = getGrid().getTile(current.getIndX(), current.getIndY() + 1);
			else if (direction == 'R' && getGrid().canEnter(current.getIndX() + 1, current.getIndY()))
				nextTile = getGrid().getTile(current.getIndX() + 1, current.getIndY());
			if (nextTile != null)
				getGrid().setOccupied(nextTile, this);
		}
	}
	
	/**
	 * Checks if the tile the sprite is currently on contains an inactivated
	 * trap. If such a trap is found, the sprite will become trapped by it.
	 */
	private void checkTrap()
	{
		Entity e = getGrid().getEntity(getCurrentTile());
		if (e instanceof Trap && getX() == e.getX() && getY() == e.getY())
		{
			Trap trap = (Trap) e;
			if (trap.getBufferPassed() && !trap.activated())
				trap.activate(this);
		}
	}
	
	/**
	 * Returns the direction the sprite is facing.
	 * 
	 * @return the direction the sprite is facing
	 */
	public char getFacingDirection()
	{
		return direction;
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
	
	/**
	 * Returns the player that the sprite belongs to.
	 * 
	 * @return the player that the sprite belongs to
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean onCenterArea()
	{
		return getCurrentTile().getType() == TileType.Dirt;
	}
	
	public Tile getNextTile()
	{
		return nextTile;
	}
	
	public void setNextTile(Tile tile)
	{
		nextTile = tile;
	}
	
	@Override
	public void draw()
	{
		super.draw();
		for (int i = 0; i < DIRS.length; i++)
		{
			if (direction == DIRS[i])
			{
				drawQuad(getX() + BAR_DX[i], getY() + BAR_DY[i], BAR_WIDTH[i], BAR_HEIGHT[i], BAR_COLOR);
				return;
			}
		}
	}
}