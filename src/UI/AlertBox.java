package UI;

import static helpers.Artist.FONT_SIZE;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.quickLoad;

import java.awt.Rectangle;

public class AlertBox
{
	private static final int ALERT_BOX_WIDTH = 550;
	private static final int ALERT_BOX_HEIGHT = 400;
	private static final int ALERT_BOX_X = WIDTH / 2 - ALERT_BOX_WIDTH / 2;
	private static final int ALERT_BOX_Y = HEIGHT / 2 - ALERT_BOX_HEIGHT / 2;
	private static final int ALERT_BOX_TEXT_PADDING = 30;
	private static final int ALERT_BOX_TEXT_BETWEEN_PADDING = 10;
	private static final int ALERT_BOX_TEXT_TAB_PADDING = 20;
	private static final int ALERT_BOX_IMAGE_SIZE = 100;
	private static final int ALERT_BOX_BUTTON_X = ALERT_BOX_X + ALERT_BOX_WIDTH - 30 - 128;
	private static final int ALERT_BOX_BUTTON_Y = ALERT_BOX_Y + ALERT_BOX_HEIGHT - 20 - 54;
	
	private UI alertBox;
	
	private static final int TEXT_X = ALERT_BOX_X + ALERT_BOX_TEXT_PADDING;
	private static final int TEXT_X_TABBED = TEXT_X + ALERT_BOX_TEXT_TAB_PADDING;
	private int y;
	
	public AlertBox()
	{
		alertBox = new UI();
		alertBox.addItem(new UIItem(quickLoad("white"), new Rectangle(ALERT_BOX_X,
				ALERT_BOX_Y, ALERT_BOX_WIDTH, ALERT_BOX_HEIGHT)));
		y = ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING;
	}
	
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
	
	public void addImage(String textureName, int x)
	{
		alertBox.addItem(new UIItem(quickLoad(textureName), new Rectangle(ALERT_BOX_X + x, y,
				ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_SIZE)));
	}
	
	public void addButton(String name, String textureName)
	{
		alertBox.addButton(name, textureName, ALERT_BOX_BUTTON_X, ALERT_BOX_BUTTON_Y);
	}
	
	public void draw()
	{
		alertBox.draw();
	}
	
	public boolean isButtonClicked(String buttonName)
	{
		return alertBox.isButtonClicked(buttonName);
	}
}
