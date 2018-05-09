package data;

import static helpers.Artist.drawQuadTex;

import org.newdawn.slick.opengl.Texture;

/**
 * The Entity class represents an entity in the game that players can interact
 * with. Entities exist within a TileGrid and can change locations.
 * 
 * @author Elizabeth Zou
 */

public abstract class Entity
{
	/**
	 * The x and y coordinates (in pixels) of the Entity.
	 */
	private float x;
	private float y;
	
	/**
	 * The width and height (in pixels) of the Entity.
	 */
	private int width;
	private int height;
	
	/**
	 * The texture of the Entity.
	 */
	private Texture texture;
	
	/**
	 * The grid in which the Entity exists.
	 */
	private TileGrid grid;
	
	/**
	 * Whether or not the Entity exists.
	 */
	private boolean exists = true;
	
	/**
	 * Constructs an Entity.
	 * 
	 * @param texture the texture of the entity
	 * @param startTile the starting tile of the entity
	 * @param grid the grid in which the entity exists
	 */
	public Entity(Texture texture, Tile startTile, TileGrid grid)
	{
		this.texture = texture;
		this.x = startTile.getIndX() * TileGrid.SIZE;
		this.y = startTile.getIndY() * TileGrid.SIZE;
		this.width = TileGrid.SIZE;
		this.height = TileGrid.SIZE;
		this.grid = grid;
	}
	
	/**
	 * Draws the entity onto the game display.
	 */
	public void draw()
	{
		drawQuadTex(texture, x, y, width, height);
	}
	
	/**
	 * Sets the entity to nonexistent.
	 */
	public void remove()
	{
		exists = false;
	}
	
	/**
	 * Returns the x coordinate (in pixels) of the entity.
	 * 
	 * @return the x coordinate (in pixels) of the entity
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Sets the x coordinate (in pixels) of the entity.
	 * 
	 * @param x the new x coordinate (in pixels) of the entity
	 */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * Returns the y coordinate (in pixels) of the entity.
	 * 
	 * @return the y coordinate (in pixels) of the entity
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Sets the y coordinate (in pixels) of the entity.
	 * 
	 * @param y the new y coordinate (in pixels) of the entity
	 */
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * Returns the texture of the entity.
	 * 
	 * @return the texture of the entity
	 */
	public Texture getTexture()
	{
		return texture;
	}
	
	/**
	 * Returns the grid in which the entity exists.
	 * 
	 * @return the grid in which the entity exists.
	 */
	public TileGrid getGrid()
	{
		return grid;
	}
	
	/**
	 * Returns whether or not the entity exists.
	 * 
	 * @return whether or not the entity exists
	 */
	public boolean exists()
	{
		return exists;
	}
	
	/**
	 * Returns the tile in which the entity is currently in.
	 * 
	 * @return the tile in which the entity is currently in
	 */
	public Tile currTile()
	{
		return grid.getTile((int) (x / TileGrid.SIZE), (int) (y / TileGrid.SIZE));
	}
	
	/**
	 * Updates the state of the entity.
	 */
	public abstract void update();
}
