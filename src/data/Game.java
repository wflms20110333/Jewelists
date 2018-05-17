package data;

import static helpers.Artist.*;

import java.awt.Rectangle;
import java.util.ArrayList;

import UI.InfoBar;
import UI.Scoreboard;
import UI.UI;
import helpers.StateManager;

import org.lwjgl.input.Keyboard;

/**
 * The Game class represents the actual gameplay of the game.
 * 
 * @author Elizabeth Zou
 */
public class Game
{
	public static final int INFO_BAR_WIDTH_TILES = 6;
	public static final int INFO_BAR_HEIGHT_TILES = 2;
	public static final int INFO_BAR_WIDTH = TileGrid.SIZE * INFO_BAR_WIDTH_TILES;
	public static final int INFO_BAR_HEIGHT = TileGrid.SIZE * INFO_BAR_HEIGHT_TILES;
	
	public static final int SCOREBOARD_HEIGHT_TILES = 2;
	public static final int SCOREBOARD_WIDTH = WIDTH;
	public static final int SCOREBOARD_HEIGHT = TileGrid.SIZE * SCOREBOARD_HEIGHT_TILES;
	private TileGrid grid;
	
	Spawner monsterSpawner;
	Spawner jewelSpawner;
	Player[] players;
	UI ui;
	ArrayList<Trap> traps;
	
	public Game(int[][] keys)
	{
		this(new TileGrid(), keys);
	}
	
	public Game(TileGrid tg, int[][] keys)
	{
		grid = tg;
		monsterSpawner = new MonsterSpawner(10, grid, quickLoad("monster"), 100);
		
		players = new Player[] {
			new Player(this, grid, keys[0], quickLoad("emoji")),
			new Player(this, grid, keys[1], quickLoad("emoji2"))
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
	}
	
	public void update()
	{
		grid.draw();
		monsterSpawner.update();
		jewelSpawner.update();
		for (Player player : players)
			player.update();
		ui.update();
		for (int i = traps.size() - 1; i >= 0; i--)
		{
			Trap trap = traps.get(i);
			trap.update();
			if (!trap.exists())
			{
				traps.remove(i);
				grid.removeEntity(trap.currTile());
			}
		}
	}
	
	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}
	
	public Player[] getPlayers()
	{
		return players;
	}
	
	public void end()
	{
		StateManager.setState(StateManager.GameState.MAINMENU);
		StateManager.game = null;
	}
	
	public void addTrap(Trap trap)
	{
		traps.add(trap);
	}
}
