package data;

import java.util.ArrayList;

// change to iterate thru, grid, at any moment half (or probably a lot smaller) chance to spawn??
public class JewelSpawner extends Spawner
{
	private ArrayList<Entity> entityTypes;
	
	public JewelSpawner(float spawnTime, TileGrid grid, ArrayList<Entity> entityTypes)
	{
		super(spawnTime, grid);
		this.entityTypes = entityTypes;
	}

	@Override
	public void spawn()
	{
		int ind = (int) (Math.random() * entityTypes.size());
		Entity e = entityTypes.get(ind);
		while (true) {
			int x = (int) (Math.random() * TileGrid.COLS);
			int y = (int) (Math.random() * TileGrid.ROWS);
			Tile tile = getGrid().getTile(x, y);
			if (tile.getType() == TileType.Cave && getGrid().getEntity(x, y) == null) {
				Jewel j = new Jewel(e.getTexture(), tile, getGrid(), TileGrid.SIZE, TileGrid.SIZE);
				add(j);
				getGrid().setEntity(x, y, j);
				return;
			}
		}
	}
}
