package data;

import java.util.ArrayList;

/**
 * The JewelSpawner class blah blah
 * 
 * @author Elizabeth Zou
 */
public class JewelSpawner extends Spawner
{
	/**
	 * A list of template entities for spawning.
	 */
	private ArrayList<Entity> entityTypes;
	
	/**
	 * Constructs a JewelSpawner.
	 * 
	 * @param spawnTime the time interval between spawns
	 * @param grid the tile grid in which spawns take place
	 * @param entityType the template entities for spawning
	 */
	public JewelSpawner(float spawnTime, TileGrid grid, ArrayList<Entity> entityTypes)
	{
		super(spawnTime, grid);
		this.entityTypes = entityTypes;
	}
	
	/**
	 * Spawns a random jewel into a random empty tile, if such tile exists.
	 */
	@Override
	public void spawn()
	{
		int ind = (int) (Math.random() * entityTypes.size());
		Entity e = entityTypes.get(ind);
		Tile tile = getGrid().randEmptyTile();
		if (tile != null)
		{
			Jewel j = new Jewel(e.getTexture(), tile, getGrid(), ((Jewel) e).getValue());
			add(j);
			getGrid().setEntity(tile.getIndX(), tile.getIndY(), j);
		}
	}
}
