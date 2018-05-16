package UI;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class Button extends UIItem
{
	public static int LEFT_MOUSE = 0;
	
	private String name;
	
	public Button(String name, Texture texture, int x, int y, int width, int height)
	{
		this(name, texture, new Rectangle(x, y, width, height));
	}
	
	public Button(String name, Texture texture, int x, int y)
	{
		this(name, texture, new Rectangle(x, y, texture.getImageWidth(), texture.getImageHeight()));
	}
	
	public Button(String name, Texture texture, Rectangle rect) {
		super(texture, rect);
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isClicked() {
		return Mouse.isButtonDown(LEFT_MOUSE) && super.getRect().contains(
			Mouse.getX(), Artist.HEIGHT - Mouse.getY() - 1
		);
	}
	
	@Override
	public String toString() {
		return "Button: " + name + " " + getRect();
	}
}
