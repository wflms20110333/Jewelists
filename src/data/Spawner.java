package data;

import java.util.ArrayList;
import static helpers.Clock.*;

public abstract class Spawner
{
	private float timeSinceLastSpawn, spawnTime;
	private ArrayList<Entity> entityList;
	private TileGrid grid;
	
	public Spawner(float spawnTime, TileGrid grid)
	{
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		entityList = new ArrayList<Entity>();
		this.grid = grid;
	}
	
	public void update()
	{
		timeSinceLastSpawn += delta();
		if (timeSinceLastSpawn > spawnTime) {
			spawn();
			timeSinceLastSpawn = 0;
		}
		
		for (Entity e : entityList) {
			e.update();
			e.draw();
		}
	}
	
	public void add(Entity e)
	{
		entityList.add(e);
	}
	
	public void remove(Entity e)
	{
		entityList.remove(e);
	}
	
	public TileGrid getGrid()
	{
		return grid;
	}
	
	public abstract void spawn();
}
