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
	
	/**
	 * Half the number of tiles the middle area is wide.
	 */
	public static final int MIDDLE_HALF_TILES = 4;

	/**
	 * The number of columns and rows in the TileGrid.
	 */
	public static final int COLS = WIDTH / SIZE;
	public static final int ROWS = HEIGHT / SIZE - Game.SCOREBOARD_HEIGHT_TILES;
	
	/**
	 * Used for determining direction and position increments.
	 */
	public static final char[] order = {'U', 'L', 'D', 'R'};
	public static final int[] changeX = {0, -1, 0, 1};
	public static final int[] changeY = {-1, 0, 1, 0};

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
	 * Whether each cell is currently occupied by a moving entity.
	 */
	private Entity[][] occupied;

	/**
	 * Constructs a TileGrid formed by cave tiles, with an area in the center
	 * formed by dirt tiles.
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
		occupied = new Entity[COLS][ROWS];
	}
	
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
		if (!validIndex(xCoord, yCoord))
			return;
		if (map[xCoord][yCoord].getType() == type)
			return;
		map[xCoord][yCoord].setType(type);
	}
	
	/**
	 * Sets the type of a tile at a given cell in the tile grid.
	 * 
	 * @param tile the tile at the given cell
	 * @param type the new type of the new tile
	 */
	public void setTile(Tile tile, TileType type)
	{
		if (!validIndex(tile.getIndX(), tile.getIndY()))
			return;
		if (map[tile.getIndX()][tile.getIndY()].getType() == type)
			return;
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
	
	public Entity getMovingEntity(Tile tile)
	{
		return occupied[tile.getIndX()][tile.getIndY()];
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
	}
	
	/**
	 * Places an entity into a given cell in the tile grid.
	 * 
	 * @param tile the tile at the given cell
	 * @param entity the entity to place into the tile grid
	 */
	public void setEntity(Tile tile, Entity entity)
	{
		entities[tile.getIndX()][tile.getIndY()] = entity;
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
	}
	
	/**
	 * Removes an entity from a given cell in the tile grid.
	 * 
	 * @param tile the tile at the given cell
	 */
	public void removeEntity(Tile tile)
	{
		entities[tile.getIndX()][tile.getIndY()] = null;
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
	 * Sets the moving entity that occupies a given cell.
	 * 
	 * @param tile the tile that forms the given cell
	 * @param e the given moving entity
	 */
	public void setOccupied(Tile tile, Entity e)
	{
		occupied[tile.getIndX()][tile.getIndY()] = e;
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
		while (true)
		{
			int x = (int) (Math.random() * COLS);
			int y = (int) (Math.random() * ROWS);
			if (!validIndex(x, y))
				continue;
			Tile tile = getTile(x, y);
			if (tile.getType() == TileType.Cave && getEntity(x, y) == null && occupied[x][y] == null)
				return tile;
		}
	}
	
	/**
	 * Returns whether the cell represented by the given indexes is open for
	 * entry by a moving entity. A cell is open for entry if it is within the
	 * bounds of the tile grid, or the tile type cave or dirt, and not
	 * currently occupied by another moving entity.
	 * 
	 * @param xCoord the x index of the cell
	 * @param yCoord the y index of the cell
	 * @return whether the cell represented by the given indexes is open for
	 * 		   entry by a moving entity
	 */
	public boolean canEnter(int xCoord, int yCoord)
	{
		if (!validIndex(xCoord, yCoord))
			return false;
		if (occupied[xCoord][yCoord] != null)
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
	
	/**
	 * Returns whether a given position is valid. The position is valid if it
	 * is within the bounds of the tile grid and not part of an area covered by
	 * an info bar.
	 * 
	 * @param xCoord the x index of the given position
	 * @param yCoord the y index of the given position
	 * @return whether a given position is valid
	 */
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