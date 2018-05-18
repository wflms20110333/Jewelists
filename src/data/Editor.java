package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import UI.AlertBox;
import UI.UI;
import helpers.StateManager;

import static helpers.Artist.*;
import static helpers.StateManager.*;

/**
 * The Editor class allows users to customize the map layout of their games.
 * 
 * @author Elizabeth Zou
 */
public class Editor
{
	/**
	 * Class constants for creating the alert box.
	 */
	private static final int ALERT_BOX_PADDING = 50;
	private static final int ALERT_BOX_IMAGE_SIZE = 100;
	
	/**
	 * The user interface of the Editor.
	 */
	private UI menuUI;
	
	/**
	 * The user interface of the Editor's alert box.
	 */
	private AlertBox alertBox;
	
	/**
	 * Whether or not the alert box is displayed.
	 */
	private boolean alert;
	
	/**
	 * The tile grid that the user is editing.
	 */
	private TileGrid grid;
	
	/**
	 * The types of tiles that the user can put on the customized map.
	 */
	private TileType[] types;
	
	/**
	 * The index in types that the user is setting tiles to.
	 */
	private int index;
	
	/**
	 * The number of times {@link #update} is called after the game state is
	 * set to main menu.
	 */
	private int count = 0;
	
	/**
	 * Constructs an Editor.
	 */
	public Editor()
	{
		menuUI = new UI();
		menuUI.addButton("Play", "button_play", 0, 0);
		
		alertBox = new AlertBox();
		alertBox.addString(new String[]{"Click on cells to set them."});
		alertBox.addString(new String[]{"Press the right arrow key to", "switch between drawing", "water tiles and cave tiles."});
		alertBox.addImage(TileType.Water.textureName, ALERT_BOX_PADDING);
		alertBox.addImage("change_tiles", ALERT_BOX_PADDING + ALERT_BOX_IMAGE_SIZE);
		alertBox.addImage(TileType.Cave.textureName, ALERT_BOX_PADDING + ALERT_BOX_IMAGE_SIZE * 2);
		alertBox.addButton("Okay", "button_okay");
		
		this.alert = true;
		this.grid = new TileGrid();
		this.types = new TileType[2];
		this.types[0] = TileType.Water;
		this.types[1] = TileType.Cave;
		this.index = 0;
	}
	
	/**
	 * Updates the state of the editor. A tile is set if the mouse clicks on
	 * it, and the tile type that tiles are set to move through types as the
	 * right arrow is pressed.
	 */
	public void update()
	{
		grid.draw();
		if (alert)
			alertBox.draw();
		else
			menuUI.draw();
		
		if (count < StateManager.COUNT_LIMIT)
			count++;
		else
		{
			updateButtons();
			if (!alert)
			{ 
				if (Mouse.isButtonDown(0))
					setTile();
				while (Keyboard.next())
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState())
						moveIndex();
			}
		}
	}
	
	/**
	 * Checks for clicking of the buttons, and carries out respective actions.
	 */
	public void updateButtons()
	{
		if (alert)
		{
			if (alertBox.isButtonClicked("Okay"))
			{
				alert = false;
				count = 0;
			}
		}
		else
		{
			if (menuUI.isButtonClicked("Play"))
			{
				game = new Game(grid, keys);
				setState(GameState.GAME);
			}
		}
	}
	
	/**
	 * Sets a tile to a new type.
	 */
	private void setTile()
	{
		int x = (int) Math.floor(Mouse.getX() / TileGrid.SIZE);
		int y = (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TileGrid.SIZE);
		if (grid.validIndex(x, y) && grid.getTile(x, y).getType() != TileType.Dirt)
			grid.setTile(x, y, types[index]);
	}
	
	/**
	 * Moves the index in types that the user is setting tiles to.
	 */
	private void moveIndex()
	{
		index++;
		index %= types.length;
	}
}