package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.AlertBox;
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
	 * The user interface of the Setting's alert box.
	 */
	private AlertBox alertBox;
	
	/**
	 * Whether or not the alert box is displayed.
	 */
	private boolean alert;
	
	/**
	 * The number of times {@link #update} is called after the game state is
	 * set to settings.
	 */
	private int count = 0;
	
	private static final int L = WIDTH / 4;
	private static final int R = WIDTH * 3 / 4;
	private static final int H = (int) (HEIGHT * 0.5);
	private static final int[] X = {L, L - 100, L, L + 100, R - 250, R - 150, R - 50, R + 50};
	private static final int[] Y = {H - 100, H, H, H, H, H, H, H};
	
	/**
	 * Constructs a Settings.
	 */
	public Settings()
	{
		background = quickLoad("background");
		menuUI = new UI();
		menuUI.addButton("Back", "button_back", 50, 50);
		menuUI.addButton("Player1", "button_player1", WIDTH / 4 - 64, (int) (HEIGHT * 0.1));
		menuUI.addButton("Player2", "button_player2", WIDTH * 3 / 4 - 192, (int) (HEIGHT * 0.1));
		player1UI = new UI();
		for (int i = 0; i < StateManager.NUM_KEYS; i++)
			player1UI.addButton("" + i, "button_key", X[i], Y[i], getKeyText(StateManager.getKey(0, i)));
		player2UI = new UI();
		for (int i = 0; i < StateManager.NUM_KEYS; i++)
			player2UI.addButton("" + i, "button_key", X[i], Y[i], getKeyText(StateManager.getKey(1, i)));
		player1Screen = true;
		
		alertBox = new AlertBox();
		alertBox.addString(new String[]{"To set up a command:", "Click on the command to",
				"set and press down on the", "intended keyboard command", "at the same time."});
		alertBox.addButton("Okay", "button_okay");
		alert = true;
	}
	
	/**
	 * Updates the state of the settings.
	 */
	public void update()
	{
		drawQuadTex(background, 0, 0, WIDTH, HEIGHT);
		menuUI.draw();
		if (player1Screen)
			player1UI.draw();
		else
			player2UI.draw();
		
		if (alert)
			alertBox.draw();
		
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
			if (Mouse.isButtonDown(0))
			{
				if (menuUI.isButtonClicked("Back"))
					StateManager.setState(GameState.MAINMENU);
				if (player1Screen)
				{
					if (menuUI.isButtonClicked("Player2"))
						player1Screen = false;
					
					// MUST HOLD MOUSE DOWN WHILE CHOOSING NEW KEY!!
					for (int i = 0; i < StateManager.NUM_KEYS; i++)
					{
						if (player1UI.isButtonClicked("" + i))
						{
							Keyboard.next();
							StateManager.setKeys(0, i, Keyboard.getEventKey());
							player1UI.getButton("" + i).setText(getKeyText(StateManager.getKey(0, i)));
						}
					}
				}
				else
				{
					if (menuUI.isButtonClicked("Player1"))
						player1Screen = true;
					
					// MUST HOLD MOUSE DOWN WHILE CHOOSING NEW KEY!!
					for (int i = 0; i < StateManager.NUM_KEYS; i++)
					{
						if (player2UI.isButtonClicked("" + i))
						{
							Keyboard.next();
							StateManager.setKeys(1, i, Keyboard.getEventKey());
							player2UI.getButton("" + i).setText(getKeyText(StateManager.getKey(1, i)));
						}
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
	
	public String getKeyText(int key)
	{
		switch (key)
		{
		case Keyboard.KEY_0: return "0";
		case Keyboard.KEY_1: return "1";
		case Keyboard.KEY_2: return "2";
		case Keyboard.KEY_3: return "3";
		case Keyboard.KEY_4: return "4";
		case Keyboard.KEY_5: return "5";
		case Keyboard.KEY_6: return "6";
		case Keyboard.KEY_7: return "7";
		case Keyboard.KEY_8: return "8";
		case Keyboard.KEY_9: return "9";
		case Keyboard.KEY_A: return "A";
		case Keyboard.KEY_B: return "B";
		case Keyboard.KEY_C: return "C";
		case Keyboard.KEY_D: return "D";
		case Keyboard.KEY_E: return "E";
		case Keyboard.KEY_F: return "F";
		case Keyboard.KEY_G: return "G";
		case Keyboard.KEY_H: return "H";
		case Keyboard.KEY_I: return "I";
		case Keyboard.KEY_J: return "J";
		case Keyboard.KEY_K: return "K";
		case Keyboard.KEY_L: return "L";
		case Keyboard.KEY_M: return "M";
		case Keyboard.KEY_N: return "N";
		case Keyboard.KEY_O: return "O";
		case Keyboard.KEY_P: return "P";
		case Keyboard.KEY_Q: return "Q";
		case Keyboard.KEY_R: return "R";
		case Keyboard.KEY_S: return "S";
		case Keyboard.KEY_T: return "T";
		case Keyboard.KEY_U: return "U";
		case Keyboard.KEY_V: return "V";
		case Keyboard.KEY_W: return "W";
		case Keyboard.KEY_X: return "X";
		case Keyboard.KEY_Y: return "Y";
		case Keyboard.KEY_Z: return "Z";
		case Keyboard.KEY_TAB: return "Tab";
		case Keyboard.KEY_CAPITAL: return "Caps";
		case Keyboard.KEY_LSHIFT: return "L Sh";
		case Keyboard.KEY_EQUALS: return "=";
		case Keyboard.KEY_LBRACKET: return "[";
		case Keyboard.KEY_RBRACKET: return "]";
		case Keyboard.KEY_BACKSLASH: return "\\";
		case Keyboard.KEY_SEMICOLON: return ";";
		case Keyboard.KEY_RETURN: return "Ret";
		case Keyboard.KEY_COMMA: return ",";
		case Keyboard.KEY_PERIOD: return ".";
		case Keyboard.KEY_SLASH: return "/";
		case Keyboard.KEY_RSHIFT: return "R Sh";
		case Keyboard.KEY_UP: return "U Ar";
		case Keyboard.KEY_LEFT: return "L Ar";
		case Keyboard.KEY_DOWN: return "D Ar";
		case Keyboard.KEY_RIGHT: return "R Ar";
		}
		return "N/A";
	}
}
