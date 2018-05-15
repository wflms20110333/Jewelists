package UI;

import static helpers.Artist.quickLoad;

import java.util.ArrayList;

/**
 * The UI class represents user interfaces that can be displayed to users.
 * 
 * @author Elizabeth Zou
 */
public class UI
{
	private ArrayList<UIItem> uiList;
	
	/**
	 * Constructs a UI.
	 */
	public UI()
	{
		uiList = new ArrayList<UIItem>();
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
		uiList.add(new Button(name, quickLoad(textureName), x, y));
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
		return getButton(buttonName).isClicked();
	}
	
	/**
	 * Returns the button with the given name, null if no such button exists.
	 * 
	 * @param buttonName the given name
	 * @return the button with the given name, null if no such button exists
	 */
	private Button getButton(String buttonName)
	{
		for (UIItem b : uiList)
			if (b instanceof Button && ((Button) b).getName().equals(buttonName))
				return (Button) b;
		return null;
	}
	
	/**
	 * Draws all the buttons in the ui.
	 */
	public void draw()
	{
		for (UIItem uiItem : uiList)
			uiItem.draw();
	}
}
