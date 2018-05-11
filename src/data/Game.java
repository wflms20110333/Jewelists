package data;

import static helpers.Artist.quickLoad;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

/**
 * The Game class represents the actual gameplay of the game.
 * 
 * @author Elizabeth Zou
 */
public class Game
{
	/**
	 * The 
	 */
	private TileGrid grid;	
	//Spawner monsterSpawner;
	Spawner jewelSpawner;
	Player player1;
	Player player2;
	
	public Game()
	{
		grid = new TileGrid();
		//Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		//monsterSpawner = new MonsterSpawner(10, e);
		int[] keys =
		{ Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		player1 = new Player(grid, keys, quickLoad("emoji"), TileType.Deposit2);
		player2 = new Player(grid, keys, quickLoad("emoji2"), TileType.Deposit1);

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
	}
	
	public Game(int[][] map)
	{
		grid = new TileGrid(map);
		//Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		//monsterSpawner = new MonsterSpawner(10, e);
		int[] keys =
		{ Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		player1 = new Player(grid, keys, quickLoad("emoji"), TileType.Deposit2);
		player2 = new Player(grid, keys, quickLoad("emoji2"), TileType.Deposit1);

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
	}
	
	public Game(TileGrid tg)
	{
		grid = tg;
		//Monster e = new Monster(quickLoad("monster_32"), grid.getTile(10, 10), grid, 5);
		//monsterSpawner = new MonsterSpawner(10, e);
		int[] keys =
		{ Keyboard.KEY_UP, Keyboard.KEY_LEFT, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT, Keyboard.KEY_RSHIFT,
				Keyboard.KEY_SEMICOLON, Keyboard.KEY_L, Keyboard.KEY_K, Keyboard.KEY_J };
		player1 = new Player(grid, keys, quickLoad("emoji"), TileType.Deposit2);
		player2 = new Player(grid, keys, quickLoad("emoji2"), TileType.Deposit1);

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
	}
	
	public void update()
	{
		grid.draw();
		//monsterSpawner.update();
		jewelSpawner.update();
		player1.update();
		player2.update();
	}
	
	public void setPlayer1Keys(int index, int key)
	{
		player1.setKey(index, key);
	}
	
	public void setPlayer2Keys(int index, int key)
	{
		player2.setKey(index, key);
	}
	
	public void setGrid(TileGrid tg)
	{
		grid = tg;
		player1.setGrid(tg);
		player2.setGrid(tg);
		jewelSpawner.setGrid(tg);
	}
}
