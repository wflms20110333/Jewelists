package data;

/**
 * The MonsterSpawner class blah blah
 * 
 * @author Elizabeth Zou
 */

public class MonsterSpawner extends Spawner
{
	/**
	 * The template entity for spawning.
	 */
	private Entity entityType;
	
	/**
	 * Constructs a MonsterSpawner.
	 * 
	 * @param spawnTime the time interval between spawns
	 * @param entityType the template entity for spawning
	 */
	public MonsterSpawner(float spawnTime, Entity entityType)
	{
		super(spawnTime, entityType.getGrid());
		this.entityType = entityType;
	}
	
	/**
	 * Returns the template entity for spawning.
	 * 
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
		Tile tile = getGrid().randEmptyTile();
		if (tile != null)
			add(new Monster(getEntityType().getTexture(), tile, getGrid(), ((Monster) getEntityType()).getSpeed()));
	}
}
