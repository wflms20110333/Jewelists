package data;

public class MonsterSpawner extends Spawner
{
	private Entity entityType;
	
	public MonsterSpawner(float spawnTime, Entity entityType)
	{
		super(spawnTime, entityType.getGrid());
		this.entityType = entityType;
	}
	
	public Entity getEntityType()
	{
		return entityType;
	}
	
	@Override
	public void spawn()
	{
		while (true) {
			int x = (int) (Math.random() * TileGrid.COLS);
			int y = (int) (Math.random() * TileGrid.ROWS);
			Tile tile = getGrid().getTile(x, y);
			if (tile.getType() == TileType.Cave && getGrid().getEntity(x, y) == null) {
				add(new Monster(getEntityType().getTexture(), tile, getGrid(), 
						TileGrid.SIZE, TileGrid.SIZE, ((Monster) getEntityType()).getSpeed()));
				return;
			}
		}
	}
}
