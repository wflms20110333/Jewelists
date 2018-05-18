package UI;

import java.awt.Rectangle;

import org.newdawn.slick.Color;

import static helpers.Artist.*;

public class UIString extends UIItem
{
	private String text;
	
	public UIString(String text, int x, int y)
	{
		super(null, new Rectangle(x, y, 0, 0));
		this.text = text;
	}
	
	@Override
	public void draw()
	{
		drawString(getRect().x, getRect().y, text, Color.black);
	}
}
