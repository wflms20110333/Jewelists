package UI;

import static helpers.Artist.drawString;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

/**
 * Represents clickable buttons for the user interface.
 * 
<<<<<<< HEAD
 * @author Elizabeth Zou
 * @author An Nguyen
=======
 * @author Elizabeth Zou, An Nguyen
 * Dependencies: lwjgl (to obtain mouse input)
 * 				slick(to manage textures and colors)
>>>>>>> 051edf505379cc5107512c73591359f0375cdab2
 */
public class Button extends UIItem
{
	/**
	 * The padding for text drawn inside of the Button
	 */
	private static final int PADDING = 10;
	
	public static int LEFT_MOUSE = 0;
	
	private String name;
	private String text;
	
	/**
	 * Create a new button
	 * 
	 * @param name the button's name
	 * @param texture the texture of the button
	 * @param x the x coordinate of the top-left corner
	 * @param y the y coordinate of the top-left corner
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public Button(String name, Texture texture, int x, int y, int width, int height)
	{
		this(name, texture, new Rectangle(x, y, width, height), null);
	}
	
	/**
	 * Create a new button
	 * 
	 * @param name the button's name
	 * @param texture the texture of the button
	 * @param x the x coordinate of the top-left corner
	 * @param y the y coordinate of the top-left corner
	 */
	public Button(String name, Texture texture, int x, int y)
	{
		this(name, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()), null);
	}
	
	/**
	 * Create a new button
	 * 
	 * @param name the button's name
	 * @param texture the texture of the button
	 * @param x the x coordinate of the top-left corner
	 * @param y the y coordinate of the top-left corner
	 * @param text the text displayed in the button
	 */
	public Button(String name, Texture texture, int x, int y, String text)
	{
		this(name, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()), text);
	}
	
	/**
	 * Create a new button
	 * 
	 * @param name the button's name
	 * @param texture the texture of the button
	 * @param rect the bounding rectangle of the button
	 * @param text the text displayedi n the button
	 */
	public Button(String name, Texture texture, Rectangle rect, String text)
	{
		super(texture, rect);
		this.name = name;
		this.text = text;
	}
	
	/**
	 * @return the button's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the button's name
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return whether or not the button is clicked
	 */
	public boolean isClicked()
	{
		return Mouse.isButtonDown(LEFT_MOUSE) && super.getRect().contains(
			Mouse.getX(), Artist.HEIGHT - Mouse.getY() - 1
		);
	}
	
	/**
	 * Draw the Button
	 */
	@Override
	public void draw()
	{
		super.draw();
		if (text != null)
			drawString(getRect().x + PADDING, getRect().y + PADDING, text, Color.black);
	}
	
	/**
	 * Set the text displayed on the button
	 * @param text the new text displayed
	 */
	public void setText(String text)
	{
		this.text = text;
	}
}
