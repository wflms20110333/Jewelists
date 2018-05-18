package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import UI.UI;
import UI.UIItem;
import UI.UIString;
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
	private static final int ALERT_BOX_TEXT_PADDING = 30;
	private static final int ALERT_BOX_TEXT_BETWEEN_PADDING = 10;
	private static final int ALERT_BOX_TEXT_TAB_PADDING = 20;
	
	private static final int ALERT_BOX_PADDING = 100;
	private static final int ALERT_BOX_IMAGE_PADDING = ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING + 
			ALERT_BOX_TEXT_TAB_PADDING * 2 + FONT_SIZE * 4 + ALERT_BOX_TEXT_BETWEEN_PADDING * 2;
	private static final int ALERT_BOX_IMAGE_SIZE = 100;
	
	
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
		alertBox.addItem(new UIString("Click on cells to set them.", ALERT_BOX_X + ALERT_BOX_TEXT_PADDING,
				ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING));
		alertBox.addItem(new UIString("Press the right arrow key to", ALERT_BOX_X + ALERT_BOX_TEXT_PADDING,
				ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING + ALERT_BOX_TEXT_TAB_PADDING + FONT_SIZE));
		alertBox.addItem(new UIString("switch between drawing", ALERT_BOX_X + ALERT_BOX_TEXT_PADDING + 
				ALERT_BOX_TEXT_TAB_PADDING, ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING + ALERT_BOX_TEXT_TAB_PADDING + 
				FONT_SIZE * 2 + ALERT_BOX_TEXT_BETWEEN_PADDING));
		alertBox.addItem(new UIString("water tiles and cave tiles.", ALERT_BOX_X + ALERT_BOX_TEXT_PADDING + 
				ALERT_BOX_TEXT_TAB_PADDING, ALERT_BOX_Y + ALERT_BOX_TEXT_PADDING + ALERT_BOX_TEXT_TAB_PADDING + 
				FONT_SIZE * 3 + ALERT_BOX_TEXT_BETWEEN_PADDING * 2));
		alertBox.addItem(new UIItem(quickLoad(TileType.Water.textureName), new Rectangle(ALERT_BOX_X + 
				ALERT_BOX_PADDING, ALERT_BOX_IMAGE_PADDING, ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_SIZE)));
		alertBox.addItem(new UIItem(quickLoad(TileType.Cave.textureName), new Rectangle(ALERT_BOX_X + ALERT_BOX_PADDING * 2 + 
				ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_PADDING, ALERT_BOX_IMAGE_SIZE, ALERT_BOX_IMAGE_SIZE)));
		
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