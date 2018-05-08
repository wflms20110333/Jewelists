package data;

import static helpers.Artist.drawQuadTex;

import org.newdawn.slick.opengl.Texture;

public abstract class Entity
{
	private float x;
	private float y;
	private int width;
	private int height;
	private Texture texture;
	private TileGrid grid;
	
	public Entity(Texture texture, Tile startTile, TileGrid grid, int width, int height)
	{
		this.texture = texture;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.grid = grid;
	}
	
	public void draw()
	{
		drawQuadTex(texture, x, y, width, height);
	}
	
	public abstract void update();

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public TileGrid getGrid()
	{
		return grid;
	}
}
