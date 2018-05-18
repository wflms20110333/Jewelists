package UI;

import static helpers.Artist.drawQuadTex;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

/**
 * Parent object of all user-interface textures displayed on screen
 * 
 * @author An Nguyen
 */
public class UIItem
{
	private Texture texture;
	private Rectangle rect;

	/**
	 * Create a new user-interface component
	 * @param texture the texture of the component
	 * @param rect the rectangle bounding the component
	 */
	public UIItem(Texture texture, Rectangle rect)
	{
		this.texture = texture;
		this.rect = rect;
	}

	/**
	 * @return the texture of the component
	 */
	public Texture getTexture()
	{
		return texture;
	}

	/**
	 * Set the texture of the UI Component
	 * @param texture the texture of the component
	 */
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	/**
	 * @return the bounding rectangle of the component
	 */
	public Rectangle getRect()
	{
		return rect;
	}

	/**
	 * Set the bounding rectangle of the component
	 * @param rect the new bounding rectangle
	 */
	public void setRect(Rectangle rect)
	{
		this.rect = rect;
	}

	public void draw()
	{
		drawQuadTex(texture, rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Update the UIItem. This currently does nothing and should be 
	 */
	public void update()
	{
		draw();
	}
}
