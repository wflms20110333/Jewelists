package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

/**
 * The Settings class controls the setting of keyboard commands in the game.
 * 
 * @author Elizabeth Zou
 */
public class Settings
{
	/**
	 * The background of the Settings.
	 */
	private Texture background;
	
	/**
	 * The user interface of the Settings; allows going back to the main menu,
	 * and switching between editing player 1's keyboard commands and player
	 * 2's keyboard commands.
	 */
	private UI menuUI;
	
	/**
	 * The user interface of editing player 1's keyboard commands.
	 */
	private UI player1UI;
	
	/**
	 * The user interface of editing player 2's keyboard commands.
	 */
	private UI player2UI;
	
	/**
	 * Whether or not the Settings is currently editing player 1's keyboard
	 * commands.
	 */
	private boolean player1Screen;
	
	/**
	 * The number of times {@link #update} is called after the game state is
	 * set to settings.
	 */
	private int count = 0;
	
	/**
	 * Constructs a Settings.
	 */
	public Settings()
	{
		background = quickLoad("white");
		menuUI = new UI();
		menuUI.addButton("Back", "button_back", 50, 50);
		menuUI.addButton("Player1", "button_player1", WIDTH / 4 - 64, (int) (HEIGHT * 0.1));
		menuUI.addButton("Player2", "button_player2", WIDTH * 3 / 4 - 192, (int) (HEIGHT * 0.1));
		player1UI = new UI();
		player1UI.addButton("0", "button_key", WIDTH / 4 - 64, (int) (HEIGHT * 0.3));
		player1UI.addButton("1", "button_key", WIDTH / 4 - 64 - 100, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("2", "button_key", WIDTH / 4 - 64, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("3", "button_key", WIDTH / 4 - 64 + 100, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("4", "button_key", WIDTH * 3 / 4 - 64 - 292, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("5", "button_key", WIDTH * 3 / 4 - 64 - 192, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("6", "button_key", WIDTH * 3 / 4 - 64 - 92, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("7", "button_key", WIDTH * 3 / 4 - 64 + 8, (int) (HEIGHT * 0.3) + 100);
		player1UI.addButton("8", "button_key", WIDTH * 3 / 4 - 64 + 108, (int) (HEIGHT * 0.3) + 100);
		player2UI = new UI();
		player2UI.addButton("0", "button_key", WIDTH / 4 - 64, (int) (HEIGHT * 0.3));
		player2UI.addButton("1", "button_key", WIDTH / 4 - 64 - 100, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("2", "button_key", WIDTH / 4 - 64, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("3", "button_key", WIDTH / 4 - 64 + 100, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("4", "button_key", WIDTH * 3 / 4 - 64 - 292, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("5", "button_key", WIDTH * 3 / 4 - 64 - 192, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("6", "button_key", WIDTH * 3 / 4 - 64 - 92, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("7", "button_key", WIDTH * 3 / 4 - 64 + 8, (int) (HEIGHT * 0.3) + 100);
		player2UI.addButton("8", "button_key", WIDTH * 3 / 4 - 64 + 108, (int) (HEIGHT * 0.3) + 100);
		player1Screen = true;
	}
	
	/**
	 * Updates the state of the settings.
	 */
	public void update()
	{
		drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT * 2);
		menuUI.draw();
		if (count < StateManager.COUNT_LIMIT)
			count++;
		else
			updateButtons();
		if (player1Screen)
			player1UI.draw();
		else
			player2UI.draw();
	}
	
	/**
	 * Checks for clicking of the buttons, and carries out respective actions.
	 */
	private void updateButtons()
	{
		if (Mouse.isButtonDown(0))
		{
			if (menuUI.isButtonClicked("Back"))
				StateManager.setState(GameState.MAINMENU);
			if (player1Screen)
			{
				if (menuUI.isButtonClicked("Player2"))
					player1Screen = false;
				
				// MUST HOLD MOUSE DOWN WHILE CHOOSING NEW KEY!!
				for (int i = 0; i < 9; i++)
				{
					if (player1UI.isButtonClicked("" + i))
					{
						Keyboard.next();
						StateManager.setKeys(0, i, Keyboard.getEventKey());
					}
				}
			}
			else
			{
				if (menuUI.isButtonClicked("Player1"))
					player1Screen = true;
				
				// MUST HOLD MOUSE DOWN WHILE CHOOSING NEW KEY!!
				for (int i = 0; i < 9; i++)
				{
					if (player2UI.isButtonClicked("" + i))
					{
						Keyboard.next();
						StateManager.setKeys(1, i, Keyboard.getEventKey());
					}
				}
			}
		}
	}
	
	/**
	 * Resets the number of times {@link #update} is called after the game
	 * state is set to settings; this method is called when the game state is
	 * set to settings from another state.
	 */
	public void resetCount()
	{
		count = 0;
	}
}
