package data;

import java.util.ArrayList;
import static helpers.Clock.*;

/**
 * The Spawner class is an abstract class that spawns specified entities after
 * a set time interval at random locations.
 * 
 * @author Elizabeth Zou
 */
public abstract class Spawner
{
	/**
	 * The time interval between spawns.
	 */
	private float spawnTime;
	
	/**
	 * The change in time since the last spawn.
	 */
	private float timeSinceLastSpawn;
	
	/**
	 * The list of entities currently managed by the Spawner.
	 */
	private ArrayList<Entity> entityList;
	
	/**
	 * The tile grid in which spawns take place.
	 */
	private TileGrid grid;
	
	/**
	 * Constructs a Spawner.
	 * 
	 * @param spawnTime time interval between spawns
	 * @param grid the tile grid in which spawns take place
	 */
	public Spawner(float spawnTime, TileGrid grid)
	{
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		entityList = new ArrayList<Entity>();
		this.grid = grid;
	}
	
	/**
	 * Spawns if the defined time interval has passed since the last spawn, and
	 * updates the state of and draws each entity managed onto the game display
	 * if the entity exists, or removes it otherwise.
	 */
	public void update()
	{
		timeSinceLastSpawn += getSeconds();
		if (timeSinceLastSpawn > spawnTime)
		{
			spawn();
			timeSinceLastSpawn = 0;
		}

		for (int i = entityList.size() - 1; i >= 0; i--)
		{
			Entity e = entityList.get(i);
			if (e.exists())
			{
				e.update();
				e.draw();
			}
			else
				entityList.remove(i);
		}
	}
	
	/**
	 * Adds an entity to be managed by the Spawner.
	 * 
	 * @param e the entity to be added
	 */
	public void add(Entity e)
	{
		entityList.add(e);
	}
	
	/**
	 * Removes a given entity.
	 * 
	 * @param e the given entity
	 */
	public void remove(Entity e)
	{
		entityList.remove(e);
	}
	
	/**
	 * Returns the tile grid in which spawns take place.
	 * 
	 * @return the tile grid in which spawns take place
	 */
	public TileGrid getGrid()
	{
		return grid;
	}
	
	/**
	 * Sets the tile grid in which spawns take place.
	 * 
	 * @param tg the new tile grid in which spawns take place
	 */
	public void setGrid(TileGrid tg)
	{
		grid = tg;
		for (Entity e : entityList)
			e.setGrid(tg);
	}
	
	/**
	 * Returns the number of entities currently managed by the Spawner.
	 * 
	 * @return the number of entities currently managed by the Spawner
	 */
	public int getNumSpawned()
	{
		return entityList.size();
	}
	
	/**
	 * Spawns a new entity into the tile grid.
	 */
	public abstract void spawn();
}
