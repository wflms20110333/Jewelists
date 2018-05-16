package data;

import static helpers.Artist.quickLoad;
import static helpers.Artist.drawString;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import UI.InfoBar;
import UI.UI;

import helpers.KeyboardHelper;

/**
 * The Game class represents the actual gameplay of the game.
 * 
 * @author Elizabeth Zou
 */
public class Game
{
<<<<<<< HEAD
	private TileGrid grid;
	
	//Spawner monsterSpawner;
	
=======
	/**
	 * The 
	 */
	private TileGrid grid;	
	Spawner monsterSpawner;
>>>>>>> master
	Spawner jewelSpawner;
	Player[] players;
	UI ui;
	
	//private KeyboardHelper keyboard;
	
	public Game()
	{
<<<<<<< HEAD
		this(new TileGrid());
=======
		grid = new TileGrid();
		Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		monsterSpawner = new MonsterSpawner(10, e);
		int[] keys1 = { Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		player1 = new Player(grid, keys1, quickLoad("emoji"), TileType.Deposit1, TileType.Deposit2);
		int[] keys2 = { Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT,
					Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T, Keyboard.KEY_Y };
		player2 = new Player(grid, keys2, quickLoad("emoji2"), TileType.Deposit2, TileType.Deposit1);

		Jewel j1 = new Jewel(quickLoad("jewel_green_32"), grid.getTile(0, 0), grid, 1);
		Jewel j2 = new Jewel(quickLoad("jewel_red"), grid.getTile(0, 0), grid, 2);
		Jewel j3 = new Jewel(quickLoad("jewel_blue"), grid.getTile(0, 0), grid, 3);
		Jewel j4 = new Jewel(quickLoad("jewel_purple"), grid.getTile(0, 0), grid, 4);
		Jewel j5 = new Jewel(quickLoad("jewel_yellow"), grid.getTile(0, 0), grid, 5);
		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(j1);
		jewelList.add(j2);
		jewelList.add(j3);
		jewelList.add(j4);
		jewelList.add(j5);
		jewelSpawner = new JewelSpawner(10, grid, jewelList);
		
		//keyboard = new KeyboardHelper();
>>>>>>> master
	}
	
	public Game(int[][] map)
	{
<<<<<<< HEAD
		this(new TileGrid(map));
=======
		grid = new TileGrid(map);
		Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		monsterSpawner = new MonsterSpawner(10, e);
		int[] keys1 = { Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		player1 = new Player(grid, keys1, quickLoad("emoji"), TileType.Deposit1, TileType.Deposit2);
		int[] keys2 = { Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT,
					Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T, Keyboard.KEY_Y };
		player2 = new Player(grid, keys2, quickLoad("emoji2"), TileType.Deposit2, TileType.Deposit1);

		Jewel j1 = new Jewel(quickLoad("jewel_green_32"), grid.getTile(0, 0), grid, 1);
		Jewel j2 = new Jewel(quickLoad("jewel_red"), grid.getTile(0, 0), grid, 2);
		Jewel j3 = new Jewel(quickLoad("jewel_blue"), grid.getTile(0, 0), grid, 3);
		Jewel j4 = new Jewel(quickLoad("jewel_purple"), grid.getTile(0, 0), grid, 4);
		Jewel j5 = new Jewel(quickLoad("jewel_yellow"), grid.getTile(0, 0), grid, 5);
		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(j1);
		jewelList.add(j2);
		jewelList.add(j3);
		jewelList.add(j4);
		jewelList.add(j5);
		jewelSpawner = new JewelSpawner(10, grid, jewelList);
		
		//keyboard = new KeyboardHelper();
>>>>>>> master
	}
	
	public Game(TileGrid tg)
	{
		grid = tg;
		Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		monsterSpawner = new MonsterSpawner(10, e);
		int[] keys1 = { Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
<<<<<<< HEAD
		
		players = new Player[] {
			new Player(grid, keys, quickLoad("emoji"), TileType.Deposit2, TileType.Deposit1),
			new Player(grid, keys, quickLoad("emoji2"), TileType.Deposit1, TileType.Deposit2)
		};
		
		ui = new UI();
		ui.addItem(new InfoBar(players[0], null, 0, 0, 200, 80));
=======
		player1 = new Player(grid, keys1, quickLoad("emoji"), TileType.Deposit1, TileType.Deposit2);
		int[] keys2 = { Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D, Keyboard.KEY_LSHIFT,
					Keyboard.KEY_E, Keyboard.KEY_R, Keyboard.KEY_T, Keyboard.KEY_Y };
		player2 = new Player(grid, keys2, quickLoad("emoji2"), TileType.Deposit2, TileType.Deposit1);
>>>>>>> master

		ArrayList<Entity> jewelList = new ArrayList<>();
		jewelList.add(new Jewel(quickLoad("jewel_green_32"), grid.getTile(0, 0), grid, 1));
		jewelList.add(new Jewel(quickLoad("jewel_red"), grid.getTile(0, 0), grid, 2));
		jewelList.add(new Jewel(quickLoad("jewel_blue"), grid.getTile(0, 0), grid, 3));
		jewelList.add(new Jewel(quickLoad("jewel_purple"), grid.getTile(0, 0), grid, 4));
		jewelList.add(new Jewel(quickLoad("jewel_yellow"), grid.getTile(0, 0), grid, 5));
		
		jewelSpawner = new JewelSpawner(10, grid, jewelList);
		
		//keyboard = new KeyboardHelper();
	}
	
	public void update()
	{
		grid.draw();
<<<<<<< HEAD
//		monsterSpawner.update();
=======
		monsterSpawner.update();
>>>>>>> master
		jewelSpawner.update();
		for (Player player : players)
			player.update();
		ui.draw();
	}
	
	public void setPlayerKeys(int player, int index, int key) {
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
}
