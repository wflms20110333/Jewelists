package UI;

import static helpers.Artist.FONT_SIZE;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.quickLoad;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

/**
 * Represents the alert box that appears after a game
 * 
 * @author Elizabeth Zou
 * Dependencies: slick (for managing textures on the alert box)
 */
public class AlertBox
{
	
	// Dimensions of the alert box
	public static final int ALERT_BOX_WIDTH = 600;
	public static final int ALERT_BOX_HEIGHT = 400;
	private static final int ALERT_BOX_X = WIDTH / 2 - ALERT_BOX_WIDTH / 2;
	private static final int ALERT_BOX_Y = HEIGHT / 2 - ALERT_BOX_HEIGHT / 2;
	
	// Padding values
	private static final int ALERT_BOX_TEXT_PADDING = 30;
	private static final int ALERT_BOX_TEXT_BETWEEN_PADDING = 10;
	private static final int ALERT_BOX_TEXT_TAB_PADDING = 20;
	
	// Size of image
	private static final int ALERT_BOX_IMAGE_SIZE = 100;
	
	// Dimensions of the button
	private static final int ALERT_BOX_BUTTON_X = ALERT_BOX_X + ALERT_BOX_WIDTH - 30 - 128;
	private static final int ALERT_BOX_BUTTON_Y = ALERT_BOX_Y + ALERT_BOX_HEIGHT - 20 - 54;
	
	// Padding on text
	private static final int TEXT_X = ALERT_BOX_X + ALERT_BOX_TEXT_PADDING;
	private static final int TEXT_X_TABBED = TEXT_X + ALERT_BOX_TEXT_TAB_PADDING;
	
	private UI alertBox;
	
	private int y;
	
	/**
	 * Create a new alert box
	 */
	public AlertBox()
	{
		alertBox = new UI();
		alertBox.addItem(new UIItem(quickLoad("white"), new Rectangle(ALERT_BOX_X,
				ALERT_BOX_Y, ALERT_BOX_WIDTH, ALERT_BOX_HEIGHT)));
		y = ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING;
	}
	
	/**
	 * Add string into the Alert box
	 * @param segments the array of strings. Each position is a line
	 */
	public void addString(String[] segments)
	{
		alertBox.addItem(new UIString(segments[0], TEXT_X, y));
		for (int i = 1; i < segments.length; i++)
		{
			y += FONT_SIZE + ALERT_BOX_TEXT_BETWEEN_PADDING;
			alertBox.addItem(new UIString(segments[i], TEXT_X_TABBED, y));
		}
		y += FONT_SIZE + ALERT_BOX_TEXT_TAB_PADDING;
	}
	
	/**
	 * Add an image onto the box
	 * @param textureName the texture of the image
	 * @param x the x-coordinate of the image
	 */
	public void addImage(String textureName, int x)
	{
		alertBox.addItem(new UIItem(quickLoad(textureName), new Rectangle(ALERT_BOX_X + x, y,
				ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_SIZE)));
	}
	
	/**
	 * Add an image onto the box
	 * 
	 * @param texture the texture of the image
	 * @param x the x coordinate of the image
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public void addImage(Texture texture, int x, int width, int height)
	{
		alertBox.addItem(new UIItem(texture, new Rectangle(ALERT_BOX_X + x, y, width, height)));
	}
	
	/**
	 * Add a button onto the box
	 * @param name the name of the button
	 * @param textureName the name of the texture on the button
	 */
	public void addButton(String name, String textureName)
	{
		alertBox.addButton(name, textureName, ALERT_BOX_BUTTON_X, ALERT_BOX_BUTTON_Y);
	}
	
	/**
	 * Draw the alert box
	 */
	public void draw()
	{
		alertBox.draw();
	}
	
	/**
	 * Determine if a button on the box is clicked
	 * @param buttonName the name of the button
	 * @return whether the button on the box is clicked
	 */
	public boolean isButtonClicked(String buttonName)
	{
		return alertBox.isButtonClicked(buttonName);
	}
}
