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
	 * Constructs a user interface display
	 */
	public UI()
	{
		uiList = new ArrayList<UIItem>();
	}

	/**
	 * Add a button to the display
	 * 
	 * @param name the button's name, used to identify it
	 * @param textureName the name of the texture (name of the file)
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 */
	public void addButton(String name, String textureName, int x, int y)
	{
		uiList.add(new Button(name, quickLoad(textureName), x, y));
	}
	
	/**
	 * Add a button to the display
	 * 
	 * @param name the button's name, used to identify it
	 * @param textureName the name of the texture (name of the file)
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public void addButton(String name, String textureName, int x, int y, int width, int height)
	{
		uiList.add(new Button(name, quickLoad(textureName), x, y, width, height));
	}
	
	/**
	 * Add a button to the display
	 * 
	 * @param name the button's name, used to identify it
	 * @param textureName the name of the texture (name of the file)
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param text the string contained inside of the button
	 */
	public void addButton(String name, String textureName, int x, int y, String text)
	{
		uiList.add(new Button(name, quickLoad(textureName), x, y, text));
	}

	/**
	 * Add a new UI Item into the interface
	 * @param item the item to add
	 */
	public void addItem(UIItem item)
	{
		uiList.add(item);
	}

	/**
	 * Remove a UI Item from the interface
	 * @param item the item to remove
	 */
	public void removeItem(UIItem item)
	{
		uiList.remove(item);
	}

	/**
	 * Determine if a button is clicked
	 * 
	 * @param buttonName the name of the button
	 * @return whether or not the button is clicked
	 */
	public boolean isButtonClicked(String buttonName)
	{
		return getButton(buttonName).isClicked();
	}

	/**
	 * Get a specific button
	 * @param buttonName the name of the button
	 * @return the button object
	 */
	public Button getButton(String buttonName)
	{
		for (UIItem b : uiList)
			if (b instanceof Button && ((Button) b).getName().equals(buttonName))
				return (Button) b;
		return null;
	}

	/**
	 * Update all the UI Items
	 */
	public void update()
	{
		for (UIItem uiItem : uiList)
			uiItem.update();
	}

	/**
	 * Draw all the UI Items on screen
	 */
	public void draw()
	{
		for (UIItem uiItem : uiList)
			uiItem.draw();
	}
}
