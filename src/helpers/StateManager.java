package helpers;

import org.lwjgl.input.Keyboard;

import data.Editor;
import data.Game;
import data.MainMenu;
import data.Settings;

/**
 * The StateManager class manages the state of the game blah blah
 * 
 * @author Elizabeth Zou
 * Dependencies: lwjgl (to manage keyboard inputs)
 */
public class StateManager
{
	/**
	 * The GameState enum represents different states of the game.
	 * 
	 * @author Elizabeth Zou
	 */
	public static enum GameState
	{
		/**
		 * Main menu of the game, where users can start the game, edit keyboard
		 * commands, customize a map layout, or quit.
		 */
		MAINMENU,
		
		/**
		 * The actual gameplay of the game.
		 */
		GAME,
		
		/**
		 * The editor of the game, where users can customize a map layout.
		 */
		EDITOR,
		
		/**
		 * The settings of the game, where users can edit keyboard commands.
		 */
		SETTINGS
	}
	
	/**
	 * The current game state of the game, set to the main menu at launch of
	 * the application.
	 */
	private static GameState gameState = GameState.MAINMENU;
	
	/**
	 * The main menu of the game.
	 */
	private static MainMenu mainMenu;
	
	/**
	 * The actual game play of the game.
	 */
	private static Game game;
	
	/**
	 * The map editor of the game.
	 */
	private static Editor editor;
	
	/**
	 * The keyboard settings of the game.
	 */
	private static Settings settings;
	
	/**
	 * The wait time before button clicking is allowed.
	 */
	public static final int COUNT_LIMIT = 10;
	
	/**
	 * The number of key commands each player has.
	 */
	public static final int NUM_KEYS = 8;
	
	/**
	 * The key commands of the players.
	 */
	private static int[][] keys = {
			{ Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT, Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T },
			{ Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT, Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K }
	};
	
	/**
	 * Updates the state of the game.
	 */
	public static void update()
	{
		switch (gameState)
		{
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAME:
			if (game == null)
				game = new Game(keys);
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor();
			editor.update();
			break;
		case SETTINGS:
			if (settings == null)
				settings = new Settings();
			settings.update();
			break;
		}
	}
	
	/**
	 * Sets the state of the game.
	 * 
	 * @param newState the new state of the game
	 */
	public static void setState(GameState newState)
	{
		if (newState == GameState.SETTINGS && settings != null)
			settings.resetCount();
		if (newState == GameState.MAINMENU && mainMenu != null)
			mainMenu.resetCount();
		if (newState == GameState.EDITOR)
			editor = new Editor();
		gameState = newState;
	}
	
	/**
	 * Returns a given player's key command.
	 * 
	 * @param playerNumber the number of the given player
	 * @param index the index of the key to be retrieved
	 * @return the given player's key command
	 */
	public static int getKey(int playerNumber, int index)
	{
		return keys[playerNumber][index];
	}
	
	/**
	 * Sets a new keyboard command.
	 * 
	 * @param playerNumber the number of the player to set the new command
	 * @param index the index of the command
	 * @param key the new keyboard command
	 */
	public static void setKeys(int playerNumber, int index, int key)
	{
		keys[playerNumber][index] = key;
	}
}
