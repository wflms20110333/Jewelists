package data;

import static helpers.Artist.*;

/**
 * The TileGrid class represents the layout of the map, represented by square
 * cells formed by tiles.
 * 
 * @author Elizabeth Zou
 */
public class TileGrid
{
	/**
	 * The dimensions (in pixels) of each square cell in the TileGrid.
	 */
	public static final int SIZE = 32;
	
	public static final int MIDDLE_HALF_TILES = 4;

	/**
	 * The number of columns and rows in the TileGrid.
	 */
	public static final int COLS = WIDTH / SIZE;
	public static final int ROWS = HEIGHT / SIZE - Game.SCOREBOARD_HEIGHT_TILES;

	/**
	 * The number of cells in the TileGrid.
	 */
	//private static final int NUM_CELLS = COLS * ROWS;

	/**
	 * The tiles that form the TileGrid.
	 */
	private Tile[][] map;

	/**
	 * The entities currently occupying the cells of the TileGrid, null if the
	 * cell is unoccupied.
	 */
	private Entity[][] entities;

	/**
	 * The number of cells that are currently filled, either by a wall or an
	 * occupying static entity.
	 */
	//private int fillCount = 0;

	/**
	 * Constructs a TileGrid formed by cave tiles.
	 */
	public TileGrid()
	{
		map = new Tile[COLS][ROWS];
		for (int j = 0; j < Game.INFO_BAR_HEIGHT_TILES; j++)
			for (int i = Game.INFO_BAR_WIDTH_TILES; i < COLS - Game.INFO_BAR_WIDTH_TILES; i++)
				map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Cave);
		for (int j = Game.INFO_BAR_HEIGHT_TILES; j < ROWS; j++)
			for (int i = 0; i < COLS; i++)
				map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Cave);
		for (int j = 0; j < MIDDLE_HALF_TILES; j++) {
			for (int i = 0; i < MIDDLE_HALF_TILES - j; i++) {
				map[COLS / 2 + i][ROWS / 2 + j] = new Tile(COLS / 2 + i, ROWS / 2 + j, SIZE, SIZE, TileType.Dirt);
				map[COLS / 2 + i][ROWS / 2 - j - 1] = new Tile(COLS / 2 + i, ROWS / 2 - j - 1, SIZE, SIZE, TileType.Dirt);
				map[COLS / 2 - i - 1][ROWS / 2 + j] = new Tile(COLS / 2 - i - 1, ROWS / 2 + j, SIZE, SIZE, TileType.Dirt);
				map[COLS / 2 - i - 1][ROWS / 2 - j - 1] = new Tile(COLS / 2 - i - 1, ROWS / 2 - j - 1, SIZE, SIZE, TileType.Dirt);
			}
		}
		entities = new Entity[COLS][ROWS];
	}

	/**
	 * Constructs a TileGrid using a 2D integer array that represents the tile
	 * types of the cells.
	 * 
	 * @param newMap the 2D integer array that represents the tile types of the
	 *               cells, 0 representing a cave tile, 1 representing a wall tile,
	 *               2 representing player 1's deposit, and 3 representing player
	 *               2's deposit.
	 */
	/*
	public TileGrid(int[][] newMap)
	{
		map = new Tile[COLS][ROWS];
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				switch (newMap[j][i])
				{
				case 0:
					map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Cave);
					break;
				case 1:
					map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Wall);
					//fillCount++;
					break;
				case 2:
					map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Deposit1);
					//fillCount++;
					break;
				case 3:
					map[i][j] = new Tile(i, j, SIZE, SIZE, TileType.Deposit2);
					//fillCount++;
					break;
				}
			}
		}
		entities = new Entity[COLS][ROWS];
	}
	*/

	/**
	 * Draws the tile grid.
	 */
	public void draw()
	{
		for (Tile[] arr : map)
			for (Tile t : arr)
				if (t != null)
					t.draw();
	}

	/**
	 * Sets the type of a tile at a given cell in the tile grid.
	 * 
	 * @param xCoord the x index of the given cell
	 * @param yCoord the y index of the given cell
	 * @param type the new type of the new tile
	 */
	public void setTile(int xCoord, int yCoord, TileType type)
	{
		if (map[xCoord][yCoord].getType() == type)
			return;
		/*
		if (map[xCoord][yCoord].getType() == TileType.Cave)
			fillCount++;
		else if (type == TileType.Cave)
			fillCount--;
		*/
		map[xCoord][yCoord].setType(type);
	}
	
	public void setTile(Tile tile, TileType type)
	{
		if (map[tile.getIndX()][tile.getIndY()].getType() == type)
			return;
		/*
		if (map[tile.getIndX()][tile.getIndY()].getType() == TileType.Cave)
			fillCount++;
		else if (type == TileType.Cave)
			fillCount--;
		*/
		map[tile.getIndX()][tile.getIndY()].setType(type);
	}

	/**
	 * Returns the tile at a given cell in the tile grid.
	 * 
	 * @param xCoord the x index of the given cell
	 * @param yCoord the y index of the given cell
	 * @return the tile at the given cell in the tile grid
	 */
	public Tile getTile(int xCoord, int yCoord)
	{
		return map[xCoord][yCoord];
	}

	/**
	 * Places an entity into a given cell in the tile grid.
	 * 
	 * @param xCoord the x index of the given cell
	 * @param yCoord the y index of the given cell
	 * @param entity the entity to place into the tile grid
	 */
	public void setEntity(int xCoord, int yCoord, Entity entity)
	{
		entities[xCoord][yCoord] = entity;
		//fillCount++;
	}
	
	public void setEntity(Tile tile, Entity entity)
	{
		entities[tile.getIndX()][tile.getIndY()] = entity;
		//fillCount++;
	}

	/**
	 * Removes an entity from a given cell in the tile grid.
	 * 
	 * @param xCoord the x index of the given cell
	 * @param yCoord the y index of the given cell
	 */
	public void removeEntity(int xCoord, int yCoord)
	{
		entities[xCoord][yCoord] = null;
		//fillCount--;
	}
	
	public void removeEntity(Tile tile)
	{
		entities[tile.getIndX()][tile.getIndY()] = null;
		//fillCount--;
	}

	/**
	 * Returns the entity at a given cell in the tile grid, null if the cell is
	 * unoccupied.
	 * 
	 * @param xCoord the x index of the given cell
	 * @param yCoord the y index of the given cell
	 * @return the entity at the given cell in the tile grid, null if the cell
	 *         is unoccupied
	 */
	public Entity getEntity(int xCoord, int yCoord)
	{
		return entities[xCoord][yCoord];
	}
	
	/**
	 * Returns the entity at a given cell in the tile grid, null if the cell is
	 * unoccupied.
	 * 
	 * @param tile the tile that forms the given cell
	 * @return the entity at the given cell in the tile grid, null if the cell
	 *         is unoccupied
	 */
	public Entity getEntity(Tile tile)
	{
		return entities[tile.getIndX()][tile.getIndY()];
	}
	
	/**
	 * Returns the tile representing a random unoccupied cell in the tile grid,
	 * null if no such cell exists.
	 * 
	 * @return the tile representing a random unoccupied cell in the tile grid,
	 * 		   null if no such cell exists
	 */
	public Tile randEmptyTile()
	{
		//if (fillCount == NUM_CELLS)
			//return null;
		while (true)
		{
			int x = (int) (Math.random() * COLS);
			int y = (int) (Math.random() * ROWS);
			if (!validIndex(x, y))
				continue;
			Tile tile = getTile(x, y);
			if (tile.getType() == TileType.Cave && getEntity(x, y) == null)
				return tile;
		}
	}
	
	/**
	 * Returns whether the cell represented by the given indexes is within the
	 * bounds of the tile grid and of the tile type cave.
	 * 
	 * @param xCoord the x index of the cell
	 * @param yCoord the y index of the cell
	 * @return whether the cell represented by the given indexes is within the
	 * 		   bounds of the tile grid and of the tile type cave
	 */
	public boolean canEnter(int xCoord, int yCoord)
	{
		if (!validIndex(xCoord, yCoord))
			return false;
		return map[xCoord][yCoord].getType() == TileType.Cave || map[xCoord][yCoord].getType() == TileType.Dirt;
	}
	
	/**
	 * Returns the tile above a given tile, null if no such tile exists.
	 * 
	 * @param tile the given tile
	 * @return the tile above a given tile, null if no such tile exists
	 */
	public Tile up(Tile tile)
	{
		if (!validIndex(tile.getIndX(), tile.getIndY() - 1))
			return null;
		return map[tile.getIndX()][tile.getIndY() - 1];
	}
	
	/**
	 * Returns the tile to the left of a given tile, null if no such tile
	 * exists.
	 * 
	 * @param tile the given tile
	 * @return the tile to the left of a given tile, null if no such tile
	 * 		   exists
	 */
	public Tile left(Tile tile)
	{
		if (!validIndex(tile.getIndX() - 1, tile.getIndY()))
			return null;
		return map[tile.getIndX() - 1][tile.getIndY()];
	}
	
	/**
	 * Returns the tile below a given tile, null if no such tile exists.
	 * 
	 * @param tile the given tile
	 * @return the tile below a given tile, null if no such tile exists
	 */
	public Tile down(Tile tile)
	{
		if (!validIndex(tile.getIndX(), tile.getIndY() + 1))
			return null;
		return map[tile.getIndX()][tile.getIndY() + 1];
	}
	
	/**
	 * Returns the tile to the right of a given tile, null if no such tile
	 * exists.
	 * 
	 * @param tile the given tile
	 * @return the tile to the right of a given tile, null if no such tile
	 * 		   exists
	 */
	public Tile right(Tile tile)
	{
		if (!validIndex(tile.getIndX() + 1, tile.getIndY()))
			return null;
		return map[tile.getIndX() + 1][tile.getIndY()];
	}
	
	public boolean validIndex(int xCoord, int yCoord)
	{
		if (xCoord < 0 || yCoord < 0 || xCoord >= COLS || yCoord >= ROWS)
			return false;
		if (yCoord < Game.INFO_BAR_HEIGHT_TILES && (xCoord < Game.INFO_BAR_WIDTH_TILES ||
				xCoord >= COLS - Game.INFO_BAR_WIDTH_TILES))
			return false;
		return true;
	}
}
