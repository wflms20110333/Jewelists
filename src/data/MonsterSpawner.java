package data;

import org.newdawn.slick.opengl.Texture;

/**
 * The MonsterSpawner class spawns monsters into the game.
 * 
 * @author Elizabeth Zou
 * @author Collin McMahon
 */
public class MonsterSpawner extends Spawner
{
	/**
	 * The maximum number of monsters allowed on the grid.
	 */
	private static final int MAX = 10;
	
	/**
	 * The texture of the monsters spawned.
	 */
	private Texture texture;
	
	/**
	 * The speed of the monsters spawned.
	 */
	private float speed;
	
	/**
	 * Constructs a MonsterSpawner.
	 * 
	 * @param spawnTime the time interval between spawns
	 * @param entityType the template entity for spawning
	 * @param texture the texture of the monsters spawned
	 * @param speed the speed of the monsters spawned
	 */
	public MonsterSpawner(float spawnTime, TileGrid grid, Texture texture, float speed)
	{
		super(spawnTime, grid);
		this.texture = texture;
		this.speed = speed;
	}
	
	/**
	 * Spawns a monster into a random empty tile, if the number of monsters
	 * currently on the grid has not exceeded MAX yet, and if such an empty
	 * tile exists.
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