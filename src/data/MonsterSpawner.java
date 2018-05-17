package data;

/**
 * The MonsterSpawner class blah blah
 * 
 * @author Elizabeth Zou
 */

public class MonsterSpawner extends Spawner
{
	public static final float MONSTER_DEFAULT_SPEED = 100;
	
	/**
	 * The maximum number of monsters allowed on the grid.
	 */
	private static final int MAX = 10;
	
	/**
	 * The template entity for spawning.
	 */
	private Entity entityType;
	
	/**
	 * Constructs a MonsterSpawner.
	 * 
	 * @param spawnTime the number of seconds between spawns
	 * @param entityType the template entity for spawning
	 */
	public MonsterSpawner(float spawnTime, Entity entityType)
	{
		super(spawnTime, entityType.getGrid());
		this.entityType = entityType;
	}
	
	/**
	 * @return the template entity for spawning
	 */
	public Entity getEntityType()
	{
		return entityType;
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
			add(new Monster(getEntityType().getTexture(), tile, getGrid(), MONSTER_DEFAULT_SPEED));
	}
}
