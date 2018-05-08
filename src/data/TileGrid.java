package data;

import static helpers.Artist.*;
public class TileGrid
{
	public static final int SIZE = 32;
	public static final int COLS = WIDTH / SIZE, ROWS = HEIGHT / SIZE;
	public Tile[][] map;
	private Entity[][] entities;
	
	public TileGrid()
	{
		map = new Tile[COLS][ROWS];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				map[i][j] = new Tile(i * SIZE, j * SIZE, SIZE, SIZE, TileType.Cave);
		entities = new Entity[COLS][ROWS];
	}
	
	public TileGrid(int[][] newMap)
	{
		map = new Tile[COLS][ROWS];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (newMap[j][i])
				{
				case 0: map[i][j] = new Tile(i * SIZE, j * SIZE, SIZE, SIZE, TileType.Cave); break;
				case 1: map[i][j] = new Tile(i * SIZE, j * SIZE, SIZE, SIZE, TileType.Wall); break;
				case 2: map[i][j] = new Tile(i * SIZE, j * SIZE, SIZE, SIZE, TileType.Deposit1); break;
				case 3: map[i][j] = new Tile(i * SIZE, j * SIZE, SIZE, SIZE, TileType.Deposit2); break;
				}
			}
		}
		entities = new Entity[COLS][ROWS];
	}
	
	public void draw()
	{
		for (Tile[] arr : map)
			for (Tile t : arr)
				t.draw();
	}
	
	public void setTile(int xCoord, int yCoord, TileType type)
	{
		map[xCoord][yCoord] = new Tile(xCoord * SIZE, yCoord *  SIZE, SIZE, SIZE, type);
	}
	
	public Tile getTile(int xCoord, int yCoord)
	{
		return map[xCoord][yCoord];
	}
	
	public void setEntity(int xCoord, int yCoord, Entity entity)
	{
		entities[xCoord][yCoord] = entity;
	}
	
	public void removeEntity(int xCoord, int yCoord)
	{
		entities[xCoord][yCoord] = null;
	}
	
	public Entity getEntity(int xCoord, int yCoord)
	{
		return entities[xCoord][yCoord];
	}
}
