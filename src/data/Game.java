package data;

import static helpers.Artist.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
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
	
	public Game()
	{
		this(new TileGrid());
	}
	
	public Game(TileGrid tg)
	{
		grid = tg;
		Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 100);
		monsterSpawner = new MonsterSpawner(3, e);
		
		int[] keys1 = { Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		int[] keys2 = { Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT,
				Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T, Keyboard.KEY_Y };
		players = new Player[] {
			new Player(this, grid, keys1, quickLoad("emoji")), //, TileType.Deposit1, TileType.Deposit2),
			new Player(this, grid, keys2, quickLoad("emoji2")) //, TileType.Deposit2, TileType.Deposit1)
		};
		
		ui = new UI();
		ui.addItem(new InfoBar(players[0], null, 0, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new InfoBar(players[1], null, WIDTH - INFO_BAR_WIDTH, 0, INFO_BAR_WIDTH, INFO_BAR_HEIGHT));
		ui.addItem(new Scoreboard(null, new Rectangle((WIDTH - SCOREBOARD_WIDTH) / 2, 
			HEIGHT - SCOREBOARD_HEIGHT, SCOREBOARD_WIDTH, SCOREBOARD_HEIGHT), this, 30000)
		);
		
		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(new Jewel(quickLoad("jewel_green_32"), grid.getTile(6, 7), grid, 1));
		jewelList.add(new Jewel(quickLoad("jewel_red"), grid.getTile(6, 7), grid, 2));
		jewelList.add(new Jewel(quickLoad("jewel_blue"), grid.getTile(6, 7), grid, 3));
		jewelList.add(new Jewel(quickLoad("jewel_purple"), grid.getTile(6, 7), grid, 4));
		jewelList.add(new Jewel(quickLoad("jewel_yellow"), grid.getTile(6, 7), grid, 5));
		
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
	
	public Player[] getPlayers()
	{
		return players;
	}
	
	public void setPlayerKeys(int player, int index, int key)
	{
		if (players.length < player)
			System.err.println();
		else
			players[player - 1].setKey(index, key);
	}
	
	public void setGrid(TileGrid tg)
	{
		grid = tg;
		for (Player player : players)
			player.setGrid(tg);
		jewelSpawner.setGrid(tg);
		monsterSpawner.setGrid(tg);
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
