package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

import UI.AlertBox;
import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

/**
 * The MainMenu class represents the main menu screen of the game.
 * 
 * @author Elizabeth Zou
 * Dependencies: slick(to manage textures)
 */
public class MainMenu
{
	/**
	 * Constants for placing the location of items.
	 */
	private static final int SETTINGS_PADDING = 50;
	private static final int BUTTON_HALF_WIDTH = 106;
	private static final int BUTTON_WIDTH = 212;
	private static final int BUTTON_HEIGHT = 90;
	
	/**
	 * Constants for scaling the location of items.
	 */
	private static final float SCALE_TITLE = 0.1f;
	private static final float SCALE_PLAY = 0.6f;
	private static final float SCALE_EDIT = 0.7f;
	private static final float SCALE_QUIT = 0.8f;
	
	/**
	 * The background of the MainMenu.
	 */
	private Texture background;
	
	/**
	 * The title of the game.
	 */
	private Texture title;
	
	/**
	 * The user interface of the MainMenu.
	 */
	private UI menuUI;
	
	/**
	 * The alert box that pops up at the MainMenu.
	 */
	private AlertBox alertBox;
	
	/**
	 * Whether the alert box is displayed.
	 */
	private boolean alert;
	
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
		background = quickLoad("background");
		title = quickLoad("title");
		menuUI = new UI();
		menuUI.addButton("Play", "button_play", WIDTH / 2 - BUTTON_HALF_WIDTH,
				(int) (HEIGHT * SCALE_PLAY), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuUI.addButton("Edit", "button_edit", WIDTH / 2 - BUTTON_HALF_WIDTH,
				(int) (HEIGHT * SCALE_EDIT), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuUI.addButton("Quit", "button_quit", WIDTH / 2 - BUTTON_HALF_WIDTH,
				(int) (HEIGHT * SCALE_QUIT), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuUI.addButton("Settings", "button_settings", SETTINGS_PADDING, SETTINGS_PADDING);
		
		alertBox = new AlertBox();
		alertBox.addString(new String[]{"If you click a button,", "it will respond.", "Maybe just kinda slow."});
		alertBox.addString(new String[]{"ENJOY!"});
		alertBox.addButton("Okay", "button_okay");
		alert = true;
	}
	
	/**
	 * Updates the state of the main menu.
	 */
	public void update()
	{
		drawQuadTex(background, 0, 0, WIDTH, HEIGHT);
		drawQuadTex(title, WIDTH / 2 - title.getImageWidth() / 2, (int) (HEIGHT * SCALE_TITLE), title.getImageWidth(), title.getImageHeight());
		if (alert)
			alertBox.draw();
		else
			menuUI.draw();
		
		if (count < StateManager.COUNT_LIMIT)
			count++;
		else
			updateButtons();
	}
	
	/**
	 * Checks for clicking of the buttons, and carries out respective actions.
	 */
	private void updateButtons()
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
				StateManager.setState(GameState.GAME);
			else if (menuUI.isButtonClicked("Edit"))
				StateManager.setState(GameState.EDITOR);
			else if (menuUI.isButtonClicked("Quit"))
				System.exit(0);
			else if (menuUI.isButtonClicked("Settings"))
				StateManager.setState(GameState.SETTINGS);
		}
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