package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import UI.UI;

import static helpers.Artist.*;
import static helpers.StateManager.*;

/**
 * The Editor class allows users to customize the map layout of their games.
 * 
 * @author Elizabeth Zou
 */
public class Editor
{
	private UI menuUI;
	
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
	 * Constructs an Editor.
	 */
	public Editor()
	{
		menuUI = new UI();
		menuUI.addButton("Play", "button_play", 0, 0);
		
		this.grid = new TileGrid();
		this.types = new TileType[2];
		this.types[0] = TileType.Cave;
		this.types[1] = TileType.Water;
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
		if (Mouse.isButtonDown(0))
		{
			setTile();
		}
		while (Keyboard.next())
		{
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState())
				moveIndex();
			//if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState())
				//saveMap("mapTest", grid);
			/*
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState())
			{
				setGrid(grid);
				setState(GameState.GAME);
			}
			*/
		}
		
		updateButtons();
	}
	
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
