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
	public static GameState gameState = GameState.MAINMENU;
	
	/**
	 * The main menu of the game.
	 */
	public static MainMenu mainMenu;
	
	/**
	 * The actual game play of the game.
	 */
	public static Game game;
	
	/**
	 * The map editor of the game.
	 */
	public static Editor editor;
	
	/**
	 * The keyboard settings of the game.
	 */
	public static Settings settings;
	
	public static int[][] keys = {
			{ Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT, Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J },
			{ Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT, Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T, Keyboard.KEY_Y }
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
		gameState = newState;
	}
	
	/*
	public static void setGame(Game newGame)
	{
		game = newGame;
	}
	*/
	
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
