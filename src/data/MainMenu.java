package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

/**
 * The MainMenu class represents the main menu screen of the game.
 * 
 * @author Elizabeth Zou
 */
public class MainMenu
{
	/**
	 * The background of the MainMenu.
	 */
	private Texture background;
	
	/**
	 * The user interface of the MainMenu.
	 */
	private UI menuUI;
	
	/**
	 * The number of times {@link #update} is called after the game state is
	 * set to main menu.
	 */
	private int count = 0;
	
	/**
	 * Constructs a MainMenu.
	 */
	public MainMenu()
	{
		background = quickLoad("white");
		menuUI = new UI();
		menuUI.addButton("Play", "button_play", WIDTH / 2 - 128, (int) (HEIGHT * 0.45));
		menuUI.addButton("Edit", "button_edit", WIDTH / 2 - 128, (int) (HEIGHT * 0.55));
		menuUI.addButton("Quit", "button_quit", WIDTH / 2 - 128, (int) (HEIGHT * 0.65));
		menuUI.addButton("Settings", "button_settings", 50, 50);
	}
	
	/**
	 * Updates the state of the main menu.
	 */
	public void update()
	{
		drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT * 2);
		menuUI.draw();
		if (count < 10)
			count++;
		else
			updateButtons();
	}
	
	/**
	 * Checks for clicking of the buttons, and carries out respective actions.
	 */
	private void updateButtons()
	{
		if (menuUI.isButtonClicked("Play"))
			StateManager.setState(GameState.GAME);
		else if (menuUI.isButtonClicked("Edit"))
			StateManager.setState(GameState.EDITOR);
		else if (menuUI.isButtonClicked("Quit"))
			System.exit(0);
		else if (menuUI.isButtonClicked("Settings"))
			StateManager.setState(GameState.SETTINGS);
	}
	
	/**
	 * Resets the number of times {@link #update} is called after the game
	 * state is set to main menu; this method is called when the game state is
	 * set to main menu from another state.
	 */
	public void resetCount()
	{
		count = 0;
	}
}
