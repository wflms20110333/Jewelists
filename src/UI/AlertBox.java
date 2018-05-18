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
 */
public class AlertBox
{
	/**
	 * The dimensions of the AlertBox.
	 */
	public static final int ALERT_BOX_WIDTH = 600;
	public static final int ALERT_BOX_HEIGHT = 400;
	private static final int ALERT_BOX_X = WIDTH / 2 - ALERT_BOX_WIDTH / 2;
	private static final int ALERT_BOX_Y = HEIGHT / 2 - ALERT_BOX_HEIGHT / 2;
	
	/**
	 * The padding values of the AlertBox.
	 */
	private static final int ALERT_BOX_TEXT_PADDING = 30;
	private static final int ALERT_BOX_TEXT_BETWEEN_PADDING = 10;
	private static final int ALERT_BOX_TEXT_TAB_PADDING = 20;
	
	/**
	 * The default size of images in the AlertBox.
	 */
	private static final int ALERT_BOX_IMAGE_SIZE = 100;
	
	/**
	 * The dimensions of the button of the AlertBox.
	 */
	private static final int ALERT_BOX_BUTTON_X = ALERT_BOX_X + ALERT_BOX_WIDTH - 30 - 128;
	private static final int ALERT_BOX_BUTTON_Y = ALERT_BOX_Y + ALERT_BOX_HEIGHT - 20 - 54;
	
	/**
	 * The padding values for the text of the AlertBox.
	 */
	private static final int TEXT_X = ALERT_BOX_X + ALERT_BOX_TEXT_PADDING;
	private static final int TEXT_X_TABBED = TEXT_X + ALERT_BOX_TEXT_TAB_PADDING;
	
	/**
	 * The user interface of the AlertBox.
	 */
	private UI alertBox;
	
	/**
	 * The y coordinate for adding UIItems to the AlertBox.
	 */
	private int y;
	
	/**
	 * Constructs an AlertBox.
	 */
	public AlertBox()
	{
		alertBox = new UI();
		alertBox.addItem(new UIItem(quickLoad("white"), new Rectangle(ALERT_BOX_X,
				ALERT_BOX_Y, ALERT_BOX_WIDTH, ALERT_BOX_HEIGHT)));
		y = ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING;
	}
	
	/**
	 * Adds a string to the alert box.
	 * 
	 * @param segments the segments composing the string; each will be
	 * 		  displayed on its own line
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
	 * Adds an image to the alert box.
	 * 
	 * @param textureName the name of the texture of the image
	 * @param x the x coordinate (in pixels) of the image
	 */
	public void addImage(String textureName, int x)
	{
		alertBox.addItem(new UIItem(quickLoad(textureName), new Rectangle(ALERT_BOX_X + x, y,
				ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_SIZE)));
	}
	
	/**
	 * Adds an image to the alert box.
	 * 
	 * @param texture the texture of the image
	 * @param x the x coordinate (in pixels) of the image
	 * @param width the width (in pixels) of the image
	 * @param height the height (in pixels) of the image
	 */
	public void addImage(Texture texture, int x, int width, int height)
	{
		alertBox.addItem(new UIItem(texture, new Rectangle(ALERT_BOX_X + x, y, width, height)));
	}
	
	/**
	 * Add a button to the alert box.
	 * 
	 * @param name the name of the button
	 * @param textureName the name of the texture of the button
	 */
	public void addButton(String name, String textureName)
	{
		alertBox.addButton(name, textureName, ALERT_BOX_BUTTON_X, ALERT_BOX_BUTTON_Y);
	}
	
	/**
	 * Draws the alert box,
	 */
	public void draw()
	{
		alertBox.draw();
	}
	
	/**
	 * Determines if a given button on the alert box is clicked.
	 * 
	 * @param buttonName the name of the given button
	 * @return whether the given button is clicked
	 */
	public boolean isButtonClicked(String buttonName)
	{
		return alertBox.isButtonClicked(buttonName);
	}
}
