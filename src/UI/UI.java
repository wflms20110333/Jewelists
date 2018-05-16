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

	public void addButton(String name, String textureName, int x, int y)
	{
		uiList.add(new Button(name, quickLoad(textureName), x, y));
	}

	public void addItem(UIItem item)
	{
		uiList.add(item);
	}

	public void removeItem(UIItem item)
	{
		uiList.remove(item);
	}

	public boolean isButtonClicked(String buttonName)
	{
		return getButton(buttonName).isClicked();
	}

	private Button getButton(String buttonName)
	{
		for (UIItem b : uiList)
			if (b instanceof Button && ((Button) b).getName().equals(buttonName))
				return (Button) b;
		return null;
	}

	public void update()
	{
		for (UIItem uiItem : uiList)
			uiItem.update();
	}

	public void draw()
	{
		for (UIItem uiItem : uiList)
			uiItem.draw();
	}
}
