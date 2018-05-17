package data;

import org.newdawn.slick.opengl.Texture;

/**
 * The MonsterSpawner class blah blah
 * 
 * @author Elizabeth Zou
 */

public class MonsterSpawner extends Spawner
{
	/**
	 * The maximum number of monsters allowed on the grid.
	 */
	private static final int MAX = 10;
	
	private Texture texture;
	private float speed;
	
	/**
	 * Constructs a MonsterSpawner.
	 * 
	 * @param spawnTime the time interval between spawns
	 * @param entityType the template entity for spawning
	 */
	public MonsterSpawner(float spawnTime, TileGrid grid, Texture texture, float speed)
	{
		super(spawnTime, grid);
		this.texture = texture;
		this.speed = speed;
	}
	
	/**
	 * Spawns a monster into a random empty tile, if such tile exists.
	 */
	@Override
	public void spawn()
	{
		if (getNumSpawned() == MAX)
			return;
		Tile tile = getGrid().randEmptyTile();
		if (tile != null)
			add(new Monster(texture, tile, getGrid(), speed));
	}
}
