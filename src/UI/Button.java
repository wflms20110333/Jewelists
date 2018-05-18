package UI;

import static helpers.Artist.drawString;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class Button extends UIItem
{
	private static final int PADDING = 10;
	
	public static int LEFT_MOUSE = 0;
	
	private String name;
	private String text;
	
	public Button(String name, Texture texture, int x, int y, int width, int height)
	{
		this(name, texture, new Rectangle(x, y, width, height), null);
	}
	
	public Button(String name, Texture texture, int x, int y)
	{
		this(name, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()), null);
	}
	
	public Button(String name, Texture texture, int x, int y, String text)
	{
		this(name, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()), text);
	}
	
	public Button(String name, Texture texture, Rectangle rect, String text)
	{
		super(texture, rect);
		this.name = name;
		this.text = text;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isClicked()
	{
		return Mouse.isButtonDown(LEFT_MOUSE) && super.getRect().contains(
			Mouse.getX(), Artist.HEIGHT - Mouse.getY() - 1
		);
	}
	
	@Override
	public void draw()
	{
		super.draw();
		if (text != null)
			drawString(getRect().x + PADDING, getRect().y + PADDING, text, Color.black);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
