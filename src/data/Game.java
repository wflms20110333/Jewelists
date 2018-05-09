package data;

public class Game
{
	private TileGrid grid;
	private Player player;

	public Game(int[][] map)
	{
		grid = new TileGrid(map);
		// player = new Player(grid)
	}
}
