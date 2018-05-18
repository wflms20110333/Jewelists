package data;

import static helpers.Artist.*;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import UI.InfoBar;
import UI.Scoreboard;
import UI.UI;
import helpers.StateManager;

/**
 * The Game class represents the actual gameplay of the game.
 * 
 * @author Elizabeth Zou
 */
public class Game
{
	/**
	 * Dimensions of the info bars.
	 */
	public static final int INFO_BAR_WIDTH_TILES = 6;
	public static final int INFO_BAR_HEIGHT_TILES = 2;
	public static final int INFO_BAR_WIDTH = TileGrid.SIZE * INFO_BAR_WIDTH_TILES;
	public static final int INFO_BAR_HEIGHT = TileGrid.SIZE * INFO_BAR_HEIGHT_TILES;
	
	/**
	 * Dimensions of the scoreboard.
	 */
	public static final int SCOREBOARD_HEIGHT_TILES = 2;
	public static final int SCOREBOARD_WIDTH = WIDTH;
	public static final int SCOREBOARD_HEIGHT = TileGrid.SIZE * SCOREBOARD_HEIGHT_TILES;
	
	/**
	 * The tile grid that represents the Game.
	 */
	private TileGrid grid;
	
	/**
	 * The spawners of the Game.
	 */
	Spawner monsterSpawner;
	Spawner jewelSpawner;
	
	/**
	 * The players of the Game.
	 */
	Player[] players;
	
	/**
	 * The user interface of the Game, which displays the info bars and
	 * scoreboard.
	 */
	UI ui;
	
	/**
	 * The traps currently set up in the Game.
	 */
	ArrayList<Trap> traps;
	
	ArrayList<Projectile> projectiles;
	
	/**
	 * Constructs a Game.
	 * 
	 * @param keys the keyboard commands of the players
	 */
	public Game(int[][] keys)
	{
		this(new TileGrid(), keys);
	}
	
	/**
	 * Constructs a Game.
	 * 
	 * @param tg the tile grid that represents the game
	 * @param keys the keyboard commands of the players
	 */
	public Game(TileGrid tg, int[][] keys)
	{
		grid = tg;
		monsterSpawner = new MonsterSpawner(10, grid, quickLoad("monster"), 100);
		
		players = new Player[] {
			new Player(this, grid, keys[0], quickLoad("emoji"), Color.red),
			new Player(this, grid, keys[1], quickLoad("emoji2"), Color.green)
		};
		
		ui = new UI();
		ui.addItem(new InfoBar(players[0], null, 0, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new InfoBar(players[1], null, WIDTH - INFO_BAR_WIDTH, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new Scoreboard(null, new Rectangle((WIDTH - SCOREBOARD_WIDTH) / 2, 
			HEIGHT - SCOREBOARD_HEIGHT, SCOREBOARD_WIDTH, SCOREBOARD_HEIGHT), this, 50)
		);
		
		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(new Jewel(quickLoad("jewel_green"), grid.getTile(6, 7), grid, 1));
		jewelList.add(new Jewel(quickLoad("jewel_red"), grid.getTile(6, 7), grid, 2));
		jewelList.add(new Jewel(quickLoad("jewel_blue"), grid.getTile(6, 7), grid, 3));
		jewelList.add(new Jewel(quickLoad("jewel_purple"), grid.getTile(6, 7), grid, 4));
		jewelList.add(new Jewel(quickLoad("jewel_orange"), grid.getTile(6, 7), grid, 5));
		
		jewelSpawner = new JewelSpawner(3, grid, jewelList);
		traps = new ArrayList<>();
		projectiles = new ArrayList<>();
	}
	
	/**
	 * Updates the state of the game.
	 */
	public void update()
	{
		grid.draw();
		monsterSpawner.update();
		jewelSpawner.update();
		for (Player player : players)
			player.update();
		for (Projectile projectile : projectiles)
			projectile.update();
		ui.update();
		for (int i = traps.size() - 1; i >= 0; i--)
		{
			Trap trap = traps.get(i);
			trap.update();
			if (!trap.exists())
			{
				traps.remove(i);
				grid.removeEntity(trap.getCurrentTile());
			}
		}
	}
	
	/**
	 * Returns the players of the game.
	 * 
	 * @return the players of the game
	 */
	public Player[] getPlayers()
	{
		return players;
	}
	
	/**
	 * Ends the game. The screen returns to the main menu.
	 */
	public void end()
	{
		StateManager.setState(StateManager.GameState.MAINMENU);
		StateManager.game = null;
	}
	
	/**
	 * Adds a given trap to the game.
	 * 
	 * @param trap the given trap
	 */
	public void addTrap(Trap trap)
	{
		traps.add(trap);
	}
}
