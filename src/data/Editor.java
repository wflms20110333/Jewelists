package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import UI.UI;
import UI.UIItem;
import helpers.StateManager;

import static helpers.Artist.*;
import static helpers.StateManager.*;

import java.awt.Rectangle;

/**
 * The Editor class allows users to customize the map layout of their games.
 * 
 * @author Elizabeth Zou
 */
public class Editor
{
	
	private static final int ALERT_BOX_WIDTH = 600;
	private static final int ALERT_BOX_HEIGHT = 400;
	private static final int ALERT_BOX_X = WIDTH / 2 - ALERT_BOX_WIDTH / 2;
	private static final int ALERT_BOX_Y = HEIGHT / 2 - ALERT_BOX_HEIGHT / 2;
	
	
	/**
	 * The user interface of the Editor.
	 */
	private UI menuUI;
	
	private UI alertBox;
	
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
		
		alertBox = new UI();
		alertBox.addItem(new UIItem(quickLoad("white"), new Rectangle(ALERT_BOX_X, ALERT_BOX_Y, ALERT_BOX_WIDTH, ALERT_BOX_HEIGHT)));
		
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
		menuUI.draw();
		alertBox.draw();
		drawString(ALERT_BOX_X, ALERT_BOX_Y, "hello", Color.blue);
		
		if (count < StateManager.COUNT_LIMIT)
			count++;
		else
			updateButtons();
		
		if (Mouse.isButtonDown(0))
			setTile();
		while (Keyboard.next())
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState())
				moveIndex();
	}
	
	/**
	 * Checks for clicking of the buttons, and carries out respective actions.
	 */
	public void updateButtons()
	{
		if (menuUI.isButtonClicked("Play"))
		{
			game = new Game(grid, keys);
			setState(GameState.GAME);
		}
	}
	
	/**
	 * Sets a tile to a new type.
	 */
	private void setTile()
	{
		grid.setTile((int) Math.floor(Mouse.getX() / TileGrid.SIZE), 
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TileGrid.SIZE), types[index]);
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