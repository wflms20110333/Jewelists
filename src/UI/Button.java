package UI;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

public class Button extends UIItem
{
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
}
