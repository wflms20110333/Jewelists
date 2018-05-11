package UI;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import static helpers.Artist.*;

/**
 * The UI class represents user interfaces that can be displayed to users.
 * 
 * @author Elizabeth Zou
 */
public class UI
{
	/**
	 * The list of buttons managed and displayed by the UI.
	 */
	private ArrayList<Button> buttonList;
	
	/**
	 * Constructs a UI.
	 */
	public UI()
	{
		buttonList = new ArrayList<Button>();
	}
	
	/**
	 * Adds a button to the UI.
	 * 
	 * @param name the name of the button
	 * @param textureName the name of the texture of the button
	 * @param x the x coordinate (in pixels) of the button
	 * @param y the y coordinate (in pixels) of the button
	 */
	public void addButton(String name, String textureName, int x, int y)
	{
		buttonList.add(new Button(name, quickLoad(textureName), x, y));
	}
	
	/**
	 * Returns whether or not a given button is clicked
	 * 
	 * @pre the mouse is clicked
	 * @param buttonName the name of the given button
	 * @return whether or not the given button is clicked
	 */
	public boolean isButtonClicked(String buttonName)
	{
		Button b = getButton(buttonName);
		float mouseX = Mouse.getX();
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (mouseX > b.getX() && mouseX < b.getX() + b.getWidth() &&
			mouseY > b.getY() && mouseY < b.getY() + b.getHeight())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the button with the given name, null if no such button exists.
	 * 
	 * @param buttonName the given name
	 * @return the button with the given name, null if no such button exists
	 */
	private Button getButton(String buttonName)
	{
		for (Button b : buttonList)
			if (b.getName().equals(buttonName))
				return b;
		return null;
	}
	
	/**
	 * Draws all the buttons in the ui.
	 */
	public void draw()
	{
		for (Button b : buttonList)
			drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
	}
}
