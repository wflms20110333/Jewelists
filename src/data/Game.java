package data;

import static helpers.Artist.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Color;

import UI.InfoBar;
import UI.Scoreboard;
import UI.UI;
import helpers.Clock;
import helpers.StateManager;

/**
 * The Game class represents the actual gameplay of the game.
 * 
 * @author Elizabeth Zou
 * @author An Nguyen
 * Dependencies: slick(to manage colors)
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
	 * The duration of the game.
	 */
	public static final int GAME_LENGTH = 300;
	public static final int MONSTER_SPEED = 220;
	
	/**
	 * The tile grid that represents the Game.
	 */
	private TileGrid grid;
	
	/**
	 * The spawners of the Game.
	 */
	private Spawner monsterSpawner;
	private Spawner jewelSpawner;
	
	/**
	 * The players of the Game.
	 */
	private Player[] players;
	
	/**
	 * The user interface of the Game, which displays the info bars and
	 * scoreboard.
	 */
	private UI ui;
	
	/**
	 * The traps currently set up in the Game.
	 */
	private ArrayList<Trap> traps;
	
	/**
	 * The projectiles currently in the Game.
	 */
	private ArrayList<Projectile> projectiles;
	
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
		monsterSpawner = new MonsterSpawner(10, grid, quickLoad("monster"), MONSTER_SPEED);
		
		players = new Player[] {
			new Player(this, grid, keys[0], quickLoad("emoji"), Color.red, "red"),
			new Player(this, grid, keys[1], quickLoad("emoji2"), Color.blue, "blue")
		};
		
		ui = new UI();
		ui.addItem(new InfoBar(players[0], null, 0, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new InfoBar(players[1], null, WIDTH - INFO_BAR_WIDTH, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new Scoreboard(null, new Rectangle((WIDTH - SCOREBOARD_WIDTH) / 2, 
			HEIGHT - SCOREBOARD_HEIGHT, SCOREBOARD_WIDTH, SCOREBOARD_HEIGHT), this, GAME_LENGTH)
		);
		
		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(new Jewel(quickLoad("jewel_red"), grid.getTile(6, 7), grid, 1));
		jewelList.add(new Jewel(quickLoad("jewel_orange"), grid.getTile(6, 7), grid, 2));
		jewelList.add(new Jewel(quickLoad("jewel_green"), grid.getTile(6, 7), grid, 3));
		jewelList.add(new Jewel(quickLoad("jewel_blue"), grid.getTile(6, 7), grid, 4));
		jewelList.add(new Jewel(quickLoad("jewel_purple"), grid.getTile(6, 7), grid, 5));
		
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
		
		int numOnCenter = 0;
		for (Player player : players)
			if (player.getSprite().onCenterArea())
				numOnCenter++;
		for (Player player : players) {
			if (numOnCenter < 2 && player.getSprite().onCenterArea())
				player.addScore(Clock.getSeconds());
			player.update();
		}
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
			Projectile projectile = iterator.next();
			if (!projectile.exists())
				iterator.remove();
			else
				projectile.update();
		}
		
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
	 * Adds a given projectile to the game.
	 * 
	 * @param projectile the given projectile
	 */
	public void addProjectile(Projectile projectile)
	{
		projectiles.add(projectile);
	}
	
	/**
	 * Returns a list of the projectiles currently in the game.
	 * 
	 * @return a list of the projectiles currently in the game.
	 */
	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	/**
	 * Ends the game. The screen returns to the main menu.
	 */
	public void end()
	{
		StateManager.setState(StateManager.GameState.MAINMENU);
		StateManager.setGame(null);
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
