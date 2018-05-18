package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;

import static helpers.Artist.*;

/**
 * Represents a string on the screen
 * @author Elizabeth Zou
 */
public class UIString extends UIItem
{
	private String text;
	
	/**
	 * Create a new text component
	 * 
	 * @param text the string
	 * @param x the x-coordinate from left to draw the string
	 * @param y the y-coordinate from top to draw the string
	 */
	public UIString(String text, int x, int y)
	{
		super(null, new Rectangle(x, y, 0, 0));
		this.text = text;
	}
	
	/**
	 * Draw the String onto the screen
	 */
	@Override
	public void draw()
	{
		drawString(getRect().x, getRect().y, text, Color.black);
	}
}
