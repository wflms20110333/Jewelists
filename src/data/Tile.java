package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

/**
 * The Tile class blah blah
 * 
 * @author Elizabeth Zou
 */

public class Tile
{
	/**
	 * The x and y indexes of the cell formed by the Tile.
	 */
	private int indX;
	private int indY;
	
	/**
	 * The x and y coordinates (in pixels) of the Tile.
	 */
	private int x;
	private int y;
	
	/**
	 * The width and height (in pixels) of the Tile.
	 */
	private int width;
	private int height;
	
	/**
	 * The texture of the Tile.
	 */
	private Texture texture;
	
	/**
	 * The type of the Tile.
	 */
	private TileType type;
	
	/**
	 * Constructs a Tile.
	 * 
	 * @param x the x index of the cell formed by the tile
	 * @param y the y index of the cell formed by the tile
	 * @param width the width of the tile
	 * @param height the height of the tile
	 * @param type the type of the tile
	 */
	public Tile(int x, int y, int width, int height, TileType type)
	{
		this.indX = x;
		this.indY = y;
		this.x = x * TileGrid.SIZE;
		this.y = y * TileGrid.SIZE;
		this.width = width;
		this.height = height;
		this.type = type;
		this.texture = quickLoad(type.textureName);
	}
	
	/**
	 * Draws the tile onto the game display.
	 */
	public void draw()
	{
		drawQuadTex(texture, x, y, width, height);
	}
	
	/**
	 * Returns the x index of the cell formed by the tile.
	 * @return the x index of the cell formed by the tile
	 */
	public int getIndX()
	{
		return indX;
	}
	
	/**
	 * Returns the y index of the cell formed by the tile.
	 * @return the y index of the cell formed by the tile
	 */
	public int getIndY()
	{
		return indY;
	}
	
	/**
	 * Returns the width of the tile.
	 * @return the width of the tile
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of the tile.
	 * @return the height of the tile
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Returns the texture of the tile.
	 * @return the texture of the tile
	 */
	public Texture getTexture()
	{
		return texture;
	}
	
	/**
	 * Returns the type of the tile.
	 * @return the type of the tile
	 */
	public TileType getType()
	{
		return type;
	}
	
	/**
	 * Sets the type of the tile.
	 * @param type the new type of the tile
	 */
	public void setType(TileType type)
	{
		this.type = type;
		texture = quickLoad(type.textureName);
	}
}
